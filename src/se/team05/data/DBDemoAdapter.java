package se.team05.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

public class DBDemoAdapter extends DBAdapter {
	
	Context context;

	public DBDemoAdapter(Context context) {
		super(context);
		
		this.context = context;
	}
	
	public boolean loadSQLFile(String filename)
	{
		this.open();
		
		BufferedReader br = null;
		try
		{

			br = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)), 1024 * 4);
			String line = null;
			db.beginTransaction();
			while ((line = br.readLine()) != null)
			{
				db.execSQL(line);
			}
			db.setTransactionSuccessful();
		} catch (IOException e)
		{
			Log.e("test", "read database init file error");
			return false;
		} finally
		{
			db.endTransaction();
			if (br != null)
			{
				try
				{
					br.close();
				} catch (IOException e)
				{
					Log.e("test", "buffer reader close error");
					return false;
				}
			}
		}
		this.close();
		return true;
	}
	
	public void clearDB()
	{
		this.open();
		
		db.delete(DBRouteAdapter.TABLE_ROUTES, null, null);
		db.delete(DBGeoPointAdapter.TABLE_GEOPOINTS, null, null);
		db.delete(DBCheckPointAdapter.TABLE_CHECKPOINTS, null, null);
		db.delete(DBResultAdapter.TABLE_RESULT, null, null);
		db.delete(DBTrackAdapter.TABLE_TRACKS, null, null);
		db.delete(DBResultAdapter.TABLE_RESULT, null, null);
		
		this.close();
	}

}
