package com.teamecho.sketchtris;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


public class SketchtrisView extends View {
	private Paint paint; //paint object to draw shapes
	private shape currentShape;  //what shape is currently falling, may implement circular buffer later
	private SketchtrisGrid myGrid; //talking bout myGrid dun dun dun, OOOoo oooooo, OOoooo ooooo
	private int numUpdates; //how many updates we've done
	private int gamePlayTime; //how long the game has been playing
	private int fps; //arbitrary amount until we unify this across classes
	
	public SketchtrisView(Activity context) {
		super(context);
		//hostActivity = context; //track who I display inside of to pass Context to methods
		//setBackgroundColor(color.holo_blue_dark);
		///setContentView(R.layout.activity_gameplay);
		setFocusable(true);  //this view can have focus
		//setFocusableInTouchMode(false); //focus priority on touch
		paint = new Paint(); //create Paint object to make shapes show up on screen
		myGrid = new SketchtrisGrid(); //init my grid obj to play on
		startGameVars(); //set all my gameplay instance specific vars
	}
	
	public SketchtrisGrid getGrid() {
		return myGrid; 
	}
	
	public void setCurrentShape(shape s) {
		currentShape = s; 
	}
	
	public void startGameVars() {
		numUpdates = 0; 
		gamePlayTime = 0; 
		fps = 60; //arbitrary number for now
		//make call here to create an empty grid for gameplay
	}
	
    @Override
    protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            myGrid.paint(canvas, paint); //paints elements
            //invalidate(); //ensures redraw occurs -- UNNECESSARY
    }

}
