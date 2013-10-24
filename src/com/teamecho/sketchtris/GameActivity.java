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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
 
public class GameActivity extends FragmentActivity implements OnGesturePerformedListener, GameOverFragment.NoticeDialogListener { 
 
	private SketchtrisView gameplayView;
	private GestureLibrary myGestureLib; 
	private GestureOverlayView gOV;
	private FrameLayout mFL; 
	private SketchtrisGrid mGrid; 
	private final int startX = mGrid.COLS/2 - 3; // centers falling piece
	private final int startY = 0;
	public shape currentShape;
	public char shapeName;
	//number of each shape
	public int i = 15, l = 15, j = 15, z = 15, s = 15, o = 15, t = 15;
	public View view;
	public LayoutInflater inflater;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); //call superclass Activity's onCreate to pass Bundle obj
		//setContentView(R.layout.activity_gameplay); <-- heres your issue, this can't be
		inflater = this.getLayoutInflater();
	    view = inflater.inflate(R.layout.activity_gameplay, null);
	    gOV = new GestureOverlayView(this); 
		gameplayView = new SketchtrisView(this); 
		mGrid = gameplayView.getGrid(); 
		mFL = new FrameLayout(this);
		
		
		
		gOV.setScaleX(0.85f);
		gOV.setScaleY(0.85f);

		gOV.setTranslationX(-100);
		gOV.setTranslationY(-100);
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
			          shapeName = (p.name.toCharArray())[0];
			          currentShape = new shape(shapeName, mGrid);
			          updateNumOfPieces();
			          gameplayView.setCurrentShape(currentShape); // draws shape at intialize place.
			          //mGrid.placeShape(new shape(p.name, this), startX, startY);
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
			}
	    });
	    
	    
	    
        mFL.addView(gameplayView, 0);
        mFL.addView(gOV,1);
        mFL.addView(view,2);
        setContentView(mFL);
        gameOver();
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
	
	
	public void gameOver(){		
		//if spots greater than the max row have a 1			
			//confirmGameOver();
			//clear out grid
			//change activity		
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
		currentShape.fall(1);		
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

}
