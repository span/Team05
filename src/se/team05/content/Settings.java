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

package se.team05.content;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class contains user's settings. It works as a model in that it retains
 * the data that it saves between sessions and also supplies other methods with said data.
 * This is made possible by the use of the Shared Preferences library
 * 
 * @author Markus
 *
 */
public class Settings
{
	public static final String PREFERENCES_NAME = "se.team05.activity.PersonalTrainerSettings";
	public static final String PREFERENCES_USER_WEIGHT = "userWeight";
	public static final String PREFERENCES_USER_NAME = "userName";
	public static final String PREFERENCES_USER_PREFERRED_TRAINING_RUNNING = "userPreferredTraining";
	public static final int PREFERENCES_USER_DEFAULT_WEIGHT = 75;
	public static final String PREFERENCES_USER_DEFAULT_NAME = "BATMAN";

	private String userName;
	private int userWeight;
	private boolean isPrefActivityRunning;

	static SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	private Context context;
	
	/**
	 * Constructor for this class. Sets the file name for the shared preferences to be accessed.
	 * 
	 * @param context the activity calling this class.
	 */
	public Settings(Context context)
	{
		this.context = context;
		
		sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
		userName = sharedPreferences.getString(PREFERENCES_USER_NAME, PREFERENCES_USER_DEFAULT_NAME);
		userWeight = sharedPreferences.getInt(PREFERENCES_USER_WEIGHT, PREFERENCES_USER_DEFAULT_WEIGHT);
        isPrefActivityRunning = sharedPreferences.getBoolean(PREFERENCES_USER_PREFERRED_TRAINING_RUNNING, true);
	    editor = sharedPreferences.edit();
	}

	/**
	 * After writing to the shared preferences we must commit to save changes.
	 * This is called after all writing has been done. Called by calling class.
	 */
	public void commitChanges()
	{
		editor.commit();
	}

	/**
	 * Set method for setting user's name
	 * 
	 * @return string representing the user's name
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * Get method for user's name
	 * 
	 * @param userName User's name in a string
	 */
	public void setUserName(String userName)
	{
		editor.putString(PREFERENCES_USER_NAME, userName);
	}

	/**
	 * Get method for user's weight
	 * 
	 * @return int representing kilograms
	 */
	public int getUserWeight()
	{
		return userWeight;
	}

	/**
	 * Set method for user's weight
	 * 
	 * @param userWeight int representing kilograms
	 */
	public void setUserWeight(int userWeight)
	{
		editor.putInt(PREFERENCES_USER_WEIGHT, userWeight);
	}

	/**
	 * Get method for user's desired activity
	 * 
	 * @return true if running, false for walking
	 */
	public boolean isPrefActivityRunning()
	{
		return isPrefActivityRunning;
	}

	/**
	 * Set method for if user's desired activity is running
	 * 
	 * @param isActivityRunning boolean depicting users activity, true for running, false for walking
	 */
	public void setPrefActivityRunning(boolean isActivityRunning)
	{
		editor.putBoolean(PREFERENCES_USER_PREFERRED_TRAINING_RUNNING, isActivityRunning);
	}

}
