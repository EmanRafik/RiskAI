package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class State implements Comparable<State>{

	private Territory[] territories;
	private List<Integer> playerTerritories;
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


	public void calculateCost(int parentCost, int troopsLost) {
		this.cost = parentCost + troopsLost;
	}


	public int getHeuristic() {
		return heuristic;
	}

	public void calculateHeuristic() {
		int possiblePlayerAttacks = 0;
		int possibleOpponentAttacks = 0;
		List<Integer> attacked = new ArrayList<Integer>();
		
		for (Integer terr: playerTerritories) {
			for (Integer adj: territories[terr].getAdjacentTerrs()) {
				if (attacked.contains(adj)) {
					continue;
				}
				if (territories[adj].getHolderID() != territories[terr].getHolderID()) {
					if (territories[terr].getTroopsCount() > territories[adj].getTroopsCount() + 1) {
						attacked.add(adj);
						possiblePlayerAttacks++;
					}
				}
			}
		}
		
		attacked.clear();
		
		for (Integer terr: opponentTerritories) {
			for (Integer adj: territories[terr].getAdjacentTerrs()) {
				if (attacked.contains(adj)) {
					continue;
				}
				if (territories[adj].getHolderID() != territories[terr].getHolderID()) {
					if (territories[terr].getTroopsCount() > territories[adj].getTroopsCount() + 1) {
						attacked.add(adj);
						possibleOpponentAttacks++;
					}
				}
			}
		}
		
		heuristic = 2 * (possibleOpponentAttacks - possiblePlayerAttacks) -10* playerTerritories.size() + opponentTerritories.size();
		
	}

	@Override
	public int compareTo(State s) {
		// TODO Auto-generated method stub
		return (this.getHeuristic() + this.getCost()) - (s.getHeuristic() + s.getCost());
	}
	
	public boolean isTerminalState() {
		if (playerTerritories.size() + 1 == territories.length
				|| opponentTerritories.size() + 1 == territories.length)
			return true;
		return false;
	}
	
	/*
	 * 
	 * for (Integer terr: opponentTerritories) {
			for(Integer adj : territories[terr].getAdjacentTerrs()) {
				if (territories[adj].getHolderID() == Game.getCurrentPlayerId()) {
					heuristic += territories[terr].getTroopsCount();
				}
			}
		}
	 */

}
