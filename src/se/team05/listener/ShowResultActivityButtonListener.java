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


import se.team05.data.DatabaseHandler;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
* This is the listener class for the ShowResultsActivity activity. This class responds
* to button clicked and deletes a specified result.
* 
* @author Gustaf Werlinder && Daniel Kvist
* 
*/
public class ShowResultActivityButtonListener implements OnClickListener
{

	private Context context;		
	private DatabaseHandler databaseHandler;
	private long id;
	
	/**
	 * A simple constructor for the listener that takes the Context as a
	 * parameter.
	 * 
	 * @param context
	 *            the Context (usually an Activity) in which the listener is
	 *            used
	 */
	public ShowResultActivityButtonListener(Context context, long id)
	{
		this.context = context;
		this.id = id;
		
	}
	
	/**
	 * The onClick method of the listener deletes the result specified by result id
	 * from the database. Then user returns to result list.
	 * 
	 * @param v
	 *       not used but needed to override method correctly
	 */
	@Override
	public void onClick(View v)
	{
		this.databaseHandler = new DatabaseHandler(context);
		databaseHandler.deleteResultById(id);
	}
}
