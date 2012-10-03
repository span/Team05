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
import java.util.Arrays;
import java.util.List;

import se.team05.content.Result;
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
	private DBResultAdapter dbResultAdapter;

	public DatabaseHandler(Context context)
	{
		dBRouteAdapter = new DBRouteAdapter(context);
		dbTrackAdapter = new DBTrackAdapter(context);
		dbResultAdapter = new DBResultAdapter(context);
	}

	/**
	 * This method saves the route in the database.
	 * 
	 * @param route
	 *            the route to save
	 */
	public void saveRoute(Route route)
	{
		dBRouteAdapter.open();
		dBRouteAdapter.insertRoute(route.getName(), route.getDescription(), route.getType(), 0, 0);
		dBRouteAdapter.close();
	}

	/**
	 * Gets a Route from the database by the id provided.
	 * 
	 * @param id
	 * @return a Route if id is found, null otherwise
	 */
	public Route getRoute(int id)
	{
		// TODO Write this
		return null;
	}

	/**
	 * Get all routes from the database.
	 * 
	 * @return an array with Route objects
	 */
	public Route[] getAllRoutes()
	{
		dBRouteAdapter.open();
		Cursor cursor = dBRouteAdapter.getAllRoutes();
		List<Route> routeList = null;
		if (cursor != null)
		{
			Route route;
			cursor.moveToFirst();
			routeList = new ArrayList<Route>();

			while (!cursor.isAfterLast())
			{
				route = new Route(cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_ID)), cursor.getString(cursor
						.getColumnIndex(DBRouteAdapter.COLUMN_NAME)), cursor.getString(cursor
						.getColumnIndex(DBRouteAdapter.COLUMN_DESCRIPTION)), cursor.getInt(cursor
						.getColumnIndex(DBRouteAdapter.COLUMN_TYPE)),
						cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_TIMECOACH)) != 0, // Quick
																									// conversion
																									// to
																									// boolean
						cursor.getInt(cursor.getColumnIndex(DBRouteAdapter.COLUMN_LENGTHCOACH)) != 0);

				routeList.add(route);
				cursor.moveToNext();
			}
		}
		// TODO
		// dbResultAdapter.close(); ??? /guswer
		//
		return (Route[]) routeList.toArray();
	}

	/**
	 * Get the cursor with all routes in the database unformatted.
	 * 
	 * @return a cursor.
	 */
	public Cursor getAllRoutesCursor()
	{
		dBRouteAdapter.open();
		Cursor cursor = dBRouteAdapter.getAllRoutes();
		return cursor;
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
		dbTrackAdapter.insertTrack(cid, track.getArtist(), track.getAlbum(), track.getTitle(), track.getData(), track.getDisplayName(),
				track.getDuration());
		dbTrackAdapter.close();
	}

	/**
	 * Gets the tracks related to a specific checkpoint from the database.
	 * 
	 * @param cid
	 *            the checkpoint id that the tracks are related to
	 * @return an ArrayList of Track's
	 */
	public ArrayList<Track> getTracks(int cid)
	{
		ArrayList<Track> tracks = new ArrayList<Track>();
		dbTrackAdapter.open();
		Cursor cursor = dbTrackAdapter.fetchTrackByCid(cid);
		while (cursor.moveToNext())
		{
			tracks.add(new Track(cursor.getString(cursor.getColumnIndex(DBTrackAdapter.COLUMN_ID)), cursor.getString(cursor
					.getColumnIndex(DBTrackAdapter.COLUMN_ARTIST)), cursor.getString(cursor.getColumnIndex(DBTrackAdapter.COLUMN_ALBUM)),
					cursor.getString(cursor.getColumnIndex(DBTrackAdapter.COLUMN_TITLE)), cursor.getString(cursor
							.getColumnIndex(DBTrackAdapter.COLUMN_DATA)), cursor.getString(cursor
							.getColumnIndex(DBTrackAdapter.COLUMN_DISPLAY_NAME)), cursor.getString(cursor
							.getColumnIndex(DBTrackAdapter.COLUMN_DURATION))));
		}
		dbTrackAdapter.close();
		return tracks;
	}
	
	/**
	 * This method returns a result (an instance of class Result)
	 * retrieved from database via a database adapter.
	 * 
	 * @param id
	 * 		id tells which row to get from database.
	 * @return result
	 */
	public Result getResultById(int id)
	{
		Result result;
		
		dbResultAdapter.open();
		Cursor cursor = dbResultAdapter.fetchResultById(id);
		result = createResultFromCursor(cursor);
		dbResultAdapter.close();
		
		return result;
	}
	
	/**
	 * This method returns an array of results (instances of class Result)
	 * retrieved from database via a database adapter.
	 * 
	 * @param routId
	 * 		id tells which row to get from database.
	 * @return result
	 */
	public Result[] getAllResultsByRoutId(int routId)
	{
		List<Result> resultList = null;
		
		dbResultAdapter.open();
		Cursor cursor = dbResultAdapter.fetchResultById(routId);
		dbResultAdapter.close();		
		
		if (cursor != null)
		{
			resultList =  new ArrayList<Result>();
			Result result;
			cursor.moveToFirst();
			
			while(!cursor.isLast())
			{
				result = createResultFromCursor(cursor);
				resultList.add(result);
				cursor.moveToNext();
			}
		}		
		return (Result[]) resultList.toArray();
	}
	
	/**
	 * This help method will return a result made from values gotten through 
	 * a cursor given in parameter
	 * 
	 * @param cursor
	 * 		the cursor gives read/write access to a set retrieved from database.
	 * @return result retrieved from database.
	 */
	private Result createResultFromCursor(Cursor cursor)
	{
		//Cursor cursor = dbResultAdapter.fetchResultById(id);
		Result result = new Result(cursor.getInt(cursor.getColumnIndex(DBResultAdapter.COLUMN_ID)),
									cursor.getInt(cursor.getColumnIndex(DBResultAdapter.COLUMN_TIMESTAMP)),
									cursor.getInt(cursor.getColumnIndex(DBResultAdapter.COLUMN_TIME)),
									cursor.getInt(cursor.getColumnIndex(DBResultAdapter.COLUMN_SPEED)),
									cursor.getInt(cursor.getColumnIndex(DBResultAdapter.COLUMN_CALORIES)));
		return result;		
	}
	
	public void setResult(Result result)
	{
		dbResultAdapter.open();
		dbResultAdapter.instertResult(result.getRoutId(),
										result.getTimestamp(),
										result.getTime(),
										result.getSpeed(),
										result.getCalories());
		dbResultAdapter.close();
	}
	
	/**
	 * This method deletes a result from the database.
	 * 
	 * @param id
	 * 		id of result to be deleted.
	 */
	public void deleteResultById(int id)
	{
		dbResultAdapter.open();
		dbResultAdapter.deleteResultById(id);
		dbResultAdapter.close();
	}
	
	public void deleteAllResultsByRoutId(int routId)
	{		
		Result[] resultArray = getAllResultsByRoutId(routId);
		if (resultArray != null)
		{
			List<Result> resultList = Arrays.asList(resultArray);	
			dbResultAdapter.open();
			
			for(Result result : resultList)
			{
				dbResultAdapter.deleteResultById(result.get_id());
			}
			
			dbResultAdapter.close();
		}		
	}

}
