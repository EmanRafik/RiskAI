package models.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Game;
import models.Player;

public class AStarAgent extends Player {

	public AStarAgent(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void distributedBonusTroops() {
		int bonusArmy = super.calculateBonusArmay();
		ArrayList<Double> borderSecurityRatio = new ArrayList<Double>();
		ArrayList<Pair> arr = new ArrayList<Pair>();
		for (Integer curTerr : this.getTerritories()) {
			borderSecurityRatio.add(0.0);
			Double curBSR = borderSecurityRatio.get(borderSecurityRatio.size() - 1);
			for (Integer oppTerr : Game.getTerritories()[curTerr].getAdjacentTerrs()) {
				if (Game.getTerritories()[oppTerr].getHolderID() == this.getPlayerID()) continue;
				curBSR += Game.getTerritories()[oppTerr].getTroopsCount();
			}
			curBSR = curBSR / (1.0 * Game.getTerritories()[curTerr].getTroopsCount());
			arr.add(new Pair(curBSR, curTerr));
		}
		Collections.sort(arr, new Comparator<Pair>() { 
            @Override
            public int compare(Pair p1, Pair p2) { 
                return (int) (p1.x - p2.x);
            }
		});
		List<Pair> weakestTerr = arr.subList(0, Math.min(3, arr.size()));
		int idx = 0;
		while (bonusArmy > 0) {
			Game.getTerritories()[weakestTerr.get(idx).y].setTroopsCount(Game.getTerritories()[weakestTerr.get(idx).y].getTroopsCount() + 1);
			bonusArmy--;
			idx = (idx + 1) % weakestTerr.size();
		}
	}

	@Override
	public void performAttacks() {
		// TODO Auto-generated method stub
		
	}
	
	class Pair { 
	    double x; 
	    int y; 
	
	    public Pair(double x, int y) { 
	        this.x = x; 
	        this.y = y; 
	    } 
	} 

}
