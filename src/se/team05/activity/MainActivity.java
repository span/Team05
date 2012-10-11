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
import se.team05.listener.MainActivityButtonListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * This is the launching point of the application. The main activity simply
 * displays a few buttons which allows the user to choose which action to take
 * with the application. The main activity uses the external listener
 * MainActivityButtonListener to decide which Activity to launch next.
 * 
 * @author Daniel Kvist
 * 
 */
public class MainActivity extends Activity
{
	/**
	 * The onCreate method of the class starts off by setting the XML file which
	 * has the View content. It then picks up references to the buttons that are
	 * used and adds the View.onClickListener to both of them.
	 * 
	 * @param savedInstanceState
	 *            a bundle which contains any previous saved state.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button newRouteButton = (Button) findViewById(R.id.new_route_button);
		newRouteButton.setOnClickListener(new MainActivityButtonListener(this));

		Button useExistingButton = (Button) findViewById(R.id.use_existing_button);
		useExistingButton.setOnClickListener(new MainActivityButtonListener(this));
	}
}
