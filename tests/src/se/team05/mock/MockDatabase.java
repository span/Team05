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

package se.team05.mock;

import java.util.ArrayList;

import se.team05.content.ParcelableGeoPoint;
import se.team05.content.Route;
import se.team05.content.Track;
import se.team05.data.DatabaseHandler;
import se.team05.overlay.CheckPoint;
import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.test.ActivityInstrumentationTestCase2;

/**
 * This class is used to construct objects in the database which can be used
 * from the different testing classes.
 * 
 * @author Daniel Kvist
 * 
 */
public class MockDatabase
{
	/**
	 * Creates a new route with geo points, check points and tracks.
	 * 
	 * @param testCase
	 *            the test case you are running
	 * @param activity
	 *            the activity you are testing
	 * @return a new Route
	 */
	public static Route getRoute(ActivityInstrumentationTestCase2 testCase, Activity activity)
	{
		DatabaseHandler databaseHandler = new DatabaseHandler(activity);
		Route route = new Route("name", "description", activity);
		route.setId(databaseHandler.saveRoute(route));

		ArrayList<ParcelableGeoPoint> geoPointList = new ArrayList<ParcelableGeoPoint>();
		ParcelableGeoPoint gpA = new ParcelableGeoPoint((int) (47.975 * 1E6), (int) (17.056 * 1E6));
		ParcelableGeoPoint gpB = new ParcelableGeoPoint((int) (48.975 * 1E6), (int) (17.056 * 1E6));
		geoPointList.add(gpA);
		geoPointList.add(gpB);
		databaseHandler.saveGeoPoints(route.getId(), geoPointList);

		CheckPoint checkPoint = new CheckPoint(new ParcelableGeoPoint((int) (48.975 * 1E6), (int) (17.056 * 1E6)));
		checkPoint.setRid(route.getId());
		long cid = databaseHandler.saveCheckPoint(checkPoint);

		Cursor cursor = activity.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.DATA }, null, null, MediaStore.Audio.Media.ALBUM);
		cursor.moveToFirst();
		if (cursor.getCount() < 1)
		{
			testCase.fail("Could not find media in the media store, please add media to your device and reboot before testing again.");
		}
		else
		{
			Track track = new Track("id", "artist", "album", "title", cursor.getString(0), "displayName", "duration");
			databaseHandler.saveTrack(cid, track);
		}

		route.setCheckPoints(databaseHandler.getCheckPoints(route.getId()));
		return route;
	}
}
