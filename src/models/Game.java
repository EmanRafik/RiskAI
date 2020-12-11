package models;

public class Game {
	/**
	 * integer holding the current game map
	 *  0 -> Egypt
	 *  1 -> USA
	 */
	private int gameMap;
	/**
	 * The territories the game
	 */
	private Territory[] territories;
	/**
	 * The two game players
	 */
	private Player[] players;
	/**
	 * Integer representing the game mode
	 * 0 -> Playing mode
	 * 1 -> Simulation mode
	 */
	private int gameMode;
	
	
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public int getGameMap() {
		return gameMap;
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getGameMode() {
		return gameMode;
	}

	public Territory[] getTerritories() {
		return territories;
	}

	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	private int currentPlayerId;
	
	/**
	 * Class constructor to initialize the game
	 * @param gameMap
	 * @param players
	 * @param gameMode
	 */
	public Game(int gameMap, Player[] players, int gameMode) {
		this.gameMap = gameMap;
		this.players = players;
		this.gameMode = gameMode;
		if (gameMap == 0) {
			// ToDo: Initialize the territories according to Egypt's map
		}
		if (gameMap == 1) {
			// ToDo: Initialize the territories according to United States' map
		}
	}
	
	/**
	 * Main Game loop method
	 */
	public void run() {
		
	}
	
	/**
	 * Method to check if there is a winner
	 * @return true if there is a winning player
	 */
	private boolean isFinalState() {
		return false;
	}
}
