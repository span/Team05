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
import se.team05.activity.SettingsActivity;
import se.team05.content.Settings;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.jayway.android.robotium.solo.Solo;


/**
 * This test makes sure that the SettingsActivity presents correct values to the user,
 * that only valid input is saved and when saving that all values get safely transferred
 * to the settingsclass which acts as a model holding all the values with the help of
 * the Shared Preferences class
 * @author Markus
 *
 */
public class SettingsActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private static String USER_NAME_ONE= "Batman";
	private static String USER_NAME_TWO= " und Robin";
	private static String WEIGHT_OK_ONE = "103";
	private static String WEIGHT_OK_TWO = "30";
	private static String WEIGHT_FAULTY_ONE = "0";
	private static String WEIGHT_FAULTY_TWO = "52y";
	private static int RUNNING_INT_REPRESENTATION = 0;
	private static int NOT_RUNNING_INT_REPRESENTATION = 1;

	private Solo solo;
	private ImageView settingsImage;

	public SettingsActivityTest()
	{
		super(MainActivity.class);
	}

	/**
	 * Sets up the thest class with default values, this is to ease up repeated testing as we need to
	 * reset the strings since they are saved on the sd-card
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Activity activity = getActivity();
		solo = new Solo(this.getInstrumentation(), activity);
		settingsImage = (ImageView) activity.findViewById(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.clickOnRadioButton(0);
		solo.typeText(0, USER_NAME_ONE);
		solo.typeText(1, WEIGHT_OK_ONE);
		Button saveButton = (Button) solo.getView(R.id.save_settings);
		solo.clickOnView(saveButton);
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
		

	}

	/**
	 * On test finish, quit the project
	 */
	@Override
	protected void tearDown()
	{
		solo.finishOpenedActivities();
	}
	
	/**
	 * This tests that the user should not be able to save any values
	 * that could cause a conflict during run time, in this case only integers
	 * are allowed as weight representation
	 */
	public void testIllegalSettings()
	{	
		settingsImage =(ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);
		Button saveButton = (Button) solo.getView(R.id.save_settings);
		
        solo.clearEditText(1);
		solo.clickOnView(saveButton);
		solo.clickOnView(solo.getView(android.R.id.button1));
		
        solo.clearEditText(1);
        solo.typeText(1, WEIGHT_FAULTY_ONE);
		solo.clickOnView(saveButton);
		solo.clickOnView(solo.getView(android.R.id.button1));
		
        solo.clearEditText(1);
        solo.typeText(1, WEIGHT_FAULTY_TWO);
		solo.clickOnView(saveButton);
		solo.clickOnView(solo.getView(android.R.id.button1));
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
	}
	
	/**
	 * This test simply changes the state of everything in the SettingsActivity but does
	 * not save anything. This to make sure that when SettingsActivity is started again it
	 * does not store any unwanted data.
	 */
	public void testChangeSettingsWithoutSave()
	{
		settingsImage = (ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);		

		EditText nameEdit = solo.getEditText(0);
		String name = nameEdit.getText().toString();
		EditText weightEdit = solo.getEditText(1);
		String weight = weightEdit.getText().toString();
		boolean rState = (solo.isRadioButtonChecked(1));
		
        solo.clearEditText(0);
        solo.clearEditText(1);
		if(rState)
		{
			solo.clickOnRadioButton(0);
		}
		
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
		
		settingsImage =(ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);
		
		assertEquals(name, solo.getEditText(0).getText().toString());
		assertEquals(weight, solo.getEditText(1).getText().toString());
		assertEquals(rState, solo.isRadioButtonChecked(1));
		
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);	
	}
	
		
	/**
	 * This test makes sure that the user's settings get transferred to the settings.java model when saving
	 * and that it retains the data when changing activity and that the settingsactivity presents the 
	 * saved data correctly to represent the users saved settings.
	 */
	public void testSettingsActivitySendDataToSettings()
	{
		settingsImage = (ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);	
		Settings settings = new Settings(this.getInstrumentation().getTargetContext());
		
		String name = settings.getUserName();
		int weight = settings.getUserWeight();

		assertEquals(name, solo.getEditText(0).getText().toString());
		assertEquals(Integer.toString(weight), solo.getEditText(1).getText().toString());
		
		name += USER_NAME_TWO;
		weight += Integer.parseInt(WEIGHT_OK_TWO);
		solo.clickOnRadioButton(NOT_RUNNING_INT_REPRESENTATION);
	
		assertNotSame(name, settings.getUserName());
		assertNotSame(name, solo.getEditText(0).getText().toString());
		assertNotSame(weight, settings.getUserWeight());
		assertNotSame(weight, solo.getEditText(1).getText().toString());
		assertNotSame(solo.isRadioButtonChecked(RUNNING_INT_REPRESENTATION), settings.isPrefActivityRunning());

		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.typeText(0, name);

		solo.typeText(1, Integer.toString(weight));
		solo.clickOnRadioButton(RUNNING_INT_REPRESENTATION);
		Button saveButton = (Button) solo.getView(R.id.save_settings);	
		solo.clickOnView(saveButton);
		
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
		settingsImage = (ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);

		Settings settings2 = new Settings(this.getInstrumentation().getTargetContext());
		assertEquals(name, settings2.getUserName());
		assertEquals(name, solo.getEditText(0).getText().toString());
		
		assertEquals(Integer.toString(weight), Integer.toString(settings2.getUserWeight()));
		assertEquals(Integer.toString(weight), solo.getEditText(1).getText().toString());
		assertEquals(solo.isRadioButtonChecked(RUNNING_INT_REPRESENTATION), settings2.isPrefActivityRunning());
	}
	
}
