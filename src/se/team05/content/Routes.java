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

/**
 * A singleton class for storing routes to be accessed from different activities. This is now represented as a list
 * As development goes on this class might be made obsolete and removed.
 * @author Markus
 *
 */
public class Routes 
{
	private ArrayList<ArrayList<GeoPoint>> routes;
	private static Routes instance;

	
	/**
	 * Singleton constructor
	 * @return
	 */
	public static synchronized Routes getInstance()
	{
		if(instance == null)
		{
			instance = new Routes();
		}
		
		return instance;
	}

	/**
	 * Private part of constructor
	 */
	private Routes()
	{
		routes = new ArrayList<ArrayList<GeoPoint>>();
	}
		
	/**
	 * Method for adding a new route to the list with an identifier
	 * 
	 * @param aRoute A list of geopoints representing a route
	 * @param RouteId The identifying number of the route
	 * @return true
	 */
	public boolean addNewRoute(ArrayList<GeoPoint> aRoute, int RouteId)
	{
		routes.add(aRoute);
		return true;
	}
	
	
	/**
	 * Get method for a route saved in the list
	 * 
	 * @param idOfRoute an int representing the id-tag of the route
	 * @return the route, a list of geopoints
	 */
	public List<GeoPoint> getRoute(int idOfRoute)
	{
		return routes.get(idOfRoute);
	}
	
	/**
	 * Get the number of routes saved
	 * @return integer representing the number of saved routes
	 */
	public int getCount()
	{
		return routes.size();
	}
		
}
