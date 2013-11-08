package com.teamecho.sketchtris;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends FragmentActivity implements GameOverFragment.NoticeDialogListener  {

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
    	public char shapeName;
    	//number of each shape
    	public int i = 15, l = 15, j = 15, z = 15, s = 15, o = 15, t = 15;
    	public View view;
    	public LayoutInflater inflater;
    	public static int pieceStackHeight; 
        
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
         super.onPause(); // Always call the superclass method first
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
                String songName = settings.getString("prefKeySongPicker","none"); //defaults to none if not found
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
                        if (songName.equals("b")) {
                                mediaPlayer = MediaPlayer.create(this, R.raw.bansuriflutetablainstrumental);
                                mediaPlayer.start();
                        }
                        else if (songName.equals("e")) {
                                mediaPlayer = MediaPlayer.create(this, R.raw.etude30);
                                mediaPlayer.start();
                        }
                        else if (songName.equals("p")) {
                                mediaPlayer = MediaPlayer.create(this, R.raw.posturing);
                                mediaPlayer.start();
                        }
                        else if (songName.equals("s")) {
                                mediaPlayer = MediaPlayer.create(this, R.raw.ska);
                                mediaPlayer.start();
                        }
                        mediaPlayer.setLooping(true);
                }
        }
        
        //creates FrameLayout and adds all views to it -- put here to keep code clean
        public void createViews() {
    		LayoutInflater inflater = this.getLayoutInflater();
    	    view = inflater.inflate(R.layout.activity_gameplay, null);
    	    gOV = new GestureOverlayView(this); 
    		gameplayView = new SketchtrisView(this,gOV); 
    		mGrid = gameplayView.getGrid(); 
    		mFL = new FrameLayout(this);
    		Typeface tF = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf");
    		Typeface tRF = Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf"); 
    		
    		//inflate custom Toast
    		inflater = getLayoutInflater();
    		final View toastDefView = inflater.inflate(R.layout.toast_layout,
    		                               (ViewGroup) findViewById(R.id.toast_layout_root));
    		final TextView text = (TextView) toastDefView.findViewById(R.id.text);
    		text.setTypeface(tRF);
    		final ImageView imv = (ImageView) toastDefView.findViewById(R.id.imv);
    
            gOV.setOrientation(GestureOverlayView.ORIENTATION_HORIZONTAL);
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
    				for ( int i  = 0; i < pieceStackHeight; i++){
    					mGrid.emptyCell(i);
    				}
    				Log.v("performed","performed");
					//Toast.makeText(getBaseContext(), "Stack height: " + pieceStackHeight, Toast.LENGTH_SHORT).show();
    				//as our gestureLibrary tries to recognize drawn shapes, it creates Prediction objects;
    				//Prediction objects basically store a name and a score (score is likelihood of a match)
    				//So predictions is an ArrayList of all our Gestures with the likelihood that we have a match of each one
    			    ArrayList<Prediction> predictions = myGestureLib.recognize(gesture);
    			    //boolean isIDed = false;
    			    for (Prediction p : predictions) {
    			    	//while a higher score value is "better" and helps eliminate false positives, 
    			    	//if we're too strict nobody will ever get one of their drawn shapes recognized.
    			        if (p.score > 0.90) {
    			          if (noMoreToDraw(p.name)) {
    			        	  Toast.makeText(getBaseContext(), "You are out of " + p.name + "!", Toast.LENGTH_SHORT).show();
    			        	  break;
    			          }
    			          else {
    			        	  //grab image of shape from res/drawable and inflate into custom toast view
    			        	  int id = getResources().getIdentifier("com.teamecho.sketchtris:drawable/" + p.name.toLowerCase(), null, null);
    			        	  imv.setImageResource(id);
    			        	  imv.setImageDrawable(getResources().getDrawable(id));
    			      		  text.setText(p.name);
    			    		  Toast toast = new Toast(getApplicationContext());
    			    		  toast.setGravity(Gravity.BOTTOM, 0, 0);
    			    		  toast.setDuration(Toast.LENGTH_SHORT);
    			    		  toast.setView(toastDefView);
    			    		  toast.show();
    			        	 
	    			          shapeName = (p.name.toCharArray())[0];
	    			          currentShape = new shape(shapeName, mGrid);
	    			          updateNumOfPieces();
	    			          gameplayView.setCurrentShape(currentShape); // draws shape at intialize place.
	    			          //mGrid.placeShape(new shape(p.name, this), startX, startY);
	    	    			  if (currentShape.topHit()) {
	    	    					confirmGameOver();
	    	    					Toast.makeText(getBaseContext(), "Game over", Toast.LENGTH_SHORT).show();
	    	    					break;
	    	    			  }
	    			          gameplayView.invalidate(); 
	    			          //isIDed = true;
	    			          break;
    			          }
    			        }
    			        else {
    			        	//Toast.makeText(getBaseContext(),"Accurage below threshold: " + p.score,Toast.LENGTH_SHORT).show();
  			      		    text.setText("Unrecognized input!");
  			      		    imv.setImageDrawable(null);
  			    		    Toast toast = new Toast(getApplicationContext());
  			    		    toast.setGravity(Gravity.BOTTOM, 0, 0);
  			    		    toast.setDuration(Toast.LENGTH_SHORT);
  			    		    toast.setView(toastDefView);
  			    		    toast.show();
    			        }
    			    }
    			}
    	    });
            mFL.addView(gameplayView, 0);
            mFL.addView(gOV,1);
            mFL.addView(view,2);
            setContentView(mFL);
     }
		
    private boolean noMoreToDraw(String str) { //checks if player out of specific shape
    	//i l j z s o t
    	if (str.equalsIgnoreCase("i"))      return i <= 0;
    	else if (str.equalsIgnoreCase("l")) return l <= 0; 
    	else if (str.equalsIgnoreCase("j")) return j <= 0; 
    	else if (str.equalsIgnoreCase("z")) return z <= 0;
    	else if (str.equalsIgnoreCase("s")) return s <= 0;
    	else if (str.equalsIgnoreCase("o")) return o <= 0;
    	else if (str.equalsIgnoreCase("t")) return t <= 0; 
		return false;
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
	public void moveRight(View V){
		currentShape.move('R',1);		
	}
	
	//move piece left
	public void moveLeft(View V){
		currentShape.move('L',1);		
	}
	
	//move piece down	
	public void moveDown(View V){
		//currentShape.fall(1);		
	}
	
	//rotate piece
	public void rotateRight(View V){
		currentShape.rotateRight();
	}
	
	//rotate piece
	public void rotateLeft(View V){
		currentShape.rotateLeft();
	}
	
	//update piece array
	public void updateNumOfPieces(){	
		TextView text;
		switch(shapeName){
			case 'O':
				o-=1;
				text = (TextView) view.findViewById(R.id.numOfO);
				text.setText(o + " - O");
				break;
			case 'L':
				l-=1;
				text = (TextView) view.findViewById(R.id.numOfL);
				text.setText(l + " - L");
				break;
			case 'S':
				s-=1;
				text = (TextView) view.findViewById(R.id.numOfS);
				text.setText(s + " - S");
				break;
			case 'I':
				i-=1;
				text = (TextView) view.findViewById(R.id.numOfI);
				text.setText(i + " - I");
				break;
			case 'T':
				t-=1;
				text = (TextView) view.findViewById(R.id.numOfT);
				text.setText(t + " - T");
				break;
			case 'Z':
				z-=1;
				text = (TextView) view.findViewById(R.id.numOfZ);
				text.setText(z + " - Z");
				break;
			case 'J':
				j-=1;
				text = (TextView) view.findViewById(R.id.numOfJ);
				text.setText(j + " - J");
				break;
		}
	}
	
	/**
	@Override
	public boolean onTouchEvent(MotionEvent event){ 
	    int action = MotionEventCompat.getActionMasked(event);
	    float xCoord = event.getX();
	    float yCoord = event.getY(); 
	    
	    switch(action) {
	        case (MotionEvent.ACTION_DOWN) :
	            return true;
	        case (MotionEvent.ACTION_MOVE) :
	            return true;
	        case (MotionEvent.ACTION_UP) :
	            return true;
	        case (MotionEvent.ACTION_CANCEL) :
	            return true;
	        case (MotionEvent.ACTION_OUTSIDE) :
	            return true;      
	        default : 
	            return super.onTouchEvent(event);
	    }      
	}
	**/
}
