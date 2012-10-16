package se.team05.activity;

import se.team05.R;
import se.team05.R.layout;
import se.team05.R.menu;
import se.team05.content.CalorieCounter;
import se.team05.content.Settings;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class SettingsActivity extends Activity implements View.OnClickListener
{
	EditText nameEdit;
	EditText weightEdit;
	private String userName;
	private String userWeightString;
	private int userWeight;
	private boolean isLengthSi;
	private boolean isWeightSi;
	private boolean preferredActivityRunning;
	private Button saveSettings;
	private RadioButton radioWalking;
	private RadioButton radioRunning;
	private RadioButton radioLengthSI;
	private RadioButton radioLengthEng;
	private RadioButton radioWeightSI;
	private RadioButton radioWeightEng;
	
	static SharedPreferences sharedPreferences;
	private Settings settings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        settings = new Settings(this);
        
        userName = settings.getUserName();
        userWeight = settings.getUserWeight();
        isLengthSi = settings.isSILength();
        isWeightSi = settings.isSIWeight();
        System.out.println("ONCREATE VIKT: " + settings.isSIWeight());
        preferredActivityRunning = settings.isPrefActivityRunning();

        userWeightString = "" + userWeight;

        setUpButtons();
        
    }
    
    public void onResume(Bundle savedInstanceState)
    {
		super.onResume();
		
        userName = settings.getUserName();
        userWeight = settings.getUserWeight();
        isLengthSi = settings.isSILength();
        System.out.println("ONRESUME VIKT: " + settings.isSIWeight());
        isWeightSi = settings.isSIWeight();
        userWeightString = "" + userWeight;
    }
    
    private void setUpButtons()
    {
    	radioWalking = (RadioButton) findViewById(R.id.radio_walking);
        radioRunning = (RadioButton) findViewById(R.id.radio_running);
     	radioWalking.setChecked(!preferredActivityRunning);
     	radioRunning.setChecked(preferredActivityRunning);
     	
    	radioLengthSI = (RadioButton) findViewById(R.id.radio_length_si);
    	radioLengthEng = (RadioButton) findViewById(R.id.radio_length_eng);
    	radioLengthSI.setChecked(isLengthSi);
    	radioLengthEng.setChecked(!isLengthSi);
    	
    	radioWeightSI = (RadioButton) findViewById(R.id.radio_weight_si);
    	radioWeightEng = (RadioButton) findViewById(R.id.radio_weight_eng);
    	radioWeightSI.setChecked(isWeightSi);
    	radioWeightEng.setChecked(!isWeightSi);
    	
    	nameEdit = (EditText) findViewById(R.id.edit_name);
        nameEdit.setText(userName);
        weightEdit = (EditText) findViewById(R.id.edit_weight);
        weightEdit.setText(userWeightString);
        
        saveSettings = (Button) findViewById(R.id.save_settings);
        saveSettings.setOnClickListener(this);
    }

    
	@Override
	public void onClick(View arg0) 
	{
		
		userWeightString = weightEdit.getText().toString();
		
		if(testValues())
		{

		    userName = nameEdit.getText().toString();
			settings.setUserName(userName);
			settings.setUserWeight(userWeight);
			settings.setPrefActivityRunning(preferredActivityRunning);
			settings.setSILength(isLengthSi);
			settings.setSIWeight(isWeightSi);
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
							//TODO PUT SOMETHING HERE?
						}
					}).create();
			alertDialog.show();
		}
		System.out.println("FÄRDIG I KNAPPEN");
	}
	
	
	public void onRadioButtonClicked(View view) 
	{
	    boolean checked = ((RadioButton) view).isChecked();
	
	    switch(view.getId()) 
	    {
	        case R.id.radio_running:
	            if (checked)
	                preferredActivityRunning = true;
	            System.out.println("RUNNING JA");
	            break;
	        case R.id.radio_walking:
	            if (checked)
	            	preferredActivityRunning = false;
	            System.out.println("RUNNING NEJ");
	            break;
	            
	        case R.id.radio_length_si:
	        	if(checked)
	        		isLengthSi = true;
	        	 System.out.println("KM");
	        	break;
	        case R.id.radio_length_eng:
	        	if(checked)
	        		isLengthSi = false;
	        	 System.out.println("MILES");
	        	break;
	        	
	        case R.id.radio_weight_si:
	        	if(checked)
	        		isWeightSi = true;
	        	 System.out.println("kg");
	        	break;
	        case R.id.radio_weight_eng:
	        	if(checked)
	        		isWeightSi = false;
	        	 System.out.println("POUNDS");
	        	break;
	    }
	}
    
    private boolean testValues()
    {
    	try
    	{
    		userWeight = Integer.parseInt(userWeightString);
    	}
    	catch(NumberFormatException e)
    	{

			return false;
    	}

    	return true;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
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
