package models.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import models.Game;
import models.Player;
import models.State;

public class GreedyAgent extends Player {

	PriorityQueue<State> searchHeap;
	Game game = Game.getInstance();
	
	public GreedyAgent(int id) {
		super(id);
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		int bonusArmies = super.calculateBonusArmay();
		placeBonusArmies(bonusArmies);
		searchHeap = new PriorityQueue<>();
		State current = new State();
		current.setPlayerTerritories(new ArrayList<Integer>(getTerritories()));
		current.calculateHeuristic();
		addChildren(current);
	}

	private void placeBonusArmies(int bonusArmies) {
		
	}
	
	private void addChildren(State parent) {
		for (Integer territory : parent.getPlayerTerritories()) {
			List<Integer> adjacentTerritorries = game.getTerritories()[territory].getAdjacentTerrs();
			for (Integer adj : adjacentTerritorries) {
				if (!parent.getPlayerTerritories().contains(adj)) {
					State child = new State();
					List<Integer> childTerritories = new ArrayList<>(parent.getPlayerTerritories());
					childTerritories.add(adj);
					child.calculateHeuristic();
					searchHeap.add(child);
				}
			}
		}
	}
}
