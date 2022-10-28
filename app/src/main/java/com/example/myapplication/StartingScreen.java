package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StartingScreen extends AppCompatActivity {

    Button btnNewGame;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        btnNewGame = findViewById(R.id.btnNewGame);
        btnSettings = findViewById(R.id.btnSettings);
        //on click listener for new game button
        //loads the game activity
        btnNewGame.setOnClickListener(view->{
            //create intent for game activity and start activity
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        });
        //on click listener for settings button
        //loads the settings activity
        btnSettings.setOnClickListener(view->{
            //create intent for settings activity and start activity
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });
    }
}