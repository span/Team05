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
import se.team05.data.DatabaseHandler;
import se.team05.listener.UseExistingRouteListener;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * An activity that will present the user with the option to choose and old route.
 * Gets routes from database and presents them in a listview.
 * 
 * @author Markus, Henrik Hugo
 *
 */
public class UseExistingRouteActivity extends ListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_use_existing_route);
		
		//setProgressBarIndeterminateVisibility(true);
		
		// Setup database connection and get cursor with results
		DatabaseHandler db = new DatabaseHandler(this);
		Cursor cursor = db.getAllRoutesCursor();
		
		// Setup adapter
		RouteListCursorAdapter routeListCursorAdapter = new RouteListCursorAdapter(
				this,
				android.R.layout.simple_list_item_1,
				cursor,
				new String[] {"name"}, new int[] {android.R.id.text1}
		);
		
		// Retrieve reference to listview and apply the adapter
		//ListView listView = (ListView) findViewById(R.id.list);
		//listView.setAdapter(routeListCursorAdapter);
		this.setListAdapter(routeListCursorAdapter);
		
		//setProgressBarIndeterminateVisibility(false);
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
		Intent intent;
		Bundle bundle = new Bundle();
		
		bundle.putLong("id", id);
		
		intent = new Intent(this.getApplicationContext(), ShowExistingRouteActivity.class);
		intent.putExtra("id", id);
		
		Log.d("Id", String.valueOf(id));
		Log.d("Position", String.valueOf(position));
		
		this.startActivity(intent);
	}
	
	/**
	 * Simple class for automatically formating the content, from a cursor returned
	 * by the database, to a listview.
	 * @author Henrik Hugo
	 *
	 */
	private class RouteListCursorAdapter extends SimpleCursorAdapter {

		@SuppressWarnings("deprecation")
		public RouteListCursorAdapter(Context context, int layout, Cursor c,
				String[] from, int[] to) {
			super(context, layout, c, from, to);
		}
		
		@SuppressWarnings("deprecation")
		public RouteListCursorAdapter(Context context, int layout, Cursor c)
		{
			super(context, layout, c, null, null);
		}


	}

}


