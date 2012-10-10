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
	String id = "id";
	String artist = "artist";
	String album = "album";
	String title = "title";
	String data = "data";
	String displayName = "displayName";
	String duration = "duration";

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
