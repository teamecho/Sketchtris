package com.teamecho.sketchtris;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();
	private static final int MAX_FPS = 50;
	private static final int MAX_FRAME_SKIPS = 5;
	private static final int FRAME_PERIOD = 1000 /MAX_FPS;
	private SurfaceHolder surfaceHolder;
	private SketchtrisView sView;	
	private boolean running;
	
	public MainThread(SurfaceHolder surfaceHolder, SketchtrisView sView){
		this.surfaceHolder = surfaceHolder;
		this.sView = sView;
	}

	public void setRunning(boolean running){
		this.running = running;
	}
	
	public void run(){
		Canvas canvas;
		long tickCount = 0L;
		long beginTime;
		long timeDiff;
		int  sleepTime;
		int framesSkipped;
		
		sleepTime = 0;
		
		Log.d(TAG, "Starting game loop");
		while(running){
			canvas = null;
			try{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder){
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;
					this.sView.update();
					this.sView.drawGrid(canvas);
					timeDiff = System.currentTimeMillis() - beginTime;
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					if (sleepTime > 0){
						try{
							Thread.sleep(sleepTime);
						}
						catch (InterruptedException e){
						}
					}
					
					while(sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
						this.sView.update();
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
				}
			} finally {
				if (canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		Log.d(TAG, "Game loop executed " + tickCount + " times");
	}
	
}
