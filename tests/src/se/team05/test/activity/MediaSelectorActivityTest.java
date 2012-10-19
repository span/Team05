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

package se.team05.test.activity;

import java.util.ArrayList;

import se.team05.R;
import se.team05.activity.MediaSelectorActivity;
import se.team05.content.Track;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

/**
 * This test tests the UI elements that are not tested in the UI test for the
 * media selector.
 * 
 * @author Daniel Kvist
 * 
 */
public class MediaSelectorActivityTest extends ActivityInstrumentationTestCase2<MediaSelectorActivity>
{

	private MediaSelectorActivity activity;
	private ListView list;
	private View done;

	public MediaSelectorActivityTest()
	{
		super(MediaSelectorActivity.class);
	}

	/**
	 * Sets up the basic information needed to launch the activity.
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		setActivityInitialTouchMode(false);
		Intent intent = new Intent();
		intent.setClassName("se.team05.activity", "MediaSelectorActivity");
		intent.putParcelableArrayListExtra(MediaSelectorActivity.EXTRA_SELECTED_ITEMS, new ArrayList<Track>());
		setActivityIntent(intent);
		activity = getActivity();
		list = (ListView) activity.findViewById(R.id.list);
		done = activity.findViewById(R.id.done);
	}

	/**
	 * Tests the actual ui elements that are not tested in the UI test.
	 */
	public void testUIElements()
	{
		assertTrue("List not found", list != null);
		assertTrue("Done button not found", done != null);

		Track track = new Track("id", "artist", "album", "title", "data", "displayName", "duration");
		assertEquals("id", track.getId());
	}

}
