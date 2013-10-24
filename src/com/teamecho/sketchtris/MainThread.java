package com.teamecho.sketchtris;
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
	
	public MainThread(SketchtrisView sv, SurfaceHolder sh){
		super();
		this.view = sv;
		surHold = sh;
	}
	
	public void run() {
		long tickCount = 50;
		Log.d(TAG, "Starting game loop");
		while(running){
			if(view.currentShape != null){
				tickCount++;
				view.update();
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
}
