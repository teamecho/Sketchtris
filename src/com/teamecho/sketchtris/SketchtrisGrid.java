package com.teamecho.sketchtris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class SketchtrisGrid {

	private int mCells[];
	private int mCellWidth;
	private int mCellHeight;
	private int mLeft;
	private int mRight;
	private int mTop;
	private int mBottom;
	
	//These probably should be moved to a more general class.
	private static final int COLS = 12;
	private static final int ROWS = 20;
	

	public SketchtrisGrid(){
		//Create a Grid with each cell set to 0;
		mCells = new int[COLS * ROWS];
		for(int i = 0; i < mCells.length; i++){
			mCells[i] = 0;
		}
		//ARBITRARY NUMBERS
		mCellWidth = 57;
		mCellHeight = 50;
	}
	

    public void paint(Canvas canvas, Paint paint) {
            mLeft = 0;
            mTop = 0;
            mRight = mCellWidth; 
            mBottom = mCellHeight; 
            //mRight = mCellWidth * COLS;
            //mBottom = mCellHeight * ROWS;
            
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

           
            //paint elems -- gotta do this every call to paint() so that grid looks right as shape "falls"
            int l,t,r,b;
            int c1 = Color.parseColor("#961384");
            int c2 = Color.parseColor("#1f4c77");
            int c3 = Color.parseColor("#11751d");
            int c4 = Color.parseColor("#9b1212");
            for(int i = 0; i < mCells.length; i++) {
                    l = mLeft + (i % COLS) * mCellWidth;
                    t = mTop+(i / COLS) * mCellHeight;
                    r = l + mCellWidth;
                    b = t + mCellHeight;
                    if (i%4==0) paint.setColor(c1);
                    else if (i%4==1) paint.setColor(c2);
                    else if (i%4==2) paint.setColor(c3);
                    else paint.setColor(c4);
                    paint.setStyle((mCells[i] == 1)?Paint.Style.FILL:Paint.Style.STROKE);
                    canvas.drawRect(l, t, r, b, paint);
            }

            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
    }

	
	
	
	public int getRow(int index){
		//Where index is the index of some cell in mCells[]
		//Dividing by the Column should give the row.
		return index/COLS;
	}
	
	public boolean rowFull(int index){
		int row = getRow(index);
		int rowBegin = row * COLS;
		//int rowEnd = rowBegin + COLS;
		int sum = 0;
		for(int i = 0; i < COLS; i++){
			sum = sum + mCells[rowBegin];
			rowBegin++;
		}
		if(sum == COLS){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean rowEmpty(int index){
		int row = getRow(index);
		int rowBegin = row * COLS;
		//int rowEnd = rowBegin + COLS;
		int sum = 0;
		for(int i = 0; i < COLS; i++){
			sum = sum + mCells[rowBegin];
			rowBegin++;
		}
		if(sum > 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void dropRow(int index){
		int row = getRow(index);
		int rowBegin = row * COLS;
		if(rowEmpty(rowBegin + COLS)){
			//if the row above is empty, clear this row
			for(int i = 0; i < COLS; i++){
				mCells[rowBegin] = 0;
			}
		}
		else{
			while(!rowEmpty(rowBegin + 12)){
				//While the row above has blocks
				//Should hopefully loop enough times.
				for(int i = 0; i < COLS; i++){
					mCells[rowBegin] = mCells[rowBegin+ COLS];
					rowBegin++;
				}
			}
		}
		
	}
	
	public void fillCell(int row, int col){
		//given the row/column of a cell, fill it.
		int index = (row * 12) + col;
		mCells[index] = 1;
	}
	
}