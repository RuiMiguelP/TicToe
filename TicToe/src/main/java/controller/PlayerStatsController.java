package controller;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Board;
import model.Game;
import model.Player;

@Named("statsBean")
@SessionScoped
public class PlayerStatsController implements Serializable {
	private static final String gamesFilePath = "./proj_2/Games.txt";
	private static final long serialVersionUID = 1L;
	private static final String symbolClear = "resources/media/images/clear.png";
	private static final String symbolCross = "resources/media/images/cross.png";
	private static final String symbolCircle = "resources/media/images/circle.png";
	private static String playerName;
	private String[][] finalImages;

	public boolean searchStats() {
		ArrayList<Game> gamesList = viewPlayerGames(playerName, gamesFilePath);
		if (gamesList.size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean searchPlayerStats() {
		if (Files.exists(Paths.get(gamesFilePath))) {
			return true;
		}
		return false;
	}

	public ArrayList<Game> viewStatsPlayer() {
		ArrayList<Game> gamesList = viewPlayerGames(playerName, gamesFilePath);
		return gamesList;
	}

	/**
	 * Este método serve para ver todos os jogos de determinado jogador
	 */
	public ArrayList<Game> viewPlayerGames(String player, String filepath) {
		ArrayList<Game> games = (ArrayList<Game>) FilesController.readGamesFile(filepath);
		ArrayList<Game> playerGames = new ArrayList<>();
		for (Game game : games) {
			if (game.getPlayerOne().getUserName().equalsIgnoreCase(player)
					|| game.getPlayerTwo().getUserName().equalsIgnoreCase(player)) {
				playerGames.add(game);
			}
		}
		return playerGames;
	}

	/**
	 * Este método para reconstruir o board da última jogada
	 */
	public ArrayList<String> viewFinalBoard(Board board, int value) {
		ArrayList<String> finalImages = new ArrayList<String>();

		int[][] finalBoard = board.getBoard();
		for (int col = 0; col < finalBoard.length; col++) {
				if (board.getBoardPos(value, col) == 0) {
					finalImages.add(symbolClear);
				} else if (board.getBoardPos(value, col) == 1) {
					finalImages.add(symbolCross);
				} else {
					finalImages.add(symbolCircle);
				}
			}
		return finalImages;
	}
		
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String[][] getFinalImages() {
		return finalImages;
	}

	public void setFinalImages(String[][] finalImages) {
		this.finalImages = finalImages;
	}
}
