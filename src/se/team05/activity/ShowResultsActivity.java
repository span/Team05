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
import java.util.concurrent.TimeUnit;

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
		
		
		//TODO App is unstable if this listener is invoked. Needs to be fixed! /Gustaf
//		Button deleteResultsButton = (Button) findViewById(R.id.delete_results_button);
//		deleteResultsButton.setOnClickListener(new ShowResultActivityButtonListener(this, id));	
		
	}

	
	/**
	 * 
	 */
	private void showResults()
	{
		int timeInSeconds = result.getTime();
		long millis = timeInSeconds * 1000;
		int distanceInMeters = result.getDistance();
		double speed = (distanceInMeters / timeInSeconds) * 3.6;
		long timestamp = result.getTimestamp();
		Date date = new Date(timestamp * 1000);

		//Present date
		TextView dateView = (TextView) findViewById(R.id.show_date_result_textview);
		String dateString = String.valueOf(date);
		dateView.setText(dateString);    
		
		//Present time
		TextView timeView = (TextView) findViewById(R.id.show_time_result_textview);
		String formattedTimeString = String.format(" %02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
	            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		timeView.setText(formattedTimeString);
		
		//Present distance
		TextView distanceView = (TextView) findViewById(R.id.show_distance_result_textview);
		String formattedDistanceString = String.format(" %,d", distanceInMeters);
		distanceView.setText(formattedDistanceString);
		
		//Present speed
		TextView speedView = (TextView) findViewById(R.id.show_speed_result_textview);		
		String speedString = String.valueOf(speed);
		speedView.setText(speedString);		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
}
