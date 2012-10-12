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

import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

/**
 * The MapOnGestureListener class is a OnGestureListener and a
 * OnDoubleTapListener and listen after single tap's and double tap's to call
 * the onTap method in callback with different event types
 * 
 * @author Patrik Thituson
 * 
 */
public class MapOnGestureListener implements OnGestureListener, OnDoubleTapListener
{
	public static final int EVENT_DOUBLE_TAP = 0;
	public static final int EVENT_SINGLE_TAP = 1;

	public interface Callbacks
	{
		public void onTap(int x, int y, int eventType);
	}

	private Callbacks callback;

	/**
	 * The Constructor
	 * 
	 * @param callback
	 */
	public MapOnGestureListener(Callbacks callback)
	{
		this.callback = callback;
	}

	/**
	 * Detects double tap«s and sends the coordinates and event type DOUBLE_TAP
	 * to the callback«s onTap method
	 */
	@Override
	public boolean onDoubleTap(MotionEvent event)
	{
		callback.onTap((int) event.getX(), (int) event.getY(), EVENT_DOUBLE_TAP);
		return true;
	}
	
	/**
	 * Unused method
	 */
	@Override
	public boolean onDoubleTapEvent(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Detects single tap«s and sends the coordinates and event type SINGLE_TAP
	 * to the callback«s onTap method
	 */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent event)
	{
		callback.onTap((int) event.getX(), (int) event.getY(), EVENT_SINGLE_TAP);
		return true;
	}
	
	/**
	 * Unused method
	 */
	@Override
	public boolean onDown(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Unused method
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Unused method
	 */
	@Override
	public void onLongPress(MotionEvent e)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Unused method
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Unused method
	 */
	@Override
	public void onShowPress(MotionEvent e)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Unused method
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent event)
	{
		return false;
	}

}
