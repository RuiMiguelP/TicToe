package controller;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Game;
import model.StatsModel;

@Named
@SessionScoped
public class GlobalStatsController implements Serializable {
	private static final String gamesFilePath = "./proj_2/Games.txt";	
	private static final long serialVersionUID = 1L;
	private static final String VERTICAL = "vertical";
	private static final String HORIZONTAL = "horizontal";
	private static final String DIAGONAL = "diagonal";
	private static final String ANTIDIAGONAL = "antidiagonal";
	private int victoryHumans = 0;
	private int victoryRobots = 0;
	private int victoryDiagonal = 0;
	private int victoryAntiDiagonal = 0;
	private int victoryHorizontal = 0;
	private int victoryVertical = 0;
		
	public boolean searchGlobalStats() {
		if (Files.exists(Paths.get(gamesFilePath))) {
			return true;
		}
		return false;
	}
	
	public StatsModel calcGlobalStats() {
		setVictoryVertical(0);
		setVictoryHorizontal(0);
		setVictoryDiagonal(0);
		setVictoryAntiDiagonal(0);
		setVictoryHumans(0);
		setVictoryRobots(0);
		ArrayList<Game> gamesList = (ArrayList<Game>) FilesController.readGamesFile(gamesFilePath);
		for (Game game: gamesList) {
			if (game.getResult().equalsIgnoreCase(VERTICAL)) {
				setVictoryVertical(getVictoryVertical() + 1);
			} else if (game.getResult().equalsIgnoreCase(HORIZONTAL)) {
				setVictoryHorizontal(getVictoryHorizontal() + 1);
			} else if (game.getResult().equalsIgnoreCase(DIAGONAL)) {
				setVictoryDiagonal(getVictoryDiagonal() + 1);
			} else if (game.getResult().equalsIgnoreCase(ANTIDIAGONAL)) {
				setVictoryAntiDiagonal(getVictoryAntiDiagonal() + 1);
			} 
			if (game.getPlayerWinner().equals(game.getPlayerOne())) {
				setVictoryHumans(getVictoryHumans() + 1);
			}
			if (game.getPlayerWinner().equals(game.getPlayerTwo())){
				setVictoryRobots(getVictoryRobots() + 1);
			}
		}
		return new StatsModel(getVictoryHumans(), getVictoryRobots(), getVictoryDiagonal(), getVictoryAntiDiagonal(), getVictoryHorizontal(), getVictoryVertical());
	}
	
	public int getVictoryHumans() {
		return victoryHumans;
	}

	public void setVictoryHumans(int victoryHumans) {
		this.victoryHumans = victoryHumans;
	}

	public int getVictoryRobots() {
		return victoryRobots;
	}

	public void setVictoryRobots(int victoryRobots) {
		this.victoryRobots = victoryRobots;
	}

	public int getVictoryAntiDiagonal() {
		return victoryAntiDiagonal;
	}

	public void setVictoryAntiDiagonal(int victoryAntiDiagonal) {
		this.victoryAntiDiagonal = victoryAntiDiagonal;
	}

	public int getVictoryDiagonal() {
		return victoryDiagonal;
	}

	public void setVictoryDiagonal(int victoryDiagonal) {
		this.victoryDiagonal = victoryDiagonal;
	}

	public int getVictoryHorizontal() {
		return victoryHorizontal;
	}

	public void setVictoryHorizontal(int victoryHorizontal) {
		this.victoryHorizontal = victoryHorizontal;
	}

	public int getVictoryVertical() {
		return victoryVertical;
	}

	public void setVictoryVertical(int victoryVertical) {
		this.victoryVertical = victoryVertical;
	}
}
