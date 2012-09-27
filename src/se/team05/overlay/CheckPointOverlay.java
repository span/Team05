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
package se.team05.overlay;

import java.util.ArrayList;

import se.team05.dialog.EditCheckPointDialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;

public class CheckPointOverlay extends ItemizedOverlay<CheckPoint>
		implements EditCheckPointDialog.Callbacks
{

	private ArrayList<CheckPoint> checkPointList = new ArrayList<CheckPoint>();
	private Context context;
	private int selectedCheckpointIndex;
	public CheckPointOverlay(Drawable defaultMarker)
	{
		super(boundCenterBottom(defaultMarker));
	}

	public CheckPointOverlay(Drawable defaultMarker, Context context)
	{
		super(boundCenterBottom(defaultMarker));
		this.context =  context;
		populate();
	}

	@Override
	protected CheckPoint createItem(int i)
	{
		return checkPointList.get(i);
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return checkPointList.size();
	}
	/**
	 * Adds a checkpoint to the checkpointlist
	 * @param checkPoint
	 */
	public void addCheckPoint(CheckPoint checkPoint)
	{
		checkPointList.add(checkPoint);
		setLastFocusedIndex(-1);
		populate();
	}
	/**
	 * The onTap method that initiates the dialog
	 */
	@Override
	protected boolean onTap(int index)
	{
		selectedCheckpointIndex = index;
		CheckPoint checkPoint = checkPointList.get(index);

		EditCheckPointDialog temp = new EditCheckPointDialog(context,this,checkPoint);
		temp.show();
		return true;
	}
	/**
	 * 
	 * @return checkPointList a list with the checkpoints 
	 */
	public ArrayList<CheckPoint> getOverlays()
	{
		return checkPointList;
	}
	/**
	 * Deletes the checkpoint from the checkpointlist, a callback from the dialog
	 */
	@Override
	public void onDelete()
	{
		checkPointList.remove(selectedCheckpointIndex);
		setLastFocusedIndex(-1);
		populate();
		//Temporarily fix to call postInvalidate to update map
		//TODO ((MainActivity) context).getMapView().postInvalidate();
	}


}
