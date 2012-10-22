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
package se.team05.content;

import java.util.ArrayList;

import se.team05.overlay.CheckPoint;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class contains all data related to a single route plus all the geopoints
 * and checkpoints associated with the route. It also provides several getters
 * and setters for the various instance variables. It simply acts as a model in
 * the mvc structure.
 * 
 * @author Henrik Hugo, Daniel Kvist, Patrik Thituson, Gustaf Werlinder
 * 
 */
public class Route implements Parcelable
{
	public static String EXTRA_ID = "rid";
	private long _id;
	private String name;
	private String description;
	private int type;
	private int timecoach;
	private int lengthcoach;
	private int timePassed;
	private int calories;
	private float totalDistance;
	private boolean started;
	
	private ArrayList<ParcelableGeoPoint> geoPoints;
	private ArrayList<CheckPoint> checkPoints;
	private CalorieCounter calorieCounter;

	/**
	 * Constructor for a route
	 * 
	 * @param name
	 * @param description
	 * @param context
	 */
	public Route(String name, String description, Context context)
	{
		this(name, description, 0, -1, -1, context);
	}

	/**
	 * Constructor for a route
	 * 
	 * @param name
	 * @param description
	 * @param type
	 * @param timecoach
	 * @param lengthcoach
	 */
	public Route(String name, String description, int type, int timecoach, int lengthcoach, Context context)
	{
		this(-1, name, description, type, timecoach, lengthcoach, context);
	}

	/**
	 * Constructor for a route
	 * 
	 * @param _id
	 * @param name
	 * @param description
	 * @param type
	 * @param timecoach
	 * @param lengthcoach
	 */
	public Route(long _id, String name, String description, int type, int timecoach, int lengthcoach, Context context)
	{
		this._id = _id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.timecoach = timecoach;
		this.lengthcoach = lengthcoach;
		this.totalDistance = 0;
		this.timePassed = 0;
		this.started = false;
		this.geoPoints = new ArrayList<ParcelableGeoPoint>();
		this.checkPoints = new ArrayList<CheckPoint>();
		this.calorieCounter = new CalorieCounter(context);
	}

	/**
	 * Get string representation of the route. This is equal to the name
	 * of the route and is NOT unique.
	 * 
	 * @return string representation of route
	 */
	public String toString()
	{
		return name;
	}

	/**
	 * A unique id of the route that is normally set when saving a new
	 * route in a database or other datasource.
	 * 
	 * @return id of the route
	 */
	public long getId()
	{
		return _id;
	}

	/**
	 * The id should be unique and is normally received from the db when
	 * saving a new route. Be careful when setting this as the id is
	 * unique.
	 * @param id must be unique
	 */
	public void setId(long id)
	{
		this._id = id;
	}

	/**
	 * Name of the route is a human redable string representation of
	 * the route that is NOT unique.
	 * 
	 * @return non-unique string representation
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Name of the route should be a human readable representation
	 * of the route and does not have to be unique.
	 * 
	 * @param non-unique string representation
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * A description of the route is optional and may be empty.
	 * 
	 * @return a longer description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Set a descriptive string of the route.
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Check what type of route this is.
	 * 
	 * @return integer representation of what type the route is
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * Set type of route.
	 * 
	 * @param integer representation of what type the route is
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * Check if timecoach is enabled.
	 * 
	 * @return true if timecoach is enabled
	 */
	public int isTimecoach()
	{
		return timecoach;
	}

	/**
	 * Set whether timecoach should be enabled or not.
	 * @param true to enable timecoach
	 */
	public void setTimecoach(int timecoach)
	{
		this.timecoach = timecoach;
	}

	/**
	 * Check if lengthcoach is enabled.
	 * 
	 * @return true if lenthcoach is enabled
	 */
	public int isLengthcoach()
	{
		return lengthcoach;
	}

	/**
	 * Set whether lengthcoach should be enabled or not.
	 * 
	 * @param true to enabled lengthcoach
	 */
	public void setLengthcoach(int lengthcoach)
	{
		this.lengthcoach = lengthcoach;
	}

	/**
	 * Sets the geo points that are related to this route.
	 * 
	 * @param geoPoints2
	 */
	public void setGeoPoints(ArrayList<ParcelableGeoPoint> geoPoints)
	{
		this.geoPoints = geoPoints;
	}

	/**
	 * Gets the geo points related to this route.
	 * 
	 * @return a list of geo points
	 */
	public ArrayList<ParcelableGeoPoint> getGeoPoints()
	{
		return geoPoints;
	}

	/**
	 * Sets the checkpoint list of a route
	 * 
	 * @param checkPoints
	 *            the checkpoints to relate to the route
	 */
	public void setCheckPoints(ArrayList<CheckPoint> checkPoints)
	{
		this.checkPoints = checkPoints;
	}

	/**
	 * Gets the checkpoints of this route
	 * 
	 * @return a list of checkpoints
	 */
	public ArrayList<CheckPoint> getCheckPoints()
	{
		return checkPoints;
	}

