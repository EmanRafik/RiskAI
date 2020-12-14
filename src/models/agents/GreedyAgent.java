package models.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import models.Game;
import models.Player;
import models.State;
import models.Territory;

public class GreedyAgent extends Player {

	private PriorityQueue<State> searchHeap;
	private Game game = Game.getInstance();

	public GreedyAgent(int id) {
		super(id);
	}

	@SuppressWarnings("static-access")
	@Override
	public void play() {
		// TODO Auto-generated method stub
		int bonusArmies = super.calculateBonusArmay();
		placeBonusArmies(bonusArmies);
		searchHeap = new PriorityQueue<>();
		State current = new State();
		current.setTerritories(game.getTerritories().clone());
		Player opponent = game.getPlayers()[1 - super.getPlayerID()];
		current.setOpponentTerritories(new ArrayList<Integer>(opponent.getTerritories()));
		current.setPlayerTerritories(new ArrayList<Integer>(getTerritories()));
		current.calculateHeuristic();
		addChildren(current);
		
		State nextState = searchHeap.peek();
		super.setTerritories(nextState.getPlayerTerritories());
		game.getPlayers()[1 - super.getPlayerID()].setTerritories(nextState.getOpponentTerritories());
		game.setTerritories(nextState.getTerritories());
	}

	private void placeBonusArmies(int bonusArmies) {

	}
	
	private void addChildren(State parent) {
		Territory[] terr = parent.getTerritories();
		for (Integer territory : parent.getPlayerTerritories()) {
			List<Integer> adjacentTerritorries = terr[territory].getAdjacentTerrs();
			for (Integer adj : adjacentTerritorries) {
				if (parent.getOpponentTerritories().contains(adj)) {
					if (terr[territory].getTroopsCount() > terr[adj].getTroopsCount()) {
						State child = new State();
						List<Integer> childPlayerTerritories = new ArrayList<>(parent.getPlayerTerritories());
						childPlayerTerritories.add(adj);
						List<Integer> childOpponentTerritories = new ArrayList<>(parent.getOpponentTerritories());
						childOpponentTerritories.remove(adj);
						Territory[] childTerr = terr.clone();
						childTerr[adj].setHolderID(super.getPlayerID());
						childTerr[adj].setTroopsCount(terr[territory].getTroopsCount() - 1);
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
