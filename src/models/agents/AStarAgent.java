package models.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import controller.GameConfig;
import models.Game;
import models.Player;
import models.State;
import models.Territory;

public class AStarAgent extends Player {
	private PriorityQueue<State> searchHeap;
	

	public AStarAgent(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performAttacks() {
		// TODO Auto-generated method stub
		searchHeap = new PriorityQueue<>();
		State current = new State();
		current.setTerritories(cloneTerritories(Game.getTerritories()));
		Player opponent = Game.getPlayers()[1 - super.getPlayerID()];
		current.setOpponentTerritories(new ArrayList<Integer>(opponent.getTerritories()));
		current.setPlayerTerritories(new ArrayList<Integer>(getTerritories()));

		addChildren(current);
		State bestState = current;
		boolean terminal = false;
		while (searchHeap.size() > 0) {
			State nextState = searchHeap.poll();
			if (bestState.compareTo(nextState) > 0) {
				bestState = nextState;
			}
			if (nextState.isTerminalState()) {
				this.setTerritories(new ArrayList<Integer>(nextState.getPlayerTerritories()));
				Game.getPlayers()[1 - super.getPlayerID()]
						.setTerritories(new ArrayList<Integer>(nextState.getOpponentTerritories()));
				Game.setTerritories(cloneTerritories(nextState.getTerritories()));
				terminal = true;
				break;
			}
			addChildren(nextState);
		}
		if (!terminal) {
			this.setTerritories(new ArrayList<Integer>(bestState.getPlayerTerritories()));
			Game.getPlayers()[1 - super.getPlayerID()]
					.setTerritories(new ArrayList<Integer>(bestState.getOpponentTerritories()));
			Game.setTerritories(cloneTerritories(bestState.getTerritories()));
		}
		GameConfig.updateTerritories(Game.getTerritories());
		
	}
	
	private void addChildren(State parent) {
		Territory[] terr = parent.getTerritories();
		for (Integer territory : parent.getPlayerTerritories()) {
			List<Integer> adjacentTerritorries = terr[territory].getAdjacentTerrs();
			for (Integer adj : adjacentTerritorries) {
				if (parent.getOpponentTerritories().contains(adj)) {
					if (terr[territory].getTroopsCount() > terr[adj].getTroopsCount() + 1) {
						State child = new State();
						int adjTroops = terr[adj].getTroopsCount();
						List<Integer> childPlayerTerritories = new ArrayList<>(parent.getPlayerTerritories());
						childPlayerTerritories.add(adj);
						child.setPlayerTerritories(childPlayerTerritories);
						List<Integer> childOpponentTerritories = new ArrayList<>(parent.getOpponentTerritories());
						super.removeTerritory(childOpponentTerritories, adj);
						child.setOpponentTerritories(childOpponentTerritories);
						Territory[] childTerr = cloneTerritories(terr);
						childTerr[adj].setHolderID(super.getPlayerID());
						childTerr[adj].setTroopsCount(terr[territory].getTroopsCount() - 1 - adjTroops);
						childTerr[territory].setTroopsCount(1);
						child.setTerritories(childTerr);
						child.calculateHeuristic();
						child.calculateCost(parent.getCost(),adjTroops);
						searchHeap.add(child);
					}
				}
			}
		}
	}
	

}
