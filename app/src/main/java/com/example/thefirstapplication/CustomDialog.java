package com.example.thefirstapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.thefirstapplication.databinding.DialogBinding;

public class CustomDialog extends AlertDialog {
    DialogBinding binding;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Context context;
    EditText input;

    protected CustomDialog(Context context) {
        super(context,R.style.AlertDia);
        this.context = context;
        builder = new AlertDialog.Builder(context);
        binding = DialogBinding.inflate(getLayoutInflater());
        setInput(binding.inputText);
    }

    public EditText getInput() {
        return input;
    }

    public void setInput(EditText input) {
        this.input = input;
    }

    public void customise(Runnable runnable){
        runnable.run();
    }

    public void setTitle(String title){
        binding.alertBoxTitle.setText(title);
    }

    public void setPositiveButton(String aContinue, Runnable runnable) {
        binding.positive.setText(aContinue);
        alertDialog = builder.create();
        binding.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((!getInput().getText().toString().isEmpty()) || (input.getVisibility()==View.GONE)) {
                    performPositiveButtonClick(runnable);
                }
                else{
                    Context context1 = assignContext(context);
                    context1.setTheme(R.style.AlertDia);
                    Toast.makeText(context1,"ERROR: Empty field!",Toast.LENGTH_SHORT).show();
                }
            }

            private void performPositiveButtonClick(Runnable runnable){
                alertDialog.cancel();
                runnable.run();
            }

            private Context assignContext(Context c){
                return c;
            }
        });
    }

    public void setNegativeButton(String aContinue, Runnable runnable) {
        binding.negative.setText(aContinue);
        alertDialog = builder.create();
        binding.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performNegativeButtonClick(runnable);
            }

            private void performNegativeButtonClick(Runnable runnable) {
                alertDialog.cancel();
                runnable.run();
            }
        });
    }

    public AlertDialog createCustomDialog(){
        alertDialog.setView(binding.getRoot());
        return alertDialog;
    }


}