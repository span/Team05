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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


/**
 * An activity that will present the user with the option to view results of old routes.
 * Gets results from database and presents them in a listview.
 * 
 * @author Gustaf
 *
 */
public class ShowResultsActivity extends Activity
{

	private long _id;
	private long routId;
	private long timestamp;
	private long time;
	private long calories;
	private long distance;
	
	private Intent intent;
	
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
		intent = getIntent();
		_id = intent.getLongExtra(Result.RESULT_ID, 1);
		
		showResults();
	}
		
	/**
	 * 
	 */
//	public ShowResultsActivity(long _id, long routId, long timestamp, long time, long calories, long distance)
//	{
//		this._id = _id;
//		this.routId = routId;
//		this.timestamp = timestamp;
//		this.time = time;
//		this.calories = calories;
//		this.distance = distance;
//	}
	
	/**
	 * 
	 */
	private void showResults()
	{
		TextView timeView = (TextView) findViewById(R.id.show_time_result_textview);
		String time = "4";
		timeView.setText(time);
		
		TextView distanceView = (TextView) findViewById(R.id.show_distance_result_textview);
		String distance = "5";
		distanceView.setText(distance);
		
		TextView speedView = (TextView) findViewById(R.id.show_speed_result_textview);
		String speed = "6";
		speedView.setText(speed);
		
		TextView dateView = (TextView) findViewById(R.id.show_date_result_textview);
		String date = "7";
		dateView.setText(date);		
	}

}
