package model;

public class StatsModel {
	private int victoryHumans = 0;
	private int victoryRobots = 0;
	private int victoryDiagonal = 0;
	private int victoryAntiDiagonal = 0;
	private int victoryHorizontal = 0;
	private int victoryVertical = 0;
	
	public StatsModel(int victoryHumans, int victoryRobots, int victoryDiagonal, int victoryAntiDiagonal,
			int victoryHorizontal, int victoryVertical) {
		this.victoryHumans = victoryHumans;
		this.victoryRobots = victoryRobots;
		this.victoryDiagonal = victoryDiagonal;
		this.victoryAntiDiagonal = victoryAntiDiagonal;
		this.victoryHorizontal = victoryHorizontal;
		this.victoryVertical = victoryVertical;
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

	public int getVictoryDiagonal() {
		return victoryDiagonal;
	}

	public void setVictoryDiagonal(int victoryDiagonal) {
		this.victoryDiagonal = victoryDiagonal;
	}

	public int getVictoryAntiDiagonal() {
		return victoryAntiDiagonal;
	}

	public void setVictoryAntiDiagonal(int victoryAntiDiagonal) {
		this.victoryAntiDiagonal = victoryAntiDiagonal;
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