	/**
	 * Sets the total distance of the route. This is updated continiously when a
	 * new route is being recorded.
	 * 
	 * @param totalDistance
	 *            the total distance to set
	 */
	public void setTotalDistance(float totalDistance, Context context)
	{
		if(calorieCounter == null)
		{
			calorieCounter = new CalorieCounter(context);
		}
		this.totalDistance = totalDistance;
		calories = calorieCounter.updateCalories(totalDistance);
	}

	/**
	 * Get the total distance
	 * 
	 * @return the total distance
	 */
	public float getTotalDistance()
	{
		return totalDistance;
	}

	/**
	 * Sets the time passed of the route. This is updated continiously when a
	 * new route is being recorded.
	 * 
	 * @param timePassed
	 *            the time passed since starting the route
	 */
	public void setTimePassed(int timePassed)
	{
		this.timePassed = timePassed;
	}

	/**
	 * Get the time passed.
	 * 
	 * @return the time passed since starting the route
	 */
	public int getTimePassed()
	{
		return timePassed;
	}

	/**
	 * Convert the minutes and seconds to a string
	 * 
	 * @return a string of the time passed formatted as MM:SS
	 */
	public String getTimePassedAsString()
	{
		int timePassed = getTimePassed();
		int seconds = timePassed % 60;
		int minutes = timePassed / 60;
		return String.format("%02d:%02d", minutes, seconds);
	}

	/**
	 * Sets the started flag in the route so that we can keep track of when the
	 * route is started
	 * 
	 * @param true if started
	 */
	public void setStarted(boolean started)
	{
		this.started = started;
	}

	/**
	 * Checks to see if the route has been started or not
	 * 
	 * @return true if started
	 */
	public boolean isStarted()
	{
		return started;
	}

	/**
	 * Checks if this is a new route that has not been saved yet.
	 * 
	 * @return true if it is a new route
	 */
	public boolean isNewRoute()
	{
		return getId() == -1;
	}

	/**
	 * Get the amount of calories burned
	 * 
	 * @return amount of calories burned
	 */
	public int getCalories()
	{
		return calories;
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
		dest.writeLong(_id);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeInt(type);
		dest.writeInt(lengthcoach);
		dest.writeInt(timecoach);
		dest.writeInt(timePassed);
		dest.writeInt(calories);
		dest.writeFloat(totalDistance);
		String startedString;
		if(started)
		{
			startedString = "yes";
		}
		else
		{
			startedString = "no";
		}
		dest.writeString(startedString);
		dest.writeList(geoPoints);
		dest.writeList(checkPoints);
	}

	/**
	 * Parcelable construction, this is called automatically by the system when
	 * needed.
	 */
	public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>()
	{

		public Route createFromParcel(Parcel in)
		{
			return new Route(in);
		}

		public Route[] newArray(int size)
		{
			return new Route[size];
		}
	};

	/**
	 * Constructor for the parcelable interface that re-initiates the values
	 * 
	 * @param in
	 *            the Parcel that contains the data
	 */
	private Route(Parcel in)
	{
		this._id = in.readLong();
		this.name = in.readString();
		this.description = in.readString();
		this.type = in.readInt();
		this.lengthcoach = in.readInt();
		this.timecoach = in.readInt();
		this.timePassed = in.readInt();
		this.calories = in.readInt();
		this.totalDistance = in.readFloat();
		String started = in.readString();
		this.started = false;
		if (started.equals("yes"))
		{
			this.started = true;
		}
		ArrayList<ParcelableGeoPoint> geoPoints = new ArrayList<ParcelableGeoPoint>();
		in.readList(geoPoints, ParcelableGeoPoint.class.getClassLoader());
		this.geoPoints = geoPoints;
		
		ArrayList<CheckPoint> checkPoints = new ArrayList<CheckPoint>();
		in.readList(checkPoints, CheckPoint.class.getClassLoader());
		this.checkPoints = checkPoints;
		
		
	}
	/**
	 * Removes a checkpoint with the checkpoint id, it loops through the list to find it.
	 * @param checkPointId
	 */
	public void removeCheckPoint(long checkPointId)
	{
		for (CheckPoint checkPoint : checkPoints)
		{
			if (checkPoint.getId() == checkPointId)
			{
				checkPoints.remove(checkPoint);
				break;
			}
		}
	}
	/**
	 * Adds a checkpoint to the checkpoint list if it doesn«t exists, updates it otherwise
	 * @param checkPoint
	 */
	public void addCheckPoint(CheckPoint checkPoint)
	{
		boolean exist = false;
		for (CheckPoint oldCheckPoint : checkPoints)
		{
			if (oldCheckPoint.getLatuitude()==checkPoint.getLatuitude()&&oldCheckPoint.getLongitude()==checkPoint.getLongitude())
			{
				checkPoints.remove(oldCheckPoint);
				checkPoints.add(checkPoint);
				exist=true;
			}
		}
		if (!exist)
		{
			checkPoints.add(checkPoint);
		}
	}
}
