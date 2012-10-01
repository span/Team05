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
package se.team05.data;

import se.team05.content.Track;
import android.content.Context;

/**
 * This class handles the communication between the database, its adapters and
 * the rest of the application. It keeps an instance of each table adapter that
 * is used for quick and easy access to the database.
 * 
 * @author Daniel Kvist, Gustaf Werlinder, Markus Schutzer, Patrik Thitusson,
 *         Henrik Hugo
 * 
 */
public class DatabaseHandler
{

	private DBRouteAdapter dBRouteAdapter;
	private DBTrackAdapter dbTrackAdapter;

	public DatabaseHandler(Context context)
	{
		dBRouteAdapter = new DBRouteAdapter(context);
		dbTrackAdapter = new DBTrackAdapter(context);
	}

	public void saveRoute()
	{
		dBRouteAdapter.open();
		dBRouteAdapter.createRoute();
		dBRouteAdapter.close();
	}

	/**
	 * Saves a track into the database and relates it to a checkpoint through
	 * the checkpoint id
	 * 
	 * @param track
	 *            the track to save
	 * @param checkPointId
	 *            the id of the checkpoint to relate the track to
	 */
	public void saveTrack(Track track, int checkPointId)
	{
		dbTrackAdapter.open();
		dbTrackAdapter.createTrack(checkPointId, track.getArtist(), track.getAlbum(), track.getTitle(),
				track.getData(), track.getDisplayName(), track.getDuration());
		dbTrackAdapter.close();
	}

}
