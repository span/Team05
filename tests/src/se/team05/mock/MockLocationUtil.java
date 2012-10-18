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

package se.team05.mock;

import java.util.ArrayList;

import se.team05.listener.MapLocationListener;
import se.team05.overlay.CheckPoint;
import android.app.Service;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;

/**
 * This class can publish and retrieve mock locations from an application that
 * has the allow mock location permission. It is inspired and dervived from this
 * blog post:
 * 
 * http://ballardhack.wordpress.com/2010/09/23/location-gps-and-automated-testing-on-android/
 * 
 * @author Daniel Kvist
 * 
 */
public class MockLocationUtil
{
	public static final String PROVIDER_NAME = "testProvider";
	public static final String TAG = "testProvider";

	/**
	 * Publishes a mock location with the given information to the application
	 * you wish to test.
	 * 
	 * @param latitude
	 *            the latitude of the location
	 * @param longitude
	 *            the longitude of the location
	 * @param context
	 *            the context to operate in
	 * @param checkPoints
	 *            the checkpoints that are needed by the listener in the tested
	 *            application
	 * @param locationListener
	 *            the listener which to listen with and test
	 */
	public static void publishMockLocation(double latitude, double longitude, Context context, ArrayList<CheckPoint> checkPoints,
			MapLocationListener locationListener)
	{
		LocationManager locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
		if (locationManager.getProvider(PROVIDER_NAME) != null)
		{
			locationManager.removeTestProvider(PROVIDER_NAME);
		}
		if (locationManager.getProvider(PROVIDER_NAME) == null)
		{
			locationManager.addTestProvider(PROVIDER_NAME, "requiresNetwork" == "", "requiresSatellite" == "", "requiresCell" == "",
					"hasMonetaryCost" == "", "supportsAltitude" == "", "supportsSpeed" == "", "supportsBearing" == "",
					android.location.Criteria.POWER_LOW, android.location.Criteria.ACCURACY_FINE);
		}

		Location newLocation = new Location(PROVIDER_NAME);
		newLocation.setLatitude(latitude);
		newLocation.setLongitude(longitude);
		newLocation.setTime(System.currentTimeMillis());
		newLocation.setAccuracy(25);

		locationManager.setTestProviderEnabled(PROVIDER_NAME, true);
		locationManager.setTestProviderStatus(PROVIDER_NAME, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
		locationManager.requestLocationUpdates(PROVIDER_NAME, 0, 0, locationListener);
		locationManager.setTestProviderLocation(PROVIDER_NAME, newLocation);
	}

	/**
	 * Gets the last known location from the application you are testing.
	 * @param context the context you are testing in
	 * @return the last known location
	 */
	public Location getLastKnownLocationInApplication(Context context)
	{
		Location testLocation = null;
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.getAllProviders().contains(PROVIDER_NAME))
		{
			testLocation = locationManager.getLastKnownLocation(PROVIDER_NAME);
		}

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);
		Location realLocation = locationManager.getLastKnownLocation(provider);

		if (testLocation != null)
		{
			return testLocation;
		}
		else
		{
			return realLocation;
		}
	}
}