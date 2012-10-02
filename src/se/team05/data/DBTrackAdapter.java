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

/**
 * This class is the track adapter class used to communicate with the "tracks"
 * table in the SQLite database. It contains information about the columns,
 * basic Create, Read, Update, Delete operations and also the initial
 * "create table" statement.
 * 
 * @author Daniel Kvist
 * 
 */
public class DBTrackAdapter extends DBAdapter
{
	public static final String TABLE_TRACKS = "tracks";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CID = "cid";
	public static final String COLUMN_ARTIST = "artist";
	public static final String COLUMN_ALBUM = "album";
	public static final String COLUMN_TITLE = "title";
	public static final String COLUMN_DATA = "data";
	public static final String COLUMN_DISPLAY_NAME = "display_name";
	public static final String COLUMN_DURATION = "duration";

	public static final String DATABASE_CREATE_TRACK_TABLE = "create table " + TABLE_TRACKS + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_CID + " integer not null," + COLUMN_ARTIST
			+ " text not null, " + COLUMN_ALBUM + " text not null," + COLUMN_TITLE + " text not null," + COLUMN_DATA
			+ " text not null," + COLUMN_DISPLAY_NAME + " text not null," + COLUMN_DURATION + " text not null);";


	/**
	 * The constructor of the class which creates a new instance of the database
	 * helper and stores it as an instance of the class
	 * 
	 * @param context
	 *            the context which to operate in
	 */
	public DBTrackAdapter(Context context)
	{
		super(context);
	}

	/**
	 * This method inserts a new track into the database.
	 * 
	 * @param checkPointId
	 * 
	 * @param artist
	 *            the artist name
	 * @param album
	 *            the album name
	 * @param title
	 *            the track title
	 * @param data
	 *            the path to the track
	 * @param displayName
	 *            the display name of the track
	 * @param duration
	 *            the duration of the track
	 */
	public void createTrack(int checkPointId, String artist, String album, String title, String data,
			String displayName, String duration)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_CID, checkPointId);
		values.put(COLUMN_ALBUM, artist);
		values.put(COLUMN_ALBUM, album);
		values.put(COLUMN_ALBUM, title);
		values.put(COLUMN_ALBUM, data);
		values.put(COLUMN_ALBUM, displayName);
		values.put(COLUMN_ALBUM, duration);
		db.insert(TABLE_TRACKS, null, values);
	}
}
