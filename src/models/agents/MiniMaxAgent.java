package models.agents;

import java.util.ArrayList;
import java.util.List;

import models.Game;
import models.Player;
import models.State;

public class MiniMaxAgent extends Player {
	final int Inf = Integer.MAX_VALUE;
	
	public MiniMaxAgent(int id) {
		super(id);
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void performAttacks() {
		State current = new State();
		current.setTerritories(Game.getTerritories().clone());
		Player opponent = Game.getPlayers()[1 - super.getPlayerID()];
		current.setOpponentTerritories(new ArrayList<Integer>(opponent.getTerritories()));
		current.setPlayerTerritories(new ArrayList<Integer>(getTerritories()));
		State resultingState = maximize(current, -Inf, Inf).state;	
	}
	
	private Pair minimize(State state, int alpha, int beta) {
		if (state.isTerminalState()) {
			return new Pair(null, 0);
		}
		Pair ans = new Pair(null, Inf);
		List<State> childStates = getChildStates(state);
		for (State child : childStates) {
			
		}
		return null;
	}
	
	private Pair maximize(State state, int alpha, int beta) {
		return null;
	}
	
	private List<State> getChildStates(State currentState) {
		return null;
	}
	
	class Pair { 
	    State state;
	    int utility;
	
	    public Pair(State x, int y) { 
	        this.state = x; 
	        this.utility = y; 
	    } 
	}

}
