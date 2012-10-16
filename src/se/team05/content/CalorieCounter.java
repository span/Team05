package se.team05.content;

import se.team05.activity.RouteActivity;
import se.team05.activity.SettingsActivity;
import android.content.Context;
import android.content.SharedPreferences;

public class CalorieCounter
{
	private int weight;
	private int distance;
	private double calorieBurnedFactorWalking = 0.0004108;
	private double calorieBurnedFactorRunning = 0.00086283;
	private boolean isActivityWalking;
	private int totalCalsBurned = 0;

	public CalorieCounter(int weight, int distance, boolean isActivityWalking)
	{
		this.distance = distance;
		this.isActivityWalking = isActivityWalking;
	}
	
	public CalorieCounter(Context context)
	{
		Settings set = new Settings(context);
	}
	

	/**
	 * This is a simple calculator that hopefully will give a rough estimate of of calories burned based on
	 * users sex and distance he or she has walked.
	 * 
	 * @return calories burned represented by a double
	 */
	private void caloriesBurned()
	{
		if(isActivityWalking)
		{
			totalCalsBurned = (int) (calorieBurnedFactorWalking * weight * distance);
		}
		else if(!isActivityWalking)
		{
			totalCalsBurned = (int) (calorieBurnedFactorRunning * weight * distance);
		}
		else
		{
			totalCalsBurned = 0;
		}
	}

}
