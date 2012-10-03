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
import se.team05.listener.UseExistingRouteListener;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * An activity that will present the user with the option to choose and old route. As of now it is just a button but 
 * a future release will include a ListView representing the older routes saved in the database that the user
 * can choose from. TODO Change comments accordingly
 * 
 * @author Markus, Henrik Hugo
 *
 */
public class UseExistingRouteActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_use_existing_route);
		
		//Button runButton = (Button) findViewById(R.id.choose_saved_route_button);
		//runButton.setOnClickListener(new UseExistingRouteListener(this));
		
		setProgressBarIndeterminateVisibility(true);
		
		ListView listView = (ListView) findViewById(R.id.mylist);
		String[] values = new String[] { "Android", "iPhone", "Blackberry", "WindowsMobile",
				"WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2" };
		
		Route[] routes = new Route[] { new Route("First", "Lätt"), new Route("Second", "Medel"), new Route("Third", "Svår") };
		
		RouteArrayAdapter myAdapter = new RouteArrayAdapter(this, android.R.layout.simple_list_item_1, routes);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		
		listView.setAdapter(myAdapter);
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setProgressBarIndeterminateVisibility(false);
	}
	
	private class RouteArrayAdapter extends ArrayAdapter<Route>
	{
		
		public RouteArrayAdapter(Context context, int textViewResourceId, Route[] routes)
		{
			super(context, textViewResourceId, routes);
			
		}
	}
	
	private class Route
	{
		int _id;
		String name;
		String description;
		int type;
		boolean timecoach;
		boolean lengthcoach;
		
		public Route(String name, String description)
		{
			this.name = name;
			this.description = description;
			this.type = 0;
			this.timecoach = false;
			this.lengthcoach = false;
		}
		
		public String toString()
		{
			// TODO Ajabaja, returnera ett nytt objekt!
			return name;
		}
		
	}

}


