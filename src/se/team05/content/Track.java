package se.team05.content;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable
{
	private String id;
	private String artist;
	private String title;
	private String data;
	private String displayName;
	private String duration;

	/**
	 * 
	 * @param id
	 * @param artist
	 * @param title
	 * @param data
	 * @param displayName
	 * @param duration
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
	 * @return the data
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

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeStringArray(new String[] { id, artist, title, data, displayName, duration });
	}

	/**
	 * Parcelable construction
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
