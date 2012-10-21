package se.team05.activity;

import se.team05.R;
import se.team05.content.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingsActivity extends Activity implements View.OnClickListener
{
	EditText nameEdit;
	EditText weightEdit;

	private Button saveSettings;
	private RadioButton radioWalking;
	private RadioButton radioRunning;
//	private RadioButton radioLengthSI;
//	private RadioButton radioLengthEng;
//	private RadioButton radioWeightSI;
//	private RadioButton radioWeightEng;
	
	static SharedPreferences sharedPreferences;
	private Settings settings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        settings = new Settings(this);

        setUpButtons();     
    }
    
    public void onResume(Bundle savedInstanceState)
    {
		super.onResume();
    }
    
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
        
//    	radioLengthSI = (RadioButton) findViewById(R.id.radio_length_si);
//    	radioLengthEng = (RadioButton) findViewById(R.id.radio_length_eng);
//    	radioLengthSI.setChecked(isLengthSi);
//    	radioLengthEng.setChecked(!isLengthSi);
//    	
//    	radioWeightSI = (RadioButton) findViewById(R.id.radio_weight_si);
//    	radioWeightEng = (RadioButton) findViewById(R.id.radio_weight_eng);
//    	radioWeightSI.setChecked(isWeightSi);
//    	radioWeightEng.setChecked(!isWeightSi);
    	
    }

    
	@Override
	public void onClick(View arg0) 
	{
		if(testWeight() >= 0)
		{
		    settings.setUserName(nameEdit.getText().toString());
			settings.setUserWeight(testWeight());
			settings.setPrefActivityRunning(radioRunning.isChecked());

			settings.commitChanges();

			CharSequence text = getString(R.string.settings_saved);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(this, text, duration);
			toast.show();    
		}
		else
		{
	    	AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.invalid_values)
					.setMessage(R.string.please_review_your_values_before_submitting)
					.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
						}
					}).create();
			alertDialog.show();
		}
	}
	
	public void onRadioButtonClicked(View view) 
	{

	}
    
    private int testWeight()
    {
    	int userWeight;
    	try
    	{
    		userWeight = Integer.parseInt(weightEdit.getText().toString());
    	}
    	catch(NumberFormatException e)
    	{
			userWeight = -1;
    	}
    	return userWeight;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
