package se.team05.util;

import se.team05.R;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class Utils
{
	public interface Callbacks
	{
		public void onTimerTick();
	}
	private static final String TAG = "Personal trainer";
	private static Runnable runnable;
	private static Handler handler;
	private static WakeLock wakeLock;
	
	/**
	 * Acquires the wake lock from the system if it is available and not already
	 * held.
	 */
	public static WakeLock acquireWakeLock(Context context)
	{
		try
		{
			PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			if (wakeLock == null)
			{
				wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
			}
			if (!wakeLock.isHeld())
			{
				wakeLock.acquire();
			}
		}
		catch (RuntimeException e)
		{
			Log.e(TAG, context.getString(R.string.could_not_acquire_wakelock_), e);
		}
		return wakeLock;
	}

	/**
	 * Releases the wake lock if available and held
	 */
	public static WakeLock releaseWakeLock()
	{
		if (wakeLock != null && wakeLock.isHeld())
		{
			wakeLock.release();
			wakeLock = null;
		}
		return wakeLock;
	}

	/**
	 * Starts the timer that is used to let the user know for how long they have
	 * been using the route. After initializing, this method will be called once
	 * every second
	 */
	public static void startTimer(final Callbacks callbacks)
	{
		runnable = new Runnable()
		{
			@Override
			public void run()
			{
				callbacks.onTimerTick();
				handler.postDelayed(this, 1000);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 0);
	}

	public static void stopTimer()
	{
		handler.removeCallbacks(runnable);
	}
}
