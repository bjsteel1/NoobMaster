package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game extends AppCompatActivity {
    //bindings for views
    TextView txtZone1;
    TextView txtZone2;
    TextView txtZone3;
    TextView txtZone4;
    TextView txtSwipeUP;
    Long StartTime;
    Long EndTime;
    //used to create a buffer if someone taps too close to an edge
    int buffer = 150;
    //holds the pixel values of the edges of the phone
    int LEFTEDGE;
    int TOPEDGE;
    int RIGHTEDGE;
    int BOTTOMEDGE;
    //counter variables for the number of times a certain action was rolled
    int TAPCOUNTER;
    int DOUBLETAPCOUNTER;
    int SWIPECOUNTER;
    int ZOOMCOUNTER;
    //how many turns have currently been played
    int GAMECOUNTER;
    //holds the current zone the user needs to do motions in
    int CURRENTZONE;
    //stores highest velocity fling
    double HIGHESTVELO;
    //holds the value of users setting number of commands
    int TOTALCOMMANDS;
    //counter used to differ single taps from double taps
    int SINGLETAPCOUNTER;
    //holds random value of the number of times the user must single tap
    int NUMOFTAPS;
    //holds the random swipe direction
    String SWIPEDIRECTION;
    //holds the random zoom in or zoom out
    String ZOOMDIRECTION;
    //holds the current random action that was rolled
    String action;
    //holds the motions that can be rolled based on users setting and shared prefs
    ArrayList<String> motionOpts = new ArrayList<String>();
    //holds the directions a swipe can go
    ArrayList<String> swipeOpts = new ArrayList<String>(Arrays.asList("up", "right", "down", "left"));
    //holds the directions a zoom can go
    ArrayList<String> zoomOpts = new ArrayList<String>(Arrays.asList("out","in"));

    GestureDetector myDetector;
    ScaleGestureDetector sDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //bind views
        txtZone1 = findViewById(R.id.txtZone1);
        txtZone2 = findViewById(R.id.txtZone2);
        txtZone3 = findViewById(R.id.txtZone3);
        txtZone4 = findViewById(R.id.txtZone4);
        txtSwipeUP = findViewById(R.id.txtSwipeUp);
        //load users shared pref
        getSettings();
        //set counters to 0
        setCounters();
        myDetector = new GestureDetector(this, new GestureListener());
        sDetector = new ScaleGestureDetector(this, new ScaleListener());
    }
    //override on touch event for motion tracking
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myDetector.onTouchEvent(event) || sDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    //method to set all the counters for the app to 0
    public void setCounters(){
        GAMECOUNTER = 0;
        TAPCOUNTER = 0;
        DOUBLETAPCOUNTER = 0;
        SWIPECOUNTER = 0;
        ZOOMCOUNTER = 0;
        HIGHESTVELO = 0;
    }
    //Method to decide a random motion and a random zone
    //Once the zone is rolled we then roll a motion
    //if that motion rolls swipe we then roll a direction
    //if that motion rolls tap we then roll a random number of taps between 1 and 5
    //if that motion rolls zoom or double tap then no secondary random roll is needed
    //once all random rolls are decided then set the text in the zone to the rolled values
    public void gameTurn() {
        //create random object
        Random random = new Random();
        //get a random number between 1 and 4
        //int num is used in first switch case to decide zone
        int num = random.nextInt(4) + 1;
        //get a random number between 0 and the size of motion options array
        int num1 = random.nextInt(motionOpts.size());
        //first switch case, to get the zone from int num
        switch (num) {
            case 1:
                action = motionOpts.get(num1);
                //nested switch case that either
                //1. gets random number between 1 and 5 for single taps
                //2. double taps nothing extra is done
                //3. swipes gets random direction up down left or right
                //4. zooms gets random direction in or out
                switch(action){
                    case("Tap"):
                        NUMOFTAPS = random.nextInt(5)+1;
                        txtZone1.setText(action + " " + NUMOFTAPS + " times");
                        CURRENTZONE = num;
                        break;
                    case("Double Tap"):
                        action = motionOpts.get(num1);
                        txtZone1.setText(action);
                        CURRENTZONE = num;
                        break;
                    case("Swipe"):
                        action = motionOpts.get(num1);
                        SWIPEDIRECTION = swipeOpts.get(random.nextInt(swipeOpts.size()));
                        txtZone1.setText(action+ " " + SWIPEDIRECTION);
                        CURRENTZONE = num;
                        break;
                    case("Zoom"):
                        ZOOMDIRECTION = zoomOpts.get(random.nextInt(zoomOpts.size()));
                        action = motionOpts.get(num1);
                        txtZone1.setText(action + " " + ZOOMDIRECTION);
                        CURRENTZONE = num;
                        break;
                }
                break;
            case 2:
                action = motionOpts.get(num1);
                switch(action){
                    case("Tap"):
                        NUMOFTAPS = random.nextInt(5)+1;
                        txtZone2.setText(action + " " + NUMOFTAPS + " times");
                        CURRENTZONE = num;
                        break;
                    case("Double Tap"):
                        action = motionOpts.get(num1);
                        txtZone2.setText(action);
                        CURRENTZONE = num;
                        break;
                    case("Swipe"):
                        action = motionOpts.get(num1);
                        SWIPEDIRECTION = swipeOpts.get(random.nextInt(swipeOpts.size()));
                        txtZone2.setText(action+ " " + SWIPEDIRECTION);
                        CURRENTZONE = num;
                        break;
                    case("Zoom"):
                        ZOOMDIRECTION = zoomOpts.get(random.nextInt(zoomOpts.size()));
                        action = motionOpts.get(num1);
                        txtZone2.setText(action + " " + ZOOMDIRECTION);
                        CURRENTZONE = num;
                        break;
                }
                break;
            case 3:
                action = motionOpts.get(num1);
                switch(action){
                    case("Tap"):
                        NUMOFTAPS = random.nextInt(5)+1;
                        txtZone3.setText(action + " " + NUMOFTAPS + " times");
                        CURRENTZONE = num;
                        break;
                    case("Double Tap"):
                        action = motionOpts.get(num1);
                        txtZone3.setText(action);
                        CURRENTZONE = num;
                        break;
                    case("Swipe"):
                        action = motionOpts.get(num1);
                        SWIPEDIRECTION = swipeOpts.get(random.nextInt(swipeOpts.size()));
                        txtZone3.setText(action+ " " + SWIPEDIRECTION);
                        CURRENTZONE = num;
                        break;
                    case("Zoom"):
                        ZOOMDIRECTION = zoomOpts.get(random.nextInt(zoomOpts.size()));
                        action = motionOpts.get(num1);
                        txtZone3.setText(action + " " + ZOOMDIRECTION);
                        CURRENTZONE = num;
                        break;
                }
                break;
            case 4:
                action = motionOpts.get(num1);
                switch(action){
                    case("Tap"):
                        NUMOFTAPS = random.nextInt(5)+1;
                        txtZone4.setText(action + " " + NUMOFTAPS + " times");
                        CURRENTZONE = num;
                        break;
                    case("Double Tap"):
                        action = motionOpts.get(num1);
                        txtZone4.setText(action);
                        CURRENTZONE = num;
                        break;
                    case("Swipe"):
                        action = motionOpts.get(num1);
                        SWIPEDIRECTION = swipeOpts.get(random.nextInt(swipeOpts.size()));
                        txtZone4.setText(action+ " " + SWIPEDIRECTION);
                        CURRENTZONE = num;
                        break;
                    case("Zoom"):
                        ZOOMDIRECTION = zoomOpts.get(random.nextInt(zoomOpts.size()));
                        action = motionOpts.get(num1);
                        txtZone4.setText(action + " " + ZOOMDIRECTION);
                        CURRENTZONE = num;
                        break;
                }
                break;
        }

    }
    //method to load in users shared pref and populate
    //TOTALCOMMANDS: this is the number of turns that will be played, 10 by default
    //motionOpts: this holds the motions that can be rolled between Tap, Double Tap, Swipe, Zoom, all are in array by default
    public void getSettings() {

        SharedPreferences sharedPref = getSharedPreferences("GAME_PREFS", Context.MODE_PRIVATE);
        TOTALCOMMANDS = sharedPref.getInt("numCommands", 10);
        if (sharedPref.getString("Tap", "True").equals("True")){
            motionOpts.add("Tap");
        }
        if (sharedPref.getString("Double", "True").equals("True")){
            motionOpts.add("Double Tap");
        }
        if (sharedPref.getString("Swipe", "True").equals("True")){
            motionOpts.add("Swipe");
        }
        if (sharedPref.getString("Zoom", "True").equals("True")){
            motionOpts.add("Zoom");
        }
    }
    //method that is called to get pixel dimensions of the device
    public void getZones() {
        TOPEDGE = txtZone1.getTop();
        LEFTEDGE = txtZone1.getLeft();
        BOTTOMEDGE = txtZone4.getBottom();
        RIGHTEDGE = txtZone4.getRight();
    }
    //method used to change all the text view zones back to their default values
    public void resetZones(){
        txtZone1.setText("1");
        txtZone2.setText("2");
        txtZone3.setText("3");
        txtZone4.setText("4");
    }
    //method that is called once the user has reached their max number of turns
    //total time to complete the turns is saved
    //all data that is needed for the summary screen is package into a bundle
    //bundle is put into extra for the intent to go to summary screen
    //activity is finished so the user cannot return
    public void endGame(){
        EndTime = System.currentTimeMillis();
        double avgTime = ((EndTime - StartTime)/1000.0)/GAMECOUNTER;
        Bundle bundle = new Bundle();
        bundle.putDouble("AvgTime", avgTime);
        bundle.putInt("GameCounter", GAMECOUNTER);
        bundle.putDouble("HighestVelo", HIGHESTVELO);
        bundle.putInt("TapCounter", TAPCOUNTER);
        bundle.putInt("DoubleTapCounter", DOUBLETAPCOUNTER);
        bundle.putInt("SwipeCounter", SWIPECOUNTER);
        bundle.putInt("ZoomCounter", ZOOMCOUNTER);
        Intent intent = new Intent(this, Summary.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    //method called to increment counters
    public void incCounters(){
        SINGLETAPCOUNTER = 0;
        GAMECOUNTER++;
        switch (action){
            case("Tap"):
                TAPCOUNTER++;
                break;
            case("Double Tap"):
                DOUBLETAPCOUNTER++;
                break;
            case("Swipe"):
                SWIPECOUNTER++;
                break;
            case("Zoom"):
                ZOOMCOUNTER++;
                break;
        }
        if(GAMECOUNTER == TOTALCOMMANDS){
            endGame();
        }
    }
    //this method is called to test if the user is performing there actions in the right zone
    //switch case is used on the zone that they should be in
    //then x and y cords of the motion event are then checked against the dimenions of the device to see if users are in the correction zone
    //if the users are in the correct zones we call incCounters(), resetZones(), and gameTurn()
    //if users are not in the correct zone nothing happens and we wait for them to input the motion in the correct zone
    //this method is used for tap doubletap and swipe
    public void correctZone(MotionEvent motionEvent){
        switch(CURRENTZONE){
            case(1):
                if(motionEvent.getX() > LEFTEDGE && motionEvent.getX()< ((RIGHTEDGE/2)+buffer) && motionEvent.getY() > TOPEDGE && motionEvent.getY() < ((BOTTOMEDGE/2)+buffer)){
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
            case(2):
                if(motionEvent.getX() > ((RIGHTEDGE/2)-buffer) && motionEvent.getX()< RIGHTEDGE && motionEvent.getY() > TOPEDGE && motionEvent.getY() < ((BOTTOMEDGE/2)+buffer)) {
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
            case(3):
                if(motionEvent.getX() > LEFTEDGE && motionEvent.getX()< ((RIGHTEDGE/2)+buffer) &&motionEvent.getY() > ((BOTTOMEDGE/2)-buffer) && motionEvent.getY() < BOTTOMEDGE){
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
            case(4):
                if(motionEvent.getX() > ((RIGHTEDGE/2)-buffer) && motionEvent.getX()< RIGHTEDGE && motionEvent.getY() > ((BOTTOMEDGE/2)-buffer) && motionEvent.getY() < BOTTOMEDGE) {
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
        }
    }
    //this method is called to test if the user is performing there actions in the right zone
    //switch case is used on the zone that they should be in
    //then x and y cords of the motion event are then checked against the dimenions of the device to see if users are in the correction zone
    //if the users are in the correct zones we call incCounters(), resetZones(), and gameTurn()
    //if users are not in the correct zone nothing happens and we wait for them to input the motion in the correct zone
    //this method is used for zoom because of different syntax
    public void correctZoneZoom(ScaleGestureDetector detector){
        switch(CURRENTZONE){
            case(1):
                if(detector.getFocusX() > LEFTEDGE && detector.getFocusX()< ((RIGHTEDGE/2)+buffer) && detector.getFocusY() > TOPEDGE && detector.getFocusY() < ((BOTTOMEDGE/2)+buffer)){
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
            case(2):
                if(detector.getFocusX() > ((RIGHTEDGE/2)-buffer) && detector.getFocusX()< RIGHTEDGE && detector.getFocusY() > TOPEDGE && detector.getFocusY() < ((BOTTOMEDGE/2)+buffer)) {
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
            case(3):
                if(detector.getFocusX() > LEFTEDGE && detector.getFocusX()< ((RIGHTEDGE/2)+buffer) &&detector.getFocusY() > ((BOTTOMEDGE/2)-buffer) && detector.getFocusY() < BOTTOMEDGE){
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
            case(4):
                if(detector.getFocusX() > ((RIGHTEDGE/2)-buffer) && detector.getFocusX()< RIGHTEDGE && detector.getFocusY() > ((BOTTOMEDGE/2)-buffer) && detector.getFocusY() < BOTTOMEDGE) {
                    incCounters();
                    resetZones();
                    gameTurn();
                    break;
                }
                else break;
        }
    }


    class GestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {

            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }
        //When the user flings check to see if the swipe up to begin game text is visible
        //if true this means that the fling is to start the game not to perform a game turn
        //if the text field is hidden that means game is being played and we should be looking for swipe actions
        //if the current action that should be preformed is a swipe we then check the direction that was supposed to be swiped
        //if the user swiped the correct direction we then check if this fling is their new highest velocity
        //we then check if the fling was called in the correct zone, if so then the game will proceed.
        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            if(txtSwipeUP.getVisibility() == View.VISIBLE){
                StartTime = System.currentTimeMillis();
                getZones();
                txtSwipeUP.setVisibility(View.INVISIBLE);
                gameTurn();
            }
            else if(action.equals("Swipe")){
                switch(SWIPEDIRECTION){
                    case("up"):
                        if(motionEvent.getY() > motionEvent1.getY()){
                            double velo = Math.sqrt(((v*v) + (v1*v1)));
                            if(velo > HIGHESTVELO){
                                HIGHESTVELO = velo;
                            }
                            correctZone(motionEvent1);
                        }
                        break;
                    case("down"):
                        if(motionEvent.getY() < motionEvent1.getY()){
                            double velo = Math.sqrt(((v*v) + (v1*v1)));
                            if(velo > HIGHESTVELO){
                                HIGHESTVELO = velo;
                            }
                            correctZone(motionEvent1);
                        }
                        break;
                    case("left"):
                        if(motionEvent.getX() > motionEvent1.getX()){
                            double velo = Math.sqrt(((v*v) + (v1*v1)));
                            if(velo > HIGHESTVELO){
                                HIGHESTVELO = velo;
                            }
                            correctZone(motionEvent1);
                        }
                        break;
                    case("right"):
                        if(motionEvent.getX() < motionEvent1.getX()){
                            double velo = Math.sqrt(((v*v) + (v1*v1)));
                            if(velo > HIGHESTVELO){
                                HIGHESTVELO = velo;
                            }
                            correctZone(motionEvent1);
                        }
                        break;
                }
            }

            return false;
        }
        //if the current action that should be preformed is a tap we then increment the tap counter
        //if the tap counter is equal to the number of taps chosen randomly then we can proceed
        //we then check if the tap was called in the correct zone, if so then the game will proceed.
        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if(action.equals("Tap")){
                SINGLETAPCOUNTER++;
                if(SINGLETAPCOUNTER == NUMOFTAPS){
                    correctZone(motionEvent);
                }
            }
            return false;
        }
        //if the current action that should be preformed is a tap we then increment the tap counter twice if the user was tapping quickly
        //if the tap counter is equal to the number of taps chosen randomly or 1 greater because a double increment then we can proceed
        //we then check if the tap was called in the correct zone, if so then the game will proceed.

        //if the current action that should be preformed is a double tap
        //check if the user tapped in the correct zone
        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if(action.equals("Tap")){
                SINGLETAPCOUNTER++;
                SINGLETAPCOUNTER++;
                if(SINGLETAPCOUNTER == NUMOFTAPS || SINGLETAPCOUNTER == (NUMOFTAPS+1)){
                    correctZone(motionEvent);
                }
            }
            if(action.equals("Double Tap")){

                correctZone(motionEvent);

            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }
    }

    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        public ScaleListener() {
            super();
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return super.onScaleBegin(detector);
        }
        //check if user zoomed in or out
        //check if user zoomed in correct zone
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if(ZOOMDIRECTION == "in" && detector.getScaleFactor() < 1){
                if(action.equals("Zoom")){
                    correctZoneZoom(detector);
                }
            }
            else if(ZOOMDIRECTION == "out" && detector.getScaleFactor() > 1){
                if(action.equals("Zoom")){
                    correctZoneZoom(detector);
                }
            }

            super.onScaleEnd(detector);
        }
    }

}