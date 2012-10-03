package se.team05.view;

import se.team05.listener.MapOnGestureListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.android.maps.MapView;

public class EditRouteMapView extends MapView
{

	private GestureDetector gestureDetector;
	private Context context;

	/**
	 * 
	 * @param context
	 * @param attributes
	 */
	public EditRouteMapView(Context context, AttributeSet attributes)
	{
		super(context, attributes);
		this.context = context;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (this.gestureDetector.onTouchEvent(event))
		{
			return true;
		} else
		{
			return super.onTouchEvent(event);
		}
	}

	public void setOnGestureListener(MapOnGestureListener mapOnGestureListener)
	{
		gestureDetector = new GestureDetector(context, mapOnGestureListener);
		gestureDetector.setOnDoubleTapListener(mapOnGestureListener);
	}
}
