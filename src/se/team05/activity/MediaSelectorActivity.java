/**
	This file is part of Personal Trainer.

    Personal Trainer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    Personal Trainer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Personal Trainer.  If not, see <http://www.gnu.org/licenses/>.

    (C) Copyright 2012: Daniel Kvist, Henrik Hugo, Gustaf Werlinder, Patrik Thitusson, Markus Schutzer
 */

package se.team05.activity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import se.team05.R;
import se.team05.adapter.MediaSelectorAdapter;
import se.team05.content.Track;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;

/**
 * This activity displays a list of the available media on the device. It allows
 * selecting several items from the list and by selecting the "done" icon in the
 * options menu, the activity will return the results to the calling activity.
 * 
 * The list can be sorted via the options menu. The available sorting columns
 * are artist, title and album. By default the list is sorted by artist name.
 * 
 * The selection from the database consists of the _ID, ARTIST, ALBUM, TITLE,
 * DATA, DISPLAY_NAME and DURATION columns and is also limited to contain only
 * files that are markes as IS_MUSIC.
 * 
 * @author Daniel Kvist
 * 
 */
public class MediaSelectorActivity extends Activity implements LoaderCallbacks<Cursor>
{
	private static final int LOADER_ID_ARTIST = 0;
	private static final int LOADER_ID_ALBUM = 1;
	private static final int LOADER_ID_TITLE = 2;

	public static final String EXTRA_SELECTED_ITEMS = "selected_media";
	public static final int REQUEST_MEDIA = 0;
	private static final String TAG = "Album art decoder";

	private MediaSelectorAdapter adapter;
	private ListView listView;
	private LoaderManager loaderManager;

	private String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
	private String[] projection = { MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DISPLAY_NAME,
			MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ALBUM_ID };
	private ArrayList<Track> selectedItems;
	private HashMap<Long, Bitmap> albumArtMap;
	private ProgressDialog progressDialog;

	/**
	 * The onCreate method loads the xml layout which contains the listview. It
	 * also gets the loader manager and initiates a first load of available
	 * media and sorts it by artist name.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_selector);
		loaderManager = getLoaderManager();
		loaderManager.initLoader(LOADER_ID_ARTIST, null, this);
		listView = (ListView) findViewById(R.id.list);
		selectedItems = getIntent().getParcelableArrayListExtra(EXTRA_SELECTED_ITEMS);
		if (selectedItems == null)
		{
			selectedItems = new ArrayList<Track>();
		}
	}

	/**
	 * This method simply inflates the xml file which contains the menu options.
	 * The method i
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.media_selector_menu, menu);
		return true;
	}

	/**
	 * This is called when an option item has been selected. Depending on the
	 * user selection either the selected tracks are passed back to the calling
	 * activity or a new query is made to the media store to sort on either
	 * artist, album or title. TODO How format XML-string
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		selectedItems = adapter.getSelectedItems();
		switch (item.getItemId())
		{
			case R.id.done:
				Intent intent = new Intent();
				intent.putParcelableArrayListExtra(EXTRA_SELECTED_ITEMS, selectedItems);
				setResult(RESULT_OK, intent);
				finish();
				return true;
			case R.id.artist:
				loaderManager.initLoader(LOADER_ID_ARTIST, null, this);
				return true;
			case R.id.album:
				loaderManager.initLoader(LOADER_ID_ALBUM, null, this);
				return true;
			case R.id.track:
				loaderManager.initLoader(LOADER_ID_TITLE, null, this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Called when the cursor loader is first created. It decides which URI to
	 * query and which sorting order should be returned. The query also contains
	 * information about which columns we are interested in which selection we
	 * want.
	 */
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
	{
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setTitle("Loading media");
		progressDialog.setMessage("Please wait while we load the media on your device.");
		progressDialog.show();
		CursorLoader cursorLoader = null;
		switch (i)
		{
			case LOADER_ID_ARTIST:
				cursorLoader = new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
						MediaStore.Audio.Media.ARTIST);
				break;
			case LOADER_ID_ALBUM:
				cursorLoader = new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
						MediaStore.Audio.Media.ALBUM);
				break;
			case LOADER_ID_TITLE:
				cursorLoader = new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null,
						MediaStore.Audio.Media.TITLE);
				break;
		}
		return cursorLoader;
	}

	/**
	 * When the load has finished we create a new adapter of the cursor we
	 * receive from the media store content provider. The adapter is then set to
	 * the listvew. The adapter uses ARIST, ALBUM and TITLE to be displayed to
	 * the user.
	 */
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
	{
		progressDialog.dismiss();
		if (adapter == null)
		{
			albumArtMap = new HashMap<Long, Bitmap>();
			cursor.moveToFirst();

			while (!cursor.isAfterLast())
			{
				long albumId = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
				if (!albumArtMap.containsKey(albumId))
				{
					albumArtMap.put(albumId, buildBitmapFromUri(albumId));
				}
				cursor.moveToNext();
			}

			adapter = new MediaSelectorAdapter(getApplicationContext(), R.layout.activity_media_selector, cursor, new String[] {
					MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.TITLE }, new int[] { R.id.text_1,
					R.id.text_2, R.id.text_3 }, Adapter.NO_SELECTION, selectedItems, albumArtMap);
			listView.setAdapter(adapter);
		}
		else
		{
			adapter.swapCursor(cursor);
		}
	}

	/**
	 * Builds the album art bitmap from the specified URI if it exists.
	 * 
	 * @param albumId
	 * @return the decoded bitmap
	 */
	private Bitmap buildBitmapFromUri(long albumId)
	{
		Bitmap bitmap = null;
		try
		{
			Uri albumArtURI = Uri.parse("content://media/external/audio/albumart");
			Uri uri = ContentUris.withAppendedId(albumArtURI, albumId);
			ContentResolver resolver = getContentResolver();
			InputStream inputStream = resolver.openInputStream(uri);
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		catch (FileNotFoundException e)
		{
			Log.d(TAG, e.getMessage());
		}
		return bitmap;
	}

	/**
	 * WHen the loader is reset we just pass in null as the cursor to the
	 * adapter.
	 */
	public void onLoaderReset(Loader<Cursor> cursorLoader)
	{
		adapter.swapCursor(null);
	}
}
