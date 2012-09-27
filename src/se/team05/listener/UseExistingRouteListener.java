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
