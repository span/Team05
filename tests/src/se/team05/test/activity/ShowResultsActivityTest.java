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

//import se.team05.activity.ListExistingRoutesActivity;
//import se.team05.activity.MainActivity;
//import se.team05.activity.RouteActivity;
import se.team05.activity.ShowResultsActivity;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.data.DatabaseHandler;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.jayway.android.robotium.solo.Solo;

/**
 * This test class utilizes Robotium for simple UI-testing of
 * the ShowResultsActivity class. This class is called upon by JUnit
 * automatically.
 * @author Markus && Gustaf
 *
 */

public class ShowResultsActivityTest extends ActivityInstrumentationTestCase2<ShowResultsActivity> {
	
	private Solo solo;
	
	private ShowResultsActivity showResultsActivity;
	
	private ImageView newRouteButton;
	private ImageView useExistingButton;
	private Button deleteResultsButton;
	private DatabaseHandler databaseHandler;
	private Route route;
	private Result result;
	
	/**
	 * Making sure to call inherited parent constructor, nothing more.
	 */
	public ShowResultsActivityTest()
	{
		super(ShowResultsActivity.class);
	}
	
	/**
	 * Runs automatically by JUnit before each testcase.
	 * Sets up the testing environment before each individual test
	 * by getting a Solo instance for Robotium and setting variables
	 * for use in test-methods.
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(this.getInstrumentation(), getActivity());
		
		this.setActivityInitialTouchMode(false);
		
		showResultsActivity = this.getActivity();
		
		deleteResultsButton = (Button) showResultsActivity.findViewById(
				se.team05.R.id.delete_results_button
		);
		
		databaseHandler = new DatabaseHandler(this);
		Route route = new Route("testRoute", "enGoRunda", -1, -1, -1);
		databaseHandler.saveRoute(route);
		result = new Result(route.getId(), 1000000, 3700, 1000, 0);
		databaseHandler.saveResult(result);	
	}
	
	/**
	 * Runs automatically by JUnit after each testcase.
	 * Finalizes the activity, releasing the object, and then
	 * calls parent tear down method to do its thing.
	 */
	@Override
	protected void tearDown() throws Exception
	{
		showResultsActivity.finish();
		super.tearDown();
	}
	
	/**
	 * Checks the environment to make sure all resources necessary
	 * for testing are actually loaded. A valid environment is
	 * required for other test-methods to pass.
	 */
	public void testPreConditions()
	{
		solo.assertCurrentActivity("ShowResultsActivity expected", ShowResultsActivity.class);
		assertNotNull(deleteResultsButton);
	}

	/**
	 * Makes sure the go back function is working and that the result
	 * crated in the set up of this class still exists in database.
	 */
	public void testSoloGoBack()
	{
		solo.goBack();
		solo.assertCurrentActivity("wrong class", ListExistingResultActivity.class);		
		assertEquals(result.getId(), databaseHandler.getResultById(result.getId()));		
	}
	
	/**
	 * Makes sure the deleteResultsButton behaves as expected using
	 * Robotium. It Should delete the result being viewed and return to
	 * ListExistingResultActivity. The result being deleted is the one
	 * we set up at the beginning of this test.
	 */
	public void testDeleteResultButton()
	{
		assertTrue(cursor!=null && cursor.getCount()>0);
		solo.clickOnView(deleteResultsButton);
		solo.clickOnText("Yes");
		solo.assertCurrentActivity("wrong class", ListExistingResultActivity.class);
		assertTrue(cursor==null || cursor.getCount()<1);
	}	
}
