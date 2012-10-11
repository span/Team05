package se.team05.test.ui;

import se.team05.R;
import se.team05.activity.ListExistingRoutesActivity;
import se.team05.activity.MainActivity;
import se.team05.activity.RouteActivity;
import se.team05.data.DBCheckPointAdapter;
import se.team05.data.DBGeoPointAdapter;
import se.team05.data.DBRouteAdapter;
import se.team05.data.Database;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.jayway.android.robotium.solo.Solo;

public class UseRouteTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private Solo solo;

	public UseRouteTest()
	{
		super(MainActivity.class);
	}


	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(this.getInstrumentation(), getActivity());

		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
	}

	@Override
	protected void tearDown()
	{
		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);

		solo.finishOpenedActivities();
	}

	public void testUseRouteIfNonExisting()
	{
		solo.clickOnButton(1);
		solo.assertCurrentActivity("Expected ListExistingRoutesActivity", ListExistingRoutesActivity.class);
		solo.clickOnText("Click to add a route");
		solo.assertCurrentActivity("Expected RouteActivity", RouteActivity.class);
	}
	
	public void testUseExistingRoute()
	{
		SQLiteDatabase db = new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBRouteAdapter.COLUMN_DESCRIPTION, "desc");
		values.put(DBRouteAdapter.COLUMN_LENGTHCOACH, -1);
		values.put(DBRouteAdapter.COLUMN_TIMECOACH, -1);
		values.put(DBRouteAdapter.COLUMN_NAME, "name");
		values.put(DBRouteAdapter.COLUMN_TYPE, -1);
		long rid = db.insert(DBRouteAdapter.TABLE_ROUTES, null, values);
		
		values.clear();
		values.put(DBGeoPointAdapter.COLUMN_RID, rid);
		values.put(DBGeoPointAdapter.COLUMN_LATITUDE, "17422005");
		values.put(DBGeoPointAdapter.COLUMN_LONGITUDE, "32084093");
		db.insert(DBGeoPointAdapter.TABLE_GEOPOINTS, null, values);
		
		values.clear();
		values.put(DBGeoPointAdapter.COLUMN_RID, rid);
		values.put(DBGeoPointAdapter.COLUMN_LATITUDE, "17422005");
		values.put(DBGeoPointAdapter.COLUMN_LONGITUDE, "12084093");
		db.insert(DBGeoPointAdapter.TABLE_GEOPOINTS, null, values);
		
		values.clear();
		values.put(DBGeoPointAdapter.COLUMN_RID, rid);
		values.put(DBGeoPointAdapter.COLUMN_LATITUDE, "17422005");
		values.put(DBGeoPointAdapter.COLUMN_LONGITUDE, "10084093");
		db.insert(DBGeoPointAdapter.TABLE_GEOPOINTS, null, values);
		
		values.clear();
		values.put(DBCheckPointAdapter.COLUMN_RID, rid);
		values.put(DBCheckPointAdapter.COLUMN_LATITUDE, "17422005");
		values.put(DBCheckPointAdapter.COLUMN_LONGITUDE, "10084093");
		db.insert(DBCheckPointAdapter.TABLE_CHECKPOINTS, null, values);
		
		solo.clickOnButton(1);
		solo.clickInList(0);
		solo.assertCurrentActivity("Expected RouteActivity", RouteActivity.class);
		
		Button startButton = (Button) solo.getView(R.id.start_existing_run_button);
		Button stopButton = (Button) solo.getView(R.id.stop_existing_run_button);
		assertEquals(startButton.getVisibility(), View.VISIBLE);
		assertEquals(stopButton.getVisibility(), View.GONE);
		solo.clickOnView(startButton);
		assertEquals(startButton.getVisibility(), View.GONE);
		assertEquals(stopButton.getVisibility(), View.VISIBLE);
		solo.clickOnView(stopButton);
		solo.clickOnButton("Yes");
		assertEquals(startButton.getVisibility(), View.VISIBLE);
		assertEquals(stopButton.getVisibility(), View.GONE);
		
		solo.clickOnView(startButton);
		solo.clickOnView(stopButton);
		solo.clickOnButton("No");
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
	}

}
