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
//		for (Integer terr: opponentTerritories) {
//			for(Integer adj : territories[terr].getAdjacentTerrs()) {
//				if (territories[adj].getHolderID() == Game.getCurrentPlayerId()) {
//					heuristic += territories[terr].getTroopsCount();
//				}
//			}
//		}
		heuristic = 0;
		//add number of undefended territories
		for (Integer territory : playerTerritories) {
			for (Integer adj : territories[territory].adjacentTerrs) {
				if (territories[adj].getHolderID() != Game.getCurrentPlayerId() 
						&& territories[adj].getTroopsCount() > territories[territory].getTroopsCount() + 1) {
					heuristic++;
					break;
				}
			}
		}
		
		//subtract number of bonus troops in next step
		heuristic -= Math.max(3, playerTerritories.size() / 3);
		
		heuristic += opponentTerritories.size();
		
		//subtract number of territories that the player can attack
		for (Integer territory : opponentTerritories) {
			for (Integer adj : territories[territory].adjacentTerrs) {
				if (territories[adj].getHolderID() == Game.getCurrentPlayerId() 
						&& territories[adj].getTroopsCount() > territories[territory].getTroopsCount() + 1) {
					heuristic--;
				}
			}
		}
	}

	@Override
	public int compareTo(State s) {
		// TODO Auto-generated method stub
		if ((this.getHeuristic() + this.getCost()) > (s.getHeuristic() + s.getCost()))
			return 1;
		if ((this.getHeuristic() + this.getCost()) == (s.getHeuristic() + s.getCost()))
			return 0;
		if ((this.getHeuristic() + this.getCost()) < (s.getHeuristic() + s.getCost()))
			return -1;
		return 0;
	}
	
	public boolean isTerminalState() {
		if (playerTerritories.size() + 1 == territories.length
				|| opponentTerritories.size() + 1 == territories.length)
			return true;
		return false;
	}

}
