package models.agents;

import models.Game;
import models.Player;
import models.Territory;

public class PacifistAgent extends Player {

	public PacifistAgent(int id) {
		super(id);
	}

	@Override
	public void play() {
		int bonusArmy = super.calculateBonusArmay();
		Territory weakestTerr = Game.getTerritories()[getWeakestTerritory()];
		weakestTerr.setTroopsCount(weakestTerr.getTroopsCount() + bonusArmy);
		Territory territoryWithFewestArmy = this.getTerritoryWithFewestArmy();
	}
	
	private int getWeakestTerritory() {
		int min = Integer.MAX_VALUE;
		int weakest = -1;
		for (Integer terr : this.getTerritories()) {
			int cnt = Game.getTerritories()[terr].getTroopsCount();
			if (cnt < min) {
				min = cnt;
				weakest = terr;
			}
		}
		return weakest;
	}
	
	private Territory getTerritoryWithFewestArmy() {
		int min = Integer.MAX_VALUE;
		Territory weakest = null;
		for (Territory terr : Game.getTerritories()) {
			if (terr.getHolderID() == this.getPlayerID()) continue;
			int cnt = terr.getTroopsCount();
			if (cnt < min) {
				min = cnt;
				weakest = terr;
			}
		}
		return weakest;
	}

	@Override
	public void distributedBonusTroops() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performAttacks() {
		// TODO Auto-generated method stub
		
	}
}
