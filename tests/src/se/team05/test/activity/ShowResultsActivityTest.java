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

package se.team05.test.activity;

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

public class ShowResultsActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private Solo solo;
	private ImageView oldRouteImage;

	public ShowResultsActivityTest()
	{
		super(MainActivity.class);
	}

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

	@Override
	protected void tearDown()
	{
		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
		db.delete(DBCheckPointAdapter.TABLE_CHECKPOINTS, null, null);
		db.delete(DBGeoPointAdapter.TABLE_GEOPOINTS, null, null);
		solo.finishOpenedActivities();
	}

	public void testShowingAndDeletingResults()
	{
		DatabaseHandler databaseHandler = new DatabaseHandler(getActivity());
		Route route = new Route("name", "description");
		route.setId(databaseHandler.saveRoute(route));
		
		Result result = new Result(route.getId(), 200000000L, 3700, 1000, 0);		
		long id = databaseHandler.saveResult(result);
		result = databaseHandler.getResultById(id);
		
		Result result2 = new Result(route.getId(), 200000000L, 3700, 1000, 0);		
		long id2 = databaseHandler.saveResult(result);
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
