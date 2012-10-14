package se.team05.dialog;

import se.team05.R;
import se.team05.content.Result;
import se.team05.content.Route;
import se.team05.data.DatabaseHandler;
import se.team05.dialog.SaveRouteDialog.Callbacks;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class AlertDialogFactory
{
	public static AlertDialog newSaveResultDialog(final Context context, String giveUserResultData, final Route route, final Result result)
	{
		return new AlertDialog.Builder(context).setTitle(R.string.save_result_)
		.setMessage( giveUserResultData +  context.getString(R.string.do_you_want_to_save_this_result_))
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				DatabaseHandler databaseHandler = new DatabaseHandler(context);
				databaseHandler.saveResult(result);
				Toast.makeText(context, context.getString(R.string.result_saved) + " " + route.getName(), Toast.LENGTH_LONG).show();
			}
		}).setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id)
			{
				Callbacks callbacks = (Callbacks) context;
				callbacks.onDismissRoute();
			}
		}).create();
	}
}
