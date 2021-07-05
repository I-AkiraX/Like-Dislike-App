package com.example.thefirstapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.renderscript.ScriptGroup;
import android.view.View;
import androidx.appcompat.app.AlertDialog;

import com.example.thefirstapplication.databinding.DialogBinding;

public class CustomDialog extends AlertDialog {
    DialogBinding binding;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Context context;

    protected CustomDialog(Context context) {
        super(context);
        this.context = context;
        builder = new AlertDialog.Builder(context);
        binding = DialogBinding.inflate(getLayoutInflater());
    }

    public void setPositiveButton(String aContinue, DialogInterface.OnClickListener o) {
        binding.positive.setText(aContinue);
        builder.setPositiveButton(aContinue, o);
        alertDialog = builder.create();
        binding.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performPositiveButtonClick();
            }
        });
    }

    public AlertDialog createCustomDialog(){
        alertDialog.setView(binding.dialogBox);
        return alertDialog;
    }

    private void performPositiveButtonClick(){
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
    }
}