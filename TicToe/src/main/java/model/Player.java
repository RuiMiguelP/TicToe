package model;

import java.io.Serializable;

/*This class defines the Players
 * 
 *The Player has one attribute : username
 *and the respective setters and getters. 
 * */
public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userName;
	private boolean isFirstTurn;
	
	public Player() {
		
	}
	
	public Player(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isFirstTurn() {
		return isFirstTurn;
	}

	public void setFirstTurn(boolean isFirstTurn) {
		this.isFirstTurn = isFirstTurn;
	}
}
