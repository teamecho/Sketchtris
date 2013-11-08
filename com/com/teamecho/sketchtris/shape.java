package com.teamecho.sketchtris;

import android.app.Application;
import android.widget.Toast;

public class shape {
        boolean isBlocked = false;
        char id = 'a'; // Name of the shape: O, l , s, z, t, L, J
        int[][] form = new int[4][4];
        int[][] rotater = new int[4][4];
        int a, b, c, d;
        SketchtrisGrid myGrid;
        int p11, p12, p13, p14, p21, p22, p23, p24, p31, p32, p33, p34, p41, p42, p43, p44;
        int[] formpoints = { p11, p12, p13, p14, p21, p22, p23, p24, p31, p32, p33, p34, p41, p42, p43, p44 };
        private GameActivity ga1;
        
        
        //NEW STUFF CHELSEA DON'T HATE ME <-- Chelsea, just hate her already - Jess
        Cube c1,c2,c3,c4;
        
        
        
        public shape(char shape, SketchtrisGrid grid){
                // Symbolic representation of the four cubes that build a shape.
                a = b = c = d = 1;
                myGrid = grid;
                id = shape;
                int rows = SketchtrisGrid.ROWS;
                int cols = SketchtrisGrid.COLS;
                
                switch(id){
                        case 'O':
                                c1 = new Cube(0, cols/2 -1);
                                c2 = new Cube(0, cols/2);
                                c3 = new Cube(1, cols/2);
                                c4 = new Cube(1, cols/2 - 1);
                                break;
                        case 'L':
                                c1 = new Cube(0, cols/2 -1);
                                c2 = new Cube(1, cols/2 - 1);
                                c3 = new Cube(2, cols/2 - 1);
                                c4 = new Cube(2, cols/2);
                                break;
                        case 'S':
                                c1 = new Cube(0, cols/2 -1);
                                c2 = new Cube(0, cols/2);
                                c3 = new Cube(1, cols/2 - 2);
                                c4 = new Cube(1, cols/2 - 1);
                                break;
                        case 'I':
                                c1 = new Cube(0, cols/2 - 1);
                                c2 = new Cube(0, cols/2);
                                c3 = new Cube(0, cols/2 - 2);
                                c4 = new Cube(0, cols/2 + 1);
                                break;
                        case 'T':
                                c1 = new Cube(0, cols/2 - 1);
                                c2 = new Cube(1, cols/2 - 1);
                                c3 = new Cube(1, cols/2 - 2);
                                c4 = new Cube(1, cols/2);
                                break;
                        case 'Z':
                                c1 = new Cube(0, cols/2 - 1);
                                c2 = new Cube(0, cols/2);
                                c3 = new Cube(1, cols/2);
                                c4 = new Cube(1, cols/2 + 1);
                                break;
                        case 'J':
                                c1 = new Cube(0, cols/2);
                                c2 = new Cube(1, cols/2);
                                c3 = new Cube(2, cols/2);
                                c4 = new Cube(2, cols/2 - 1);
                                break;
                }
                
                pushToGrid();                                
        }
                        
        public void rotateRight(){
                
                rotater = form;
                //THIS IS A ROTATE MATRIX BY CHELSEA WHO IS A POOPY HEAD
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
        
        public void shiftShapeDown(){
        	    if(!bottomHit() && !bottomCollission()){
                        removeFromGrid();
                        c1.row++;
                        c2.row++;
                        c3.row++;
                        c4.row++;
                        pushToGrid();
                }
                else{
                		GameActivity.pieceStackHeight += findTallestPiece();
                }
                
        }
        
        public int findTallestPiece() {
        	int tallest = c1.row;
        	tallest = (c2.row > tallest) ? c2.row : tallest;
        	tallest = (c3.row > tallest) ? c3.row : tallest;
        	tallest = (c4.row > tallest) ? c4.row : tallest;
        	return tallest; 
        }
        
        public void pushToGrid(){
                myGrid.fillCell(c1.row, c1.col);
                myGrid.fillCell(c2.row, c2.col);
                myGrid.fillCell(c3.row, c3.col);
                myGrid.fillCell(c4.row, c4.col);
        }
        
        public void removeFromGrid(){
                myGrid.emptyCell(c1.row, c1.col);
                myGrid.emptyCell(c2.row, c2.col);
                myGrid.emptyCell(c3.row, c3.col);
                myGrid.emptyCell(c4.row, c4.col);
        }
        
        public void shiftShapeLeft(){
                boolean valid = true;
                if(c1.col == 0 || c2.col == 0 || c3.col == 0 || c4.col == 0){
                        //WE ARE AT THE END
                        valid = false;
                }
                if(valid){
                        removeFromGrid();
                        c1.col = c1.col - 1;
                        c2.col = c2.col - 1;
                        c3.col = c3.col - 1;
                        c4.col = c4.col - 1;
                        pushToGrid();
                }
        }
        
        public void shiftShapeRight(){
                boolean valid = true;
                if(c1.col == SketchtrisGrid.COLS - 1 || c2.col == SketchtrisGrid.COLS - 1 || c3.col == SketchtrisGrid.COLS - 1 || c4.col == SketchtrisGrid.COLS - 1){
                        //WE ARE AT THE END
                        valid = false;
                }
                if(valid){
                        removeFromGrid();
                        c1.col = c1.col + 1;
                        c2.col = c2.col + 1;
                        c3.col = c3.col + 1;
                        c4.col = c4.col + 1;
                        pushToGrid();
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
        
        public boolean bottomCollission(){
        	    // O L S I T Z J 
        		if (id == 'O') {
        			if (!myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c4.row + 1, c4.col)) return true;
        		}
        		else if (id == 'L') {
        			if (!myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c4.row + 1, c4.col)) return true;
        		}
        		else if (id == 'S') {
        			if (!myGrid.isEmptyCell(c4.row + 1, c4.col) || !myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c2.row + 1, c2.col)) return true;
        		}
        		else if (id == 'I') {
        			if (!myGrid.isEmptyCell(c1.row + 1, c1.col) || !myGrid.isEmptyCell(c2.row + 1, c2.col)|| !myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c4.row + 1, c4.col)) return true;
        		}
        		else if (id == 'T') {
        			if (!myGrid.isEmptyCell(c2.row + 1, c2.col) || !myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c4.row + 1, c4.col)) return true;
        		}
        		else if (id == 'Z') {
        			if (!myGrid.isEmptyCell(c1.row + 1, c1.col) || !myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c4.row + 1, c4.col)) return true;
        		}
        		else if (id == 'J') {
        			if (!myGrid.isEmptyCell(c3.row + 1, c3.col) || !myGrid.isEmptyCell(c4.row + 1, c4.col)) return true;
        		}
                return false;
        }
        

        public boolean bottomHit(){
                if(c1.row >= SketchtrisGrid.ROWS - 1 || c2.row >= SketchtrisGrid.ROWS - 1 || c3.row >= SketchtrisGrid.ROWS - 1 || c4.row >= SketchtrisGrid.ROWS - 1 ){
                    return true;
                }
                return false;
        }
        
        public boolean topHit(){
        	return GameActivity.pieceStackHeight >= SketchtrisGrid.ROWS;
        	//return stackHeight >= SketchtrisGrid.ROWS;
        }
        
        class Cube{
                int row, col;
                Cube(int x, int y){
                        row = x;
                        col = y;
                }
        }
        
}