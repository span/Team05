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

/**
 * This is an abstract class that works as a base for all the table adapter
 * classes. It handles fetching the writable database, open and closing of the
 * database. Any table adapter should extend this class to get that
 * functionality for free.
 * 
 * @author Daniel
 * 
 */
public abstract class DBAdapter
{
	protected Database database;
	protected SQLiteDatabase db;

	/**
	 * The constructor of the class which creates a new instance of the database
	 * helper and stores it as an instance of the class
	 * 
	 * @param context
	 *            the context which to operate in
	 */
	public DBAdapter(Context context)
	{
		database = new Database(context);
	}

	/**
	 * Opens the database and stores the actual writable database object as an
	 * instance in the class. You must remember to call close() after you have
	 * finished the database operations to prevent memory leaks.
	 */
	public void open()
	{
		db = database.getWritableDatabase();
	}

	/**
	 * This method must be called after the database operations have been
	 * completed to prevent memory leaks.
	 */
	public void close()
	{
		database.close();
	}
}
