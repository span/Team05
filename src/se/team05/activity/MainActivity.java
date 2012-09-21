package se.team05.activity;

import se.team05.R;
import se.team05.listener.MainActivityButtonListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * This is the launching point of the application. The main activity simply
 * displays a few buttons which allows the user to choose which action to take
 * with the application. The main activity uses the external listener
 * MainActivityButtonListener to decide which Activity to launch next.
 * 
 * @author Daniel Kvist
 * 
 */
public class MainActivity extends Activity
{
	/**
	 * The onCreate method of the class starts off by setting the XML file which
	 * has the View content. It then picks up references to the buttons that are
	 * used and adds the View.onClickListener to both of them.
	 * 
	 * @param savedInstanceState
	 *            a bundle which contains any previous saved state.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button newRouteButton = (Button) findViewById(R.id.new_route_button);
		newRouteButton.setOnClickListener(new MainActivityButtonListener(this));

		Button useExistingButton = (Button) findViewById(R.id.use_existing_button);
		useExistingButton.setOnClickListener(new MainActivityButtonListener(this));
	}
}
