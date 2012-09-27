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

public class MediaSelectorAdapter extends SimpleCursorAdapter
{
	private Context context;
	private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
	private ArrayList<Track> listItems = new ArrayList<Track>();;

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

	public ArrayList<Boolean> getChecked()
	{
		return itemChecked;
	}
	
	@Override
	public int getCount()
	{
		return itemChecked.size();
	}

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
