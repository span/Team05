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

package se.team05.activity;

import java.sql.Date;
import java.text.DecimalFormat;

import se.team05.R;
import se.team05.content.Result;
import se.team05.data.DatabaseHandler;
import se.team05.listener.MainActivityButtonListener;
import se.team05.listener.ShowResultActivityButtonListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


/**
 * An activity that will present the user with the option to view a result of an old route.
 * Gets results from database and present them.
 * 
 * @author Gustaf Werlinder
 *
 */
public class ShowResultsActivity extends Activity
{

	private long id;
	
	private DatabaseHandler databaseHandler;
	private Intent intent;
	private Result result;
	
	/**
	 * The onCreate method of the class starts off by setting the XML file which
	 * has the View content.
	 * 
	 * @param savedInstanceState
	 *            a bundle which contains any previous saved state.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_results);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		this.intent = getIntent();
		this.databaseHandler = new DatabaseHandler(this);
		
		this.id = intent.getLongExtra(Result.RESULT_ID, 1);
	
		this.result = databaseHandler.getResultById(id);
				
		showResults();
		
		Button deleteResultsButton = (Button) findViewById(R.id.delete_results_button);
		deleteResultsButton.setOnClickListener(new ShowResultActivityButtonListener(this, id));	
		
	}

	
	/**
	 * 
	 */
	private void showResults()
	{
		String formattedTimeString;
		String formattedDistanceString;
		String speedString;
		String dateString;
		
		int timeInSeconds = result.getTime();
		float timeInHours = timeInSeconds / 3600;
		int seconds = timeInSeconds % 60;
		int minutes = timeInSeconds % 3600;
		int hours = timeInSeconds / 3600;
		
		int distanceInMeters = result.getDistance();
		float distanceInKm = (distanceInMeters / 1000);
		
		float speed = distanceInKm / timeInHours;
		
		long timestamp = result.getTimestamp();
		Date date = new Date(timestamp * 1000);

		//Present date
		TextView dateView = (TextView) findViewById(R.id.show_date_result_textview);
		dateString = String.valueOf(date);
		dateView.setText(dateString);
		
		//Present time
		TextView timeView = (TextView) findViewById(R.id.show_time_result_textview);
		formattedTimeString = String.format(" %02d:%02d:%02d", hours, minutes, seconds);
		timeView.setText(formattedTimeString);
		
		//Present distance
		TextView distanceView = (TextView) findViewById(R.id.show_distance_result_textview);
		formattedDistanceString = String.format(" %,d", distanceInMeters);
		distanceView.setText(formattedDistanceString);
		
		//Present speed
		TextView speedView = (TextView) findViewById(R.id.show_speed_result_textview);		
		speedString = String.valueOf(speed);
		speedView.setText(speedString);		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
}
