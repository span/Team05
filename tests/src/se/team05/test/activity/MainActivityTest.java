package se.team05.test.activity;

import com.jayway.android.robotium.solo.Solo;

import se.team05.activity.*;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private Solo solo;
	
	private MainActivity mActivity;
	
	private Button newRouteButton;
	private Button useExistingButton;
	

	public MainActivityTest()
	{
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(this.getInstrumentation(), getActivity());
		
		this.setActivityInitialTouchMode(false);
		
		mActivity = this.getActivity();
		
		newRouteButton = (Button) mActivity.findViewById(
				se.team05.R.id.new_route_button
		);
		
		useExistingButton = (Button) mActivity.findViewById(
				se.team05.R.id.use_existing_button
		);
		
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		mActivity.finish();
		super.tearDown();
	}
	
	public void testPreConditions()
	{
		solo.assertCurrentActivity("MainActivity expected", MainActivity.class);
		assertNotNull(newRouteButton);
		assertNotNull(useExistingButton);	
	}
	
	public void testNewRouteButton()
	{
		solo.clickOnView(newRouteButton);
		solo.assertCurrentActivity("wrong class", NewRouteActivity.class);
		solo.goBack();
		solo.assertCurrentActivity("wrong class", MainActivity.class);
	}
	
	public void testUseExistingButton()
	{
		solo.clickOnView(useExistingButton);
		solo.assertCurrentActivity("wrong class", UseExistingRouteActivity.class);
		solo.goBack();
		solo.assertCurrentActivity("wrong class", MainActivity.class);
	}

}
