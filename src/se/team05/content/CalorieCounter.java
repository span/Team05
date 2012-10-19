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


/**
 * A simple class for calculating calorie expenditure. This was made from a simple 
 * formula for calculating burned calories from walking or running and then calculating
 * two factors, one for running and one for running. Which factor to use is decided by the
 * user in the settings activity. This factor is then multiplied with user's weight and 
 * distance measured in metres.
 * 
 * @author Markus
 *
 */
public class CalorieCounter
{
	private double calorieBurnedFactorWalking = 0.0004108;
	private double calorieBurnedFactorRunning = 0.00086283;
	private Settings settings;

	/**
	 * Default constructor
	 */
	public CalorieCounter()
	{
	}
	
	/**
	 * Constructor, it takes a context from calling method. This is because it calls the settings class
	 * which needs a context.
	 * 
	 * @param context the calling class
	 */
	public CalorieCounter(Context context)
	{
		settings = new Settings(context);
	}
	
	/**
	 * This method both acts a get method for total burned calories and also calls the
	 * caloriesBurned method for the actual calculation.
	 * 
	 * @param distance the total distance walked in metres
	 * @return integer representing total calories used
	 */
	public int updateCalories(float distance)
	{
		return caloriesBurned(distance);
	}
	

	/**
	 * This is a simple calculator that hopefully will give a rough estimate of of calories burned based on
	 * users activity (running/walking), weight and distance he or she has walked.
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
