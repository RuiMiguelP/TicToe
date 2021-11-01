package controller;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import model.Board;
import model.Game;
import model.Player;

@Named("gameBean")
@SessionScoped
public class GameController implements Serializable {
	private static final String gamesFilePath = "./proj_2/Games.txt";
	private static final long serialVersionUID = 1L;
	private static final String VERTICAL = "vertical";
	private static final String HORIZONTAL = "horizontal";
	private static final String DIAGONAL = "diagonal";
	private static final String ANTIDIAGONAL = "antidiagonal";
	private Game game = new Game();
	private String symbolClear = "resources/media/images/clear.png";
	private String symbolCross = "resources/media/images/cross.png";
	private String symbolCircle = "resources/media/images/circle.png";
	private String symbolRobotWin = "https://media3.giphy.com/media/ecn1oGJkqaTS0/giphy.gif?cid=790b7611d6af2f15b3f0727564b91464e4c093a80eceaefd&rid=giphy.gif";
	private String symbolHumanWin = "https://media1.giphy.com/media/BdghqxNFV4efm/giphy.gif?cid=790b76112bc662cfd16596fb10ac73f68c269f822172016a&rid=giphy.gif";
	private String symbolDraw = "https://media0.giphy.com/media/A06UFEx8jxEwU/giphy.gif?cid=790b7611e0a997056aaf206e4970114862e99dd460bbd982&rid=giphy.gif";
	private HashMap<String, String> buttonId = new HashMap<>();
	private String buttonIndex;
	private int xMove;
	private int yMove;
	private int symbol;
	private Board board = new Board();
	private String playerOne;
	private String playerTwo;
	private Player onePlayer = new Player();
	private Player twoPlayer = new Player();
	private boolean end = false;
	private boolean turn;
	private boolean newGame;
	private int moveCount = 0;
	private boolean victory;
	private long startTime = 0;
	private long endTime;
	private int winningRow = 3; // values of 0,1,2
	private int winningColumn = 3; // values of 0,1,2
	private int winningDiagonal = 3; // values of 1 or -1 (diagonal or anti-diagonal);?maybe vamos ver

	@Inject
	ContentsController indexBean;

