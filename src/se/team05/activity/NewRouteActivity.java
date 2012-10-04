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
package se.team05.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import se.team05.R;
import se.team05.content.Routes;
import se.team05.content.Track;
import se.team05.data.DatabaseHandler;
import se.team05.dialog.EditCheckPointDialog;
import se.team05.dialog.SaveRouteDialog;
import se.team05.listener.MapLocationListener;
import se.team05.listener.MapOnGestureListener;
import se.team05.overlay.CheckPoint;
import se.team05.overlay.CheckPointOverlay;
import se.team05.overlay.RouteOverlay;
import se.team05.view.EditRouteMapView;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

/**
 * This activity Presents at map to the user. It also tracks the users movement
 * and will paint the route as the user moves. This is accomplished by using
 * Google's map API.
 * 
 * @author Markus Schutzer, Patrik Thitusson, Daniel Kvist
 * 
 */
public class NewRouteActivity extends MapActivity implements View.OnClickListener, EditCheckPointDialog.Callbacks,
		CheckPointOverlay.Callbacks, MapOnGestureListener.Callbacks, MapLocationListener.Callbacks
{

	private ArrayList<GeoPoint> route;
	private LocationManager locationManager;
	private String providerName;
	private int routeIdTag;
	private EditRouteMapView mapView;
	private boolean started = false;
	private MyLocationOverlay myLocationOverlay;
	private String userSpeed = "0";
	private String userDistance = "0";
	private GeoPoint lastPoint;
	private Location lastLocation;
	private float[] distanceResult = new float[3];
	private float totalDistance = 0;
	private String lengthPresentation = DISTANCE_UNIT_METRES;
	private String userDistanceRun = TOTAL_DISTANCE + userDistance + lengthPresentation;
	private CheckPointOverlay checkPointOverlay;
	private EditCheckPointDialog checkPointDialog;

	private static String DISTANCE_UNIT_MILES = "miles";
	private static String DISTANCE_UNIT_YARDS = "yards";
	private static String DISTANCE_UNIT_KILOMETRE = "Km";
	private static String DISTANCE_UNIT_METRES = " metres";
	private static float DISTANCE_THRESHOLD_EU = 1000;
	private static String TOTAL_DISTANCE = "Total Distance: ";
	private ArrayList<Track> selectedTracks = new ArrayList<Track>();
	private DatabaseHandler databaseHandler;
	private CheckPoint currentCheckPoint;;

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
		setContentView(R.layout.activity_while_running);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		routeIdTag = Routes.getInstance().getCount();
		route = new ArrayList<GeoPoint>();

		databaseHandler = new DatabaseHandler(this);

		Button stopAndSaveButton = (Button) findViewById(R.id.stop_and_save_button);
		stopAndSaveButton.setOnClickListener(this);

		Button startRunButton = (Button) findViewById(R.id.start_run_button);
		startRunButton.setOnClickListener(this);

		Button addCheckPointButton = (Button) findViewById(R.id.add_checkpoint);
		addCheckPointButton.setOnClickListener(this);

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
			System.out.println("NO PROVIDER:" + providerName);
		}

		List<Overlay> overlays = mapView.getOverlays();
		Drawable drawable = getResources().getDrawable(R.drawable.green_markerc);

		RouteOverlay routeOverlay = new RouteOverlay(route, 78, true);
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		checkPointOverlay = new CheckPointOverlay(drawable, this);

		overlays.add(routeOverlay);
		overlays.add(myLocationOverlay);
		overlays.add(checkPointOverlay);

		mapView.postInvalidate();
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
			selectedTracks = data.getParcelableArrayListExtra(MediaSelectorActivity.EXTRA_SELECTED_ITEMS);
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
	 * put it in a list (route).
	 * 
	 * @param location
	 *            the new location of the user
	 */
	@Override
	public void updateLocation(Location location)
	{

		if (started)
		{
			GeoPoint p = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
			route.add(p);

			userSpeed = "Your Speed: " + location.getSpeed() + DISTANCE_UNIT_KILOMETRE + "/h";

			// if(lastPoint != null)
			// {
			// Location.distanceBetween(p.getLatitudeE6(), p.getLongitudeE6(),
			// lastPoint.getLatitudeE6(), lastPoint.getLongitudeE6(),
			// distanceResult);
			//
			// if(distanceResult[0] != 0)
			// {
			// totalDistance += distanceResult[0];
			// userDistance = "Total Distance: " + totalDistance + "meter?";
			// }
			// }

			if (lastLocation != null)
			{
				totalDistance += lastLocation.distanceTo(location);

				if (totalDistance >= DISTANCE_THRESHOLD_EU)
				{
					lengthPresentation = DISTANCE_UNIT_KILOMETRE;
					userDistance = new DecimalFormat("#.##").format(totalDistance / 1000);
				}
				else
				{
					userDistance = "" + (int) totalDistance;
				}

				userDistanceRun = TOTAL_DISTANCE + userDistance + lengthPresentation;
			}

			lastPoint = p;
			lastLocation = location;

			TextView speedView = (TextView) findViewById(R.id.show_speed_textview);
			speedView.setText(userSpeed);

			TextView distanceView = (TextView) findViewById(R.id.show_distance_textview);
			distanceView.setText(userDistanceRun);

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
	 * Get method for returning the Routelist consisting of geopoints.
	 * 
	 * @return ArrayList representing Geo Points.
	 */
	public ArrayList<GeoPoint> getRoute()
	{
		return route;
	}

	/**
	 * Button listener for this activity. Will activate the desired outcome of
	 * any of the three buttons.
	 * 
	 * @param v
	 *            the button being pressed.
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.start_run_button:
				started = true;
				View v2 = findViewById(R.id.start_run_button);
				v2.setVisibility(View.GONE);
				View v3 = findViewById(R.id.stop_and_save_button);
				v3.setVisibility(View.VISIBLE);
				break;
			case R.id.stop_and_save_button:
				SaveRouteDialog saveRouteDialog = new SaveRouteDialog(this);
				saveRouteDialog.show();
//				databaseHandler.saveRoute(new Route("name", "description", 0, -1, -1));
//				Intent intent = new Intent(this, MainActivity.class);
//				this.startActivity(intent);
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
		}
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
		checkPoint.setId(databaseHandler.saveCheckPoint(checkPoint));
		for (Track track : selectedTracks)
		{
			databaseHandler.saveTrack(checkPoint.getId(), track);
		}
		selectedTracks.clear();
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
	 * it as a add dialog. This method also saves the newly created checkpoint as
	 * the current checkpoint for future reference.
	 * 
	 * @param geoPoint
	 */
	private void createCheckPoint(GeoPoint geoPoint)
	{
		CheckPoint checkPoint = new CheckPoint(geoPoint);
		currentCheckPoint = checkPoint;
		checkPointOverlay.addCheckPoint(checkPoint);
		showCheckPointDialog(checkPoint, EditCheckPointDialog.MODE_ADD);
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
}