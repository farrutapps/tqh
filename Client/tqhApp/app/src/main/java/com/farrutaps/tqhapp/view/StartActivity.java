package com.farrutaps.tqhapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.controller.Parameters;

public class StartActivity extends AppCompatActivity { //implements AlertDialog.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        try {
            showUserPicker(getWindow().getDecorView().getRootView());
        } catch(Exception e) {
        }
    }

    public void showUserPicker(View view) {
        UserPickerDialog dialog = new UserPickerDialog();
        dialog.show(getFragmentManager(), Parameters.USER_PICKER.name());
    }
}
