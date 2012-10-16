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

package se.team05.content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import se.team05.R;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

/**
 * This class manages playing of sounds from a sound pool and recording of new
 * sounds. This class should only be used for sounds that are less then 1MB in
 * size, for playing music the media player or a media service should be used.
 * 
 * @author Daniel Kvist
 * 
 */
public class SoundManager
{
	private static final String TAG = "SoundManager";
	private static final String ALBUM_ART_FILENAME = "album-art.png";

	private File storageDirectory;
	private SoundPool soundPool;
	private Context context;
	private MediaRecorder mediaRecorder;
	private File soundFile = null;
	private boolean recording = false;
	private int soundID;

	/**
	 * Simple constructor which just takes a context as a parameter. It can be
	 * used when all you want to do is to record a new sound.
	 * 
	 * @param context
	 *            the context which to operate in
	 */
	public SoundManager(Context context)
	{
		this(context, null, -1);
	}

	/**
	 * A constructor that is used when you want to play sounds from a sound
	 * pool. It stores the parameters as instance variables which are used in
	 * other methods and then goes on to create the default directory and album
	 * art.
	 * 
	 * @param context
	 *            the context which to operate in
	 * @param soundPool
	 *            the sound pool which contains the sounds
	 * @param soundID
	 *            the id of the sounds you wish to play
	 */
	public SoundManager(Context context, SoundPool soundPool, int soundID)
	{
		this.context = context;
		this.soundPool = soundPool;
		this.soundID = soundID;
		createFolder();
		createAlbumArt();
	}

	/**
	 * Creates the default directory to save all recordings in
	 */
	private void createFolder()
	{
		storageDirectory = new File(Environment.getExternalStorageDirectory(), "/Music/personal-trainer/");
		if (!storageDirectory.exists())
		{
			storageDirectory.mkdirs();
		}
	}

	/**
	 * Creates album art image from the launcher icon and saves it in the
	 * default directory
	 */
	private void createAlbumArt()
	{
		if (storageDirectory.exists())
		{
			AsyncTask<Void, Void, Void> bdt = new BitmapDecoderTask();
			bdt.execute();
		}
	}

	/**
	 * Checks if there is a recording taking place and returns true if it is and
	 * false otherwise.
	 * 
	 * @return true if recording, else false
	 */
	public boolean isRecording()
	{
		return recording;
	}

	/**
	 * Plays the sound given in the constructor and plays it at the volume given
	 * as a paramter. The volume ranges from 0 to 1.
	 * 
	 * @param volume
	 *            the volume as a 0 to 1 value
	 */
	public void play(float volume)
	{
		soundPool.play(soundID, volume, volume, 0, 0, 1);
	}

	/**
	 * Disposes of the sound currently set and clears up the memory that it was
	 * using by unloading it.
	 */
	public void dispose()
	{
		soundPool.unload(soundID);
	}

	/**
	 * Starts a recording of a new sound from the users microphone. After adding
	 * the sounds it lets the media store know about the new sound by
	 * broadcasting to its content resolver. It uses a temporary path to record
	 * into.
	 * 
	 * @throws IOException
	 */
	public void startRecording() throws IOException
	{
		recording = true;
		if (storageDirectory.exists())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String name = sdf.format(new Date(System.currentTimeMillis()));
			soundFile = new File(storageDirectory, "recording-" + name + ".mp3");
			mediaRecorder = new MediaRecorder();
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.setOutputFile(soundFile.getAbsolutePath());
			mediaRecorder.prepare();
			mediaRecorder.start();
		}
		else
		{
			Log.e(TAG, "Could not create directory " + storageDirectory);
		}
	}

	/**
	 * This must be called when the user wants to stop recording. It stops the
	 * media player and clears up the memory and then it saves the new sound to
	 * the sd-card.
	 */
	public void stopRecording()
	{
		if (mediaRecorder != null)
		{
			mediaRecorder.stop();
			mediaRecorder.release();
			saveToLibrary();
			recording = false;
		}
	}

	/**
	 * Saves the recording to the sd-card with the name
	 * "personal-trainer-[current time].mp3 and with recognizable titles, album
	 * and artist information to make the sound easy to find. It then sends a
	 * broadcast to the media store to let it know that a new sound is
	 * available. Finally a message is shown to the user via a toast message
	 * that lets the user know that a sound has been added.
	 */
	private void saveToLibrary()
	{
		ContentValues values = new ContentValues(7);
		values.put(MediaStore.Audio.Media.TITLE, soundFile.getName());
		values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (System.currentTimeMillis() / 1000));
		values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
		values.put(MediaStore.Audio.Media.DATA, soundFile.getAbsolutePath());
		values.put(MediaStore.Audio.Media.ARTIST, "Personal Trainer");
		values.put(MediaStore.Audio.Media.ALBUM, "Personal Trainer Recordings");
		values.put(MediaStore.Audio.Media.IS_MUSIC, true);

		ContentResolver contentResolver = context.getContentResolver();
		Uri mediaStoreUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Uri newUri = contentResolver.insert(mediaStoreUri, values);

		Cursor c = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.ALBUM_ID }, MediaStore.Audio.Media.ALBUM + "="
						+ "'Personal Trainer Recordings'", null, null);
		c.moveToFirst();
		int albumId = c.getInt(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

		final Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
		values = new ContentValues(2);
		values.put(MediaStore.Audio.Albums.ALBUM_ID, albumId);
		values.put(MediaStore.Audio.Albums.ALBUM_ART, soundFile.getParent() + ALBUM_ART_FILENAME);
		contentResolver.insert(artworkUri, values);

		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
		Toast.makeText(context, context.getString(R.string.added_new_recording_) + soundFile.getAbsolutePath(),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Private class to execute the bitmap decoding when first creating the
	 * album art.
	 */
	private class BitmapDecoderTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			File file = new File(storageDirectory, ALBUM_ART_FILENAME);
			if (!file.exists())
			{
				Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
				try
				{
					FileOutputStream outputStream = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
					outputStream.flush();
					outputStream.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return null;
		}
	}
}
