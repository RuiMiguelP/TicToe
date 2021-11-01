package model;

import java.io.Serializable;
import java.util.Arrays;


/*This class defines the playing board.
 * 
 * Each board is initialized as a two dimentional array of
 * integers = 0 by default;
 * 
 * There are attributes for the i,j position of 
 * the Array. 
 * 
 * The setters read String's to treat the input from the
 * frontend.
 * */
public class Board implements Serializable{
	private static final long serialVersionUID = 1L;
	private int[][] board;

	public Board() {
	  this.board = new int[3][3];
	  for(int[] row:board) {
		  Arrays.fill(row, 0);
	  }
	}
	
	@Override
	public String toString() {
		String print = "";
		for(int[] row:board) {
			  print = print+ Arrays.toString(row)+"\n";
		  }
		return print;
	}
	
	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int getBoardPos(int i , int j) {
		return board[i][j];
	}

	public void setBoardPos(int i , int j, int value) {
		this.board[i][j] = value;
	}
}
