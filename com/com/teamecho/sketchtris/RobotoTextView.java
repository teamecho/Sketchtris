package com.teamecho.sketchtris;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class RobotoTextView extends TextView {

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(MainActivity.tF);
		this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30); 
        
        TypedArray customAttrs = context.obtainStyledAttributes(attrs,R.styleable.RobotoTextView); 
    	final int n = customAttrs.getIndexCount();
    	for (int i = 0; i < n; ++i) {
    	    int attr = customAttrs.getIndex(i);
    	    switch (attr) {
    	        case R.styleable.RobotoTextView_letter:
    	            String myLetter = customAttrs.getString(attr);
    	            break;
    	        case R.styleable.RobotoTextView_value:
    	            String myValue = customAttrs.getString(attr);
    	            break;
    	    }
    	}
    	customAttrs.recycle(); //give back array for later re-use
    }
    

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setTypeface(MainActivity.tF);
    }

    public RobotoTextView(Context context) {
        super(context);
        this.setTypeface(MainActivity.tF);
    }
}
