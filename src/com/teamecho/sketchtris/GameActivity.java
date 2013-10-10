package com.teamecho.sketchtris;

import java.util.ArrayList;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class GameActivity extends FragmentActivity implements OnGesturePerformedListener, GameOverFragment.NoticeDialogListener {
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
	    
	    confirmGameOver();
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
	
	/*tests to see if the game is over ******* NEEDS TO BE MOVED TO THE GAME VIEW ********
	 *returns true if it is*/
	public boolean gameOver(){
		boolean over = false;		
		//if spots greater than the max row have a 1
			//over = true;
			confirmGameOver();
			//clear out grid
			//change activity
		return over;
	}
	
	//popup fragment for game over
	public void confirmGameOver() {
	    DialogFragment newFragment = new GameOverFragment();
	    newFragment.show(getSupportFragmentManager(), "gameover");
	}
	
	//for game over button
	public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
    }
	
	//move piece right
	public void moveRight(){
		
		
	}
	
	//move piece left
	public void moveLeft(){
		
		
	}
	
	//move piece down
	
	public void moveDown(){
		
		
	}
	
	//rotate piece
	public void rotate(){
		
	
	}
	
	
}
