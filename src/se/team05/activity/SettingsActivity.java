package se.team05.activity;

import se.team05.R;
import se.team05.R.layout;
import se.team05.R.menu;
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
	public static final String PREFERENCES_NAME = "PersonalTrainerSettings";
	public static final String PREFERENCES_USER_WEIGHT = "userWeight";
	public static final String PREFERENCES_USER_NAME = "userName";
	public static final String PREFERENCES_USER_SEX = "userSex";
	EditText nameEdit;
	EditText weightEdit;
	private String userName;
	private String userWeightString;
	private int userWeight;
	private boolean isFemale;
	private Button saveSettings;
	private RadioButton radioFemale;
	private RadioButton radioMale;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        

               
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        userName = settings.getString(PREFERENCES_USER_NAME, "BATMAN");
        userWeight = settings.getInt(PREFERENCES_USER_WEIGHT, 75);
        isFemale = settings.getBoolean(PREFERENCES_USER_SEX, true);
        userWeightString = "" + userWeight;


        radioFemale = (RadioButton) findViewById(R.id.radio_female);
        radioMale = (RadioButton) findViewById(R.id.radio_male);
    	radioFemale.setChecked(isFemale);
    	radioMale.setChecked(!isFemale);
        nameEdit = (EditText) findViewById(R.id.edit_name);
        nameEdit.setText(userName);
        weightEdit = (EditText) findViewById(R.id.edit_weight);
        weightEdit.setText(userWeightString);
        saveSettings = (Button) findViewById(R.id.save_settings);
        saveSettings.setOnClickListener(this);
        int y = 3;
        
    }

    
	@Override
	public void onClick(View arg0) 
	{
		userWeightString = weightEdit.getText().toString();
		
		if(testValues())
		{
			SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
		    SharedPreferences.Editor editor = settings.edit();
		      
		    userName = nameEdit.getText().toString();
		    
		    editor.putString(PREFERENCES_USER_NAME, userName);
		    editor.putInt(PREFERENCES_USER_WEIGHT, userWeight);
		    editor.putBoolean(PREFERENCES_USER_SEX, isFemale);
	
		    editor.commit();

		    
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
		
	}
	
	
	public void onRadioButtonClicked(View view) 
	{
	    boolean checked = ((RadioButton) view).isChecked();
	
	    switch(view.getId()) 
	    {
	        case R.id.radio_female:
	            if (checked)
	                isFemale = true;
	            break;
	        case R.id.radio_male:
	            if (checked)
	            	isFemale = false;
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
