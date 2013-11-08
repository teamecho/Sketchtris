package com.teamecho.sketchtris;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Tutorial extends Activity {
	GestureOverlayView gOV; 
	GestureLibrary myGestureLib; 
	ImageView imv; 
	FrameLayout fml; 
	TextView descv; 
	private int pane;
	private String curName, curString; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		pane = 0; 
		curName="I";
		curString = "Draw the following shape from right to left.";
		fml = new FrameLayout(this);
		super.onCreate(savedInstanceState);
		Typeface tF = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf"); 
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.activity_tutorial, null, false);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                             RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		descv = (TextView)v.findViewById(R.id.descv);
		descv.setText(curString);
		descv.setTypeface(tF);
		descv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30); 
		descv.setTextColor(Color.parseColor("#FFFFFF"));
		
		gOV = new GestureOverlayView(this);
        gOV.setOrientation(GestureOverlayView.ORIENTATION_VERTICAL);
        gOV.setEventsInterceptionEnabled(true);
        gOV.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);	
	    myGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
	    if (!myGestureLib.load()) finish();
	    gOV.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			@Override
			public void onGesturePerformed(GestureOverlayView arg0, Gesture gesture) {
			    ArrayList<Prediction> predictions = myGestureLib.recognize(gesture);
			    for (Prediction p : predictions) {
			        if (p.score > 4.5 && (p.name.equals(curName))) {
			          Toast.makeText(getBaseContext(), "Good job!", Toast.LENGTH_SHORT).show();
			          setPane(++pane);
			        }
			     }
			}
	    });
		
	    imv = (ImageView)v.findViewById(R.id.imgv);
        fml.addView(v, 0);
        fml.addView(gOV,1);
        setContentView(fml);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public void setPane(int num) {
		switch(pane) {
			case 1:	//S
					curName="S";
					imv.setImageResource(R.drawable.s);
					curString = "Draw the following shape from right to left.";
					descv.setText(curString);
					break;
			case 2: //O
					curName="O";
					curString = "Draw the following shape from left to right.";
					descv.setText(curString);
					imv.setImageResource(R.drawable.o);
					break;
			case 3: //L
					curName="L";
					curString = "Draw the following shape from right to left.";
					descv.setText(curString);
					imv.setImageResource(R.drawable.l);
					break;
			case 4: //J
					curName="J";
					curString = "Draw the following shape from left to right.";
					descv.setText(curString);
					imv.setImageResource(R.drawable.j);
					break;
			case 5: //Z
					curName="Z";
					curString = "Draw the following shape from left to right.";
					descv.setText(curString);
					imv.setImageResource(R.drawable.z);
					break;
			case 6: //T 
					curName="T";
					curString = "Draw the following shape in two parts. From the top down for part one. Then right to left for part two.";
					descv.setText(curString);
					imv.setImageResource(R.drawable.t);
					break;
			case 7: //prompt to start game
					descv.setText("Nice going! Now you're ready to dive in and play the game. Head back to the menu and launch the game.");
					imv.setImageResource(R.drawable.done);
					break; 
			default: break;
		}
	}

}
