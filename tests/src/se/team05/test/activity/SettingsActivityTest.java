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

import java.util.ArrayList;

import se.team05.R;
import se.team05.activity.ListExistingResultsActivity;
import se.team05.activity.ListExistingRoutesActivity;
import se.team05.activity.MainActivity;
import se.team05.activity.RouteActivity;
import se.team05.activity.SettingsActivity;
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
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import se.team05.R;
import se.team05.content.Settings;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

import com.jayway.android.robotium.solo.Solo;

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<MainActivity>
{
	private static String USER_NAME = "Temp Hej, Dr.";
	private static String WEIGHT_OK = "43";
	private static String WEIGHT_FAULTY_ONE = "0";
	private static String WEIGHT_FAULTY_TWO = "52y";

	private Solo solo;
	private ImageView settingsImage;

	public SettingsActivityTest()
	{
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
		Activity activity = getActivity();
		solo = new Solo(this.getInstrumentation(), activity);
		settingsImage = (ImageView) activity.findViewById(R.id.image_settings);
	}

	@Override
	protected void tearDown()
	{
		solo.finishOpenedActivities();
	}
	
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
	
	public void testChangeSettingsWithSave()
	{	
		
		settingsImage = (ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);

		boolean rStateBefore = (solo.isRadioButtonChecked(1));
		
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.typeText(0, USER_NAME);
        solo.typeText(1, WEIGHT_OK);
        
		if(rStateBefore)
		{
			solo.clickOnRadioButton(0);
		}
		else
		{
			solo.clickOnRadioButton(1);
		}
		boolean rStateAfter = (solo.isRadioButtonChecked(1));
		
		Button saveButton =(Button) solo.getView(R.id.save_settings);
		solo.clickOnView(saveButton);
		
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
		settingsImage =(ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		solo.assertCurrentActivity("Expected SettingsActivity", SettingsActivity.class);
		
		assertEquals(USER_NAME, solo.getEditText(0).getText().toString());
		assertEquals(WEIGHT_OK, solo.getEditText(1).getText().toString());
		assertEquals(rStateAfter, solo.isRadioButtonChecked(1));
		
		solo.clickOnActionBarHomeButton();		
		solo.assertCurrentActivity("Expected MainActivity", MainActivity.class);
	}
	
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
	
	public void testSettingsActivitySendDataToSettings()
	{
		settingsImage = (ImageView) solo.getView(R.id.image_settings);
		solo.clickOnView(settingsImage);
		Settings settings = new Settings(this.getInstrumentation().getTargetContext());
		String nameBefore = settings.getUserName();
		int weightBefore = settings.getUserWeight();
		
		assertEquals(nameBefore, solo.getEditText(0).getText().toString());
		assertEquals(Integer.toString(weightBefore), solo.getEditText(1).getText().toString());
		
		String nameAfter = nameBefore + "TEST";
		int weightAfter = weightBefore + 100;
		assertNotSame(nameAfter, settings.getUserName());
		assertNotSame(weightAfter, settings.getUserWeight());

		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.typeText(0, nameAfter);
		solo.typeText(1, Integer.toString(weightAfter));
		Button saveButton = (Button) solo.getView(R.id.save_settings);
		
		solo.clickOnView(saveButton);
		assertEquals(nameBefore + "TEST", solo.getEditText(0).getText().toString());
		assertEquals(Integer.toString(weightBefore+100), solo.getEditText(1).getText().toString());
		assertEquals(nameAfter, solo.getEditText(0).getText().toString());
		assertEquals(Integer.toString(weightAfter), solo.getEditText(1).getText().toString());

	}
	
}
