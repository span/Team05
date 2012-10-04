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
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

public class SaveRouteDialog extends Dialog implements View.OnClickListener
{
	public interface Callbacks
	{
		public void onSaveRoute(String name, String description, boolean saveResult);
	}

	private Context context;
	private Callbacks callbacks;

	public SaveRouteDialog(Context context)
	{
		super(context);
		this.context = context;
		this.callbacks = (Callbacks) context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_save_route);
		setTitle(context.getString(R.string.save_route));
		
		((Button) findViewById(R.id.cancel_button)).setOnClickListener(this);
		((Button) findViewById(R.id.save_button)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.cancel_button:
				dismiss();
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
