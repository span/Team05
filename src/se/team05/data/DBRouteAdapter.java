package se.team05.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBRouteAdapter
{
	public static final String TABLE_ROUTES = "routes";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_TIMECOACH = "timecoach";
	public static final String COLUMN_LENGTHCOACH = "lengthcoach";

	public static final String DATABASE_CREATE_ROUTE_TABLE = "create table " + TABLE_ROUTES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_NAME + " text not null, " + COLUMN_DESCRIPTION
			+ " text not null," + COLUMN_TYPE + " integer not null," + COLUMN_TIMECOACH + " integer not null,"
			+ COLUMN_LENGTHCOACH + " integer not null);";

	private DatabaseHelper databaseHelper;
	private SQLiteDatabase db;

	public DBRouteAdapter(Context context)
	{
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
		values.put(COLUMN_NAME, "myRoute");
		values.put(COLUMN_DESCRIPTION, "myDescription");
		values.put(COLUMN_TYPE, 0);
		values.put(COLUMN_TIMECOACH, 0);
		values.put(COLUMN_LENGTHCOACH, 0);
		db.insert(TABLE_ROUTES, null, values);
	}
}
