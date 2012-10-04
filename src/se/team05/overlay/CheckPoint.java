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

package se.team05.overlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * The Checkpoint class in which we can set different attributes and contains a
 * geopoint
 * 
 * @author Patrik Thituson
 * @version 1.0
 */
public class CheckPoint extends OverlayItem
{

	private long _id;
	private long rid;
	private String name;
	private int radius;

	/**
	 * The default Constructor which initiates the route id to -1
	 * 
	 * @param geoPoint
	 */
	public CheckPoint(GeoPoint geoPoint)
	{
		this(geoPoint, "CheckPoint", 30, -1);
	}

	private CheckPoint(GeoPoint geoPoint, String name, int radius, int rid)
	{
		super(geoPoint, "", "");
		this.setName(name);
		this.setRadius(radius);
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
	 * @param rid the route id to set
	 */
	public void setRid(long rid)
	{
		this.rid = rid;
	}
}
