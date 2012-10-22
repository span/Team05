package se.team05.activity;

import se.team05.R;
import se.team05.content.Settings;
import se.team05.dialog.AlertDialogFactory;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * This class represents what user has stored in his or her settings. It acts mainly
 * as a visual representation of what is stored and allows the user to make changes.
 * The acutal storing is made in the settings class
 * 
 * @author Markus
 *
 */
public class SettingsActivity extends Activity implements View.OnClickListener
{
	private EditText nameEdit;
	private EditText weightEdit;

	private Button saveSettings;
	private RadioButton radioWalking;
	private RadioButton radioRunning;

	private Settings settings;

	/**
	 * Will fetch the saved values from settings.java if no saved settings have been
	 * done, will instead fetch default values.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		settings = new Settings(this);

		setUpButtons();
	}

	public void onResume(Bundle savedInstanceState)
	{
		super.onResume();
	}

	/**
	 * Sets up the button and radio listeners and also fills in the edittext
	 * boxes and radio buttons with the values found in settings class
	 */
	private void setUpButtons()
	{
		radioWalking = (RadioButton) findViewById(R.id.radio_walking);
		radioRunning = (RadioButton) findViewById(R.id.radio_running);
		radioWalking.setChecked(!settings.isPrefActivityRunning());
		radioRunning.setChecked(settings.isPrefActivityRunning());

		nameEdit = (EditText) findViewById(R.id.edit_name);
		nameEdit.setText(settings.getUserName());
		weightEdit = (EditText) findViewById(R.id.edit_weight);
		weightEdit.setText(String.valueOf(settings.getUserWeight()));

		saveSettings = (Button) findViewById(R.id.save_settings);
		saveSettings.setOnClickListener(this);
	}

	/**
	 * Listener for the save settings button, it will check that the values are ok
	 * and if not will make an alertdialog telling the user this. Will not save anything
	 * until the user has made sure that the values are correct. Will fire a toast upon
	 * saving notifying the user of this
	 */
	@Override
	public void onClick(View arg0)
	{

		if(testWeight() > 0)

		{
			settings.setUserName(nameEdit.getText().toString());
			settings.setUserWeight(testWeight());
			settings.setPrefActivityRunning(radioRunning.isChecked());

			CharSequence text = getString(R.string.settings_saved);
			
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(this, text, duration);
			toast.show();
		}
		else
		{
			AlertDialogFactory.newAlertMessageDialog(
					this, 
					getString(R.string.invalid_values),
					getString(R.string.please_review_your_values_before_submitting))
					.show();
		}
	}

	/**
	 * Listener for the Radio buttons not implemented fully as the calculations are done in the
	 * onClick method but this method is still needed for when user presses the radio buttons
	 * 
	 * @param view
	 */
	public void onRadioButtonClicked(View view)
	{
	}

	/**
	 * This tests that the user has not input any illegal values, as of now it checks
	 * if user has not chosen a value less than zero or any letters for the weight which
	 * should be a non negative integer
	 * 
	 * @return -1 if test failed
	 */
	private int testWeight()
	{
		int userWeight;
		try
		{
			userWeight = Integer.parseInt(weightEdit.getText().toString());
		}
		catch (NumberFormatException e)
		{
			userWeight = -1;
		}
		return userWeight;
	}

	/**
	 * The UP/HOME Button which will direct the user to the mainactivity, will not save any input done
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
