package se.team05;

import java.util.ArrayList;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MainActivity extends MapActivity implements LocationListener {

    private LocationManager locationManager;
    private String provider;
    private ArrayList<GeoPoint> path;
    private RouteOverlay routeOverlay;
    private MyLocationOverlay myOverlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        path = new ArrayList<GeoPoint>();
        
        // Example points to be removed
        path.add(new GeoPoint((int) (57.714443 * 1E6), (int) (11.944649 * 1E6)));
        path.add(new GeoPoint((int) (57.720517 * 1E6), (int) (11.945293 * 1E6)));
        path.add(new GeoPoint((int) (57.720403 * 1E6), (int) (11.955721 * 1E6)));
        path.add(new GeoPoint((int) (57.713962 * 1E6), (int) (11.945336 * 1E6)));
      
        myOverlay = new MyLocationOverlay(this, mapView);
        routeOverlay = new RouteOverlay(path);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null)
        {
            // TODO Remove toast before release
            Toast.makeText(this, "Provider " + provider + " has been selected.", Toast.LENGTH_SHORT).show();
            onLocationChanged(location);
        }
        
        mapView.getOverlays().add(routeOverlay);
        mapView.getOverlays().add(myOverlay);
        mapView.postInvalidate();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        myOverlay.enableMyLocation();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        myOverlay.disableMyLocation();
        locationManager.removeUpdates(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        int lat = (int) (location.getLatitude() * 1E6);
        int lng = (int) (location.getLongitude() * 1E6);
        GeoPoint gp = new GeoPoint(lat, lng);
        path.add(gp);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
    }
}
