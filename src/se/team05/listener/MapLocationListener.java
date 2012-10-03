package se.team05.listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MapLocationListener implements LocationListener
{
	public interface Callbacks
	{
		public void updateLocation(Location location);
	}

	private Callbacks callback;

	public MapLocationListener(Callbacks callback)
	{
		this.callback = callback;
	}

	@Override
	public void onLocationChanged(Location location)
	{
		callback.updateLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub

	}

}
