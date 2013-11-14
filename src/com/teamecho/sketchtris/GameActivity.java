package com.teamecho.sketchtris;
 
import java.util.ArrayList;
 
import android.content.Intent;
 
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;
 
public class GameActivity extends FragmentActivity implements GameOverFragment.NoticeDialogListener { 
 
  private SketchtrisView gameplayView; 
	private GestureLibrary myGestureLib; 
	private GestureOverlayView gOV;
	private FrameLayout mFL; 
	private SketchtrisGrid mGrid; 
	private final int startX = mGrid.COLS/2 - 3; // centers falling piece
	private final int startY = 0;
	public shape currentShape;
	private MainThread thread;
	private SurfaceHolder surHold; // holds the screen while pieces like rain.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //call superclass Activity's onCreate to pass Bundle obj
		//setContentView(R.layout.activity_gameplay); <-- heres your issue, this can't be
		gOV = new GestureOverlayView(this); 
		gameplayView = new SketchtrisView(this, gOV); 
		mGrid = gameplayView.getGrid();
		mFL = new FrameLayout(this);
		//thread = new MainThread(gameplayView, surHold);
	
		 //gestureOverlayView.addView(surfaceView);    
        gOV.setOrientation(GestureOverlayView.ORIENTATION_VERTICAL);
        gOV.setEventsInterceptionEnabled(true);
        gOV.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);	
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
				// clean grid from last gesture?
				for ( int i  = 0; i < (SketchtrisGrid.COLS*SketchtrisGrid.ROWS); i++){
					mGrid.emptyCell(i);
				}
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
 
			          gameplayView.setCurrentShape(new shape((p.name.toCharArray())[0], mGrid)); // draws shape at intialize place.
			          //mGrid.placeShape(new shape(p.name, this), startX, startY);
			          if(gameplayView.currentShape != null) { gameplayView.currentShape.shiftShapeDown();}
			         
			          surfaceCreated(surHold);
			          
			          gameplayView.invalidate(); 
			          
			        
			         //gameplayView.invalidate();  
			         // mGrid.paint(canvas, paint);
			        }
			        else {
			        	if (p.score > 9.5 && !p.name.equals("T")) {
			        		Toast.makeText(getBaseContext(),p.name,Toast.LENGTH_SHORT).show();
			        	}
			        }
			      }
			    gOV.setFocusableInTouchMode(false);
			    }
	    });
        mFL.addView(gameplayView, 0);
        mFL.addView(gOV,1);
        setContentView(mFL);
        surfaceDestroyed(surHold);
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
	
	public void surfaceCreated(SurfaceHolder holder) {
		
		       thread.setRunning(true);
		       thread.start();
		
		    }
		
		 
		
		   public void surfaceDestroyed(SurfaceHolder holder) {
		        boolean retry = true;
		        while (retry) {
		          try {
		                thread.join();
		                retry = false;
		
		            } catch (InterruptedException e) {
		               // try again shutting down the thread
		           }
		
		        }
		
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
