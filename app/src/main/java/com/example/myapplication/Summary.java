package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends AppCompatActivity {
    TextView txtTaps;
    TextView txtDoubleTaps;
    TextView txtSwipes;
    TextView txtZooms;

    TextView txtTotalCommands;
    TextView txtAvgReactionTime;
    TextView txtFastestSwipe;

    Bundle bundle;
    Button btnPlayAgain;
    Button btnMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        bundle = getIntent().getExtras();

        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnMainMenu = findViewById(R.id.btnMainMenu);
        txtTaps = findViewById(R.id.txtTaps);
        txtDoubleTaps = findViewById(R.id.txtDoubleTaps);
        txtSwipes = findViewById(R.id.txtSwipes);
        txtZooms = findViewById(R.id.txtZooms);
        txtTotalCommands = findViewById(R.id.txtTotalCommands);
        txtAvgReactionTime = findViewById(R.id.txtAvgReactionTime);
        txtFastestSwipe = findViewById(R.id.txtFastestSwipe);


        populateTexts();
        //launch game activity again
        btnPlayAgain.setOnClickListener(view ->{
            Intent intent = new Intent(this, Game.class);
            startActivity(intent);
        });
        //take user back to starting screen
        btnMainMenu.setOnClickListener(view ->{
            Intent intent = new Intent(this, StartingScreen.class);
            startActivity(intent);
        });
    }
    //inflate the views from bundle from game activity
    public void populateTexts(){
        txtTaps.setText(bundle.getInt("TapCounter",0 )+" Taps");
        txtDoubleTaps.setText(bundle.getInt("DoubleTapCounter", 0)+" Double Taps");
        txtSwipes.setText(bundle.getInt("SwipeCounter", 0)+" Swipes");
        txtZooms.setText(bundle.getInt("ZoomCounter", 0)+" Zooms");
        txtTotalCommands.setText(bundle.getInt("GameCounter", 0)+"");
        txtFastestSwipe.setText(Math.round(bundle.getDouble("HighestVelo"))/10.00+" pixels per second");
        txtAvgReactionTime.setText((bundle.getDouble("AvgTime"))+" seconds");
    }
}