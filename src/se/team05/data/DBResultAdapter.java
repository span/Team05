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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This class is the result adapter class used to communicate with the "result"
 * table in the SQLite database. It contains information about the columns,
 * and basic Create, Read, Delete operations and also the initial
 * "create table" statement.
 * 
 * @author Daniel Kvist, Gustaf Werlinder
 * 
 */
public class DBResultAdapter extends DBAdapter {

	public static final String TABLE_RESULT = "result";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_RID = "rid";
	public static final String COLUMN_TIMESTAMP = "timestamp";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_DISTANCE = "distance";
	public static final String COLUMN_CALORIES = "calories";
	
	public static final String DATABASE_CREATE_RESULT_TABLE = "create table " + TABLE_RESULT
			+ "(" + COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_RID + " integer not null, "
			+ COLUMN_TIMESTAMP + " integer not null, "
			+ COLUMN_TIME + " integer not null, "
			+ COLUMN_DISTANCE + " integer not null, "
			+ COLUMN_CALORIES + " integer not null);";
		
	/**
	 * The constructor of the class which creates a new instance of the database
	 * helper and stores it as an instance of the class
	 * 
	 * @param context
	 *            the context which to operate in
	 */
	public DBResultAdapter(Context context) 
	{
		super(context);
	}
	
	/**
	 * Method inserts the result (generated while user following a route) in database table TABLE_RESULT
	 * @param rid
	 * 			the route id 
	 * @param date
	 * 			the date when results are generated
	 * @param time
	 * 			the time used to complete the route
	 * @param distance
	 * 			the distance of the route
	 * @param calories
	 * 			calories used while carrying out the route
	 * @return the id given to the result in the database
	 */
	public long instertResult (long rid, int date, int time, int distance, int calories)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_RID, rid);
		values.put(COLUMN_TIMESTAMP, date);
		values.put(COLUMN_TIME, time);
		values.put(COLUMN_DISTANCE, distance);
		values.put(COLUMN_CALORIES, calories);
		return db.insert(TABLE_RESULT, null, values);
	}
	
	/**
	 * Returns the cursor of the row of the result (that was generated when user followed a route).
	 * 
	 * @param id
	 * 		database id of the result
	 * @return Cursor to the result
	 */
	public Cursor fetchResultById(int id)
	{
		return db.query(TABLE_RESULT, null, COLUMN_ID + "=" + id, null, null, null, null);
	}
	
	/**
	 * Returns the cursors of the rows of all results corresponding to a route.
	 * 
	 * @param rid
	 * 				database route id
	 * @return Cursor to the result
	 */
	public Cursor fetchResultByRid(int rid)
	{
		return db.query(TABLE_RESULT, null, COLUMN_RID + "=" + rid, null, null, null, null);
	}
	
	/**
	 * Deletes the result with the given id in the database.
	 * 
	 * @param id
	 * 		the id of the specific result to be deleted
	 * @return the number of rows affected
	 */
	public int deleteResultById(int id)
	{
		return db.delete(TABLE_RESULT, COLUMN_ID + "=" + id, null);
	}
	
	/**
	 * Deletes all results that correspond with the route specified by id in the database.
	 * 
	 * @param rid
	 *            the rout id whose results shall be deleted
	 * @return the number of rows affected
	 */
	public int deleteResultByRid(int rid)
	{
		return db.delete(TABLE_RESULT, COLUMN_RID + "=" + rid, null);
	}

}
