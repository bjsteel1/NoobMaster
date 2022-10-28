package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    Button btnCancel;
    Button btnSave;
    SeekBar seekCommands;
    CheckBox chkTap;
    CheckBox chkDoubleTap;
    CheckBox chkSwipe;
    CheckBox chkZoom;
    TextView txtCommands;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        seekCommands = findViewById(R.id.seekCommands);
        chkTap = findViewById(R.id.chkTap);
        chkDoubleTap = findViewById(R.id.chkDoubleTap);
        chkSwipe = findViewById(R.id.chkSwipe);
        chkZoom = findViewById(R.id.chkZoom);
        txtCommands = findViewById(R.id.txtCommands);

        sharedPref = getSharedPreferences("GAME_PREFS", Context.MODE_PRIVATE);

        populateView();
        //when user presses the save button save their shared pref and launch starting screen
        btnSave.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("numCommands", seekCommands.getProgress());
            if (chkTap.isChecked()) {
                editor.putString("Tap", "True");
            } else {
                editor.putString("Tap", "False");
            }
            if (chkDoubleTap.isChecked()) {
                editor.putString("Double", "True");
            } else {
                editor.putString("Double", "False");
            }
            if (chkSwipe.isChecked()) {
                editor.putString("Swipe", "True");
            } else {
                editor.putString("Swipe", "False");
            }
            if (chkZoom.isChecked()) {
                editor.putString("Zoom", "True");
            } else {
                editor.putString("Zoom", "False");
            }
            editor.commit();

            Intent intent = new Intent(this, StartingScreen.class);
            startActivity(intent);
            finish();
        });
        //launch starting screen when user presses cancel
        btnCancel.setOnClickListener(view -> {
            Intent intent = new Intent(this, StartingScreen.class);
            startActivity(intent);
            finish();
        });
        seekCommands.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //when the user scrolls the seekbar update the textbox to reflect the current value
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtCommands.setText(seekCommands.getProgress() + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
    //inflate the views with values from shared pref
    public void populateView() {
        if (sharedPref.getString("Tap", "True").equals("True")) {
            chkTap.setChecked(true);
        } else {
            chkTap.setChecked(false);
        }
        if (sharedPref.getString("Double", "True").equals("True")) {
            chkDoubleTap.setChecked(true);
        } else {
            chkDoubleTap.setChecked(false);
        }
        if (sharedPref.getString("Swipe", "True").equals("True")) {
            chkSwipe.setChecked(true);
        } else {
            chkSwipe.setChecked(false);
        }
        if (sharedPref.getString("Zoom", "True").equals("True")) {
            chkZoom.setChecked(true);
        } else {
            chkZoom.setChecked(false);
        }

        seekCommands.setProgress(sharedPref.getInt("numCommands", 10));
        txtCommands.setText(seekCommands.getProgress() + "");
    }
}