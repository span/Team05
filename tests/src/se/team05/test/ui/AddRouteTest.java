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
import se.team05.activity.MainActivity;
import se.team05.activity.RouteActivity;
import se.team05.activity.SettingsActivity;
import se.team05.data.DBRouteAdapter;
import se.team05.data.Database;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;
/**
 * This class contains methods for various UI-tests concerning the
 * creation of a new route.
 * @author Henrik Hugo, Daniel Kvist
 *
 */
public class AddRouteTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	private static String ROUTE_NAME = "Hello Route";
	private static String ROUTE_DESC = "Hello Description";

	private Solo solo;
	private ImageView newRouteImage;
	private ImageView oldRouteImage;

	/**
	 * Making sure to call inherited parent constructor, nothing more.
	 */
	public AddRouteTest()
	{
		super(MainActivity.class);
	}

	/**
	 * Setting up Robotium for each test and clearing the database.
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		Activity activity = getActivity();
		solo = new Solo(this.getInstrumentation(), activity);

		newRouteImage = (ImageView) activity.findViewById(R.id.image_new_route);
		oldRouteImage = (ImageView) activity.findViewById(R.id.image_existing_route);

		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
	}

	/**
	 * Clearing the database and tell Robotium to finish all activities.
	 */
	@Override
	protected void tearDown()
	{
		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);

		solo.finishOpenedActivities();
	}

	/**
	 * Testing by adding a new route and checking if it appears in the list of
	 * existing routes.
	 */
	public void testAddRoute()
	{
		solo.clickOnView(newRouteImage);
		solo.assertCurrentActivity("Route activity expected", RouteActivity.class);
		solo.clickOnButton(1);
		solo.clickOnButton(1);

		solo.typeText(0, ROUTE_NAME); // TODO static string
		solo.typeText(1, ROUTE_DESC);

		solo.clickOnButton("Save");

		solo.clickOnView(oldRouteImage);

		solo.searchText(ROUTE_NAME);
	}

	/**
	 * Tests if it is possible to enter the add route screen and go back. Then
	 * tests to make sure a warning is displayed if the route has been started
	 * when the user presses back. Then stops the recording of the route,
	 * discards it and goes back to main.
	 */
	public void testAddRouteAndDiscard()
	{
		solo.clickOnView(newRouteImage);
		solo.assertCurrentActivity("Route activity expected", RouteActivity.class);
		solo.goBack();
		solo.assertCurrentActivity("Main activity expected", MainActivity.class);
		solo.clickOnView(newRouteImage);
		solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.start_button));
		solo.goBack();
		solo.clickOnText("No");
		solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.stop_button));
		solo.clickOnText("Discard");
		solo.clickOnText("Yes");
		solo.assertCurrentActivity("Main activity expected", MainActivity.class);
	}

	/**
	 * Tests if it is possible to enter the settings from the add route screen.
	 * Then proceeds to testing if a warning id displayed if the users tries to
	 * enter the settings after the route has been started. Finally stops the
	 * recording and makes sure the settings can be entered.
	 */
	public void testAddRouteAndLaunchSettings()
	{
		solo.clickOnView(newRouteImage);
		solo.assertCurrentActivity("Route activity expected", RouteActivity.class);
		solo.clickOnActionBarItem(R.id.settings);
		solo.assertCurrentActivity("Settings activity expected", SettingsActivity.class);
		solo.goBack();
		solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.start_button));
		solo.clickOnActionBarItem(R.id.settings);
		solo.clickOnText(solo.getCurrentActivity().getString(android.R.string.ok));
		solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.stop_button));
		solo.clickOnActionBarItem(R.id.settings);
		solo.assertCurrentActivity("Settings activity expected", SettingsActivity.class);
	}
}
