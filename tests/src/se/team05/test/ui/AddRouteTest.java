package se.team05.test.ui;

import se.team05.activity.MainActivity;
import se.team05.data.DBRouteAdapter;
import se.team05.data.Database;

import com.jayway.android.robotium.solo.Solo;

import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

public class AddRouteTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private static String ROUTE_NAME = "Hello Route";
	private static String ROUTE_DESC = "Hello Description";
	
	private Solo solo;
	
	/**
	 * Making sure to call inherited parent constructor, nothing more.
	 */
	public AddRouteTest()
	{
		super(MainActivity.class);
	}
	
	/**
	 * Setting up Robotium for each test and clearing database.
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(this.getInstrumentation(), getActivity());
		
		SQLiteDatabase db =  new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
	}
	
	/**
	 * Clearing database and tell Robotium to finish all activities.
	 */
	@Override
	protected void tearDown()
	{
		SQLiteDatabase db =  new Database(this.getInstrumentation().getTargetContext()).getWritableDatabase();
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
		
		solo.finishOpenedActivities();
	}
	
	/**
	 * Testing by adding a new route and checking if it appears in 
	 * the list of existing routes.
	 */
	public void testAddRoute()
	{
			solo.clickOnButton("New route");
			solo.clickOnButton("Start Run");
			solo.clickOnButton("Stop and Save");
			
			solo.typeText(0, ROUTE_NAME); // TODO static string
			solo.typeText(1, ROUTE_DESC);
			
			solo.clickOnButton("Save");
			
			solo.clickOnButton("Use existing route");
			
			solo.searchText(ROUTE_NAME);
	}

}
