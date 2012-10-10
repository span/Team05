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

/**
 * This class is the result class used when saving results after finishing a route.
 * It contains several constructors in order to be be flexible during development
 * of the android application.
 * It also contains getters and setters for all the fields.
 * 
 * @author Gustaf Werlinder (guswer)
 *
 */
public class Result {
	
	public static String RESULT_ID = "id";
	
	private int _id;
	private long routId;
	private int timestamp;
	private int time;
	private int calories;
	private int distance;
	
	
	/**
	 * Constructor with default settings only to be used by developers
	 */
	public Result()
	{
		this._id = 1;
		this.routId = 1;
		this.timestamp = 1349286608;	// Date: October 3 2012, Time: 18:50
		this.time = 300;  				// 5 minutes
		this.calories = 1337;			// kcal
		this.distance = 1337;
	}

	/**
	 * Constructor that create an instance of the class. All fields must be set on creation.
	 * 
	 * @param _id
	 * 		the id of this instance
	 * @param routId
	 * 		id of the rout 
	 * @param timestamp
	 * 		time and date when the result is created in the format of nanoseconds passed since year 1970
	 * @param time
	 * 		time (seconds) it took to end the route corresponding with this result
	 * @param distance
	 * 		distance of the route
	 * @param calories
	 * 		calories (kcal) used during route
	 */
	public Result(int _id, int routId, int timestamp, int time, int distance, int calories)
	{
		this._id = _id;
		this.routId = routId;
		this.timestamp = timestamp;
		this.time = time;
		this.calories = calories;
	}
	
	/**
	 * Constructor that create an instance of the class.
	 * The field _id is not set on creation.
	 * All other fields are set on creation.
	 * @param routId
	 * 		id of the rout 
	 * @param timestamp
	 * 		time and date when the result is created in the format of nanoseconds passed since year 1970
	 * @param time
	 * 		time (seconds) it took to end the route corresponding with this result
	 * @param distance
	 * 		distance of the route
	 * @param calories
	 * 		calories (kcal) used during route
	 */
	public Result(long routId, int timestamp, int time, int distance, int calories)
	{
		this.routId = routId;
		this.timestamp = timestamp;
		this.time = time;
		this.calories = calories;
		this.distance = distance;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	public long getRoutId() {
		return routId;
	}

	public void setRouteId(long l) {
		this.routId = l;
	}

	/**
	 * Get timestamp.
	 * 
	 * @return time and date when the result was created 
	 * 			in the format of nanoseconds passed since year 1970
	 */
	public int getTimestamp() {
		return timestamp;
	}
	/**
	 * Set timestamp.
	 * 
	 * @param timestamp
	 * 		time and date when the result is created
	 * 		in the format of nanoseconds passed since year 1970
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * Get time it took to complete route.
	 * 
	 * @return time (seconds) it took to complete route
	 */
	public int getTime() {
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
	 * @return the distance
	 */
	public int getDistance()
	{
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance)
	{
		this.distance = distance;
	}
	
}
