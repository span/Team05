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
package se.team05.listener;

import se.team05.R;
import se.team05.activity.ListExistingRoutesActivity;
import se.team05.activity.RouteActivity;
import se.team05.activity.SettingsActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * This is the listener class for the main activity and it takes a Context as a
 * parameter which is used to launch the next Activity. This class is
 * responsible for deciding which button was clicked and it then responds
 * accordingly by launching the activity that the user wishes to see next. If no
 * matching View can be found we just do a default return.
 * 
 * @author Daniel Kvist
 * 
 */
public class MainActivityButtonListener implements OnClickListener
{
	private Context context;
	ImageView imageViewButton;

	/**
	 * A simple constructor for the listener that takes the Context as a
	 * parameter.
	 * 
	 * @param context
	 *            the Context (usually an Activity) in which the listener is
	 *            used
	 */
	public MainActivityButtonListener(Context context)
	{
		this.context = context;
	}

	/**
	 * The onClick method of the listener simply checks which View triggered the
	 * click event and then creates an intent that is used to launch the
	 * activity which corresponds to the view. Also will paint the button (represented by an imageview) in
	 * a transparent grey colour to inform the user that the button has been registered.
	 * 
	 * @param v
	 *            the view which triggers the event
	 */
	@Override
	public void onClick(View v)
	{
		Intent intent;
		switch (v.getId())
		{
			case R.id.image_new_route:
				intent = new Intent(context, RouteActivity.class);
				imageViewButton = (ImageView) v.findViewById(R.id.image_new_route);
				imageViewButton.setColorFilter(0x60EDEDED, PorterDuff.Mode.MULTIPLY);
				break;
			case R.id.image_existing_route:
				intent = new Intent(context, ListExistingRoutesActivity.class);
				imageViewButton = (ImageView) v.findViewById(R.id.image_existing_route);
				imageViewButton.setColorFilter(0x60EDEDED, PorterDuff.Mode.MULTIPLY);
				break;
			case R.id.image_settings:
				intent = new Intent(context, SettingsActivity.class);
				imageViewButton = (ImageView) v.findViewById(R.id.image_settings);
				imageViewButton.setColorFilter(0x60EDEDED, PorterDuff.Mode.MULTIPLY);
				break;
			default:
				return;
		}
	
		context.startActivity(intent);
	}
}
