package se.team05.activity;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class NewRouteActivity extends MapActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}

}
