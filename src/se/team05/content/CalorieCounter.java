package se.team05.content;

import se.team05.R;
import se.team05.activity.RouteActivity;
import se.team05.activity.SettingsActivity;
import android.content.Context;
import android.content.SharedPreferences;

public class CalorieCounter
{
	private int weight = 75;
	private boolean isActivityWalking = true;
	private double calorieBurnedFactorWalking = 0.0004108;
	private double calorieBurnedFactorRunning = 0.00086283;
	private Settings settings;

	public CalorieCounter()
	{
	}
	
	public CalorieCounter(Context context)
	{
		settings = new Settings(context);
	}
	
	public int updateCalories(float distance)
	{
		return caloriesBurned(distance);
	}
	

	/**
	 * This is a simple calculator that hopefully will give a rough estimate of of calories burned based on
	 * users sex and distance he or she has walked.
	 * 
	 * @return calories burned represented by a double
	 */
	private int caloriesBurned(float distance)
	{
		if(settings.isPrefActivityRunning())
		{
			return (int) (calorieBurnedFactorRunning * settings.getUserWeight() * distance);
		}
		else
		{
			return (int) (calorieBurnedFactorWalking * settings.getUserWeight() * distance);
		}

	}

}
