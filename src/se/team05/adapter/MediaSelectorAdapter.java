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
	private ArrayList<Track> selectedItems;

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
	 * @param selectedItems2 
	 */
	public MediaSelectorAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags, ArrayList<Track> selectedItems)
	{
		super(context, layout, cursor, from, to, flags);
		this.context = context;
		this.selectedItems = selectedItems;
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
		Cursor c = getCursor();
		c.moveToPosition(position);
		final Track track = new Track(c.getString(0), c.getString(1), c.getString(2), c.getString(3),
				c.getString(4), c.getString(5), c.getString(6));
		
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
					selectedItems.add(track);
				}
				else if (!cb.isChecked())
				{
					selectedItems.remove(track);
				}
			}
		});
		// If the selected items contains the current item, set the checkbox to be checked
		checkBox.setChecked(selectedItems.contains(track));
		return super.getView(position, convertView, parent);
	}

	/**
	 * Returns an array list with all the selected items as Track objects.
	 * 
	 * @return the selected items
	 */
	public ArrayList<Track> getSelectedItems()
	{
		return selectedItems;
	}
}
