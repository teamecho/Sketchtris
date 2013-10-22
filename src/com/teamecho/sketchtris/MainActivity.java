package com.teamecho.sketchtris;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Typeface tF = Typeface.createFromAsset(getAssets(),"Roboto-Thin.ttf"); 
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.activity_main, null, false);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                             RelativeLayout.LayoutParams.WRAP_CONTENT);
		TextView coinText = (TextView)v.findViewById(R.id.coinText);
		coinText.setTypeface(tF);
		coinText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30); 
		coinText.setTextColor(Color.parseColor("#FFFFFF"));
	    v.setOnClickListener(this);
		v.setLayoutParams(param);
		setContentView(v);
		
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(750); //manage blink time
		anim.setStartOffset(10);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		coinText.startAnimation(anim); 
		
		Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
		//e.clear(); //wipes out all prefs
		//e.apply(); //asynchronous update of sharedPrefs
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false); //true sets it every single time
		SharedPreferences settings = getSharedPreferences("preferences", 2);
		if (settings.getBoolean("prefDoShowTut", true)) { //is app launching for first time?      
			Intent intent = new Intent(this, Tutorial.class);
			startActivity(intent);
		    // first time task -- load tutorial here
		    settings.edit().putBoolean("prefDoShowTut", false).apply(); //flag that not first app load
		}
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public void launchGame(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		launchGame(v); 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
    	case R.id.action_settings:
    		Intent intent = new Intent(this, SettingsActivity.class);
    		startActivity(intent);
            return true;
    	case R.id.launch_tut:
    		Intent intent2 = new Intent(this, Tutorial.class);
    		startActivity(intent2);
            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
