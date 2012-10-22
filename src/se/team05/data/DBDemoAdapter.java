package se.team05.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import se.team05.content.Track;
import se.team05.dialog.AlertDialogFactory;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

public class DBDemoAdapter extends DBAdapter
{

	Context context;

	public DBDemoAdapter(Context context)
	{
		super(context);

		this.context = context;
	}

	public boolean loadSQLFile(String filename)
	{
		this.open();

		BufferedReader br = null;
		try
		{

			br = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)), 1024 * 4);
			String line = null;
			db.beginTransaction();
			while ((line = br.readLine()) != null)
			{
				db.execSQL(line);
			}
			db.setTransactionSuccessful();
		}
		catch (IOException e)
		{
			Log.e("test", "read database init file error");
			return false;
		}
		finally
		{
			db.endTransaction();
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					Log.e("test", "buffer reader close error");
					return false;
				}
			}
		}
		this.close();
		return true;
	}

	public void clearDB()
	{
		this.open();

		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
		db.delete(DBGeoPointAdapter.TABLE_GEOPOINTS, null, null);
		db.delete(DBCheckPointAdapter.TABLE_CHECKPOINTS, null, null);
		db.delete(DBResultAdapter.TABLE_RESULT, null, null);
		db.delete(DBTrackAdapter.TABLE_TRACKS, null, null);
		db.delete(DBResultAdapter.TABLE_RESULT, null, null);

		this.close();
	}

	/**
	 * Loads all the checkpoints from the database and loops through them to
	 * save a track from the media store with each checkpoint. If there is no media on
	 * the device we break out and show a warning to the user.
	 */
	public void initTracks()
	{
		DatabaseHandler databaseHandler = new DatabaseHandler(context);
		DBCheckPointAdapter dbCheckPointAdapter = new DBCheckPointAdapter(context);
		dbCheckPointAdapter.open();
		Cursor checkPointCursor = dbCheckPointAdapter.fetchAllCheckPoints();
		if (checkPointCursor != null && checkPointCursor.getCount() > 0)
		{
			Cursor trackCursor;
			checkPointCursor.moveToFirst();
			while (!checkPointCursor.isAfterLast())
			{
				trackCursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
						new String[] { MediaStore.Audio.Media.DATA }, null, null, MediaStore.Audio.Media.ALBUM);
				trackCursor.moveToFirst();
				if (trackCursor.getCount() > 0)
				{
					Track track = new Track("id", "artist", "album", "title", trackCursor.getString(0), "displayName", "duration");
					databaseHandler.saveTrack(checkPointCursor.getLong(0), track);
					trackCursor.close();
				}
				else
				{
					AlertDialogFactory.newAlertMessageDialog(context, "No media",
							"You have no media on your device, please transfer at least one music track and reboot your device");
					trackCursor.close();
					break;
				}
				checkPointCursor.moveToNext();
			}
		}
		checkPointCursor.close();
	}

}
