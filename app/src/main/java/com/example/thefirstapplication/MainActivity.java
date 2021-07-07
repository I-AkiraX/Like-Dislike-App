package com.example.thefirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.Context;
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

    private Button btnStart;
    private Button btnStartAgain;
    private Button btnExit;
    private TextView txtMessage;

    AlertDialog inputAlertDialog;
    CustomDialog inputCustomDialog;
    ActivityMainBinding binding;


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

        //Context contextMain = this;
        //contextMain.setTheme(R.style.AlertDia);

        inputCustomDialog = new CustomDialog(new ContextThemeWrapper(this, R.style.AlertDia));

        inputCustomDialog.customise(() -> inputCustomDialog.setTitle("Enter the topic you want to talk about!"));
        inputCustomDialog.setPositiveButton("Continue", MainActivity.this::keepTalking);
        inputCustomDialog.setNegativeButton("Cancel", () -> {

        });
        inputAlertDialog = inputCustomDialog.createCustomDialog();
        inputAlertDialog.show();

        startAgain();
    }
    public void keepTalking(){
        String t = inputCustomDialog.getInput().getText().toString();

        CustomDialog finalCustomDialog = new CustomDialog(this);
        finalCustomDialog.customise(() -> {
            finalCustomDialog.binding.inputText.setVisibility(View.GONE);
            finalCustomDialog.setTitle("Do you like " + t + " ?");
        });
        finalCustomDialog.setPositiveButton("Yes", () -> likeTopic(t));
        finalCustomDialog.setNegativeButton("No", () -> dislikeTopic(t));
        finalCustomDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        AlertDialog finalAlertDialog = finalCustomDialog.createCustomDialog();
        finalAlertDialog.show();
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