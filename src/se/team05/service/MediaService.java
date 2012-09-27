package se.team05.service;

import java.io.IOException;
import java.util.ArrayList;

import se.team05.R;
import se.team05.activity.UseExistingRouteActivity;
import se.team05.listener.MediaServicePhoneStateListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


public class MediaService extends Service implements OnCompletionListener, OnPreparedListener, OnErrorListener, MediaServicePhoneStateListener.Callbacks
{
	public static final String ACTION_PLAY = "com.example.maptest.action.PLAY";
	public static final String DATA_PLAYLIST = "com.example.maptest.data.PLAYLIST";

	private MediaPlayer mediaPlayer;
	private ArrayList<String> playList;
	private PhoneStateListener phoneStateListener;
	private TelephonyManager telephonyManager;

	private static final int NOTIFICATION_ID = 1;

	@Override
	public void onCreate()
	{
		mediaPlayer = new MediaPlayer();
	}

	public int onStartCommand(Intent intent, int flags, int startId)
	{
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		phoneStateListener = new MediaServicePhoneStateListener(this);
		
		initNotification();
		
		playList = intent.getStringArrayListExtra(DATA_PLAYLIST);
		if (intent.getAction().equals(ACTION_PLAY))
		{
			if (!mediaPlayer.isPlaying())
			{
				try
				{
					mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(playList.get(0)));
					mediaPlayer.setOnCompletionListener(this);
					mediaPlayer.setOnErrorListener(this);
					mediaPlayer.setOnPreparedListener(this);
					mediaPlayer.prepareAsync();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return START_STICKY;
	}

	private void initNotification()
	{
		Context context = getApplicationContext();
		Intent notificationIntent = new Intent(context, UseExistingRouteActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
		        0, notificationIntent,
		        PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationManager nm = (NotificationManager) context
		        .getSystemService(Context.NOTIFICATION_SERVICE);

		Resources res = context.getResources();
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		
		builder.setContentIntent(contentIntent)
		            .setSmallIcon(R.drawable.ic_launcher)
		            .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
		            .setTicker("My ticker string")
		            .setWhen(System.currentTimeMillis())
		            .setOngoing(true)
		            .setContentTitle("My title")
		            .setContentText("My content text");
		Notification n = builder.getNotification();

		nm.notify(NOTIFICATION_ID, n);

	}

	private void cancelNotification()
	{
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(NOTIFICATION_ID);
	}

	@Override
	public void onPrepared(MediaPlayer player)
	{
		telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		playMedia();
	}
	
	@Override
	public void onRing()
	{
		pauseMedia();
	}

	@Override
	public void onIdle()
	{
		playMedia();
	}

	public void playMedia()
	{
		if (!mediaPlayer.isPlaying())
		{
			mediaPlayer.start();
		}
	}

	public void pauseMedia()
	{
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.pause();
		}

	}

	public void stopMedia()
	{
		if (mediaPlayer.isPlaying())
		{
			mediaPlayer.stop();
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra)
	{
		// ... react appropriately ...
		// The MediaPlayer has moved to the Error state, must be reset!
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp)
	{
		stopMedia();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (mediaPlayer != null)
		{
			stopMedia();
			mediaPlayer.release();
		}
		
		if(phoneStateListener != null)
		{
			telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
		}
		
		cancelNotification();
	}

}
