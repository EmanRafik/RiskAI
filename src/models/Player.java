package models;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
	/**
	 * score represents the current score of the player color represents the actual
	 * color of the player on the map 0 -> red 1 -> blue territories is a list of
	 * the territories' ids held by the player
	 */
	private int score, color, playerID;
	private List<Integer> territories;

	public Player(int id) {
		this.score = 0;
		this.playerID = id;
		this.territories = new ArrayList<Integer>();
	}

	public abstract void play();

	public abstract void distributedBonusTroops();

	public abstract void performAttacks();

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public List<Integer> getTerritories() {
		return territories;
	}

	public void setTerritories(List<Integer> territories) {
		this.territories = territories;
	}

	public int calculateBonusArmay() {
		return Math.max(3, territories.size() / 3);
	}

	// Function to remove a territory from this player's possession
	public void removeTerritory(int id) {
		for (int i = 0; i < territories.size(); i++) {
			if (territories.get(i) == id) {
				territories.remove(i);
			}
		}
	}

}
