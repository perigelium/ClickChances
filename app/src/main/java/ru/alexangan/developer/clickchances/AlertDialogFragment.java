package ru.alexangan.developer.clickchances;

/**
 * Created by Administrator on 05.08.16.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class AlertDialogFragment extends DialogFragment implements View.OnClickListener {
    //Supply keys for the Bundle
    public static String titleText = "title";
    public static String messageText = "message";
    public static String neutralBtnText = "back";
    public static String Enable_Yes_Btn = "Enable_Yes_Btn";
    public static String Enable_No_Btn = "Enable_No_Btn";
    public static String Enable_Neutral_Btn = "Enable_Neutral_Btn";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get supplied title and message body.

        Bundle messages = getArguments();
        final Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(messages != null)
        {
            if(messages.getBoolean(Enable_Yes_Btn)) {

                builder.setPositiveButton(R.string.btnYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity) context).onDialogYesClick();
                    }
                });
            }

            if(messages.getBoolean(Enable_No_Btn)) {

                builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity) context).onDialogNoClick();
                    }
                });
            }

            if(messages.getBoolean(Enable_Neutral_Btn)) {

                builder.setNeutralButton(messages.getString(neutralBtnText), null);
            }

            builder.setTitle(messages.getString(titleText));
            builder.setMessage(messages.getString(messageText));
        }
        else
        {
            //Supply default text if no arguments were set.
            builder.setTitle("Sorry");
            builder.setMessage("There was an error.");
        }

        AlertDialog dialog = builder.create();
        return dialog;
    }

    public void onClick(View v) {
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}