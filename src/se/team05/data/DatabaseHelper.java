package se.team05.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DBRouteAdapter.DATABASE_CREATE_ROUTE_TABLE);
		database.execSQL(DBTrackAdapter.DATABASE_CREATE_TRACK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DBRouteAdapter.TABLE_ROUTES);
		db.execSQL("DROP TABLE IF EXISTS " + DBTrackAdapter.TABLE_TRACKS);
		onCreate(db);
	}

}