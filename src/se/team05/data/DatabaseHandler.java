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
package se.team05.data;

import java.util.ArrayList;
import java.util.List;

import se.team05.content.Route;
import se.team05.content.Track;
import android.content.Context;
import android.database.Cursor;

/**
 * This class handles the communication between the database, its adapters and
 * the rest of the application. It keeps an instance of each table adapter that
 * is used for quick and easy access to the database.
 * 
 * @author Daniel Kvist, Gustaf Werlinder, Markus Schutzer, Patrik Thitusson,
 *         Henrik Hugo
 * 
 */
public class DatabaseHandler
{

	private DBRouteAdapter dBRouteAdapter;
	private DBTrackAdapter dbTrackAdapter;

	public DatabaseHandler(Context context)
	{
		dBRouteAdapter = new DBRouteAdapter(context);
		dbTrackAdapter = new DBTrackAdapter(context);
	}

	public void saveRoute()
	{
		dBRouteAdapter.open();
		dBRouteAdapter.createRoute("", "", 0, 0, 0);
		dBRouteAdapter.close();
	}

	/**
	 * Saves a track into the database and relates it to a checkpoint through
	 * the checkpoint id
	 * 
	 * @param cid
	 *            the id of the checkpoint to relate the track to
	 * @param track
	 *            the track to save
	 */
	public void saveTrack(int cid, Track track)
	{
		dbTrackAdapter.open();
		dbTrackAdapter.insertTrack(cid, track.getArtist(), track.getAlbum(), track.getTitle(), track.getData(),
				track.getDisplayName(), track.getDuration());
		dbTrackAdapter.close();
	}
	
	/**
	 * Gets a Route from the database by the id provided.
	 * @param id
	 * @return a Route if id is found, null otherwise
	 */
	public Route getRoute(int id)
	{
		// TODO Write this
		return null;
	}
	
	public Route[] getAllRoutes()
	{
		dBRouteAdapter.open();
		Cursor cursor = dBRouteAdapter.getAllRoutes();
		List<Route> routeList = null;
		if(cursor != null)
		{
			Route route;
			cursor.moveToFirst();
			routeList = new ArrayList<Route>();
			
			while(!cursor.isAfterLast())
			{
				route = new Route(
						cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_ID)),
						cursor.getString(cursor.getColumnIndex(DBRouteAdapter.COLUMN_NAME)),
						cursor.getString(cursor.getColumnIndex(DBRouteAdapter.COLUMN_DESCRIPTION)),
						cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_TYPE)),
						cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_TIMECOACH)) != 0, //Quick conversion to boolean
						cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_LENGTHCOACH)) != 0
				);
				
				routeList.add(route);
				cursor.moveToNext();
			}
		}
		
		return (Route[]) routeList.toArray();
	}
	
	public Cursor getAllRoutesCursor()
	{
		dBRouteAdapter.open();
		Cursor cursor = dBRouteAdapter.getAllRoutes();
		return cursor;
	}

}
