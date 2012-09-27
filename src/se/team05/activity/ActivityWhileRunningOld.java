package se.team05.activity;

import se.team05.R;
import se.team05.content.Routes;
import se.team05.overlay.RouteOverlay;
import android.os.Bundle;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class ActivityWhileRunningOld extends MapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_while_running_old);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        if(Routes.getInstance().getCount() > 0)
        {
        	RouteOverlay routeOverlay = new RouteOverlay(Routes.getInstance().getRoute(0), 78, true); 
        	mapView.getOverlays().add(routeOverlay);
        }
        else
        {
        	System.out.println("TEST: FÖR FÅ RUTTER SPARADE"+ Routes.getInstance().getCount());
        }
        
    }

    

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
