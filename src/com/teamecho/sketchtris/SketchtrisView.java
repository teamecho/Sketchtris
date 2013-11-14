package com.teamecho.sketchtris;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class SketchtrisView extends SurfaceView implements SurfaceHolder.Callback{
	private Paint paint; //paint object to draw shapes
	shape currentShape;  //what shape is currently falling, may implement circular buffer later
	private SketchtrisGrid myGrid; //talking bout myGrid dun dun dun, OOOoo oooooo, OOoooo ooooo
	private GestureOverlayView gOV;
	
	private MainThread thread;
	
	public SketchtrisView(Activity context, GestureOverlayView g) {
		super(context);
		
		getHolder().addCallback(this);
		//hostActivity = context; //track who I display inside of to pass Context to methods
		//setBackgroundColor(color.holo_blue_dark);
		//setContentView(R.layout.activity_gameplay);
		setFocusable(true);  //this view can have focus
		thread = new MainThread(this.getHolder(), this);
		//setFocusableInTouchMode(false); //focus priority on touch
		paint = new Paint(); //create Paint object to make shapes show up on screen
		myGrid = new SketchtrisGrid(); //init my grid obj to play on
		gOV = g;
		startGameVars(); //set all my gameplay instance specific vars
	}
	
	public SketchtrisGrid getGrid() {
		return myGrid; 
	}
	
	public void setCurrentShape(shape s) {
		currentShape = s; 
	}
	
	public void startGameVars() {
		currentShape = null;
	}
	
	private void drawGrid(Canvas canvas){
		myGrid.paint(canvas, paint);
		
		
	}
    @Override
    protected void onDraw(Canvas canvas) {
    /*	int count = 0;
            super.onDraw(canvas);
            myGrid.paint(canvas, paint); //paints elements
            if(currentShape != null) {currentShape.shiftShapeDown(); }
            //invalidate(); //ensures redraw occurs -- UNNECESSARY
            if(currentShape != null && count < myGrid.ROWS){
            	count++;
            	for(int i = 0; i< count; i++){
            	currentShape.shiftShapeDown();
            }
            }
            */
    	drawGrid(canvas);
    }
    
    protected void update(){
    	long time = System.currentTimeMillis();
    	
    	//create game over check somewhere once the grid is working right
    	//Assume this is not while drawing? 
    	//if(time > nextUpdate){
    	//	nextUpdate = time + 1000 / fps;
    	//	ticks++;

			currentShape.shiftShapeDown();
    		onDraw(myGrid.currentCanvas);
    		
    		//SOMEWHERE IN THIS HELL:
    		// CHECK IF THE ACTIVE SHAPE HAS HIT THE BOTTOM
    		// IF IT HAS
    		// REACTIVATE GOV
    		
    		// THEN KILL SELF BY IMPLAING SELF ON Z shape
    		
    	//}
    	
    }


	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
	}
	public SurfaceHolder getHolder(){
		return thread.getSurHold();
	}

}
