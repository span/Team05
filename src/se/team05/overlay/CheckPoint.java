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

package se.team05.overlay;

import java.util.ArrayList;
import java.util.Collections;

import se.team05.content.ParcelableGeoPoint;
import se.team05.content.Track;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.OverlayItem;

/**
 * The Checkpoint class in which we can set different attributes and contains a
 * geopoint
 * 
 * @author Patrik Thituson
 * @version 1.0
 */
public class CheckPoint extends OverlayItem implements Parcelable
{

	private long _id;
	private long rid;
	private String name;
	private int radius;
	private ParcelableGeoPoint geoPoint;
	private ArrayList<Track> tracks;

	/**
	 * The default Constructor which initiates the route id to -1
	 * 
	 * @param geoPoint
	 */
	public CheckPoint(ParcelableGeoPoint geoPoint)
	{
		this(geoPoint, "CheckPoint", 30, -1);
	}

	/**
	 * 
	 * @param geoPoint
	 * @param name
	 * @param radius
	 * @param rid
	 */
	private CheckPoint(ParcelableGeoPoint geoPoint, String name, int radius, long rid)
	{
		super(geoPoint, "", "");
		this.setName(name);
		this.setRadius(radius);
		this.setGeoPoint(geoPoint);
		this.setRid(rid);
		tracks = new ArrayList<Track>();
	}

	/**
	 * Sets the geopoint of the checkpoint
	 * 
	 * @param geoPoint
	 *            the geo point to set
	 */
	private void setGeoPoint(ParcelableGeoPoint geoPoint)
	{
		this.geoPoint = geoPoint;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the radius of wich the checkpoint should be activated
	 */
	public int getRadius()
	{
		return radius;
	}

	/**
	 * @param radius
	 *            the radius of wich the checkpoint should be activated
	 */
	public void setRadius(int radius)
	{
		this.radius = radius;
	}

	/**
	 * @return the checkpoint id
	 */
	public long getId()
	{
		return _id;
	}

	/**
	 * @param id
	 *            the checkpoint id to set
	 */
	public void setId(long id)
	{
		this._id = id;
	}

	/**
	 * @return the rid (route id)
	 */
	public long getRid()
	{
		return rid;
	}

	/**
	 * @param rid
	 *            the route id to set
	 */
	public void setRid(long rid)
	{
		this.rid = rid;
	}

	/**
	 * 
	 * @return latitude of checkpoint
	 */
	public int getLatuitude()
	{
		return geoPoint.getLatitudeE6();
	}

	/**
	 * 
	 * @return longitude of checkpoint
	 */
	public int getLongitude()
	{
		return geoPoint.getLongitudeE6();
	}

	/**
	 * Adds all the tracks of the selected tracks to the checkpoint
	 * 
	 * @param selectedTracks
	 *            the tracks that have been selected
	 */
	public void addTracks(ArrayList<Track> selectedTracks)
	{
		tracks.addAll(selectedTracks);
	}

	/**
	 * Gets the tracks of the checkpoint
	 * 
	 * @return an arraylist with the selected tracks
	 */
	public ArrayList<Track> getTracks()
	{
		return tracks;
	}

	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(geoPoint, flags);
		dest.writeString(name);
		dest.writeInt(radius);
		dest.writeLong(rid);
		dest.writeLong(_id);
		dest.writeParcelableArray((Track[]) tracks.toArray(), flags);
	}

	/**
	 * Parcelable construction, this is called automatically by the system when
	 * needed.
	 */
	public static final Parcelable.Creator<CheckPoint> CREATOR = new Parcelable.Creator<CheckPoint>()
	{

		public CheckPoint createFromParcel(Parcel in)
		{
			return new CheckPoint(in);
		}

		public CheckPoint[] newArray(int size)
		{
			return new CheckPoint[size];
		}
	};

	/**
	 * Constructor for the parcelable interface that re-initiates the values
	 * 
	 * @param in
	 *            the Parcel that contains the data
	 */
	private CheckPoint(Parcel in)
	{
		this((ParcelableGeoPoint) in.readParcelable(ParcelableGeoPoint.class.getClassLoader()), 
								  in.readString(), 
								  in.readInt(),
								  in.readLong());
		this.setId(in.readLong());
		ArrayList<Track> tracks = new ArrayList<Track>();
		Track[] arrayTracks = (Track[])in.readParcelableArray(Track.class.getClassLoader());
		Collections.addAll(tracks, arrayTracks);
		this.addTracks(tracks);
	}
}
