package se.team05.util;

import se.team05.data.DBDemoAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * This async task loads the demo data stored in the sql files in the assets
 * folder. It needs to be instantiated with a context through the constructor.
 * 
 * @author Henrik Hugo, Daniel Kvist
 * 
 */
public class DemoDataLoader extends AsyncTask<Void, Void, Void>
{
	private Context context;
	private ProgressDialog progressDialog;

	/**
	 * Constrcutor which takes a context to be able to load the data from
	 * database and display the dialog.
	 * 
	 * @param context
	 */
	public DemoDataLoader(Context context)
	{
		this.context = context;
	}

	/**
	 * Before the task is doing its main work we set up a progress dialog to
	 * show.
	 */
	@Override
	protected void onPreExecute()
	{
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.setTitle("Loading...");
		progressDialog.setMessage("Loading demo data, please wait...");
		progressDialog.show();
	}

	/**
	 * The main work is being done here. First we clear the existing tables if
	 * any and the load the data from the assets. Finish off by initiating the
	 * tracks as well.
	 */
	@Override
	protected Void doInBackground(Void... params)
	{
		DBDemoAdapter dbDemoAdapter = new DBDemoAdapter(context);
		dbDemoAdapter.clearDB();
		dbDemoAdapter.loadSQLFile("checkpoint.sql");
		dbDemoAdapter.loadSQLFile("geopoints.sql");
		dbDemoAdapter.loadSQLFile("results.sql");
		dbDemoAdapter.loadSQLFile("routes.sql");
		dbDemoAdapter.initTracks();
		return null;
	}

	/**
	 * When we are done executing we can dismiss the dialog.
	 */
	@Override
	protected void onPostExecute(Void result)
	{
		progressDialog.dismiss();
	}

}
