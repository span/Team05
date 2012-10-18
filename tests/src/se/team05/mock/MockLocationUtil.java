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

public class MockLocationUtil
{
	public static final String PROVIDER_NAME = "testProvider";
	public static final String TAG = "testProvider";

	public static void publishMockLocation(double latitude, double longitude, Context context, ArrayList<CheckPoint> checkPoints, MapLocationListener locationListener)
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
}