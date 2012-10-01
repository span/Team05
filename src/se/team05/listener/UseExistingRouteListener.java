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


import se.team05.activity.ActivityWhileRunningOld;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * The listener for this activity, as of now i is just a button transporting the user to the ActivityWhileRunningOld
 * i.e. the saved route on map. This listener will be updated in a later release letting the user choose routes from
 * a ListView TODO Change comments to reflect this when it is done
 * @author Markus
 *
 */
public class UseExistingRouteListener implements OnClickListener
{

	private Context context;

	/**
	 * The constructor
	 * @param context the context sent to the object
	 */
	public UseExistingRouteListener(Context context)
	{
		this.context = context;
	}
	
	/**
	 * Listener for the button starting the next activity.
	 */
	public void onClick(View view) 
	{
		Intent intent = new Intent(context, ActivityWhileRunningOld.class);
		context.startActivity(intent);
	}

}
