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
*/

package se.team05.activity;

import se.team05.R;
import se.team05.content.Result;
import se.team05.data.DatabaseHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


/**
 * An activity that will present the user with the option to view a result of an old route.
 * Gets results from database and present them.
 * 
 * @author Gustaf
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
	}

	
	/**
	 * 
	 */
	private void showResults()
	{
		int time = result.getTime();          
		int distance = result.getDistance();
		int timestamp = result.getTimestamp(); //Temporary during development
		float speed = distance / time;  //Temporary during development
		
		TextView timeView = (TextView) findViewById(R.id.show_time_result_textview);
		String timeString = String.valueOf(time);
		timeView.setText(timeString);
		
		TextView distanceView = (TextView) findViewById(R.id.show_distance_result_textview);
		String distanceString = String.valueOf(distance);
		distanceView.setText(distanceString);
		
		TextView speedView = (TextView) findViewById(R.id.show_speed_result_textview);
		String speedString = String.valueOf(speed);
		speedView.setText(speedString);
		
		TextView dateView = (TextView) findViewById(R.id.show_date_result_textview);
		String date = String.valueOf(timestamp);
		dateView.setText(date);	
	}

}
