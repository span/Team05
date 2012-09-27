package se.team05.content;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is a classic plain old java object which stores information about a
 * specific music track that the user has selected. The class implements the
 * Parcelable interface so that it can be easily transferred via intents when
 * the application is switching between activities.
 * 
 * @author Daniel Kvist
 * 
 */
public class Track implements Parcelable
{
	private String id;
	private String artist;
	private String title;
	private String data;
	private String displayName;
	private String duration;

	/**
	 * A constructor which takes all the necessary fields as Strings.
	 * 
	 * @param id
	 *            the id of the track
	 * @param artist
	 *            the artist of the track
	 * @param title
	 *            the title of the track
	 * @param data
	 *            the data file path
	 * @param displayName
	 *            the display name of the track
	 * @param duration
	 *            the duration of the track
	 */
	public Track(String id, String artist, String album, String title, String data, String displayName, String duration)
	{
		this.id = id;
		this.artist = artist;
		this.title = title;
		this.data = data;
		this.displayName = displayName;
		this.duration = duration;
	}

	/**
	 * Constructor for the parcelable interface that re-initiates the values
	 * 
	 * @param in
	 *            the Parcel that contains the data
	 */
	public Track(Parcel in)
	{
		String[] stringData = new String[6];
		in.readStringArray(stringData);

		this.id = stringData[0];
		this.artist = stringData[1];
		this.title = stringData[2];
		this.data = stringData[3];
		this.displayName = stringData[4];
		this.duration = stringData[5];
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return the artist
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return the data path
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * @return the duration
	 */
	public String getDuration()
	{
		return duration;
	}

	/**
	 * This is used be the parcelable interface to describe the object content.
	 */
	@Override
	public int describeContents()
	{
		return 0;
	}

	/**
	 * This is used by the parcelable interface to temporarily store data when
	 * being sent as a parcel.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeStringArray(new String[] { id, artist, title, data, displayName, duration });
	}

	/**
	 * Parcelable construction, this is called automatically by the system when needed.
	 */
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	{
		public Track createFromParcel(Parcel in)
		{
			return new Track(in);
		}

		public Track[] newArray(int size)
		{
			return new Track[size];
		}
	};
}
