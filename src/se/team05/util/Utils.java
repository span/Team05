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
package se.team05.util;

import se.team05.R;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

/**
 * This utility class is used to perform system services such as acquiring and
 * releasing the wake lock and also to start a timer that updates a callback
 * through the Callbacks interface.
 * 
 * @author Daniel Kvist
 * 
 */
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

	/**
	 * Stops the timer
	 */
	public static void stopTimer()
	{
		handler.removeCallbacks(runnable);
	}
}
