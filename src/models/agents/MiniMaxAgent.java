package models.agents;

import java.util.ArrayList;
import java.util.List;

import controller.GameConfig;
import models.Game;
import models.Player;
import models.State;
import models.Territory;

public class MiniMaxAgent extends Player {
	private final int Inf = Integer.MAX_VALUE;
	private int MAX_DEPTH;
	private int depth;
	
	public MiniMaxAgent(int id) {
		super(id);
		MAX_DEPTH = 500;
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void performAttacks() {
		for (int i = 0; i < 3; i++) {
			State current = new State();
			current.setTerritories(cloneTerritories(Game.getTerritories()));
			Player opponent = Game.getPlayers()[1 - super.getPlayerID()];
			current.setOpponentTerritories(new ArrayList<Integer>(opponent.getTerritories()));
			current.setPlayerTerritories(new ArrayList<Integer>(getTerritories()));
			depth = 0;
			State nextState = maximize(current, -Inf, Inf).state;
			if (nextState == null) continue;
			this.setTerritories(new ArrayList<Integer>(nextState.getOpponentTerritories()));
			Game.getPlayers()[1 - super.getPlayerID()]
					.setTerritories(new ArrayList<Integer>(nextState.getPlayerTerritories()));
			Game.setTerritories(cloneTerritories(nextState.getTerritories()));
		}
		GameConfig.updateTerritories(Game.getTerritories());
	}
	
	private Pair minimize(State state, double alpha, double beta) {
		depth++;
		if (depth > MAX_DEPTH) return new Pair(null, eval(state));
		if (state.isTerminalState()) {
			return new Pair(null, eval(state));
		}
		Pair ans = new Pair(null, Inf);
		List<State> childStates = getChildStates(state);
		if (childStates.size() == 0) {
			return new Pair(null, 0);
		}
		for (State child : childStates) {
			double utility = maximize(child, alpha, beta).utility;
			if (utility < ans.utility) {
				ans.state = child;
				ans.utility = utility;
			}
			if (ans.utility <= alpha) break;
			if (ans.utility < beta) beta = ans.utility;
			child.undoDistribute();
		}
		return ans;
	}
	
	private Pair maximize(State state, double alpha, double beta) {
		depth++;
		if (depth == MAX_DEPTH) return new Pair(null, eval(state));
		if (state.isTerminalState()) {
			return new Pair(null, eval(state));
		}
		Pair ans = new Pair(null, -Inf);
		List<State> childStates = getChildStates(state);
		if (childStates.size() == 0) {
			return new Pair(null, 0);
		}
		for (State child : childStates) {
			double utility = minimize(child, alpha, beta).utility;
			if (utility > ans.utility) {
				ans.state = child;
				ans.utility = utility;
				child.undoDistribute();
			}
			if (ans.utility >= beta) break;
			if (ans.utility > alpha) alpha = ans.utility;
		}
		return ans;
	}
	
	private List<State> getChildStates(State currentState) {
		List<State> nextStates = new ArrayList<State>();
		Territory[] terr = currentState.getTerritories();
		for (Integer territory : currentState.getPlayerTerritories()) {
			List<Integer> adjacentTerritorries = terr[territory].getAdjacentTerrs();
			for (Integer adj : adjacentTerritorries) {
				if (currentState.getOpponentTerritories().contains(adj)) {
					if (terr[territory].getTroopsCount() > terr[adj].getTroopsCount()) {
						State child = new State();
						List<Integer> childPlayerTerritories = new ArrayList<>(currentState.getPlayerTerritories());
						childPlayerTerritories.add(adj);
						child.setPlayerTerritories(childPlayerTerritories);
						List<Integer> childOpponentTerritories = new ArrayList<>(currentState.getOpponentTerritories());
						removeTerritory(childOpponentTerritories, adj);
						child.setOpponentTerritories(childOpponentTerritories);
						Territory[] childTerr = cloneTerritories(terr);
						childTerr[adj].setHolderID(super.getPlayerID());
						childTerr[adj].setTroopsCount(terr[territory].getTroopsCount() - 1);
						childTerr[territory].setTroopsCount(1);
						child.setTerritories(childTerr);
						child.swapPlayerTerritories();
						child.distributeBonusTroops(Math.max(3, child.getPlayerTerritories().size() / 3));
						nextStates.add(child);
					}
				}
			}
		}
		return nextStates;
	}
	
	private double eval(State state) {
		if (depth == MAX_DEPTH) {
			return (state.getPlayerTerritories().size() + 1.0) / state.getTerritories().length;
		}
		if (state.getPlayerTerritories().size() + 1 == state.getTerritories().length) {
			return 1.0;
		}
		return 0.0;
	}
	
	class Pair { 
	    State state;
	    double utility;
	
	    public Pair(State x, double y) { 
	        this.state = x; 
	        this.utility = y; 
	    } 
	}

}
