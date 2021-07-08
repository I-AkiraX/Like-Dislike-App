package com.example.thefirstapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

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

        inputCustomDialog = new CustomDialog(new ContextThemeWrapper(this, R.style.AlertDia));

        inputCustomDialog.customise(() -> inputCustomDialog.setCustomTitle(getString(R.string.enter_topic)));
        inputCustomDialog.setPositiveButton(getString(R.string.continue_btn), MainActivity.this::keepTalking);
        inputCustomDialog.setNegativeButton(getString(R.string.cancel_btn), () -> {

        });
        inputAlertDialog = inputCustomDialog.createCustomDialog();
        inputAlertDialog.show();

        startAgain();
    }
    public void keepTalking(){
        String t = inputCustomDialog.getInput().getText().toString();

        CustomDialog finalCustomDialog = new CustomDialog(new ContextThemeWrapper(this,R.style.AlertDia));
        finalCustomDialog.customise(() -> {
            finalCustomDialog.binding.inputText.setVisibility(View.GONE);
            finalCustomDialog.setCustomTitle(String.format(getString(R.string.do_you_like),t));
        });
        finalCustomDialog.setPositiveButton(getString(R.string.yes_btn), () -> likeTopic(t));
        finalCustomDialog.setNegativeButton(getString(R.string.no_btn), () -> dislikeTopic(t));
        finalCustomDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        AlertDialog finalAlertDialog = finalCustomDialog.createCustomDialog();
        finalAlertDialog.show();
    }

    public void likeTopic(String t){
        Context context = getApplicationContext();
        CharSequence message =  String.format(getString(R.string.im_happy),t);
        int duration =Toast.LENGTH_LONG;
        Toast toast= Toast.makeText(context,message,duration);
        toast.show();
    }
    public void dislikeTopic(String t){
        Context context = getApplicationContext();
        CharSequence message = String.format(getString(R.string.sad_you_dislike),t);
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