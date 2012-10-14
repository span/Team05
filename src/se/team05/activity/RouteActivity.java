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

    (C) Copyright 2012: Daniel Kvist, Henrik Hugo, Gustaf Werlinder, Patrik Thitusson, Markus Schutzer
 */

package se.team05.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import se.team05.R;
import se.team05.content.ParcelableGeoPoint;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.content.Track;
import se.team05.data.DatabaseHandler;
import se.team05.dialog.AlertDialogFactory;
import se.team05.dialog.EditCheckPointDialog;
import se.team05.dialog.SaveRouteDialog;
import se.team05.listener.MapLocationListener;
import se.team05.listener.MapOnGestureListener;
import se.team05.overlay.CheckPoint;
import se.team05.overlay.CheckPointOverlay;
import se.team05.overlay.RouteOverlay;
import se.team05.service.MediaService;
import se.team05.util.Utils;
import se.team05.view.EditRouteMapView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

/**
 * The main use of this activity and of this application in general is that the
 * user is supposed to run a route of his or her choice and then be able to save
 * it. As the main idea is that a user will run a route, we will from here on
 * refer to this action as "running" or "exercise".
 * 
 * This activity Presents a map to the user. In the main menu the user gets the
 * choice of running a new route or an existing one he/she has saved from
 * earlier and thusly this activity will serve both functions. If the user
 * chooses to run a new run he/she will be presented with a map and the
 * possibility to record a new route. Recording will paint the path that the
 * user undertakes, as well as time distance and speed. The user can also place
 * checkpoints, which the user can use to activate music or sound at a given
 * location. Both checkpoints and paths is represented by geopoints. After
 * completion, the possibility to save this route will appear and the user gets
 * transferred to the start screen. If the user chooses an old route instead,
 * the old one will be painted in grey at the start and a new path in blue will
 * gradually get painted as the user moves along. When the user is done he or
 * she will instead be presented with the possibility to save the result
 * 
 * @author Markus Schutzer, Patrik Thitusson, Daniel Kvist
 */
