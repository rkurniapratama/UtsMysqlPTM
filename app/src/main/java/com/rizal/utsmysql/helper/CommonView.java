package com.rizal.utsmysql.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CommonView {
    private AppCompatActivity act;
    private ProgressDialog progress;

    public CommonView(Activity activity) {
        act = (AppCompatActivity) activity;
    }

    public CommonView(Context context) {
        act = (AppCompatActivity) context;
    }

    public void enableDisableEditText(boolean isEnabled, EditText editText) {
        editText.setFocusable(isEnabled);
        editText.setFocusableInTouchMode(isEnabled) ;
        editText.setClickable(isEnabled);
        editText.setLongClickable(isEnabled);
        editText.setCursorVisible(isEnabled) ;
    }

    public int getIndexSpinner(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    public void popUp(final String message) {
        try {
            if (act == null) {
                return;
            }
            act.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (act != null) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                act);
                        alertDialogBuilder.setTitle("Informasi");
                        alertDialogBuilder.setMessage(message);
                        alertDialogBuilder.setPositiveButton("OK", null);
                        alertDialogBuilder.create().show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startProgressBar(String message) {
        if (act == null) {
            return;
        }
        progress = new ProgressDialog(act) {
            @Override
            public void onBackPressed() {
                progress.dismiss();
            }
        };
        progress.setMessage(message);
        progress.setCancelable(false);
        progress.show();
    }

    public void startProgressBarNonCancelable(String message) {
        if (act == null) {
            return;
        }
        progress = new ProgressDialog(act);
        progress.setMessage(message);
        progress.setCancelable(false);
        progress.show();
    }

    public void stopProgressBar() {
        if (act != null && progress != null) {
            progress.dismiss();
        }
    }
}
