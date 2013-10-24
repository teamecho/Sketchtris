package com.teamecho.sketchtris;

public class shape {
	boolean isBlocked = false;
	char id = 'a'; // Name of the shape: O, l , s, z, t, L, J 
	int[][] form = new int[4][4];
	int[][] rotater = new int[4][4];
	int a, b, c, d;
	SketchtrisGrid myGrid;
	int p11, p12, p13, p14, p21, p22, p23, p24, p31, p32, p33, p34, p41, p42, p43, p44;
	int[] formpoints = { p11, p12, p13, p14, p21, p22, p23, p24, p31, p32, p33, p34, p41, p42, p43, p44 };
	public shape(char shape, SketchtrisGrid grid){
		// Symbolic representation of the four cubes that build a shape.
		a = b = c = d = 1;
		myGrid = grid;
		id = shape;
		
		p11 = SketchtrisGrid.COLS*(SketchtrisGrid.ROWS-SketchtrisGrid.ROWS/2+1) + ((SketchtrisGrid.COLS/2)-2);
		p12 = p11 + 1; p13 = p12 + 1; p14 = p13 + 1;
		p21 = SketchtrisGrid.COLS*(SketchtrisGrid.ROWS-SketchtrisGrid.ROWS/2) + ((SketchtrisGrid.COLS/2)-2);
		p22 = p21 + 1; p23 = p22 + 1; p24 = p23 + 1;
		p31 = SketchtrisGrid.COLS*(SketchtrisGrid.ROWS-SketchtrisGrid.ROWS/2-1) + ((SketchtrisGrid.COLS/2)-2);
		p32 = p31 + 1; p33 = p32 + 1; p34 = p33 + 1;
		p41 = SketchtrisGrid.COLS*(SketchtrisGrid.ROWS-SketchtrisGrid.ROWS/2+1-2) + ((SketchtrisGrid.COLS/2)-2);
		p42 = p41 + 1; p43 = p42 + 1; p44 = p43 + 1;
				
		
		switch(id){
			case 'O':
				myGrid.fillCell(p12);
				myGrid.fillCell(p13);
				myGrid.fillCell(p22);
				myGrid.fillCell(p23);
				break;
			case 'L':
				myGrid.fillCell(p21);
				myGrid.fillCell(p22);
				myGrid.fillCell(p23);
				myGrid.fillCell(p24);
				break;
			case 'S':
				myGrid.fillCell(p13);
				myGrid.fillCell(p22);
				myGrid.fillCell(p23);
				myGrid.fillCell(p32);
				break;
			case 'I':
				myGrid.fillCell(p12);
				myGrid.fillCell(p22);
				myGrid.fillCell(p32);
				myGrid.fillCell(p42);
				break;
			case 'T':
				myGrid.fillCell(p12);
				myGrid.fillCell(p22);
				myGrid.fillCell(p23);
				myGrid.fillCell(p32);
				break;
			case 'Z':
				myGrid.fillCell(p12);
				myGrid.fillCell(p22);
				myGrid.fillCell(p23);
				myGrid.fillCell(p33);
				break;
			case 'J':
				myGrid.fillCell(p13);
				myGrid.fillCell(p23);
				myGrid.fillCell(p32);
				myGrid.fillCell(p33);
				break;
		}
	}
			
	public void rotateRight(){
		
		rotater = form;
		//THIS IS A ROTATE MATRIX BY CHELSEA WHO IS SO AWESOME
		// abcd miea
		// efgh njfb
		// ijkl okgc
		// mnop plhd
		
		if(!myGrid.isEmptyCell(p41)) { myGrid.fillCell(p11); myGrid.emptyCell(p41); }
		if(!myGrid.isEmptyCell(p31)) { myGrid.fillCell(p12); myGrid.emptyCell(p31); }
		if(!myGrid.isEmptyCell(p21)) { myGrid.fillCell(p13); myGrid.emptyCell(p21); }
		if(!myGrid.isEmptyCell(p11)) { myGrid.fillCell(p14); myGrid.emptyCell(p11); }
		
		if(!myGrid.isEmptyCell(p42)) { myGrid.fillCell(p21); myGrid.emptyCell(p42); }
		if(!myGrid.isEmptyCell(p32)) { myGrid.fillCell(p22); myGrid.emptyCell(p32); } 
		if(!myGrid.isEmptyCell(p22)) { myGrid.fillCell(p23); myGrid.emptyCell(p22); } 
		if(!myGrid.isEmptyCell(p12)) { myGrid.fillCell(p24); myGrid.emptyCell(p12); } 
		
		if(!myGrid.isEmptyCell(p43)) { myGrid.fillCell(p31); myGrid.emptyCell(p43); } 
		if(!myGrid.isEmptyCell(p33)) { myGrid.fillCell(p32); myGrid.emptyCell(p33); } 
		if(!myGrid.isEmptyCell(p23)) { myGrid.fillCell(p33); myGrid.emptyCell(p23); } 
		if(!myGrid.isEmptyCell(p13)) { myGrid.fillCell(p34); myGrid.emptyCell(p13); }
		
		if(!myGrid.isEmptyCell(p44)) { myGrid.fillCell(p41); myGrid.emptyCell(p44); } 
		if(!myGrid.isEmptyCell(p34)) { myGrid.fillCell(p42); myGrid.emptyCell(p34); } 
		if(!myGrid.isEmptyCell(p24)) { myGrid.fillCell(p43); myGrid.emptyCell(p24); } 
		if(!myGrid.isEmptyCell(p14)) { myGrid.fillCell(p44); myGrid.emptyCell(p14); } 
	}
	
