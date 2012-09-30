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
import android.os.Bundle;
import android.widget.Button;

/**
 * An activity that will present the user with the option to choose and old route. As of now it is just a button but 
 * a future release will include a ListView representing the older routes saved in the database that the user
 * can choose from. TODO Change comments accordingly
 * 
 * @author Markus
 *
 */
public class UseExistingRouteActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_existing_route);
		
		Button runButton = (Button) findViewById(R.id.choose_saved_route_button);
		runButton.setOnClickListener(new UseExistingRouteListener(this));
	}


}
