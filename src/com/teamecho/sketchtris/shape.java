package com.teamecho.sketchtris;

public class shape {
	char id = 'a';
	int[][] form = new int[4][4];
	int[][] rotater = new int[4][4];
	int a, b, c, d;
	
	public shape(char shape){
		a = 1;
		b = 1;
		c = 1;
		d = 1;
	
	switch(id){
		case 'o':
			form[0][1] = a;
			form[0][2] = b;
			form[1][1] = c;
			form[1][2] = d;
		case 'L':
			form[0][1] = a;
			form[1][1] = b;
			form[2][1] = c;
			form[2][2] = d;
		case 's':
			form[0][2] = a;
			form[1][2] = b;
			form[2][1] = c;
			form[2][1] = d;
		case 'l':
			form[0][1] = a;
			form[1][1] = b;
			form[2][1] = c;
			form[3][1] = d;
		case 't':
			form[0][1] = a;
			form[1][1] = b;
			form[1][2] = c;
			form[2][1] = d; 
		}
	}
			
	public void rotate(){
		rotater = form;
		// abcd miea
		// efgh njfb
		// ijkl okgc
		// mnop plhd
		
		form[0][0] = rotater[3][0];
		form[0][1] = rotater[2][0];
		form[0][2] = rotater[1][0];
		form[0][3] = rotater[0][0];
		
		form[1][0] = rotater[3][1];
		form[1][1] = rotater[2][1];
		form[1][2] = rotater[1][1];
		form[1][3] = rotater[0][1];
		
		form[2][0] = rotater[3][2];
		form[2][1] = rotater[2][2];
		form[2][2] = rotater[1][2];
		form[2][3] = rotater[0][2];
		
		form[3][0] = rotater[3][3];
		form[3][1] = rotater[2][3];
		form[3][2] = rotater[1][3];
		form[3][3] = rotater[0][3];
	}
}
