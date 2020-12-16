package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

	public void distributedBonusTroops() {
		int bonusArmy = calculateBonusArmay();
		ArrayList<Double> borderSecurityRatio = new ArrayList<Double>();
		ArrayList<Pair> arr = new ArrayList<Pair>();
		for (Integer curTerr : this.getTerritories()) {
			borderSecurityRatio.add(0.0);
			Double curBSR = borderSecurityRatio.get(borderSecurityRatio.size() - 1);
			for (Integer oppTerr : Game.getTerritories()[curTerr].getAdjacentTerrs()) {
				if (Game.getTerritories()[oppTerr].getHolderID() == this.getPlayerID()) continue;
				curBSR += Game.getTerritories()[oppTerr].getTroopsCount();
			}
			curBSR = curBSR / (1.0 * Game.getTerritories()[curTerr].getTroopsCount());
			arr.add(new Pair(curBSR, curTerr));
		}
		Collections.sort(arr, new Comparator<Pair>() { 
            @Override
            public int compare(Pair p1, Pair p2) { 
                return (int) (p1.x - p2.x);
            }
		});
		List<Pair> weakestTerr = arr.subList(0, Math.min(3, arr.size()));
		int idx = 0;
		while (bonusArmy > 0) {
			Game.getTerritories()[weakestTerr.get(idx).y].setTroopsCount(Game.getTerritories()[weakestTerr.get(idx).y].getTroopsCount() + 1);
			bonusArmy--;
			idx = (idx + 1) % weakestTerr.size();
		}
	}
	
	// Function to remove a territory from this player's possession
	public void removeTerritory(int id) {
		for (int i = 0; i < territories.size(); i++) {
			if (territories.get(i) == id) {
				territories.remove(i);
			}
		}
	}

	class Pair { 
	    double x; 
	    int y; 
	
	    public Pair(double x, int y) { 
	        this.x = x; 
	        this.y = y; 
	    } 
	} 
}
