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
	private shape currentShape;  //what shape is currently falling, may implement circular buffer later
	private SketchtrisGrid myGrid; //talking bout myGrid dun dun dun, OOOoo oooooo, OOoooo ooooo
	private int numUpdates; //how many updates we've done
	private int gamePlayTime; //how long the game has been playing
	private int fps; //arbitrary amount until we unify this across classes
	private long nextUpdate; //next update call
	private long nextShift;  //next time to shift the piece down (at the normal pace)
	private long ticks;
	private GestureOverlayView gOV;
	private MainThread thread;
	
	public SketchtrisView(Activity context, GestureOverlayView g) {
		super(context);
		setFocusable(true);  //this view can have focus
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
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
		numUpdates = 0; 
		gamePlayTime = 0; 
		fps = 60; //arbitrary number for now
		//make call here to create an empty grid for gameplay
		nextUpdate = 0;
		nextShift = 1;
		ticks = 0;
		currentShape = null;
	}
	
	
	public void drawGrid(Canvas canvas){
		myGrid.paint(canvas, paint);
	}
	
    @Override
    protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawGrid(canvas);
           
    }
    
    protected void update(){
    	if(currentShape != null){
    		if(!currentShape.bottomHit() && !currentShape.bottomCollission()){
    			currentShape.shiftShapeDown();
    		}
    		else{
    			currentShape = null;
    		}
    	}
    }


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
		
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while(retry){
			try{
				thread.join();
				retry = false;
			}
			catch(InterruptedException e){
				//try again!
			}
		}
		
	}
	
	public void gestureInputted(){
	
	}

}
