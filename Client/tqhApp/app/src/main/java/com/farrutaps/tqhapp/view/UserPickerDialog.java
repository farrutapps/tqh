package com.farrutaps.tqhapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.farrutaps.tqhapp.R;
import com.farrutaps.tqhapp.controller.Controller;
import com.farrutaps.tqhapp.controller.Parameters;

/**
 * Created by Sònia Batllori on 27/02/2018.
 */
public class UserPickerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_user)
                .setItems(R.array.users, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra(Parameters.USER_ID.name(), which);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });

        return builder.create();
    }
}
