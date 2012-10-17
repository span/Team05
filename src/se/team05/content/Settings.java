package se.team05.content;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings
{
	public static final String PREFERENCES_NAME = "se.team05.activity.PersonalTrainerSettings";
	public static final String PREFERENCES_USER_WEIGHT = "userWeight";
	public static final String PREFERENCES_USER_NAME = "userName";
	public static final String PREFERENCES_USER_SEX = "userSex";
	public static final String PREFERENCES_USER_LENGTH_UNIT = "userLengthUnit";
	public static final String PREFERENCES_USER_WEIGHT_UNIT_KG = "userWeightUnit";
	public static final String PREFERENCES_USER_PREFERRED_TRAINING_RUNNING = "userPreferredTraining";
	public static final int PREFERENCES_USER_DEFAULT_WEIGHT = 75;
	public static final String PREFERENCES_USER_DEFAULT_NAME = "BATMAN";

	private String userName;
	private int userWeight;
	private boolean isSILength;
	private boolean isSIWeight;
	private boolean isPrefActivityRunning;

	static SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	private Context context;
	
	public Settings(Context context)
	{
		this.context = context;
		
		sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, 0);
		userName = sharedPreferences.getString(PREFERENCES_USER_NAME, PREFERENCES_USER_DEFAULT_NAME);
		userWeight = sharedPreferences.getInt(PREFERENCES_USER_WEIGHT, PREFERENCES_USER_DEFAULT_WEIGHT);
        isSILength = sharedPreferences.getBoolean(PREFERENCES_USER_LENGTH_UNIT, true);
        isPrefActivityRunning = sharedPreferences.getBoolean(PREFERENCES_USER_PREFERRED_TRAINING_RUNNING, true);
        isSIWeight = sharedPreferences.getBoolean(PREFERENCES_USER_WEIGHT_UNIT_KG, true);
	    editor = sharedPreferences.edit();
	}

	public void commitChanges()
	{
		editor.commit();
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		editor.putString(PREFERENCES_USER_NAME, userName);
	}

	public int getUserWeight()
	{
		return userWeight;
	}

	public void setUserWeight(int userWeight)
	{
		editor.putInt(PREFERENCES_USER_WEIGHT, userWeight);
	}

	public boolean isSILength()
	{
		return isSILength;
	}

	public void setSILength(boolean isSILength)
	{
		editor.putBoolean(PREFERENCES_USER_LENGTH_UNIT, isSILength);
	}

	public boolean isSIWeight()
	{
		return isSIWeight;
	}

	public void setSIWeight(boolean isSIWeight)
	{
		editor.putBoolean(PREFERENCES_USER_WEIGHT_UNIT_KG, isSIWeight);
	}

	public boolean isPrefActivityRunning()
	{
		return isPrefActivityRunning;
	}

	public void setPrefActivityRunning(boolean isActivityRunning)
	{
		editor.putBoolean(PREFERENCES_USER_PREFERRED_TRAINING_RUNNING, isActivityRunning);
	}

}
