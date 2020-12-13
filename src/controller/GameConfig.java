package controller;

import models.Player;

/*
 * A singleton class keeping the choices made to initialize the game
 */
public class GameConfig {
	
	// This class is a singleton
	private static GameConfig single_instance = null;

	// static method to create instance of the class
	public static GameConfig getInstance() {
		if (single_instance == null)
			single_instance = new GameConfig();
		return single_instance;
	}

	// The two players
	private static Player player1;
	private static Player player2;
	// The game mode
	private static int gameMode;
	private static int gameMap;

	public static Player getPlayer1() {
		return player1;
	}

	public static void setPlayer1(Player player1) {
		GameConfig.player1 = player1;
	}

	public static Player getPlayer2() {
		return player2;
	}

	public static void setPlayer2(Player player2) {
		GameConfig.player2 = player2;
	}

	public static int getGameMode() {
		return gameMode;
	}

	public static void setGameMode(int gameMode) {
		GameConfig.gameMode = gameMode;
	}

	public static int getGameMap() {
		return gameMap;
	}

	public static void setGameMap(int gameMap) {
		GameConfig.gameMap = gameMap;
	}
}
