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

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;
/**
 * This class is created to be able to save the geoPoints when a configuration changed has occured.
 * It is a geopoint that can be Parcelable
 * @author Snadde
 *
 */
public class ParcelableGeoPoint extends GeoPoint implements Parcelable {

    public ParcelableGeoPoint(int lat, int lon) {
    	super(lat, lon);
    }

	/**
	 * This is used be the parcelable interface to describe the object content.
	 */
    public int describeContents() {
        return 0;
    }
	/**
	 * This is used by the parcelable interface to temporarily store data when
	 * being sent as a parcel.
	 */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getLatitudeE6());
        out.writeInt(getLongitudeE6());
    }

	/**
	 * Parcelable construction, this is called automatically by the system when needed.
	 */
    public static final Parcelable.Creator<ParcelableGeoPoint> CREATOR
            = new Parcelable.Creator<ParcelableGeoPoint>() {
    	
        public ParcelableGeoPoint createFromParcel(Parcel in) {
            return new ParcelableGeoPoint(in);
        }

        public ParcelableGeoPoint[] newArray(int size) {
            return new ParcelableGeoPoint[size];
        }
    };
    
	/**
	 * Constructor for the parcelable interface that re-initiates the values
	 * 
	 * @param in
	 *            the Parcel that contains the data
	 */
    private ParcelableGeoPoint(Parcel in) {
    	super(in.readInt(), in.readInt());
    }

}