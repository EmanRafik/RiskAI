package models;

import java.util.Comparator;
import java.util.List;

public class State implements Comparator<State>{

	private Territory[] territories;
	private List<Integer> playerTerritories;
	private List<Integer> opponentTerritories;
	
	private int cost, heuristic;
	
	
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


	public void calculateCost() {
		
	}


	public int getHeuristic() {
		return heuristic;
	}


	public void calculateHeuristic() {
		
	}


	@Override
	public int compare(State s1, State s2) {
		// TODO Auto-generated method stub
		return s2.getHeuristic() - s1.getHeuristic();
	}

}
