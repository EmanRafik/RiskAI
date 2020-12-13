package models;

import models.agents.GreedyAgent;
import models.agents.HumanAgent;

public class Game {
	/**
	 * game singleton instance
	 */
	private static Game instance;
	/**
	 * integer holding the current game map
	 *  0 -> Egypt
	 *  1 -> USA
	 */
	private static int gameMap;
	/**
	 * The territories the game
	 */
	private static Territory[] territories;
	/**
	 * The two game players
	 */
	private static Player[] players;
	/**
	 * Integer representing the game mode
	 * 0 -> Playing mode
	 * 1 -> Simulation mode
	 */
	private static int gameMode;
	
	
	public static int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public static int getGameMap() {
		return gameMap;
	}

	public static Player[] getPlayers() {
		return players;
	}

	public static int getGameMode() {
		return gameMode;
	}

	public static Territory[] getTerritories() {
		return territories;
	}

	public static void setCurrentPlayerId(int currentPlayerId) {
		Game.currentPlayerId = currentPlayerId;
	}

	private static int currentPlayerId;
	
	/**
	 * Class constructor to initialize the game
	 * @param gameMap
	 * @param players
	 * @param gameMode
	 */
	private Game(int gameMap, Player[] players, int gameMode) {
		Game.gameMap = gameMap;
		Game.players = players;
		Game.gameMode = gameMode;
		if (gameMap == 0) {
			// ToDo: Initialize the territories according to Egypt's map
		}
		if (gameMap == 1) {
			// ToDo: Initialize the territories according to United States' map
		}
	}
	
	public static synchronized Game getInstance() {
		if (instance == null) {
			players = new Player[2];
			players[0] = new HumanAgent(0);
			players[1] = new GreedyAgent(1);
			instance = new Game(0, players, 0);
		}
		return instance;
	}
	
	public static synchronized Game getInstance(int gameMap, Player[] players, int gameMode) {
		if (instance == null) {
			instance = new Game(gameMap, players, gameMode);
		}
		return instance;
	}
	
	
	
	/**
	 * Main Game loop method
	 */
	public static void run() {
		while (!isFinalState()) {
			players[currentPlayerId].play();
			currentPlayerId = getNextPlayer();
		}
	}
	
	/**
	 * Method to check if there is a winner
	 * @return true if there is a winning player
	 */
	private static boolean isFinalState() {
		for (Player player : players) {
			if (player.getTerritories().size() == territories.length) return true;
		}
		return false;
	}
	
	private static int getNextPlayer() {
		return 1 - currentPlayerId;
	}
}
