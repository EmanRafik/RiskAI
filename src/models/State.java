package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class State implements Comparable<State>{

	private Territory[] territories;
	private List<Integer> playerTerritories;
	private Territory[] initialTerritories;
	private List<Integer> opponentTerritories;
	
	private int cost = 0, heuristic = 0;
	
	
	public Territory[] getTerritories() {
		return territories;
	}


	public void setTerritories(Territory[] territories) {
		this.territories = territories;
	}


	public List<Integer> getPlayerTerritories() {
		return playerTerritories;
	}


	public void setPlayerTerritories(List<Integer> playerTerritories) {
		this.playerTerritories = playerTerritories;
	}


	public List<Integer> getOpponentTerritories() {
		return opponentTerritories;
	}


	public void setOpponentTerritories(List<Integer> opponentTerritories) {
		this.opponentTerritories = opponentTerritories;
	}


	public int getCost() {
		return cost;
	}


	public void calculateCost(int parentCost) {
		this.cost = parentCost + 1;
	}


	public int getHeuristic() {
		return heuristic;
	}

	public void calculateHeuristic() {
		for (Integer terr: opponentTerritories) {
			for(Integer adj : territories[terr].getAdjacentTerrs()) {
				if (territories[adj].getHolderID() == Game.getCurrentPlayerId()) {
					heuristic += territories[terr].getTroopsCount();
				}
			}
		}
	}

	@Override
	public int compareTo(State s) {
		return (this.getHeuristic() + this.getCost()) - (s.getHeuristic() + s.getCost());
	}
	
	public boolean isTerminalState() {
		if (playerTerritories.size() + 1 == territories.length
				|| opponentTerritories.size() + 1 == territories.length)
			return true;
		return false;
	}
	
	public void distributeBonusTroops(int bonusArmy) {
		initialTerritories = Game.getCurrentPlayer().cloneTerritories(territories);
		ArrayList<Double> borderSecurityRatio = new ArrayList<Double>();
		ArrayList<Pair> arr = new ArrayList<Pair>();
		for (Integer curTerr : playerTerritories) {
			double curBSR = 0.0;
			for (Integer oppTerr : territories[curTerr].getAdjacentTerrs()) {
				if (playerTerritories.contains(oppTerr)) continue;
				curBSR += territories[oppTerr].getTroopsCount();
			}
			curBSR = curBSR / (1.0 * territories[curTerr].getTroopsCount());
			borderSecurityRatio.add(curBSR);
			arr.add(new Pair(curBSR, curTerr));
		}
		Collections.sort(arr, new Comparator<Pair>() { 
            @Override
            public int compare(Pair p1, Pair p2) { 
                return (int) (p2.x - p1.x);
            }
		});
		List<Pair> weakestTerr = arr.subList(0, Math.min(3, arr.size()));
		if (weakestTerr.size() == 0) return;
		int idx = 0;
		while (bonusArmy > 0) {
			territories[weakestTerr.get(idx).y].setTroopsCount(territories[weakestTerr.get(idx).y].getTroopsCount() + 1);
			bonusArmy--;
			idx = (idx + 1) % weakestTerr.size();
		}
	}
	
	public void undoDistribute() {
		this.territories = initialTerritories;
	}
	
	public void swapPlayerTerritories() {
		List<Integer> temp = playerTerritories;
		playerTerritories = opponentTerritories;
		opponentTerritories = temp;
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
