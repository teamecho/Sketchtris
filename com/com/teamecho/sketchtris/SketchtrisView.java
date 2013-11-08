package com.teamecho.sketchtris;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SketchtrisView extends SurfaceView implements SurfaceHolder.Callback{
	private Paint paint; //paint object to draw shapes
	protected shape currentShape;  //what shape is currently falling, may implement circular buffer later
	private SketchtrisGrid myGrid; //talking bout myGrid dun dun dun, OOOoo oooooo, OOoooo ooooo
	private MainThread thread;
	
	public SketchtrisView(Activity context, GestureOverlayView g) {
		super(context);
		setFocusable(true);  //this view can have focus
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
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
			int height) { }


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while(retry){
			try{
				thread.join();
				retry = false;
			}
			catch(InterruptedException e){ /**try again!**/ }
		}
	}
	
	public void gestureInputted(){
	
	}

}
