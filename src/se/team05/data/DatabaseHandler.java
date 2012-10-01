package se.team05.data;

import android.content.Context;

public class DatabaseHandler {

	private DBRouteAdapter dBRouteAdapter;
	
	public DatabaseHandler(Context context)
	{
		// TODO Auto-generated constructor stub
		
		dBRouteAdapter = new DBRouteAdapter(context);
	}	
	
	public void saveRoute()
	{		
		dBRouteAdapter.open();
		dBRouteAdapter.createRoute();
		dBRouteAdapter.close();
	}
	
}
