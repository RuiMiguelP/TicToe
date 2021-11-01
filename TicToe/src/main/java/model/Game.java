package model;
import java.io.Serializable;
import java.util.ArrayList;


import controller.FilesController;


public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	private Player playerOne;
	private Player playerTwo;
	private Player playerWinner;
	private String durationGame;
	private Board board;
	private String result; 
	private boolean isDraw;
	 
	public Game() {

	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

	public Player getPlayerWinner() {
		return playerWinner;
	}

	public void setPlayerWinner(Player playerWinner) {
		this.playerWinner = playerWinner;
	}

	public String getDurationGame() {
		return durationGame;
	}

	public void setDurationGame(String durationGame) {
		this.durationGame = durationGame;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isDraw() {
		return isDraw;
	}

	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
