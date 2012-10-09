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
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_list_existing_results);
		
		
		// Add empty view with quick link to record a new route
//		this.context = getApplicationContext();
//		TextView emptyView = (TextView) findViewById(R.id.empty_view);
//		emptyView.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent = new Intent(context, RouteActivity.class);
//				startActivity(intent);
//			}
//		});
//		getListView().setEmptyView(emptyView);
		
		//setProgressBarIndeterminateVisibility(true);
		
		// Setup database connection and get cursor with results		
		long rid = getIntent().getLongExtra(Route.EXTRA_ID, -1);
		DatabaseHandler db = new DatabaseHandler(this);
		System.out.println("Print 2:" + rid);
		Cursor cursor = db.getAllResultsCursorByRid(rid);
		
		startManagingCursor(cursor);
		
		@SuppressWarnings("deprecation")
		ListAdapter adapter = new SimpleCursorAdapter(this,
						android.R.layout.simple_list_item_1,
						cursor,
						new String[] {DBResultAdapter.COLUMN_ID},
						new int[] {android.R.id.text1});
		
		setListAdapter(adapter);
		
		// Setup adapter
//		ResultListCursorAdapter resultListCursorAdapter = new ResultListCursorAdapter(
//				this,
//				android.R.layout.simple_list_item_1,
//				cursor);
//		
//		this.setListAdapter(resultListCursorAdapter);
//		
		//setProgressBarIndeterminateVisibility(false);
	}
	
//	public void onListItemClick(ListView l, View v, int position, long id)
//	{
//		//Starta dialog
//		Intent intent;
//		Bundle bundle = new Bundle();
//		
//		bundle.putLong("id", id);
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
