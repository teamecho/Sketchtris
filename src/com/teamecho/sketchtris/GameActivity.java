package com.teamecho.sketchtris;

import android.app.Activity;
import android.gesture.GestureLibrary;
import android.os.Bundle;

public class GameActivity extends Activity {
	GestureLibrary myGestureLib; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
	}
	
}
