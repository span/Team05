package se.team05.listener;

import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class MapOnGestureListener implements OnGestureListener, OnDoubleTapListener
{
	public static final int EVENT_DOUBLE_TAP = 0;
	public static final int EVENT_SINGLE_TAP = 1;
	
	public interface Callbacks
	{
		public void onTap(int x, int y, int eventType);
	}

	private Callbacks callback;
	
	public MapOnGestureListener(Callbacks callback)
	{
		this.callback = callback;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event)
	{
		callback.onTap((int) event.getX(), (int) event.getY(), EVENT_DOUBLE_TAP);
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event)
	{
		callback.onTap((int) event.getX(), (int) event.getY(), EVENT_SINGLE_TAP);
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent event)
	{
		return false;
	}

}
