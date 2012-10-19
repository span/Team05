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
package se.team05.test.ui;

import se.team05.R;
import se.team05.activity.ListExistingResultsActivity;
import se.team05.activity.ListExistingRoutesActivity;
import se.team05.activity.MainActivity;
import se.team05.activity.RouteActivity;
import se.team05.activity.ShowResultsActivity;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.data.DBCheckPointAdapter;
import se.team05.data.DBGeoPointAdapter;
import se.team05.data.DBRouteAdapter;
import se.team05.data.Database;
import se.team05.data.DatabaseHandler;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;

/**
 * This test is first and foremost a UI test of the activities
 * ShowResultsActivity and ListExistingResultsActivity and it
 * should be run in an Android phone or emulator.
 * It also tests the Result class getters and setters.
 * 
 * @author Markus and Gustaf
 *
 */
public class ShowResultsActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private Solo solo;
	private ImageView oldRouteImage;

	/**
	 * Call inherited parent constructor.
	 */
	public ShowResultsActivityTest()
	{
		super(MainActivity.class);
	}

	/**
	 * Automatically run by JUnit to before a test is run.
	 * Will set up the needed environment fo test to run correctly.
	 * A Solo instance is created for Robotium and setting variables
	 * are created for beeing used in the test-method.
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Activity activity = getActivity();
		solo = new Solo(this.getInstrumentation(), activity);
		oldRouteImage = (ImageView) activity.findViewById(R.id.image_existing_route);

		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
		db.delete(DBCheckPointAdapter.TABLE_CHECKPOINTS, null, null);
		db.delete(DBGeoPointAdapter.TABLE_GEOPOINTS, null, null);
	}

	/**
	 * Automatically run by JUnit after each test is ended.
	 * Finalizes the activity, releasing the object, and then
	 * calls parent tear down method.
	 */
	@Override
	protected void tearDown()
	{
		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
		db.delete(DBCheckPointAdapter.TABLE_CHECKPOINTS, null, null);
		db.delete(DBGeoPointAdapter.TABLE_GEOPOINTS, null, null);
		solo.finishOpenedActivities();
	}

	/**
	 * Uses Robotium to assert the show_result_button starts 
	 * the List Activity ListExistingRoutesActivity and 
	 * that the Home button in the action bar works properly.
	 */
	 public void testListExistingResultsActivity()
	 {	
		DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
		Route route = new Route("name", "description", getActivity());
		route.setId(databaseHandler.saveRoute(route));
		
		solo.clickOnView(oldRouteImage);
		solo.assertCurrentActivity("Expected ListExistingRoutesActivity", ListExistingRoutesActivity.class);
		solo.clickInList(0);
		solo.assertCurrentActivity("Expected RouteActivity", RouteActivity.class);
		Button showResultButton =(Button) solo.getView(R.id.show_result_button);
		solo.clickOnView(showResultButton);
		solo.assertCurrentActivity("Expected ListExistingResultsActivity", ListExistingResultsActivity.class);
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
	 }
	 
	/**
	 * Tests getters and setters of the Result class.
	 * Uses Robotium to assert the List Activity ListExistingRoutesActivity 
	 * creates a list of results that can be clicked to start the activity 
	 * ShowResultsActivity. Asserts that the delete_result_button works properly.
	 * Finally asserts that in the activity ShowResultsActivity
	 * the Home button in the action bar works properly.
	 */
	public void testShowingAndDeletingResults()
	{		
		DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
		Route route = new Route("name", "description", getActivity());
		route.setId(databaseHandler.saveRoute(route));

		Result result = new Result();
		
		long id = 144;
		result.setId(id);
		assertTrue(id == result.getId());
		id = 0;
		result.setId(id);
		assertTrue(id == result.getId());
		id = -1;
		result.setId(id);
		assertTrue(id == result.getId());
		
		long rid = 144;
		result.setRid(rid);
		assertTrue(rid == result.getRid());
		rid = 0;
		result.setRid(rid);
		assertTrue(rid == result.getRid());
		rid = -1;
		result.setRid(rid);
		assertTrue(rid == result.getRid());
		result.setRid(route.getId());
		
		long timestamp = 0;
		result.setTimestamp(timestamp);
		assertTrue(timestamp == result.getTimestamp());
		timestamp = -1;
		result.setTimestamp(timestamp);
		assertTrue(timestamp == result.getTimestamp());
		timestamp = 265951984;
		result.setTimestamp(timestamp);
		assertTrue(timestamp == result.getTimestamp());
		
		int time = 0;
		result.setTime(time);
		assertTrue(result.getTime() == 1);
		time = -1;
		result.setTime(time);
		assertTrue(time == result.getTime());
		time = 25627;
		result.setTime(time);
		assertTrue(time == result.getTime());
		
		int calories = 0;
		result.setCalories(calories);
		assertTrue(calories == result.getCalories());
		calories = -1;
		result.setCalories(calories);
		assertTrue(calories == result.getCalories());
		calories = 144;
		result.setCalories(calories);
		assertTrue(calories == result.getCalories());
		
		int distance = 0;
		result.setDistance(distance);
		assertTrue(distance == result.getDistance());
		distance = -1;
		result.setDistance(distance);
		assertTrue(distance == result.getDistance());
		distance = 12;
		result.setDistance(distance);
		assertTrue(distance == result.getDistance());
		
		id = databaseHandler.saveResult(result);
		result = databaseHandler.getResultById(id);
		
		Result result2 = new Result(route.getId(), 400000000L, 6700, 2000, 500);		
		long id2 = databaseHandler.saveResult(result2);
		result2 = databaseHandler.getResultById(id2);
		
		solo.clickOnView(oldRouteImage);
		solo.assertCurrentActivity("Expected ListExistingRoutesActivity", ListExistingRoutesActivity.class);
		solo.clickInList(0);
		solo.assertCurrentActivity("Expected RouteActivity", RouteActivity.class);
		Button showResultButton =(Button) solo.getView(R.id.show_result_button);
		solo.clickOnView(showResultButton);
		solo.assertCurrentActivity("Expected ListExistingResultsActivity", ListExistingResultsActivity.class);
		solo.clickInList(0);		
		solo.assertCurrentActivity("Expected ShowResultsActivity", ShowResultsActivity.class);
		Button deleteButton =(Button) solo.getView(R.id.delete_results_button);
		solo.clickOnView(deleteButton);
		solo.assertCurrentActivity("Expected ListExistingResultsActivity", ListExistingResultsActivity.class);
		solo.clickInList(0);
		solo.assertCurrentActivity("Expected ShowResultsActivity", ShowResultsActivity.class);
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);		
	}
}
