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

package se.team05.view;

import se.team05.listener.MapOnGestureListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.android.maps.MapView;

/**
 * The EditRouteMapView class is a subclass from mapView with a overwritten
 * onTouchEvent method which is used for putting checkpoints anywhere on the
 * map.
 * 
 * @author Patrik Thituson
 * @version 1.0
 */
public class EditRouteMapView extends MapView
{

	private GestureDetector gestureDetector;
	private Context context;

	/**
	 * The Constructor
	 * 
	 * @param context
	 * @param attributes
	 */
	public EditRouteMapView(Context context, AttributeSet attributes)
	{
		super(context, attributes);
		this.context = context;
	}

	/**
	 * The on touch event uses a gestureDetector for its events, the
	 * gestureDetector can detect single tap and double tap
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (gestureDetector!=null&&this.gestureDetector.onTouchEvent(event))
		{
			return true;
		} else
		{
			return super.onTouchEvent(event);
		}
	}

	/**
	 * Sets the GestureDetector with mapOnGestureListener so it can handle the
	 * events this method must be called after creation of the class or it«s
	 * functionality will not work
	 * 
	 * @param mapOnGestureListener
	 */
	public void setOnGestureListener(MapOnGestureListener mapOnGestureListener)
	{
		gestureDetector = new GestureDetector(context, mapOnGestureListener);
		gestureDetector.setOnDoubleTapListener(mapOnGestureListener);
	}
}
