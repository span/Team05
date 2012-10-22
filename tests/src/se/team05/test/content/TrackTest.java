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
package se.team05.test.content;

import se.team05.content.Track;
import android.os.Parcel;
import android.test.AndroidTestCase;

/**
 * This class tests the basic functionality of the Track POJO
 * 
 * @author Daniel Kvist
 * 
 */
public class TrackTest extends AndroidTestCase
{
	private String id = "id";
	private String artist = "artist";
	private String album = "album";
	private String title = "title";
	private String data = "data";
	private String displayName = "displayName";
	private String duration = "duration";
	private Track track;

	/**
	 * Constructor that calls super
	 */
	public TrackTest()
	{
		super();
	}

	/**
	 * Sets up a default track to use for testing
	 */
	public void setUp()
	{
		track = new Track(id, artist, album, title, data, displayName, duration);
	}

	/**
	 * Tests all the getters by comparing them to our default track
	 */
	public void testGetters()
	{
		assertEquals(id, track.getId());
		assertEquals(artist, track.getArtist());
		assertEquals(album, track.getAlbum());
		assertEquals(title, track.getTitle());
		assertEquals(data, track.getData());
		assertEquals(displayName, track.getDisplayName());
		assertEquals(duration, track.getDuration());
	}

	/**
	 * Tests the parcelable creation interface by writing a track to a parcel
	 * and the creating a new track to make sure they are equal.
	 */
	public void testParcelableCreation()
	{
		Parcel parcel = Parcel.obtain();
		track.writeToParcel(parcel, 0);
		parcel.setDataPosition(0);

		Track createFromParcel = (Track) Track.CREATOR.createFromParcel(parcel);
		assertEquals(track.getData(), createFromParcel.getData());
	}

	/**
	 * Just sets the track to null to allow the gc to take it
	 */
	public void tearDown()
	{
		track = null;
	}
}
