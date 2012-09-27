package se.team05.listener;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MediaServicePhoneStateListener extends PhoneStateListener
{
	public interface Callbacks
	{
		public void onRing();
		public void onIdle();
	}
	
	private Callbacks callback;
	private boolean isPaused = false;
	
	public MediaServicePhoneStateListener(Callbacks callback)
	{
		this.callback = callback;
	}
	
	@Override
	public void onCallStateChanged(int state, String incomingNumber)
	{
		switch (state)
		{
			case TelephonyManager.CALL_STATE_RINGING:
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if(!isPaused)
				{
					isPaused = true;
					callback.onRing();
				}
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if(isPaused)
				{
					callback.onIdle();
					isPaused = false;
				}
				break;
		}
	}
}
