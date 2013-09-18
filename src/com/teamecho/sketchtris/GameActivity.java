package com.teamecho.sketchtris;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.Toast;

public class GameActivity extends Activity implements OnGesturePerformedListener {
	GestureLibrary myGestureLib; //this will have the defined gestures stored in it, so it knows what to look for
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//call superclass Activity's onCreate to pass Bundle obj
		super.onCreate(savedInstanceState);
		//set the layout to the xml defined for gameplay file
		setContentView(R.layout.activity_gameplay);
		//create GestureOverlayView obj -- this will encompass the region where the gestures are picked up
		GestureOverlayView gestureOverlayView = (GestureOverlayView)findViewById(R.id.gestureOverlayView1);
		//link this guy up to the listener we define for when a gesture happens
	    gestureOverlayView.addOnGesturePerformedListener(this);
	    //make sure our gestures pre defined to correspond to Tetris shapes are used 
	    myGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
	    //error check in case we can't open the gestures file
	    if (!myGestureLib.load()) {
	    	finish(); 
	    }
	}

	@Override
	//this method fires off when the user has finished inputting the gesture
	public void onGesturePerformed(GestureOverlayView arg0, Gesture gesture) {
		//as our gestureLibrary tries to recognize drawn shapes, it creates Prediction objects;
		//Prediction objects basically store a name and a score (score is likelihood of a match)
		//So predictions is an ArrayList of all our Gestures with the likelihood that we have a match of each one
	    ArrayList<Prediction> predictions = myGestureLib.recognize(gesture);
	    for (Prediction p : predictions) {
	    	//while a higher score value is "better" and helps eliminate false positives, 
	    	//if we're too strict nobody will ever get one of their drawn shapes recognized.
	        if (p.score > 6.0) {
	          Toast.makeText(this, p.name, Toast.LENGTH_SHORT).show(); //just simple popup to say shape name, using Tetris letters
	        }
	      }
	}
	
}
