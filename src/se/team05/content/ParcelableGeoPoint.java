package se.team05.content;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;

public class ParcelableGeoPoint extends GeoPoint implements Parcelable {

    public ParcelableGeoPoint(int lat, int lon) {
    	super(lat, lon);
    }

    
    public int describeContents() {
        return 0;
    }
	
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(getLatitudeE6());
        out.writeInt(getLongitudeE6());
    }

    public static final Parcelable.Creator<ParcelableGeoPoint> CREATOR
            = new Parcelable.Creator<ParcelableGeoPoint>() {
    	
        public ParcelableGeoPoint createFromParcel(Parcel in) {
            return new ParcelableGeoPoint(in);
        }

        public ParcelableGeoPoint[] newArray(int size) {
            return new ParcelableGeoPoint[size];
        }
    };

    private ParcelableGeoPoint(Parcel in) {
    	super(in.readInt(), in.readInt());
    }

}