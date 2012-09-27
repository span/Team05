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
*/
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
