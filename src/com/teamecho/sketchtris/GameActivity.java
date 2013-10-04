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
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast; 

public class GameActivity extends Activity {
	private SketchtrisView gameplayView; 
	private GestureLibrary myGestureLib; 
	private GestureOverlayView gOV;
	private FrameLayout mFL; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //call superclass Activity's onCreate to pass Bundle obj
		
		//solution 2
		gOV = new GestureOverlayView(this); 
		gameplayView = new SketchtrisView(this); 
		mFL = new FrameLayout(this);
		
		 //gestureOverlayView.addView(surfaceView);    
        gOV.setOrientation(gOV.ORIENTATION_VERTICAL);
        gOV.setEventsInterceptionEnabled(true);
        gOV.setGestureStrokeType(gOV.GESTURE_STROKE_TYPE_MULTIPLE);	
        //gOV.addOnGesturePerformedListener(this);
	    //make sure our gestures pre defined to correspond to Tetris shapes are used 
	    myGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
	    //error check in case we can't open the gestures file
	    if (!myGestureLib.load()) {
	    	finish(); 
	    }
        
	    gOV.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			@Override
			//this method fires off when the user has finished inputting the gesture
			public void onGesturePerformed(GestureOverlayView arg0, Gesture gesture) {
				Log.v("performed","performed");
				//as our gestureLibrary tries to recognize drawn shapes, it creates Prediction objects;
				//Prediction objects basically store a name and a score (score is likelihood of a match)
				//So predictions is an ArrayList of all our Gestures with the likelihood that we have a match of each one
			    ArrayList<Prediction> predictions = myGestureLib.recognize(gesture);
			    for (Prediction p : predictions) {
			    	//while a higher score value is "better" and helps eliminate false positives, 
			    	//if we're too strict nobody will ever get one of their drawn shapes recognized.
			        if (p.score > 4.5 && !(p.name.equals("T"))) {
			          Toast.makeText(getBaseContext(), p.name, Toast.LENGTH_SHORT).show(); //just simple popup to say shape name, using Tetris letters
			        }
			        else {
			        	if (p.score > 6.5) {
			        		Toast.makeText(getBaseContext(),p.name,Toast.LENGTH_SHORT).show();
			        	}
			        }
			      }
			}
	    });
        mFL.addView(gameplayView, 0);
        mFL.addView(gOV,1);
        setContentView(mFL);
	}
}
