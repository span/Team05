package se.team05.test.util;

import se.team05.activity.RouteActivity;
import se.team05.listener.MapLocationListener;
import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

public class MockLocationUtil
{
	public static final String PROVIDER_NAME = "testProvider";
	public static final String TAG = "testProvider";

	public static void publishMockLocation(double latitude, double longitude, Context context)
	{
		LocationManager mLocationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
		if (mLocationManager.getProvider(PROVIDER_NAME) != null)
		{
			mLocationManager.removeTestProvider(PROVIDER_NAME);
		}
		if (mLocationManager.getProvider(PROVIDER_NAME) == null)
		{
			mLocationManager.addTestProvider(PROVIDER_NAME, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "",
					"hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "",
					android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE);
		}

		Location newLocation = new Location(PROVIDER_NAME);
		newLocation.setLatitude(latitude);
		newLocation.setLongitude(longitude);
		newLocation.setTime(System.currentTimeMillis());
		newLocation.setAccuracy(25);

		mLocationManager.setTestProviderEnabled(PROVIDER_NAME, true);
		mLocationManager.setTestProviderStatus(PROVIDER_NAME, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
		mLocationManager.requestLocationUpdates(PROVIDER_NAME, 0, 0, new MapLocationListener((RouteActivity) context));
		mLocationManager.setTestProviderLocation(PROVIDER_NAME, newLocation);
	}

	public Location getLastKnownLocationInApplication(Context ctx)
	{
		Location testLoc = null;
		LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

		if (lm.getAllProviders().contains(PROVIDER_NAME))
		{
			testLoc = lm.getLastKnownLocation(PROVIDER_NAME);
		}

		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(crit, true);
		Location realLoc = lm.getLastKnownLocation(provider);

		if (testLoc != null)
		{
			return testLoc;
		}
		else
		{
			return realLoc;
		}
	}

	private static class MockLocationListener implements LocationListener
	{
		public void onLocationChanged(Location location)
		{
		}

		public void onProviderDisabled(String provider)
		{
		}

		public void onProviderEnabled(String provider)
		{
		}

		public void onStatusChanged(String provider, int status, Bundle extras)
		{
		}
	}
}