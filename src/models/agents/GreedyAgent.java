package models.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import controller.GameConfig;
import models.Game;
import models.Player;
import models.State;
import models.Territory;

public class GreedyAgent extends Player {

	private PriorityQueue<State> searchHeap;
	// private Game game = Game.getInstance();

	public GreedyAgent(int id) {
		super(id);
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
		current.calculateHeuristic();

		addChildren(current);
		while (searchHeap.size() > 0) {
			if (searchHeap.peek().compareTo(current) > 0) {
				break;
			}
			current = searchHeap.peek();
			searchHeap.clear();
			addChildren(current);
		}
		this.setTerritories(new ArrayList<Integer>(current.getPlayerTerritories()));
		Game.getPlayers()[1 - super.getPlayerID()]
				.setTerritories(new ArrayList<Integer>(current.getOpponentTerritories()));
		Game.setTerritories(cloneTerritories(current.getTerritories()));
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
						List<Integer> childPlayerTerritories = new ArrayList<>(parent.getPlayerTerritories());
						childPlayerTerritories.add(adj);
						child.setPlayerTerritories(childPlayerTerritories);
						List<Integer> childOpponentTerritories = new ArrayList<>(parent.getOpponentTerritories());
						super.removeTerritory(childOpponentTerritories, adj);
						child.setOpponentTerritories(childOpponentTerritories);
						Territory[] childTerr = cloneTerritories(terr);
						childTerr[adj].setHolderID(super.getPlayerID());
						childTerr[adj]
								.setTroopsCount(terr[territory].getTroopsCount() - terr[adj].getTroopsCount() - 1);
						childTerr[territory].setTroopsCount(1);
						child.setTerritories(childTerr);
						child.calculateHeuristic();
						searchHeap.add(child);
					}
				}
			}
		}
	}
}
