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

public class UseExistingRouteListener implements OnClickListener
{

	private Context context;

	public UseExistingRouteListener(Context context)
	{
		this.context = context;
	}
	public void onClick(View view) 
	{
		Intent intent = new Intent(context, ActivityWhileRunningOld.class);
		context.startActivity(intent);
	}

}
