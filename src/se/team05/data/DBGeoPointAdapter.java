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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.android.maps.GeoPoint;

/**
 * This class is the geopoint adapter class used to communicate with the
 * "geopoints" table in the SQLite database. It contains information about the
 * columns, basic Create, Read, Delete operations and also the initial
 * "create table" statement.
 * 
 * @author Daniel Kvist
 * 
 */
public class DBGeoPointAdapter extends DBAdapter
{
	public static final String TABLE_GEOPOINTS = "geopoints";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_RID = "rid";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";

	public static final String DATABASE_CREATE_GEOPOINT_TABLE = "create table " + TABLE_GEOPOINTS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_RID + " integer not null," + COLUMN_LATITUDE + " integer not null, "
			+ COLUMN_LONGITUDE + " integer not null);";

	/**
	 * The constructor of the class which creates a new instance of the database
	 * helper and stores it as an instance of the class
	 * 
	 * @param context
	 *            the context which to operate in
	 */
	public DBGeoPointAdapter(Context context)
	{
		super(context);
	}

	/**
	 * 
	 * @param rid
	 * @param geoPointList
	 */
	public void insertGeoPoints(long rid, ArrayList<GeoPoint> geoPointList)
	{
		ContentValues values = new ContentValues();
		db.beginTransaction();
		for(GeoPoint geoPoint : geoPointList)
		{
			values.put(COLUMN_RID, rid);
			values.put(COLUMN_LATITUDE, geoPoint.getLatitudeE6());
			values.put(COLUMN_LONGITUDE, geoPoint.getLongitudeE6());
			db.insert(TABLE_GEOPOINTS, null, values);
			values.clear();
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	/**
	 * Fetches all geopoints from the database with the corresponding route
	 * id.
	 * 
	 * @param rid
	 *            the route id for the checkpoint
	 * @return a Cursor pointing to the result set
	 */
	public Cursor fetchGeoPointByRid(long rid)
	{
		return db.query(TABLE_GEOPOINTS, null, COLUMN_RID + "=" + rid, null, null, null, COLUMN_ID + " asc");
	}

	/**
	 * Deletes all geopoints with the corresponding route id from the
	 * database.
	 * 
	 * @param rid
	 *            the route id for the checkpoint
	 * @return the number of rows affected
	 */
	public int deleteGeoPointByRid(long rid)
	{
		return db.delete(TABLE_GEOPOINTS, COLUMN_RID + "=" + rid, null);
	}
}
