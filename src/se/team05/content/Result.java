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

/**
 * This class is the result class used when saving results after finishing a route.
 * It contains several constructors in order to be be flexible during development
 * of the android application.
 * It also contains getters and setters for all the fields.
 * 
 * @author Gustaf Werlinder (guswer)
 *
 */
public class Result implements Comparable<Result>{
	
	public static String RESULT_ID = "id";
	
	private long _id;
	private long rid;
	private long timestamp;
	private int time;
	private int calories;
	private int distance;
	
	
	/**
	 * Constructor with default settings only to be used by developers
	 */
	public Result()
	{
		this(-1, -1, System.currentTimeMillis() / 1000, 0, 0, 0);
	}

	/**
	 * Constructor that create an instance of the class. All fields must be set on creation.
	 * 
	 * @param _id
	 * 		the id of this instance
	 * @param rid
	 * 		id of the rout 
	 * @param timestamp
	 * 		time and date when the result is created in the format of seconds passed since year 1970
	 * @param time
	 * 		time (seconds) it took to end the route corresponding with this result
	 * @param distance
	 * 		distance of the route
	 * @param calories
	 * 		calories (kcal) used during route
	 */
	public Result(long _id, long rid, long timestamp, int time, int distance, int calories)
	{
		this._id = _id;
		this.rid = rid;
		this.timestamp = timestamp;
		this.time = time;
		this.distance = distance;
		this.calories = calories;
	}
	
	/**
	 * Constructor that create an instance of the class.
	 * The field _id is not set on creation.
	 * All other fields are set on creation.
	 * @param rid
	 * 		id of the rout 
	 * @param timestamp
	 * 		time and date when the result is created in the format of seconds passed since year 1970
	 * @param time
	 * 		time (seconds) it took to end the route corresponding with this result
	 * @param distance
	 * 		distance of the route
	 * @param calories
	 * 		calories (kcal) used during route
	 */
	public Result(long rid, long timestamp, int time, int distance, int calories)
	{
		this(-1, rid, timestamp, time, distance, calories);
	}

	/**
	 * Get _id of the Result class.
	 * 
	 * @return _id
	 */
	public long getId() {
		return _id;
	}

	/**
	 * Set _id of the Result class.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this._id = id;
	}
	
	/**
	 * Get the id of the Route that the result belongs to
	 * 
	 * @return rid is the id of the Route that the result belongs to
	 */
	public long getRid() {
		return rid;
	}

	/**
	 * Set the id of the Route that the result belongs to
	 * 
	 * @param rid is the id of the Route that the result belongs to
	 */
	public void setRid(long rid) {
		this.rid = rid;
	}

	/**
	 * Get timestamp that tells when the result was created.
	 * 
	 * @return timestamp is the time when the result was created 
	 * 	in the format of seconds passed since January 1 year 1970.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Set timestamp that tells when the result was created.
	 * 
	 * @param timestamp
	 * 		time when the result is created
	 * 		in the format of seconds passed since January 1 year 1970.
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Get time it took to complete route.
	 * 
	 * @return time (seconds) it took to complete route
	 */
	public int getTime() {
		if(time == 0)
		{
			setTime(1);
		}
		return time;
	}

	/**
	 * Set time it took to complete route.
	 * 
	 * @param time
	 * 		time (seconds) it took to complete route.
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Get calories consumed during route.
	 * 
	 * @return calories (kcal) consumed during route.
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * Set calories consumed during route.
	 * 
	 * @param calories
	 * 		calories (kcal) consumed during route.
	 */
	public void setCalories(int calories) {
		this.calories = calories;
	}

	/**
	 * Get the distance (meters) registered during route.
	 * 
	 * @return the distance registered during route.
	 */
	public int getDistance()
	{
		return distance;
	}

	/**
	 * Set the distance (meters) registered during route.
	 * 
	 * @param distance 
	 * 			the distance registered during route.
	 */
	public void setDistance(int distance)
	{
		this.distance = distance;
	}

	/**
	 * This method is used to compare one result with another to
	 * decide which one is the grater. The result with the greater
	 * time used to complete the route is considered to be the greater result.
	 */
	@Override
	public int compareTo(Result anotherResult)
	{
		return this.time - anotherResult.getTime();
	}
	
}
