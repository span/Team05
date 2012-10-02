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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class handles creation, updating and deletion of the database. It uses
 * the adapter classes constants for table creation. This class also defines the
 * database version number and its filename.
 * 
 * @author Daniel Kvist, Gustaf Werlinder, Henrik Hugo, Markus Schutzer, Patrik
 *         Thitusson
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;

	/**
	 * Simple constructor calling the super class and passing on the context,
	 * name and version.
	 * 
	 * @param context
	 *            the context to operate in
	 */
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Called by the system when the database is created. All table create
	 * statements and other database initialization of data goes in here. At the
	 * moment it only contains table creation data.
	 */
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DBRouteAdapter.DATABASE_CREATE_ROUTE_TABLE);
		database.execSQL(DBTrackAdapter.DATABASE_CREATE_TRACK_TABLE);
	}

	/**
	 * Called by the system when the database version is upgraded. It drops all
	 * old tables and creates then anew.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DBRouteAdapter.TABLE_ROUTES);
		db.execSQL("DROP TABLE IF EXISTS " + DBTrackAdapter.TABLE_TRACKS);
		onCreate(db);
	}

}