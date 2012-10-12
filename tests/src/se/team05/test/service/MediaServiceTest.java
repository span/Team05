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
package se.team05.test.service;

import java.util.ArrayList;

import se.team05.content.Track;
import se.team05.service.MediaService;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.test.ServiceTestCase;

/**
 * This clas tests the start and stop of the MediaService class that is used to
 * play tracks in the background.
 * 
 * @author Daniel Kvist
 * 
 */
public class MediaServiceTest extends ServiceTestCase<MediaService>
{
	private ArrayList<Track> playList;
	private String[] projection = { MediaStore.Audio.Media.DATA };

	public MediaServiceTest()
	{
		super(MediaService.class);
	}

	/**
	 * Sets up the basic playlist information that is needed to play a track.
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
		cursor.moveToFirst();
		Track track = new Track("id", "artist", "album", "title", cursor.getString(0), "displayName", "duration");
		playList = new ArrayList<Track>();
		playList.add(track);
	}

	/**
	 * Test basic startup/shutdown of Service
	 * 
	 * @throws InterruptedException
	 */
	public void testStartable() throws InterruptedException
	{
		Intent serviceIntent = new Intent();
		serviceIntent.setClass(getContext(), MediaService.class);
		serviceIntent.putExtra(MediaService.DATA_PLAYLIST, playList);
		serviceIntent.setAction(MediaService.ACTION_PLAY);
		startService(serviceIntent);
	}

	/**
	 * Test binding to service
	 */
	public void testBindable()
	{
		Intent serviceIntent = new Intent();
		serviceIntent.setClass(getContext(), MediaService.class);
		IBinder service = bindService(serviceIntent);
	}
}
