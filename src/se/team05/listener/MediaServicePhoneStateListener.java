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

package se.team05.listener;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * This is a listener that is used to handle incoming phonecalls and end of
 * phonecalls. It has a custom interface which is used to implement the
 * Callbacks pattern.
 * 
 * @author Daniel Kvist
 * 
 */
public class MediaServicePhoneStateListener extends PhoneStateListener
{
	/**
	 * An interface that the user of the listener should use to be able to
	 * recevice information about what events have occured.
	 * 
	 * @author Daniel Kvist
	 * 
	 */
	public interface Callbacks
	{
		/**
		 * Called when the telephone is starting to ring or is off the hook
		 */
		public void onRing();

		/**
		 * Called when the telephone has been hung up and the call is ended
		 */
		public void onIdle();
	}

	private Callbacks callback;
	private boolean isPaused = false;

	/**
	 * A simple constructor which only takes the calling class which must be
	 * implementing the Callbacks interface as a paramter.
	 * 
	 * @param callback
	 *            the callback class instance
	 */
	public MediaServicePhoneStateListener(Callbacks callback)
	{
		this.callback = callback;
	}

	/**
	 * Called by the system when the call state changes. It checks which state
	 * has been initiated and calls the onRing callback if the phone is ringing
	 * or off the hook and the onIdle callback if the method has been ended.
	 */
	@Override
	public void onCallStateChanged(int state, String incomingNumber)
	{
		switch (state)
		{
			case TelephonyManager.CALL_STATE_RINGING:
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if (!isPaused)
				{
					isPaused = true;
					callback.onRing();
				}
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if (isPaused)
				{
					callback.onIdle();
					isPaused = false;
				}
				break;
		}
	}
}
