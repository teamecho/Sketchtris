package com.teamecho.sketchtris;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.FrameLayout;

public class GameActivity extends FragmentActivity implements GameOverFragment.NoticeDialogListener { 

	private SketchtrisView gameplayView; 
	private GestureLibrary myGestureLib; 
	private GestureOverlayView gOV;
	private FrameLayout mFL; 
	private SketchtrisGrid mGrid; 
	private final int startX = mGrid.COLS/2 - 3; // centers falling piece
	private final int startY = 0;
	public shape currentShape;
	public MediaPlayer mediaPlayer; 
	public AudioManager audioManager;
	public OnAudioFocusChangeListener afclr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createViews(); //moved view creation code to method to keep onCreate easy to read
		SharedPreferences settings = getSharedPreferences("preferences", 2);
		if (settings.getBoolean("prefIfDoPlayAudio", true)) { //is audio enabled in prefs     
	        initAudio(); //makes music
		}
	}
	
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    //release audio on interruption
	    if (mediaPlayer != null) {
	        mediaPlayer.pause();
	    }
	}
	
	public void onResume() {
		super.onResume(); 
		if (mediaPlayer != null) {
			mediaPlayer.start();
		}
	}
	
	public void onStop() {
		super.onStop();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		} 
	}
	
	public void initAudio() {
		SharedPreferences settings = getSharedPreferences("preferences", 0);
		String songName = settings.getString("prefKeySongPicker","none");  //defaults to none if not found
		Toast.makeText(getBaseContext(), "settings.getString() --> "+songName, Toast.LENGTH_SHORT).show();
		if (songName.equals("none")) {}
		else {
			audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE); //set audioManager
			afclr = new OnAudioFocusChangeListener() {
			    public void onAudioFocusChange(int focusChange) {
			        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) { //short term loss
			            mediaPlayer.pause();
			        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) { //gains focus
			            mediaPlayer.start(); 
			        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) { //likely permanent loss of focus?
			            audioManager.abandonAudioFocus(this); 
			            mediaPlayer.pause();
			            //mediaPlayer.release();
			        }
			    }
			};
		}
		requestPlayback(songName); //grab song choice from preferences
	}
	
	public void requestPlayback(String songName) {
		int result = audioManager.requestAudioFocus(afclr, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			if (songName.equals("Teris Dubstep")) {
				mediaPlayer = MediaPlayer.create(this, R.raw.t1);
				mediaPlayer.start();
			}
			else if (songName.equals("In My Mind")) {
				mediaPlayer = MediaPlayer.create(this, R.raw.t2);
				mediaPlayer.start();
			}
			else if (songName.equals("8 Bit Get Lucky")) {
				mediaPlayer = MediaPlayer.create(this, R.raw.t3);
				mediaPlayer.start();
			}
			else if (songName.equals("Technosteppin")) {
				mediaPlayer = MediaPlayer.create(this, R.raw.t4);
				mediaPlayer.start();
			}
			mediaPlayer.setLooping(true);
		}
	}
	
	//creates FrameLayout and adds all views to it -- put here to keep code clean
	public void createViews() {
		gOV = new GestureOverlayView(this); 
		gameplayView = new SketchtrisView(this); 
		mGrid = gameplayView.getGrid(); 
		mFL = new FrameLayout(this);
		  
        gOV.setOrientation(GestureOverlayView.ORIENTATION_VERTICAL);
        gOV.setEventsInterceptionEnabled(true);
        gOV.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);	

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
			          gameplayView.invalidate(); 
			        }
			        else {
			        	if (p.score > 9.5 && p.name.equals("T")) {
			        		Toast.makeText(getBaseContext(),p.name,Toast.LENGTH_SHORT).show();
			        		break; 
			        	}
			        }
			     }
			}
	    });
        mFL.addView(gameplayView, 0);
        mFL.addView(gOV,1);
        setContentView(mFL);
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
