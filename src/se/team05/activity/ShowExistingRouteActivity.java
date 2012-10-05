package se.team05.activity;

import se.team05.R;
import se.team05.view.EditRouteMapView;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.os.Bundle;

public class ShowExistingRouteActivity extends MapActivity {
	
	private EditRouteMapView mapView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_while_running_old);
        
        //mapView = (EditRouteMapView) findViewById(R.id.mapview2);
		//mapView.setBuiltInZoomControls(true);
        
        this.setTitle(String.valueOf(this.getIntent().getExtras().getLong("id")));
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
