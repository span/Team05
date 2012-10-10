package se.team05.dialog;

import se.team05.activity.MainActivity;
import se.team05.activity.MediaSelectorActivity;
import se.team05.activity.RouteActivity;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jayway.android.robotium.solo.Solo;

public class EditCheckPointDialogTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private MainActivity activity;
	private Activity routeActivity;
	private Button newRouteButton;
	private Solo solo;

	public EditCheckPointDialogTest()
	{
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setActivityInitialTouchMode(false);

		solo = new Solo(this.getInstrumentation(), getActivity());
		activity = this.getActivity();

		newRouteButton = (Button) activity.findViewById(se.team05.R.id.new_route_button);
		
		solo.clickOnView(newRouteButton);
		solo.assertCurrentActivity("wrong class", RouteActivity.class);
		
		routeActivity = solo.getCurrentActivity();
	}
	/**
	 * Tests if the dialog pops up when click on map and that cancel removes it
	 */
	public void testShowAndCancelDialog()
	{
		solo.clickOnScreen(400, 600);
	    assertTrue("Could not find the dialog!", solo.searchText("Edit CheckPoint"));
	    String cancel = routeActivity.getString(se.team05.R.string.cancel);
	    solo.clickOnButton(cancel);
	    assertFalse("Could find the dialog!", solo.searchText("Edit CheckPoint"));
	    solo.clickOnScreen(400, 600);
	    solo.goBack();
	    solo.clickOnScreen(400, 600);
	    solo.clickOnButton(cancel);
	}
	
	public void testSaveAndDeleteCheckPoint()
	{
		solo.clickOnScreen(400, 600);
	    assertTrue("Could not find the dialog!", solo.searchText("Edit CheckPoint"));
	    EditText editText = solo.getEditText("CheckPoint");
	    solo.clearEditText(editText);
	    solo.enterText(editText, "Slottskogen");

		for (View v : solo.getViews())
		{
			if (v instanceof ProgressBar)
			{
				((ProgressBar) v).setProgress(1000);
			}
		}
		
		solo.clickOnButton("Record");
		solo.clickOnButton("Stop recording");
		assertTrue(solo.waitForText("Added new recording"));
		
		solo.clickOnButton("Select");
		solo.assertCurrentActivity("Wrong Activity excpected MediaSelectorActivity ", MediaSelectorActivity.class);
		solo.goBack();
		
	    String save = routeActivity.getString(se.team05.R.string.save);
	    solo.clickOnText(save);
	    assertFalse("Could find the dialog!", solo.searchText("Edit CheckPoint"));
	    
	    solo.clickOnScreen(400, 600);
	    assertTrue("Wrong Name!", solo.searchEditText("Slottskogen"));
	    assertFalse("SeekBar not changed!", solo.searchText("30"));
	    
	    String delete = routeActivity.getString(se.team05.R.string.delete); 
	    solo.clickOnButton(delete);
	    solo.clickOnScreen(400, 600);
	    
	    assertFalse("Wrong Name!", solo.searchEditText("Slottskogen"));
	    assertTrue("SeekBar not changed!", solo.searchText("30"));
	    
	    solo.clickOnButton("Cancel");
	    
	    
	}

	@Override
	protected void tearDown() throws Exception
	{
		routeActivity.finish();
//		activity.finish();
		super.tearDown();
	}

}
