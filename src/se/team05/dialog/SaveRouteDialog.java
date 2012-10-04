package se.team05.dialog;

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

import se.team05.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

/**
 * This Dialog class is shown to the user when the user has recorded a new route
 * and wishes to finish recording it. It has inputs for the name and description
 * of the route and it also has a checkbox which is connected to a boolean value
 * which decides if the results that the user had (time, length) should also be
 * saved with the route.
 * 
 * @author Daniel Kvist
 * 
 */
public class SaveRouteDialog extends Dialog implements View.OnClickListener
{
	/**
	 * This interface must be implemented by the calling Activity to be able to
	 * receive callbacks.
	 * 
	 */
	public interface Callbacks
	{
		public void onSaveRoute(String name, String description, boolean saveResult);

		public void onDismissRoute();
	}

	private Context context;
	private Callbacks callbacks;

	/**
	 * The constructor of the dialog takes a Context and a Callbacks as a
	 * parameter and saves it as instances of both the Context class and as a
	 * Callbacks instance.
	 * 
	 * @param context
	 *            the context to run the dialog in
	 * @param callbacks
	 *            an instance of the class that implements this class's
	 *            Callbacks interface
	 */
	public SaveRouteDialog(Context context, Callbacks callbacks)
	{
		super(context);
		this.context = context;
		this.callbacks = callbacks;
	}

	/**
	 * This method is called by the system and sets the content view that should
	 * be connected with this class and also the title of the dialog. It also
	 * adds click listeners to the buttons.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_save_route);
		setTitle(context.getString(R.string.save_route));

		((Button) findViewById(R.id.discard_button)).setOnClickListener(this);
		((Button) findViewById(R.id.save_button)).setOnClickListener(this);
	}

	/**
	 * This is called when a button is clicked and decides which action to take
	 * next. If the discard button is clicked, an alert dialog is created to
	 * prompt the user to confirm that the route really should be discarded. If,
	 * however, the save button was clicked we get the name, description and the
	 * checked value and pass that on through the callback.
	 */
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.discard_button:
				AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("Discard route?")
						.setMessage("Do you really want to discard your route?")
						.setPositiveButton("Yes", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								callbacks.onDismissRoute();
								dismiss();
							}
						}).setNegativeButton("No", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								dialog.cancel();
							}
						}).create();
				alertDialog.show();
				break;
			case R.id.save_button:
				String name = ((EditText) findViewById(R.id.name)).getText().toString();
				String description = ((EditText) findViewById(R.id.description)).getText().toString();
				boolean saveResult = ((CheckedTextView) findViewById(R.id.save_result)).isChecked();
				callbacks.onSaveRoute(name, description, saveResult);
				dismiss();
				break;
			default:
				break;
		}
	}
}
