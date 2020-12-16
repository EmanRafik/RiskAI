package models;

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
		// TODO Auto-generated method stub
		return (this.getHeuristic() + this.getCost()) - (s.getHeuristic() + s.getCost());
	}

}