	public void newGame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String key = String.valueOf(i) + "," + String.valueOf(j);
				this.buttonId.put(key, this.symbolClear);
			}
		}
		this.game = new Game();
		this.board = new Board();
		game.setBoard(this.board);
		this.moveCount = 0;
		end = false;
		setVictory(false);
		setNewGame(true);
		setPlayers();
		draftPlayer();
		startTime = System.nanoTime();
	}

	public void setPlayers() {
		onePlayer.setUserName(this.playerOne);
		twoPlayer.setUserName(this.playerTwo);
		game.setPlayerOne(onePlayer);
		game.setPlayerTwo(twoPlayer);
	}

	/**
	 * Método que escolhe a primeira pessoa a jogar
	 */
	public void draftPlayer() {

		int randomNumber = (int) (Math.random() * 2);
		if (moveCount == 0) {
			if (randomNumber == 1) {
				this.turn = true; // 1st player first
				onePlayer.setFirstTurn(true); // defines that this player played first;
				twoPlayer.setFirstTurn(false);
			} else {
				this.turn = false;// 2nd player first
				twoPlayer.setFirstTurn(true);
				onePlayer.setFirstTurn(false);
			}
		}
		indexBean.switchTurn();
	}

	/*
	 * Parses the received key to the hashMap to match the entries in the board that
	 * is a matrix.
	 */
	public void parseKey() {
		if (!buttonIndex.equals(null)) {
			this.xMove = Integer.parseInt(buttonIndex.split(",")[0]);
			this.yMove = Integer.parseInt(buttonIndex.split(",")[1]);
		}
	}

	/**
	 * Troca a vez dos jogadores
	 */
	public void swapPlayer() {
		if (turn) {
			turn = false; // 2nd Player turn
			indexBean.switchTurn(); // Switch message
		} else {
			turn = true; // 1st Player turn
			indexBean.switchTurn();// Switch message
		}
	}

	public void showSymbol() {
		parseKey();
		if (buttonId.containsKey(buttonIndex) && buttonId.get(buttonIndex).equals(symbolClear)) {
			if (turn) { // if is the player 1 turn;
				buttonId.replace(buttonIndex, symbolCross);
				symbol = 1;
				board.setBoardPos(xMove, yMove, symbol);
				swapPlayer();
				this.moveCount++;
			} else { // if it is the player 2 turn;
				buttonId.replace(buttonIndex, symbolCircle);
				symbol = 2;
				board.setBoardPos(xMove, yMove, symbol);
				swapPlayer();
				this.moveCount++;
			}
		}

		if (this.moveCount >= 5) {
			checkVictory();
			if (isVictory()) {
				end = true;
				highligthWinner();
				reloadPage();
				endTime = System.nanoTime();
				game.setDurationGame(
						String.valueOf(TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS)));
				game.setBoard(board);// Saves the final board in the game
				ArrayList<Game> gameList = saveGame(this.game);
				FilesController.writeObjectFile(gameList, gamesFilePath);
			}
			if (moveCount == 9 && !isVictory()) {
				end = true;
				highligthWinner();
				reloadPage();
				endTime = System.nanoTime();
				game.setDurationGame(
						String.valueOf(TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS)));
				game.setBoard(board); // Saves the final board in the game
				game.setDraw(true);
				game.setPlayerWinner(new Player("Draw"));
				game.setResult("DRAW");
				ArrayList<Game> gameList = saveGame(this.game);
				FilesController.writeObjectFile(gameList, gamesFilePath);
			}
		}
	}

	public ArrayList<Game> saveGame(Game game) {
		ArrayList<Game> gameList = new ArrayList<>();
		if (Files.exists(Paths.get(gamesFilePath))) {
			gameList = (ArrayList<Game>) FilesController.readGamesFile(gamesFilePath);
		}
		gameList.add(game);
		return gameList;
	}

	public void checkVictory() {
		int n = 3; // board dimension

		// check row
		for (int i = 0; i < n; i++) {
			if (board.getBoardPos(xMove, i) != symbol) {
				break;
			}
			if (i == n - 1) {
				setResult(symbol, HORIZONTAL);
				setVictory(true);
				this.winningRow = xMove;
				// report win for s
			}
		}

		// check col
		for (int i = 0; i < n; i++) {
			if (board.getBoardPos(i, yMove) != symbol) {
				break;
			}
			if (i == n - 1) {
				setResult(symbol, VERTICAL);
				setVictory(true);
				this.winningColumn = yMove;
				// report win for s
			}
		}

		// check diag if (xMove == yMove) { //we're on a diagonal
		for (int i = 0; i < n; i++) {
			if (board.getBoardPos(i, i) != symbol) {
				break;
			}
			if (i == n - 1) {
				setResult(symbol, DIAGONAL);
				setVictory(true);
				this.winningDiagonal = 1;
				// report win for s
			}
		}

		// check anti diag (thanks rampion)
		if (xMove + yMove == n - 1) {
			for (int i = 0; i < n; i++) {
				if (board.getBoardPos(i, ((n - 1) - i)) != symbol) {
					break;
				}
				if (i == n - 1) {
					setResult(symbol, ANTIDIAGONAL);
					setVictory(true);
					this.winningDiagonal = -1;
					// report win for s
				}
			}
		}
	}

	public void setResult(int symbol, String position) {
		setPlayerWinner(symbol);
		game.setResult(position);
	}

	/*
	 * Sets the Player winnerPlayer in the game;
	 */
	public void setPlayerWinner(int symbol) {
		if (symbol == 1) {
			game.setPlayerWinner(onePlayer);
		} else if (symbol == 2) {
			game.setPlayerWinner(twoPlayer);
		} 
	}

	public void highligthWinner() {
		String[] winningKey = new String[3];
		String winnerSymbol = symbolDraw;

		if (victory) {
			if (symbol == 1) {
				winnerSymbol = symbolHumanWin;
			} else if (symbol == 2) {
				winnerSymbol = symbolRobotWin;
			}
		} else {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					String key = String.valueOf(i) + "," + String.valueOf(j);
					this.buttonId.put(key, winnerSymbol);
				}
			}
		}

		if (victory) {
			if (winningColumn != 3) {
				for (int i = 0; i < 3; i++) {
					winningKey[i] = String.valueOf(i) + "," + String.valueOf(winningColumn);
					buttonId.replace(winningKey[i], winnerSymbol);
				}
				this.winningColumn = 3;

			} else if (winningRow != 3) {

				for (int j = 0; j < 3; j++) {
					winningKey[j] = String.valueOf(winningRow) + "," + String.valueOf(j);
					buttonId.replace(winningKey[j], winnerSymbol);
				}
				this.winningRow = 3;

			} else if (winningDiagonal == 1) {
				for (int j = 0; j < 3; j++) {
					winningKey[j] = String.valueOf(j) + "," + String.valueOf(j);
					buttonId.replace(winningKey[j], winnerSymbol);
				}
				this.winningDiagonal = 3;

			} else if (winningDiagonal == -1) {
				for (int j = 0; j < 3; j++) {
					winningKey[j] = String.valueOf(j) + "," + String.valueOf(((3 - 1) - j));
					buttonId.replace(winningKey[j], winnerSymbol);
				}
				this.winningDiagonal = 3;
			}
		}
	}

	public void reloadPage() {
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("section");
		indexBean.switchTurn();
	}

	public HashMap<String, String> getButtonId() {
		return buttonId;
	}

	public void setButtonId(HashMap<String, String> buttonId) {
		this.buttonId = buttonId;
	}

	public String getButtonIndex() {
		return buttonIndex;
	}

	public void setButtonIndex(String buttonIndex) {
		this.buttonIndex = buttonIndex;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public String getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(String playerOne) {
		this.playerOne = playerOne;
	}

	public String getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(String playerTwo) {
		this.playerTwo = playerTwo;

	}

	public Player getOnePlayer() {
		return onePlayer;
	}

	public void setOnePlayer(Player onePlayer) {
		this.onePlayer = onePlayer;
	}

	public Player getTwoPlayer() {
		return twoPlayer;
	}

	public void setTwoPlayer(Player twoPlayer) {
		this.twoPlayer = twoPlayer;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getxMove() {
		return xMove;
	}

	public void setxMove(int xMove) {
		this.xMove = xMove;
	}

	public int getyMove() {
		return yMove;
	}

	public void setyMove(int yMove) {
		this.yMove = yMove;
	}

	public boolean isNewGame() {
		return newGame;
	}

	public void setNewGame(boolean newGame) {
		this.newGame = newGame;
	}

	public boolean isVictory() {
		return victory;
	}

	public void setVictory(boolean victory) {
		this.victory = victory;
	}

	public int getWinningRow() {
		return winningRow;
	}

	public void setWinningRow(int winningRow) {
		this.winningRow = winningRow;
	}

	public int getWinningColumn() {
		return winningColumn;
	}

	public void setWinningColumn(int winningColumn) {
		this.winningColumn = winningColumn;
	}

	public int getWinningDiagonal() {
		return winningDiagonal;
	}

	public void setWinningDiagonal(int winningDiagonal) {
		this.winningDiagonal = winningDiagonal;
	}

	public String getSymbolRobotWin() {
		return symbolRobotWin;
	}

	public void setSymbolRobotWin(String symbolRobotWin) {
		this.symbolRobotWin = symbolRobotWin;
	}

	public String getSymbolHumanWin() {
		return symbolHumanWin;
	}

	public void setSymbolHumanWin(String symbolHumanWin) {
		this.symbolHumanWin = symbolHumanWin;
	}

	public String getSymbolDraw() {
		return symbolDraw;
	}

	public void setSymbolDraw(String symbolDrawn) {
		this.symbolDraw = symbolDrawn;
	}
}
