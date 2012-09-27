package se.team05.content;


import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

public class Routes 
{
	private ArrayList<ArrayList<GeoPoint>> routes;
	private static Routes instance;

	
	public static synchronized Routes getInstance()
	{
		if(instance == null)
		{
			instance = new Routes();
		}
		
		return instance;
	}

	
	private Routes()
	{
		routes = new ArrayList<ArrayList<GeoPoint>>();
	}
		
	
	public boolean addNewRoute(ArrayList<GeoPoint> aRoute, int RouteId)
	{
		routes.add(aRoute);
		return true;
	}
	
	
	
	public List<GeoPoint> getRoute(int idOfRoute)
	{
		return routes.get(idOfRoute);
	}
	
	public int getCount()
	{
		return routes.size();
	}
		
}
