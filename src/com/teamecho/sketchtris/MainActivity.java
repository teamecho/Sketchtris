package com.teamecho.sketchtris;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
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
		
		//RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
		//		                                                            RelativeLayout.LayoutParams.WRAP_CONTENT);
		//param.addRule(RelativeLayout.ABOVE, R.id.coinText);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                                                             RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		//param2.addRule(RelativeLayout.CENTER_VERTICAL);
		TextView coinText = (TextView)v.findViewById(R.id.coinText);
		coinText.setTypeface(tF);
		coinText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30); 
		coinText.setTextColor(Color.parseColor("#FFFFFF"));
		v.setOnClickListener(this);
		v.setLayoutParams(param);
		
		setContentView(v);
		
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(750); //You can manage the time of the blink with this parameter
		anim.setStartOffset(10);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		coinText.startAnimation(anim); 
		
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

}
