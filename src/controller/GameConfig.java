package controller;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
	// The game map
	private static int gameMap;
	// A map keeping the territories with player1's color (red)
	private static Map<Integer, ImageView> territoriesRed = new HashMap<Integer, ImageView>();
	// A map keeping the territories with player2's color (blue)
	private static Map<Integer, ImageView> territoriesBlue = new HashMap<Integer, ImageView>();
	// A map keeping the army's labels on each of the territories
	private static Map<Integer, Label> armyLabels = new HashMap<Integer, Label>();

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

	public static Map<Integer, ImageView> getTerritoriesRed() {
		return territoriesRed;
	}

	public static void setTerritoriesRed(Map<Integer, ImageView> territoriesRed) {
		GameConfig.territoriesRed = territoriesRed;
	}

	public static Map<Integer, ImageView> getTerritoriesblue() {
		return territoriesBlue;
	}

	public static void setTerritoriesblue(Map<Integer, ImageView> territoriesblue) {
		GameConfig.territoriesBlue = territoriesblue;
	}

	public static Map<Integer, Label> getArmyLabels() {
		return armyLabels;
	}

	public static void setArmyLabels(Map<Integer, Label> armyLabels) {
		GameConfig.armyLabels = armyLabels;
	}

	// This method gets the parent root and obtains the GUI components from its
	// children
	public static void fillComponents(Parent root) {
		for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
			Node child = root.getChildrenUnmodifiable().get(i);
			String id = child.getId();
			// check that the child has an id first
			if (id == null || id == "")
				continue;
			if (id.toLowerCase().contains("redmap")) {
				territoriesRed.put(getNumberId(id), (ImageView) child);
			} else if (id.toLowerCase().contains("bluemap")) {
				territoriesBlue.put(getNumberId(id), (ImageView) child);
			} else if (id.toLowerCase().contains("armynumberlabel")) {
				armyLabels.put(getNumberId(id), (Label) child);
			}
		}
	}

	private static int getNumberId(String id) {
		return Integer.parseInt(id.replaceAll("[^0-9]", ""));
	}
}
