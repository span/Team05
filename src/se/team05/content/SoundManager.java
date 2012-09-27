package se.team05.content;

import java.io.File;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class SoundManager
{
	private static final String TAG = "SoundManager";

	private SoundPool soundPool;
	private Context context;
	private MediaRecorder mediaRecorder;
	private File soundFile = null;
	
	private int soundID;
	
	public SoundManager(Context context)
	{
		this.context = context;
	}

	public SoundManager(Context context, SoundPool soundPool, int soundID)
	{
		this.context = context;
		this.soundPool = soundPool;
		this.soundID = soundID;
	}

	public void play(float volume)
	{
		soundPool.play(soundID, volume, volume, 0, 0, 1);
	}

	public void dispose()
	{
		soundPool.unload(soundID);
	}

	public void startRecording() throws IOException
	{
		File storageDirectory = Environment.getExternalStorageDirectory();
		try
		{
			soundFile = File.createTempFile("personal-sound", ".mp3", storageDirectory);
		}
		catch (IOException e)
		{
			Log.e(TAG, "Could not access sd-card");
			return;
		}
		
		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mediaRecorder.setOutputFile(soundFile.getAbsolutePath());
		mediaRecorder.prepare();
		mediaRecorder.start();
	}

	public void stopRecording()
	{
		mediaRecorder.stop();
		mediaRecorder.release();
		saveToLibrary();
	}

	private void saveToLibrary()
	{
		ContentValues values = new ContentValues(4);
		long currentTime = System.currentTimeMillis();
		values.put(MediaStore.Audio.Media.TITLE, "audio-" + soundFile.getName());
		values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (currentTime / 1000));
		values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3");
		values.put(MediaStore.Audio.Media.DATA, soundFile.getAbsolutePath());

		ContentResolver contentResolver = context.getContentResolver();
		Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Uri newUri = contentResolver.insert(base, values);
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));

		Toast.makeText(context, "Added new sound: " + soundFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
	}

}
