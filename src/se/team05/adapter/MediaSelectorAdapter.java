package se.team05.adapter;

import java.util.ArrayList;

import se.team05.R;
import se.team05.content.Track;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;

/**
 * This adapter is used by the media selector activity to display the list rows.
 * It is needed to keep track of which checkboxes have been checked and which
 * has not. The system is aggressive in trying to re-use views that are not
 * currently being displayed which leads to strange behaviour with the
 * checkboxes where they keep their "checked" state although they have not been
 * checked for a specific item.
 * 
 * The class is extending SimpleCursorAdapter for easy use of the cursor that
 * can be obtained from a database or content resolver.
 * 
 * @author Daniel Kvist
 * 
 */
public class MediaSelectorAdapter extends SimpleCursorAdapter
{
	private Context context;
	private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
	private ArrayList<Track> listItems = new ArrayList<Track>();;

	/**
	 * The constructor takes the same parameters as an ordinary simple cursor
	 * adapter and passes them up to the super class. It then loops through the
	 * cursor and initiates an array which contains references to all the list
	 * rows and if they have been checked or not.
	 * 
	 * @param context
	 *            the context which to be displayed in
	 * @param layout
	 *            the layout file for the list view
	 * @param cursor
	 *            the cursor that points to the data
	 * @param from
	 *            the fields that are to be displayed
	 * @param to
	 *            the views to display the fields in
	 * @param flags
	 *            any special flags that can be used to determine the behaviour
	 *            of the super class adapter
	 */
	public MediaSelectorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags)
	{
		super(context, layout, cursor, from, to, flags);
		this.context = context;

		int i = 0;
		while (cursor.moveToNext())
		{
			Track track = new Track(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5), cursor.getString(6));
			listItems.add(track);
			itemChecked.add(i, false);
			i++;
		}
	}

	/**
	 * Returns an arraylist of the items and if they have been checked or not
	 * 
	 * @return a list with true/false checked items
	 */
	public ArrayList<Boolean> getChecked()
	{
		return itemChecked;
	}

	/**
	 * Overridden method that getView uses to keep track of how many items the
	 * adapter has.
	 */
	@Override
	public int getCount()
	{
		return itemChecked.size();
	}

	/**
	 * Reuses old views if they have not already been reset and inflates new
	 * views for the rows in the list that needs a new one. It the adds a
	 * listener to each checkbox that is used to store information about which
	 * checkboxes have been checked or not. Finally we set the checked status of
	 * the checkbox and let the super class do it's thing.
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.media_selector_item_layout, null);
		}
		final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
		checkBox.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				CheckBox cb = (CheckBox) v.findViewById(R.id.checkbox);
				if (cb.isChecked())
				{
					itemChecked.set(position, true);
				}
				else if (!cb.isChecked())
				{
					itemChecked.set(position, false);
				}
			}
		});
		checkBox.setChecked(itemChecked.get(position));

		return super.getView(position, convertView, parent);
	}

	/**
	 * Returns an array list with all the selected items as Track objects.
	 * 
	 * @return the selected items
	 */
	public ArrayList<Track> getSelectedItems()
	{
		ArrayList<Track> selectedItems = new ArrayList<Track>();
		int i = 0;
		for (Boolean checked : itemChecked)
		{
			if (checked)
			{
				selectedItems.add(listItems.get(i));
			}
			i++;
		}
		return selectedItems;
	}
}
