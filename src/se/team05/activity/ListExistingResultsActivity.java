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

import se.team05.R;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.data.DBResultAdapter;
import se.team05.data.DBRouteAdapter;
import se.team05.data.DatabaseHandler;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * An activity that will present the user with the option to view results of old routes.
 * Gets results from database and presents them in a listview.
 * 
 * @author Gustaf
 * http://developer.android.com/reference/android/app/ListActivity.html
 *
 */
public class ListExistingResultsActivity extends ListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_existing_results);
		
		
		long rid = getIntent().getLongExtra(Route.EXTRA_ID, -1);
		DatabaseHandler db = new DatabaseHandler(this);
		
		Cursor cursor = db.getAllResultsCursorByRid(rid);
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
						//android.R.layout.simple_list_item_1,
						android.R.layout.two_line_list_item,
						cursor,
						new String[] {DBResultAdapter.COLUMN_TIME, DBResultAdapter.COLUMN_DISTANCE},
						new int[] {android.R.id.text1, android.R.id.text2},
						Adapter.NO_SELECTION);
		
		setListAdapter(adapter);
		//cursor.close();

	}
	
//	public void onListItemClick(ListView l, View v, int position, long id)
//	{

//		
//		intent = new Intent(this.getApplicationContext(), RouteActivity.class);
//		intent.putExtra(Route.EXTRA_ID, id);
//		
//		Log.d("Id", String.valueOf(id));
//		Log.d("Position", String.valueOf(position));
//		
//		this.startActivity(intent);
//	}
//	
	/**
	 * Simple class for automatically formating the content, from a cursor returned
	 * by the database, to a listview.
	 * @author Henrik Hugo
	 *
	 */
//	private class ResultListCursorAdapter extends SimpleCursorAdapter {
//
//		@SuppressWarnings("deprecation")
//		public ResultListCursorAdapter(Context context, int layout, Cursor c,
//										String[] from, int[] to)
//		{
//			super(context, layout, c, from, to);
//		}
//		
//		@SuppressWarnings("deprecation")
//		public ResultListCursorAdapter(Context context, int layout, Cursor c)
//		{
//			super(context, layout, c, null, null);
//		}
//
//
//	}

}
