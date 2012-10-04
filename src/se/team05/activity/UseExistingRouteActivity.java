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
import se.team05.data.DBRouteAdapter;
import se.team05.data.DatabaseHandler;
import se.team05.listener.UseExistingRouteListener;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * An activity that will present the user with the option to choose and old route.
 * Gets routes from database and presents them in a listview.
 * 
 * @author Markus, Henrik Hugo
 *
 */
public class UseExistingRouteActivity extends Activity implements LoaderCallbacks<Cursor>
{
	private static final int LOADER_ID_ROUTES = 42;
	
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
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this,
				android.R.layout.simple_list_item_1,
				null,
				new String[] {"name"},
				new int[] {android.R.id.text1},
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
		);
		
		getLoaderManager().initLoader(this.LOADER_ID_ROUTES, null, this);
		
		// Retrieve reference to listview and apply the adapter
		ListView listView = (ListView) findViewById(R.id.mylist);
		listView.setAdapter(adapter);
		
		//setProgressBarIndeterminateVisibility(false);
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

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		if(id == UseExistingRouteActivity.LOADER_ID_ROUTES) // Simple check as this is the only loader we have
		{
			String[] projection = {DBRouteAdapter.COLUMN_NAME};
		}
		
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {
		// TODO Auto-generated method stub
		
	}

}


