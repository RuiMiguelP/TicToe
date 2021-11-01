package controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

@Named("indexBean")
@SessionScoped
public class ContentsController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	GameController game;

	private String Message1 = "Play for Humanity";
	private String Message2 = "Play for the Robots";
	private boolean disabled = true; // Set render TRUE
	private String playHuman = "Play you puny human.";
	private String playRobot = "Play you filthy robot.";
	private String resultHuman = "";
	private String resultRobot = "";
	private boolean humansTurn = false;
	private boolean robotsTurn = false;

	// resets the messages when a new game is started
	public void reset() {
		this.Message1 = "Play for Humanity";
		this.Message2 = "Play for the Robots";
		this.disabled = true;
		game.setEnd(false);
		this.setHumansTurn(false);
		this.setRobotsTurn(false);
	}

	// Switchs the outputext that is rendered according to the player turn
	public void switchTurn() {

		if (game.isEnd() && game.isVictory()) {
			if (game.getGame().getPlayerWinner() == game.getOnePlayer()) {
				this.humansTurn = false;
				this.robotsTurn = false;
				this.resultHuman = "You saved Humanity. You are the One.";
				this.resultRobot = "You lose you piece of junk!";
			} else if (game.getGame().getPlayerWinner() == game.getTwoPlayer()) {
				this.humansTurn = false;
				this.robotsTurn = false;
				this.resultHuman = "You lose you damm dirty ape!";
				this.resultRobot = "You are the superior race. You win.";
			}
		} else if (game.isEnd() && !game.isVictory()) {
			this.humansTurn = false;
			this.robotsTurn = false;
			this.resultHuman = "It's a draw.";
			this.resultRobot = "It's a draw.";

		} else if (game.isTurn() && !game.isEnd()) {
			this.humansTurn = true;
			this.robotsTurn = false;
		} else {
			this.humansTurn = false;
			this.robotsTurn = true;
		}

	}

	// Function called to restart a game
	public void newGame() {
		game.setPlayerOne(null);
		game.setPlayerTwo(null);
		reset();
	}

	public String getMessage1() {
		return Message1;
	}

	public void setMessage1(String message1) {
		this.Message1 = message1;
	}

	public String getMessage2() {
		return Message2;
	}

	public void setMessage2(String message2) {
		this.Message2 = message2;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getPlayHuman() {
		return playHuman;
	}

	public void setPlayHuman(String playHuman) {
		this.playHuman = playHuman;
	}

	public String getPlayRobot() {
		return playRobot;
	}

	public void setPlayRobot(String playRobot) {
		this.playRobot = playRobot;
	}

	public boolean isHumansTurn() {
		return humansTurn;
	}

	public void setHumansTurn(boolean humansTurn) {
		this.humansTurn = humansTurn;
	}

	public boolean isRobotsTurn() {
		return robotsTurn;
	}

	public void setRobotsTurn(boolean robotsTurn) {
		this.robotsTurn = robotsTurn;
	}

	public String getResultHuman() {
		return resultHuman;
	}

	public void setResultHuman(String resultHuman) {
		this.resultHuman = resultHuman;
	}

	public String getResultRobot() {
		return resultRobot;
	}

	public void setResultRobot(String resultRobot) {
		this.resultRobot = resultRobot;
	}
}