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
package se.team05.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This class is the track adapter class used to communicate with the "tracks"
 * table in the SQLite database. It contains information about the columns,
 * basic Create, Read, Delete operations and also the initial
 * "create table" statement.
 * 
 * @author Daniel Kvist
 * 
 */
public class DBTrackAdapter extends DBAdapter
{
	public static final String TABLE_TRACKS = "tracks";
	public static final String COLUMN_ID = "_id";
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
	 * @param cid
	 *            the checkpoint id
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
	 * @return the id of the inserted of
	 */
	public long insertTrack(long cid, String artist, String album, String title, String data, String displayName,
			String duration)
	{
		ContentValues values = new ContentValues();
		values.put(COLUMN_CID, cid);
		values.put(COLUMN_ARTIST, artist);
		values.put(COLUMN_ALBUM, album);
		values.put(COLUMN_TITLE, title);
		values.put(COLUMN_DATA, data);
		values.put(COLUMN_DISPLAY_NAME, displayName);
		values.put(COLUMN_DURATION, duration);
		return db.insert(TABLE_TRACKS, null, values);
	}

	/**
	 * Fetches a track from the database with the corresponding id.
	 * 
	 * @param id
	 *            the id of the track
	 * @return a Cursor pointing to the result set
	 */
	public Cursor fetchTrackById(long id)
	{
		return db.query(TABLE_TRACKS, null, COLUMN_ID + "=" + id, null, null, null, null);
	}

	/**
	 * Fetches all tracks from the database with the corresponding cid.
	 * 
	 * @param cid
	 *            the checkpoint id for the tracks
	 * @return a Cursor pointing to the result set
	 */
	public Cursor fetchTrackByCid(long cid)
	{
		return db.query(TABLE_TRACKS, null, COLUMN_CID + "=" + cid, null, null, null, COLUMN_ID + " asc");
	}

	/**
	 * Deletes a single track with the corresponding id from the database.
	 * 
	 * @param id
	 *            the id of the track
	 * @return the number of rows affected
	 */
	public int deleteTrackById(long id)
	{
		return db.delete(TABLE_TRACKS, COLUMN_ID + "=" + id, null);
	}

	/**
	 * Deletes all tracks with the corresponding checkpoint id from the database.
	 * 
	 * @param cid
	 *            the checkpoint id for the tracks
	 * @return the number of rows affected
	 */
	public int deleteTrackByCid(long cid)
	{
		return db.delete(TABLE_TRACKS, COLUMN_CID + "=" + cid, null);
	}

}
