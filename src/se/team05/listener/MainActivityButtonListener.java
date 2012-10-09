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
package se.team05.listener;

import se.team05.R;
import se.team05.activity.NewRouteActivity;
import se.team05.activity.UseExistingRouteActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

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
	 * activity which corresponds to the view.
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
			case R.id.new_route_button:
				intent = new Intent(context, NewRouteActivity.class);
				break;
			case R.id.use_existing_button:
				intent = new Intent(context, UseExistingRouteActivity.class);
				break;
			default:
				return;
		}
		context.startActivity(intent);
	}
}
