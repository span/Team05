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
	private String album;
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
	 * @param album
	 *            the album of the track
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
		this.album = album;
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
		String[] stringData = new String[7];
		in.readStringArray(stringData);

		this.id = stringData[0];
		this.artist = stringData[1];
		this.album = stringData[2];
		this.title = stringData[3];
		this.data = stringData[4];
		this.displayName = stringData[5];
		this.duration = stringData[6];
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return the artist name
	 */
	public String getArtist()
	{
		return artist;
	}
	
	/**
	 * @return the album name
	 */
	public String getAlbum()
	{
		return album;
	}

	/**
	 * @return the title of the track
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
	 * @return the duration of the track
	 */
	public String getDuration()
	{
		return duration;
	}
	
	// TODO Implement equals correctly
	public boolean equals(Object other)
	{
		Track t = (Track) other;
		return t.getData().equals(this.getData());
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
		dest.writeStringArray(new String[] { id, artist, album, title, data, displayName, duration });
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
