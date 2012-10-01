package se.team05.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBRouteAdapter
{

	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;
	
	public DBRouteAdapter(Context context)
	{
		// TODO Auto-generated constructor stub
		databaseHelper = new DatabaseHelper(context);
	}
	
	public void open()
	{
		db = databaseHelper.getWritableDatabase();
	}

	public void close()
	{
		databaseHelper.close();
	}
	
	public void createRoute()
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_NAME, "myRoute");
		values.put(DatabaseHelper.COLUMN_DESCRIPTION, "myDescription");
		values.put(DatabaseHelper.COLUMN_TYPE, 0);
		values.put(DatabaseHelper.COLUMN_TIMECOACH, 0);
		values.put(DatabaseHelper.COLUMN_LENGTHCOACH, 0);
		db.insert(DatabaseHelper.TABLE_ROUTES, null, values);
	}
}
