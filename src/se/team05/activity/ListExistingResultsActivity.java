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

import java.util.List;

import org.achartengine.GraphicalView;

import se.team05.R;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.data.DBResultAdapter;
import se.team05.data.DatabaseHandler;
import se.team05.view.TimeStretchChartView;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * An activity that will present the user with the option to view results of an old route.
 * Gets results from database and presents them in a listview.
 * 
 * @author Gustaf Werlinder
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
						android.R.layout.simple_list_item_1,
						cursor,
						new String[] {DBResultAdapter.COLUMN_ID},
						new int[] {android.R.id.text1},
						Adapter.NO_SELECTION);
		
		setListAdapter(adapter);
		//cursor.close();
		List<Result> allResults = db.getAllResultsByRid(rid);
		if(allResults!=null&&allResults.size()>0)
		{
			LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
			GraphicalView timeStretchChartView = TimeStretchChartView.getNewInstance(this, allResults, db.getRoute(rid).getName());
			chartContainer.addView(timeStretchChartView);
		}
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Context context = this;
		Intent intent;
		intent = new Intent(context, ShowResultsActivity.class);
		intent.putExtra(Result.RESULT_ID, 1);
		context.startActivity(intent);
	}
}