public class RouteActivity extends MapActivity implements View.OnClickListener, EditCheckPointDialog.Callbacks,
		SaveRouteDialog.Callbacks, CheckPointOverlay.Callbacks, MapOnGestureListener.Callbacks,
		MapLocationListener.Callbacks, Utils.Callbacks
{
	private static final String TAG = "Personal trainer";
	private LocationManager locationManager;
	private String providerName;
	private EditRouteMapView mapView;
	private MyLocationOverlay myLocationOverlay;
	private String userSpeed = "0";
	private String userDistance = "0";
	private Location lastLocation;
	private CheckPointOverlay checkPointOverlay;
	private EditCheckPointDialog checkPointDialog;
	private DatabaseHandler databaseHandler;
	private CheckPoint currentCheckPoint;
	private Result routeResults;
	private List<Overlay> overlays;
	private Button stopAndSaveButton;
	private Button startButton;
	private Route route;
	private WakeLock wakeLock;
	private TextView speedView;
	private TextView distanceView;
	private Intent serviceIntent;

	private static final int DIALOG_NONE = -1;
	private static final int DIALOG_SAVE_ROUTE = 0;
	private static final int DIALOG_SAVE_RESULT = 1;
	private static final int DIALOG_CHECKPOINT = 2;
	private SaveRouteDialog saveRouteDialog;
	private TextView timeView;
	private AlertDialog saveResultDialog;

	/**
	 * Will present a map to the user and will also display a dot representing
	 * the user's location. Also contains three buttons of which one
	 * (startRunButton) will start the recording of the user's movement and will
	 * paint the track accordingly on the map. The button stopAndSaveButton will
	 * finish the run and save it. As of now it is recorded in the memory but
	 * later it will have database functionality. The button addCheckPointButton
	 * will place a checkpoint at the user's current location.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		databaseHandler = new DatabaseHandler(this);
		serviceIntent = new Intent(this, MediaService.class);
		route = new Route(getString(R.string.new_route), getString(R.string.this_is_a_new_route));
		wakeLock = Utils.acquireWakeLock(this);
		
		setupMapAndLocation();
		if (savedInstanceState != null)
		{
			restoreInstance(savedInstanceState);
		}
		else
		{
			route.setId(getIntent().getLongExtra(Route.EXTRA_ID, -1));
		}

		if (!route.isNewRoute())
		{
			initRouteAndCheckpoints();
			setTitle(getString(R.string.saved_route_) + route.getName());
		}
		setupButtons();

		if (route.isStarted())
		{
			startRoute();
		}
		
		timeView = (TextView) findViewById(R.id.show_time_textview);
		mapView.postInvalidate();
	}

	/**
	 * This method restores the instance after a configuration change has
	 * happen. Important data is saved in the OnSavedInstanceState and contains
	 * more data if a dialog is shown.
	 * 
	 * @param savedInstanceState
	 */
	private void restoreInstance(Bundle savedInstanceState)
	{
		route.setId(savedInstanceState.getLong("rid"));
		route.setTotalDistance(savedInstanceState.getFloat("totalDistance"));
		route.setTimePassed(savedInstanceState.getInt("timePassed"));
		route.setStarted(savedInstanceState.getBoolean("started"));
		
		ArrayList<ParcelableGeoPoint> geoPoints = savedInstanceState.getParcelableArrayList("geoPointList");
		route.setGeoPoints(geoPoints);
		RouteOverlay routeOverlay = new RouteOverlay(route.getGeoPoints(), 78, true);
		overlays.add(routeOverlay);
		if (route.isNewRoute())
		{
			checkPointOverlay.setCheckPoints(route.getCheckPoints());
		}
		
		int activeDialog = savedInstanceState.getInt("dialogShown");
		switch (activeDialog)
		{
			case DIALOG_CHECKPOINT:
				long checkPointId = savedInstanceState.getLong("currentCheckPoint");
				currentCheckPoint = databaseHandler.getCheckPoint(checkPointId);
				ArrayList<Track> tracks = savedInstanceState.getParcelableArrayList("tracks");
				currentCheckPoint.addTracks(tracks);
				showCheckPointDialog(currentCheckPoint, EditCheckPointDialog.MODE_EDIT);
				break;
			case DIALOG_SAVE_ROUTE:
				showSaveRouteDialog();
				saveRouteDialog.setNameOfEditText(savedInstanceState.getString("nameOfEditText"));
				saveRouteDialog.setDescriptionOfEditText(savedInstanceState.getString("descriptionOfEditText"));
				saveRouteDialog.setSaveResultChecked(savedInstanceState.getBoolean("isSaveResultChecked"));
				break;
			case DIALOG_SAVE_RESULT:
				showSaveResultDialog(route.getId());
				break;
		}
	}

	/**
	 * Starts a new alert dialog which shows the distance and time and asks the
	 * user if the results should be saved or not.
	 * 
	 * @param rid
	 */
	private void showSaveResultDialog(long rid)
	{
		routeResults = new Result(rid, (int) System.currentTimeMillis() / 1000, route.getTimePassed(),
				(int) route.getTotalDistance(), 0);
		String giveUserDistanceString = getString(R.string.distance_of_run) + userDistance + getString(R.string.km)
				+ "\n";
		String giveUserTimeString = getString(R.string.time_) + route.getTimePassedAsString() + "\n\n";
		String giveUserResultData = giveUserDistanceString + giveUserTimeString;
		saveResultDialog = AlertDialogFactory.newSaveResultDialog(this, giveUserResultData, route, routeResults);
		saveResultDialog.show();
		route.setStarted(false);
		wakeLock = Utils.releaseWakeLock();
	}

	/**
	 * Sets up the map view and the location. Sets the update rate of the
	 * location to 3000 milliseconds. Also calls the Routeoverlay which is
	 * responsible for painting the user's path on the map and tries to get a
	 * hold of the GPS-provider.
	 */
	private void setupMapAndLocation()
	{
		distanceView = (TextView) findViewById(R.id.show_distance_textview);
		speedView = (TextView) findViewById(R.id.show_speed_textview);
		mapView = (EditRouteMapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setOnGestureListener(new MapOnGestureListener(this));

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MapLocationListener(this));
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		providerName = locationManager.getBestProvider(criteria, true);
		if (providerName != null)
		{
			Log.d(TAG, getString(R.string.provider_) + providerName);
		}

		overlays = mapView.getOverlays();
		Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
		RouteOverlay routeOverlay = new RouteOverlay(route.getGeoPoints(), 78, true);
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		checkPointOverlay = new CheckPointOverlay(drawable, this);
		overlays.add(routeOverlay);
		overlays.add(myLocationOverlay);
		overlays.add(checkPointOverlay);
	}

	/**
	 * Sets up the buttons in the view. If it is a new route the Start and
	 * stopAndSaved buttons are initialized with listeners and if it is a
	 * existing route the start and stopAndSaved buttons are hidden and the
	 * buttons Run and stop are initialized with listeners and visible.
	 */
	private void setupButtons()
	{
		Button addCheckPointButton = (Button) findViewById(R.id.add_checkpoint);
		Button showResultButton = (Button) findViewById(R.id.show_result_button);
		stopAndSaveButton = (Button) findViewById(R.id.stop_button);
		startButton = (Button) findViewById(R.id.start_button);
		stopAndSaveButton.setOnClickListener(this);
		startButton.setOnClickListener(this);
		if (route.isNewRoute())
		{
			addCheckPointButton.setOnClickListener(this);
		}
		else
		{
			showResultButton.setOnClickListener(this);
			showResultButton.setVisibility(View.VISIBLE);
			addCheckPointButton.setVisibility(View.GONE);
		}
	}

	/**
	 * Gets route information from the database and draws an overlay on the map
	 * view if the user is using a previously saved map.
	 * @param geoPoints 
	 * 
	 * @param id
	 *            the route id
	 */
	private void initRouteAndCheckpoints()
	{
		route = databaseHandler.getRoute(route.getId());
		RouteOverlay routeOverlay = new RouteOverlay(route.getGeoPoints(), 10, true);
		overlays.add(routeOverlay);
		checkPointOverlay.setCheckPoints(route.getCheckPoints());
	}

	/**
	 * This is called when the user has selected a media from the media
	 * selection activity. A list of tracks is then passed back as a result
	 * which this method then saves into the database.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MediaSelectorActivity.REQUEST_MEDIA && resultCode == RESULT_OK)
		{
			ArrayList<Track> selectedTracks = data.getParcelableArrayListExtra(MediaSelectorActivity.EXTRA_SELECTED_ITEMS);
			currentCheckPoint.addTracks(selectedTracks);
		}
	}

	/**
	 * Unused method, must implement this because of MapActivity inheritance.
	 * Might be implemented in a later stage.
	 */
	@Override
	protected boolean isRouteDisplayed()
	{
		return false;
	}

	/**
	 * This will be called when user changes location. It will create a new
	 * Geopoint consisting of longitude and latitude represented by integers and
	 * put it in a list (geoPointList). It will also get the user's speed and
	 * total distance traveled and convert this data into strings to be
	 * presented on the screen. As of now this method will be called once every
	 * three seconds, this number is a tradeoff between fast updates which would
	 * be needed for doing fast paced activities like cycling and slower like
	 * walking. Slow activities could do with a lesser update interval and as
	 * such preserve battery life but as of this version the user does not have
	 * the possibility to choose what kind of activity to undertake and thus the
	 * value is hard coded.
	 * 
	 * @param location
	 *            the new location of the user
	 */
	@Override
	public void updateLocation(Location location)
	{
		GeoPoint geoPoint;
		ParcelableGeoPoint currentGeoPoint;
		if (route.isStarted())
		{
			currentGeoPoint = new ParcelableGeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
			route.getGeoPoints().add(currentGeoPoint);
			userSpeed = (3.6 * location.getSpeed()) + getString(R.string.km) + "/" + getString(R.string.h);
			if (lastLocation != null)
			{
				route.setTotalDistance(route.getTotalDistance() + lastLocation.distanceTo(location));
				userDistance = new DecimalFormat("#.##").format(route.getTotalDistance() / 1000);
			}
			lastLocation = location;
			if (!route.isNewRoute())
			{
				for (CheckPoint checkPoint : route.getCheckPoints())
				{
					geoPoint = checkPoint.getPoint();
					if (MapLocationListener.getDistance(currentGeoPoint, geoPoint) <= checkPoint.getRadius())
					{
						if (checkPoint != currentCheckPoint)
						{
							stopService(serviceIntent);
							ArrayList<Track> playList = checkPoint.getTracks();
							if (playList.size() > 0)
							{
								serviceIntent.putExtra(MediaService.DATA_PLAYLIST, playList);
								serviceIntent.setAction(MediaService.ACTION_PLAY);
								try
								{
									startService(serviceIntent);
								}
								catch (Exception e)
								{
									Log.e(TAG, getString(R.string.could_not_start_media_service_) + e.getMessage());
								}
							}
							currentCheckPoint = checkPoint;
						}
						break;
					}
				}
			}
			lastLocation = location;
			speedView.setText(userSpeed);
			distanceView.setText(userDistance + getString(R.string.km));
			mapView.postInvalidate();
		}
	}

	/**
	 * When our activity resumes, we want to register for location updates.
	 */
	protected void onResume()
	{
		super.onResume();
		myLocationOverlay.enableMyLocation();
	}

	/**
	 * When our activity pauses, we want to remove listening for location
	 * updates
	 */
	protected void onPause()
	{
		super.onPause();
		myLocationOverlay.disableMyLocation();
	}

	/**
	 * Button listener for this activity. Will activate the desired outcome of
	 * any of the buttons. In the case of Start Run the button will disappear
	 * and will be replaced by a "Stop Run"-button, start run will also start
	 * the timer and the recording of the user's locations and start drawing his
	 * or hers route on the map. If the user presses the Stop Run-button the
	 * recording will stop and the user will be prompted to either save or
	 * discard this run. This will also stop the timer. The add checkpoint will
	 * place a checkpoint at the users current location similar to the single
	 * tap implementation. If user has chosen to use an old route (instead of
	 * recording a new one) two buttons appear: "Show results" and "Start Run"
	 * (start_existing_run_button) "Show results" button will start a new
	 * activity that shows the results for the route previously chosen.
	 * 
	 * @param v
	 *            the button being pressed.
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.start_button:
				startRoute();
				break;
			case R.id.stop_button:
				Utils.stopTimer();
				route.setStarted(false);
				if(!route.isNewRoute())
				{
					showSaveResultDialog(route.getId());
					stopService(serviceIntent);
				}
				else
				{
					showSaveRouteDialog();
				}
				startButton.setVisibility(View.VISIBLE);
				stopAndSaveButton.setVisibility(View.GONE);
				wakeLock = Utils.releaseWakeLock();
				break;
			case R.id.add_checkpoint:
				if (myLocationOverlay.isMyLocationEnabled())
				{
					GeoPoint geoPoint = myLocationOverlay.getMyLocation();
					if (geoPoint != null)
					{
						createCheckPoint(geoPoint);
					}
				}
				break;
			case R.id.show_result_button:
				Context context = this;
				Intent intent;
				intent = new Intent(context, ListExistingResultsActivity.class);
				intent.putExtra(Route.EXTRA_ID, route.getId());
				context.startActivity(intent);
				break;
		}
	}

	/**
	 * Starts the timer for a new route and change the button Start to
	 * StopAndSave
	 */
	private void startRoute()
	{
		wakeLock = Utils.acquireWakeLock(this);
		route.setStarted(true);
		startButton.setVisibility(View.GONE);
		stopAndSaveButton.setVisibility(View.VISIBLE);
		Utils.startTimer(this);
	}

	/**
	 * creates a new result and initates the saveRouteDialog
	 */
	private void showSaveRouteDialog()
	{
		routeResults = new Result(-1, -1, route.getTimePassed(), (int) route.getTotalDistance(), 0);
		saveRouteDialog = new SaveRouteDialog(this, this, routeResults);
		saveRouteDialog.show();
	}

	/**
	 * Method that gets called to update the UI with how much time that has
	 * passed and presents this to the user. Will use field timePassed to
	 * determine time while not altering the timePassed variable if we want to
	 * pass that value to the database.
	 */
	@Override
	public void onTimerTick()
	{
		timeView.setText(route.getTimePassedAsString());
		route.setTimePassed(route.getTimePassed() + 1);
	}

	/**
	 * This method deletes the checkpoint with all its tracks from the database.
	 * It also calls on the checkpoint overlay to delete its current selected
	 * checkpoint from the view. We then need to invalidate the map view for it
	 * to update.
	 */
	@Override
	public void onDeleteCheckPoint(long checkPointId)
	{
		databaseHandler.deleteCheckPoint(checkPointId);
		databaseHandler.deleteTracksByCid(checkPointId);
		checkPointOverlay.deleteCheckPoint();
		mapView.postInvalidate();
	}

	/**
	 * Callback from the checkpoint dialog that tells the activity that the user
	 * has pressed the save button and thus the checkpoint with all its data
	 * should now be saved. This method also saves the "tracks" that are related
	 * to the checkpoint.
	 */
	@Override
	public void onSaveCheckPoint(CheckPoint checkPoint)
	{
		long cid = checkPoint.getId();
		if (cid > 0)
		{
			databaseHandler.updateCheckPoint(checkPoint);
			databaseHandler.deleteTracksByCid(cid);
		}
		else
		{
			cid = databaseHandler.saveCheckPoint(checkPoint);
			checkPoint.setId(cid);
		}
		for (Track track : checkPoint.getTracks())
		{
			databaseHandler.saveTrack(cid, track);
		}
		checkPoint.getTracks().clear();
	}

	/**
	 * When a checkpoint is tapped this method calls the showCheckPointDialog
	 * method with MODE_EDIT which marks it as a edit dialog. This method also
	 * sets the current checkpoint to the last tapped.
	 */
	@Override
	public void onCheckPointTap(CheckPoint checkPoint)
	{
		currentCheckPoint = checkPoint;
		showCheckPointDialog(checkPoint, EditCheckPointDialog.MODE_EDIT);
	}

	/**
	 * Initiates a new checkpoint dialog
	 * 
	 * @param checkPoint
	 * @param mode
	 */
	private void showCheckPointDialog(CheckPoint checkPoint, int mode)
	{
		checkPointDialog = new EditCheckPointDialog(this, checkPoint, mode);
		checkPointDialog.show();
	}

	/**
	 * Creates a checkpoint with the geopoint and adds it to the checkpoint
	 * overlay, it also calls showCheckPointDialog with the MODE_ADD which marks
	 * it as a add dialog. This method also saves the newly created checkpoint
	 * as the current checkpoint for future reference.
	 * 
	 * @param geoPoint
	 */
	private void createCheckPoint(GeoPoint geoPoint)
	{
		CheckPoint checkPoint = new CheckPoint(geoPoint);
		currentCheckPoint = checkPoint;
		checkPointOverlay.addCheckPoint(checkPoint);
		showCheckPointDialog(checkPoint, EditCheckPointDialog.MODE_ADD);
		mapView.postInvalidate();
	}

	/**
	 * The onTap method zooms in on double tap and creates a geopoint on single
	 * tap which it sends to createCheckPoint
	 */
	@Override
	public void onTap(int x, int y, int eventType)
	{
		switch (eventType)
		{
			case MapOnGestureListener.EVENT_DOUBLE_TAP:
				mapView.getController().zoomInFixing(x, y);
				break;
			case MapOnGestureListener.EVENT_SINGLE_TAP:
				if (checkPointDialog == null || !checkPointDialog.isShowing())
				{
					GeoPoint geoPoint = mapView.getProjection().fromPixels(x, y);
					createCheckPoint(geoPoint);
				}
				break;
		}
	}

	/**
	 * This method is called when an item in the action bar (options menu) has
	 * been pressed. Currently this only takes the user to the parent activity
	 * (main activity).
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * This method is called from the save geoPointList dialog when the user has
	 * pressed the "save" button. It creates a new geoPointList with the
	 * information given in the dialog and then saves it to the database. After
	 * that, the user is taken back to the main activity.
	 */
	@Override
	public void onSaveRoute(String name, String description, boolean saveResult)
	{
		Route route = new Route(name, description);
		route.setId(databaseHandler.saveRoute(route));
		if (saveResult)
		{
			routeResults.setRid(route.getId());
			routeResults.setTimestamp((int) System.currentTimeMillis() / 1000);
			databaseHandler.saveResult(routeResults);
		}
		databaseHandler.saveGeoPoints(route.getId(), route.getGeoPoints());
		databaseHandler.updateCheckPointRid(route.getId());
		launchMainActivity();
	}

	/**
	 * Called when a route is being dismissed. This method just launches the
	 * main activity.
	 */
	@Override
	public void onDismissRoute()
	{
		databaseHandler.deleteCheckPoints(route.getId());
		launchMainActivity();
	}

	/**
	 * Called by the system when the activity is shut down completely. Releases
	 * the wake lock.
	 */
	@Override
	public void onDestroy()
	{
		wakeLock = Utils.releaseWakeLock();
		super.onDestroy();
	}

	/**
	 * Private helper method to launch the main activity.
	 */
	private void launchMainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
	}

	/**
	 * Saves all important data to be able to handle configuration changes. if a
	 * dialog is shown it saves some extra fields to be able to restore the
	 * dialog with its properties and fields
	 */
	@Override
	protected void onSaveInstanceState(final Bundle outState)
	{
		int activeDialog = getActiveDialog();
		outState.putBoolean("started", route.isStarted());
		outState.putFloat("totalDistance", route.getTotalDistance());
		outState.putInt("timePassed", route.getTimePassed());
		outState.putLong("rid", route.getId());
		outState.putInt("dialogShown", activeDialog);
		outState.putParcelableArrayList("geoPointList", route.getGeoPoints());
		if (activeDialog == DIALOG_CHECKPOINT)
		{
			currentCheckPoint = checkPointDialog.getCheckPoint();
			onSaveCheckPoint(currentCheckPoint);
			activeDialog = DIALOG_CHECKPOINT;
			outState.putLong("currentCheckPoint", currentCheckPoint.getId());
			outState.putParcelableArrayList("tracks", currentCheckPoint.getTracks());
		}
		else if (activeDialog == DIALOG_SAVE_ROUTE)
		{
			outState.putString("nameOfEditText", saveRouteDialog.getNameOfEditText());
			outState.putString("descriptionOfEditText", saveRouteDialog.getDescriptionOfEditText());
			outState.putBoolean("isSaveResultChecked", saveRouteDialog.isSaveResultChecked());
		}
	}

	/**
	 * Private helper method to assist in calculating which, if any, dialog is
	 * shown
	 * 
	 * @return the dialog shown constant
	 */
	private int getActiveDialog()
	{
		int activeDialog = DIALOG_NONE;
		if (checkPointDialog != null && checkPointDialog.isShowing())
		{
			activeDialog = DIALOG_CHECKPOINT;
		}
		if (saveRouteDialog != null && saveRouteDialog.isShowing())
		{
			activeDialog = DIALOG_SAVE_ROUTE;
		}
		if (saveResultDialog != null && saveResultDialog.isShowing())
		{
			activeDialog = DIALOG_SAVE_RESULT;
		}
		return activeDialog;
	}

	/**
	 * Callback method which starts the timer again after back button is pressed
	 * when a dialog is shown
	 */
	@Override
	public void onResumeTimer()
	{
		Utils.startTimer(this);
	}

	/**
	 * Shows a alert dialog when back button is pressed to confirm that the user
	 * wants to discard the route. This is implemented to prevent the user to
	 * hit the back button by mistake and quit the route.
	 */
	@Override
	public void onBackPressed()
	{
		AlertDialog alertDialog = AlertDialogFactory.newConfirmBackDialog(this);
		alertDialog.show();
	}
}
