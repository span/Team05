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
	int _id;
	String name;
	String description;
	int type;
	boolean timecoach;
	boolean lengthcoach;
	
	public Route(int _id,String name, String description, int type, boolean timecoach, boolean lengthcoach)
	{
		this._id = _id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.timecoach = timecoach;
		this.lengthcoach = lengthcoach;
	}
	
	public Route(String name, String description, int type, boolean timecoach, boolean lengthcoach)
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
		this.timecoach = false;
		this.lengthcoach = false;
	}
	
	public String toString()
	{
		return name;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
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

	public boolean isTimecoach() {
		return timecoach;
	}

	public void setTimecoach(boolean timecoach) {
		this.timecoach = timecoach;
	}

	public boolean isLengthcoach() {
		return lengthcoach;
	}

	public void setLengthcoach(boolean lengthcoach) {
		this.lengthcoach = lengthcoach;
	}
	
}