	public void rotateLeft(){
		rotater = form;
		//THIS IS A ROTATE MATRIX BY CHELSEA WHO IS AWESOME
		// abcd dhlp
		// efgh cgko
		// ijkl bfjn
		// mnop aeim
		
		if(!myGrid.isEmptyCell(p14)) { myGrid.fillCell(p11); myGrid.emptyCell(p14); } 
		if(!myGrid.isEmptyCell(p13)) { myGrid.fillCell(p12); myGrid.emptyCell(p13); }
		if(!myGrid.isEmptyCell(p12)) { myGrid.fillCell(p13); myGrid.emptyCell(p12); }
		if(!myGrid.isEmptyCell(p11)) { myGrid.fillCell(p14); myGrid.emptyCell(p11); } 
     
		if(!myGrid.isEmptyCell(p24)) { myGrid.fillCell(p21); myGrid.emptyCell(p24); } 
		if(!myGrid.isEmptyCell(p23)) { myGrid.fillCell(p22); myGrid.emptyCell(p23); } 
		if(!myGrid.isEmptyCell(p22)) { myGrid.fillCell(p23); myGrid.emptyCell(p22); }
		if(!myGrid.isEmptyCell(p21)) { myGrid.fillCell(p24); myGrid.emptyCell(p21); }
 
		if(!myGrid.isEmptyCell(p34)) { myGrid.fillCell(p31); myGrid.emptyCell(p34); } 
		if(!myGrid.isEmptyCell(p33)) { myGrid.fillCell(p32); myGrid.emptyCell(p33); } 
		if(!myGrid.isEmptyCell(p32)) { myGrid.fillCell(p33); myGrid.emptyCell(p32); }
		if(!myGrid.isEmptyCell(p31)) { myGrid.fillCell(p34); myGrid.emptyCell(p31); } 

		if(!myGrid.isEmptyCell(p44)) { myGrid.fillCell(p41); myGrid.emptyCell(p44); }
		if(!myGrid.isEmptyCell(p43)) { myGrid.fillCell(p42); myGrid.emptyCell(p43); }
		if(!myGrid.isEmptyCell(p42)) { myGrid.fillCell(p43); myGrid.emptyCell(p42); }
		if(!myGrid.isEmptyCell(p41)) { myGrid.fillCell(p44); myGrid.emptyCell(p41); }
	}
	
	public void fall(int lvls){
	// Method drops the shape straight down a given number of lvls
		
		int t = lvls * SketchtrisGrid.COLS;
		for (int k = 0; k < formpoints.length; k++){
			if (!myGrid.isEmptyCell(formpoints[k]+t)){
				isBlocked = true;
			}
		}
		if (isBlocked){
			// Perform tasks for landing 
		}
		else {
			this.shiftShapeBy(t);
		}
	}
	
	public void move(char direction, int blocks){
		// Moves piece to left and right
		int t = 0;
		switch(direction){
		case 'L':
			// move these blocks to left
			t = -blocks;
		case 'R':
			// moves these blocks to right
			t = blocks;		
		}
		this.shiftShapeBy(t);
	}
	
	private void shiftShapeBy(int t){
		// method not done yet, not functional.
		
		for (int k = 0; k < formpoints.length; k++){
			if (!myGrid.isEmptyCell(formpoints[k])){
				myGrid.emptyCell(formpoints[k]);
			}
		}
		p11 = p11 + t;p12 = p12 + t;p13 = p13 + t;p14 = p14 + t;
		p21 = p21 + t;p22 = p22 + t;p23 = p23 + t;p24 = p24 + t;
		p11 = p11 + t;p12 = p12 + t;p33 = p33 + t;p34 = p34 + t;
		p11 = p11 + t;p12 = p12 + t;p43 = p43 + t;p44 = p44 + t;
	}
}
