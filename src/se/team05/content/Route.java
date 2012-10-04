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
 * An activity that will present the user with the option to choose and old route. As of now it is just a button but 
 * a future release will include a ListView representing the older routes saved in the database that the user
 * can choose from. TODO Change comments accordingly
 * 
 * @author Henrik Hugo
 *
 */
public class Route
{
	long _id;
	String name;
	String description;
	int type;
	int timecoach;
	int lengthcoach;
	
	public Route(long _id,String name, String description, int type, int timecoach, int lengthcoach)
	{
		this._id = _id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.timecoach = timecoach;
		this.lengthcoach = lengthcoach;
	}
	
	public Route(String name, String description, int type, int timecoach, int lengthcoach)
	{
		this.name = name;
		this.description = description;
		this.type = type;
		this.timecoach = timecoach;
		this.lengthcoach = lengthcoach;
	}
	
	public Route(String name, String description)
	{
		this.name = name;
		this.description = description;
		this.type = 0;
		// TODO Global variables for default values of timecoach and lengthcoach
		this.timecoach = -1;
		this.lengthcoach = -1;
	}
	
	public String toString()
	{
		return name;
	}

	public long getId() {
		return _id;
	}

	public void setId(long l) {
		this._id = l;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int isTimecoach() {
		return timecoach;
	}

	public void setTimecoach(int timecoach) {
		this.timecoach = timecoach;
	}

	public int isLengthcoach() {
		return lengthcoach;
	}

	public void setLengthcoach(int lengthcoach) {
		this.lengthcoach = lengthcoach;
	}
	
}
