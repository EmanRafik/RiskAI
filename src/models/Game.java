package models;

import java.util.ArrayList;
import java.util.List;

import models.agents.GreedyAgent;
import models.agents.HumanAgent;

public class Game {
	ArrayList<Integer> arr;
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

	public static void setTerritories(Territory[] territories) {
		Game.territories = territories;
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
			territories = new Territory[28];
			territories[0] = null;
			territories[1] = new Territory(1);
			territories[1].setAdjacentTerrs(new ArrayList<Integer>(List.of(2, 3, 14, 21)));
			territories[2] = new Territory(2);
			territories[2].setAdjacentTerrs(new ArrayList<Integer>(List.of(1, 3)));
			territories[3] = new Territory(3);
			territories[3].setAdjacentTerrs(new ArrayList<Integer>(List.of(1, 24, 9, 10, 14)));
			territories[4] = new Territory(4);
			territories[4].setAdjacentTerrs(new ArrayList<Integer>(List.of(3, 5, 9, 10)));
			territories[5] = new Territory(5);
			territories[5].setAdjacentTerrs(new ArrayList<Integer>(List.of(4, 6, 9, 11, 12)));
			territories[6] = new Territory(6);
			territories[6].setAdjacentTerrs(new ArrayList<Integer>(List.of(5)));
			territories[7] = new Territory(7);
			territories[7].setAdjacentTerrs(new ArrayList<Integer>(List.of(8, 13)));
			territories[8] = new Territory(8);
			territories[8].setAdjacentTerrs(new ArrayList<Integer>(List.of(7, 13, 17, 18)));
			territories[9] = new Territory(9);
			territories[9].setAdjacentTerrs(new ArrayList<Integer>(List.of(3, 4, 5, 10, 11)));
			territories[10] = new Territory(10);
			territories[10].setAdjacentTerrs(new ArrayList<Integer>(List.of(3, 9, 11, 14)));
			territories[11] = new Territory(11);
			territories[11].setAdjacentTerrs(new ArrayList<Integer>(List.of(5, 9, 10, 12, 14)));
			territories[12] = new Territory(12);
			territories[12].setAdjacentTerrs(new ArrayList<Integer>(List.of(5, 11, 13, 16)));
			territories[13] = new Territory(13);
			territories[13].setAdjacentTerrs(new ArrayList<Integer>(List.of(7, 8, 12, 17)));
			territories[14] = new Territory(14);
			territories[14].setAdjacentTerrs(new ArrayList<Integer>(List.of(1, 3, 10, 11, 15, 19, 20, 22)));
			territories[15] = new Territory(15);
			territories[15].setAdjacentTerrs(new ArrayList<Integer>(List.of(14, 19)));
			territories[16] = new Territory(16);
			territories[16].setAdjacentTerrs(new ArrayList<Integer>(List.of(11, 12, 14, 17, 19, 23)));
			territories[17] = new Territory(17);
			territories[17].setAdjacentTerrs(new ArrayList<Integer>(List.of(8, 12, 13, 16, 18)));
			territories[18] = new Territory(18);
			territories[18].setAdjacentTerrs(new ArrayList<Integer>(List.of(8, 17)));
			territories[19] = new Territory(19);
			territories[19].setAdjacentTerrs(new ArrayList<Integer>(List.of(11, 14, 15, 16, 20, 23)));
			territories[20] = new Territory(20);
			territories[20].setAdjacentTerrs(new ArrayList<Integer>(List.of(14, 19, 21, 22, 23)));
			territories[21] = new Territory(21);
			territories[21].setAdjacentTerrs(new ArrayList<Integer>(List.of(1, 14, 20, 22, 24, 25, 27)));
			territories[22] = new Territory(22);
			territories[22].setAdjacentTerrs(new ArrayList<Integer>(List.of(20, 21, 23, 24)));
			territories[23] = new Territory(23);
			territories[23].setAdjacentTerrs(new ArrayList<Integer>(List.of(16, 17, 19, 20, 22, 24, 25, 27)));
			territories[24] = new Territory(24);
			territories[24].setAdjacentTerrs(new ArrayList<Integer>(List.of(21, 22, 23, 25)));
			territories[25] = new Territory(25);
			territories[25].setAdjacentTerrs(new ArrayList<Integer>(List.of(21, 23, 24, 26, 27)));
			territories[26] = new Territory(26);
			territories[26].setAdjacentTerrs(new ArrayList<Integer>(List.of(25)));
			territories[27] = new Territory(27);
			territories[27].setAdjacentTerrs(new ArrayList<Integer>(List.of(21, 23, 26)));
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
			if (player.getTerritories().size() + 1 == territories.length) return true;
		}
		return false;
	}
	
	private static int getNextPlayer() {
		return 1 - currentPlayerId;
	}
}
