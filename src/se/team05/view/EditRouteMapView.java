package se.team05.view;

import se.team05.R;
import se.team05.overlay.CheckPointOverlay;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class EditRouteMapView extends MapView {
	
	private int minTimeForLongClick = 800;
	private long timerIfLongClick = 0;
	private float xScreenCoordinateForLongClick;
	private float yScreenCoordinateForLongClick;
	private float tolerance=10;//pixels that your finger can move but still be a long press
	private MapActivity mapActivity;
	private Drawable drawable = this.getResources().getDrawable(R.drawable.green_markerc);
    private CheckPointOverlay checkPointOverlay;
	private GestureDetector gestureDetector; 
    
    /**
     * 
     * @param context
     * @param attributes
     */
	public EditRouteMapView(Context context, AttributeSet attributes) {
		super(context, attributes);
		gestureDetector = new GestureDetector(context, (OnGestureListener) context);
		gestureDetector.setOnDoubleTapListener((OnDoubleTapListener) context);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		
		
//		switch (event.getAction())
//		{
//			case MotionEvent.ACTION_DOWN:
//				timerIfLongClick=event.getEventTime();
//	            xScreenCoordinateForLongClick=event.getX();
//	            yScreenCoordinateForLongClick=event.getY();
//				break;
//			case MotionEvent.ACTION_MOVE:
//                float dY = Math.abs(event.getY()-yScreenCoordinateForLongClick);
//                float dX = Math.abs(event.getX()-xScreenCoordinateForLongClick);
//                if(dY>tolerance || dX>tolerance){
//                    timerIfLongClick=0;
//                }
//				break;
//			case MotionEvent.ACTION_UP:
//				long eventTime = event.getEventTime();
//	            long downTime = event.getDownTime(); 
//	            if (timerIfLongClick==downTime){          
//	                if ((eventTime-timerIfLongClick)>minTimeForLongClick){ 
//	                    	GeoPoint p = this.getProjection().fromPixels(
//	                                (int) event.getX(),
//	                                (int) event.getY());
//	                                Toast.makeText(mapActivity.getBaseContext(),                             
//	                                p.getLatitudeE6() / 1E6 + "," + 
//	                                p.getLongitudeE6() /1E6 ,                             
//	                                Toast.LENGTH_SHORT).show();
//	                                setCheckPoint(p);
//	       	        }
//				break;
//	            }
//		}
		if(this.gestureDetector.onTouchEvent(event))
		{
			return true;
		}
		else
		{
			return super.onTouchEvent(event);
		}
	}

	
	public void setMapActivity(MapActivity mapActivity) {
		this.mapActivity = mapActivity;
		
	}
	

	

}
