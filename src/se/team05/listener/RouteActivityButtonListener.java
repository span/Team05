package se.team05.listener;

import se.team05.R;
import android.view.View;
import android.view.View.OnClickListener;

public class RouteActivityButtonListener implements OnClickListener
{	
	public interface Callbacks
	{
		public void onStartRouteClick();
		public void onStopRouteClick();
		public void onAddCheckPointClick();
		public void onShowResultClick();
	}
	
	private Callbacks callbacks;
	
	public RouteActivityButtonListener(Callbacks callbacks)
	{
		this.callbacks = callbacks;
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
				callbacks.onStartRouteClick();
				break;
			case R.id.stop_button:
				callbacks.onStopRouteClick();
				break;
			case R.id.add_checkpoint:
				callbacks.onAddCheckPointClick();
				break;
			case R.id.show_result_button:
				callbacks.onShowResultClick();
				break;
		}
	}

}
