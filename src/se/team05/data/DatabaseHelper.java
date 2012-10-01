package se.team05.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

  public static final String TABLE_ROUTES = "routes";
  public static final String COLUMN_ID = "id";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_DESCRIPTION = "description";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_TIMECOACH = "timecoach";
  public static final String COLUMN_LENGTHCOACH = "lengthcoach";
  

  private static final String DATABASE_NAME = "data.db";
  private static final int DATABASE_VERSION = 1;

  /* 
   * CREATE TABLE table_name
(
column_name1 data_type,
column_name2 data_type,
column_name3 data_type,
....
)
*/
  
  // Database creation sql statement
  private static final String DATABASE_CREATE_ROUTE_TABLE = "create table "
      + TABLE_ROUTES 
      + "(" + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_NAME + " text not null, " 
      + COLUMN_DESCRIPTION + " text not null," 
      + COLUMN_TYPE + " integer not null," 
      + COLUMN_TIMECOACH + " integer not null,"
      + COLUMN_LENGTHCOACH + " integer not null);";
  

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE_ROUTE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(DatabaseHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
    onCreate(db);
  }

} 