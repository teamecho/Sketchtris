package com.teamecho.sketchtris;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
	int TICKS_PER_SECOND = 50;
	int SKIP_TICKS = 1000/TICKS_PER_SECOND;
	int MAX_FRAMESKIP  =10;
	private static final String TAG = MainThread.class.getSimpleName();
	private SurfaceHolder surHold;
	
	SketchtrisView view;
	private boolean running;
	public void setRunning(boolean running){
		this.running = running;
	}
	
	public MainThread(SurfaceHolder sh, SketchtrisView sv){
		super();
		this.view = sv;
		setSurHold(sh);
	}
	
	public boolean isRunning() {
        return running;
    }
	
	@SuppressLint("WrongCall")
	public void run() {
		Canvas canvas;
		
		long tickCount = 50;
		Log.d(TAG, "Starting game loop");
		while(running){
			canvas = null;
			try{ 
				canvas = this.getSurHold().lockCanvas();
				synchronized(getSurHold()){
					
					if(view.currentShape != null){
						tickCount++;
						view.update();
						view.onDraw(canvas);
					}
				}
			}
			finally { 
				if(canvas != null){
					getSurHold().unlockCanvasAndPost(canvas);
				}
			}
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times");
	}
	
	public void destroyThread() throws InterruptedException{
		boolean retry = true;
		while(retry){
			this.join();
			retry = false;
		}
		
	}

	public SurfaceHolder getSurHold() {
		return surHold;
	}

	public void setSurHold(SurfaceHolder surHold) {
		this.surHold = surHold;
	}
}
