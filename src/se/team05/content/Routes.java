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
