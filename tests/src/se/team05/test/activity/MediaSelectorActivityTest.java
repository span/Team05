package se.team05.test.activity;

import java.util.ArrayList;

import se.team05.R;
import se.team05.activity.MediaSelectorActivity;
import se.team05.content.Track;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ListView;

public class MediaSelectorActivityTest extends ActivityInstrumentationTestCase2<MediaSelectorActivity>
{

	private MediaSelectorActivity activity;
	private ListView list;
	private View done;

	public MediaSelectorActivityTest()
	{
		super(MediaSelectorActivity.class);
	}

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

	public void testUIElements()
	{
		assertTrue("List not found", list != null);
		assertTrue("Done button not found", done != null);
		
		Track track = new Track("id", "artist", "album", "title", "data", "displayName", "duration");
		assertEquals("id", track.getId());
	}

}
