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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import se.team05.R;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.data.DBResultAdapter;
import se.team05.data.DatabaseHandler;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

/**
 * An activity that will present the user with the option to view results of an old route.
 * Gets results from database and presents them in a listview.
 * 
 * @author Gustaf Werlinder, Henrik Hugo, Daniel Kvist, Markus Schutser
 *
 */
public class ListExistingResultsActivity extends ListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_existing_results);		
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Context context = this;
		Intent intent;
		intent = new Intent(context, ShowResultsActivity.class);
		intent.putExtra(Result.RESULT_ID, id);
		context.startActivity(intent);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		long rid = getIntent().getLongExtra(Route.EXTRA_ID, -1);
		DatabaseHandler db = new DatabaseHandler(this);
		
		Cursor cursor = db.getAllResultsCursorByRid(rid);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_1,
						cursor,
						new String[] {DBResultAdapter.COLUMN_TIMESTAMP},
						new int[] {android.R.id.text1},
						Adapter.NO_SELECTION);
		
		// Hook method for formatting the timestamp retreived from the database
		// in the cursor into human readable date and time.
		adapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex)
			{
				int timestamp_index = cursor.getColumnIndex(DBResultAdapter.COLUMN_TIMESTAMP);
				
				if(timestamp_index == columnIndex)
				{
					//Retrieve timestamp
					String createDate = cursor.getString(timestamp_index);
					TextView textView = (TextView) view;
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
					sdf.setTimeZone(TimeZone.getDefault());
					
					String result = sdf.format(
							new Timestamp(
									Long.parseLong( // To long
											createDate.trim() //Trim excess of string
									)*1000 // Convert to milliseconds instead of seconds
							)
					);
					
					// Update textview
					textView.setText(result);
					return true;
				}
				
				return false;
			}
			
		});
		
		setListAdapter(adapter);
		//cursor.close();
	}
}
