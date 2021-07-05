package com.example.thefirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thefirstapplication.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    private Button btnStart;
    private Button btnStartAgain;
    private Button btnExit;
    private TextView txtMessage;

    /** TODO:
         condition for empty field
         negative positive toast text
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(binding.getRoot());

        btnStart = binding.btnStart;
        btnStartAgain = binding.btnStartAgain;
        btnExit = binding.btnExit;
        txtMessage = binding.txtMessage;

        btnStart.setOnClickListener(this);
        btnStartAgain.setOnClickListener(v -> {
            txtMessage.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            btnStartAgain.setVisibility(View.GONE);
            btnExit.setVisibility(View.GONE);
        });
        btnExit.setOnClickListener(v -> finish());
        btnStartAgain.setVisibility(View.GONE);
        btnExit.setVisibility(View.GONE);
    }
    public void onClick(View v){
        AlertDialog.Builder builder;
        final EditText input = new EditText(this);
        input.setMaxLines(1);
        input.setImeOptions(EditorInfo.IME_ACTION_DONE);
        input.setOnEditorActionListener((v1, actionId, event) -> {
            boolean handled = true;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                MainActivity.this.softKeyboard(input, true);
                handled = false;
            }
            return handled;
        });
        builder = new AlertDialog.Builder( this,R.style.AlertDia);
        builder.setMessage("What would you like to talk about?")
                .setView(input)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.keepTalking(input.getText().toString());
                    }
                })
                .setNegativeButton ( "Cancel", null);

        //AlertDialog alertDialog = builder.show();
        //alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.keepTalking(input.getText().toString());
            }
        });
        AlertDialog alertDialog = customDialog.createCustomDialog();
        alertDialog.show();


        startAgain();
    }
    public void keepTalking(final String t){
        AlertDialog secondDialog = new AlertDialog.Builder(this)
                .setMessage("Do you like " + t +"?")
                .setPositiveButton("Yes", (dialog, which) -> likeTopic(t))
                .setNegativeButton("No", (dialog, which) -> dislikeTopic(t))
                .show();
    }
    public void likeTopic(String t){
        Context context = getApplicationContext();
        CharSequence message = "I'm happy that you like " + t + "!! :)";
        int duration =Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(context,message,duration);
        toast.show();
    }
    public void dislikeTopic(String t){
        Context context = getApplicationContext();
        CharSequence message = "So sad that you dislike " +t+ "!! :(";
        int duration =Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(context,message,duration);
        toast.show();
    }
    public void startAgain(){
        btnStart.setVisibility(View.GONE);
        txtMessage.setVisibility(View.GONE);
        btnStartAgain.setVisibility(View.VISIBLE);
        btnExit.setVisibility(View.VISIBLE);
    }

    private void softKeyboard(View view, boolean hide){
        try{
            if(hide) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            else{
                if(view.requestFocus()){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }
        catch (Exception ignored){

        }
    }
}