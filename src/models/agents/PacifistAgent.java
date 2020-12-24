package models.agents;

import controller.GameConfig;
import models.Game;
import models.Player;
import models.Territory;

public class PacifistAgent extends Player {

	public PacifistAgent(int id) {
		super(id);
	}

	@Override
	public void play() {
		
		
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
			if (terr == null) continue;
			if (terr.getHolderID() == this.getPlayerID()) continue;
			for (Integer adj : terr.getAdjacentTerrs()) {
				if (Game.getTerritories()[adj].getHolderID() == this.getPlayerID()) {
					int cnt = terr.getTroopsCount();
					if (cnt < min) {
						min = cnt;
						weakest = terr;
					}
					break;
				}
			}
			
		}
		return weakest;
	}
	
	private void attack(Territory terr) {
		for (Integer adj : terr.getAdjacentTerrs()) {
			if (Game.getTerritories()[adj].getHolderID() == this.getPlayerID() && 
					Game.getTerritories()[adj].getTroopsCount() > terr.getTroopsCount()+1) {
				int oppTroops = terr.getTroopsCount();
				terr.setTroopsCount(Game.getTerritories()[adj].getTroopsCount() - oppTroops - 1);
				Game.getPlayers()[terr.getHolderID()].removeTerritory(terr.getTerritoryID());
				terr.setHolderID(this.getPlayerID());
				this.getTerritories().add(terr.getTerritoryID());
				Game.getTerritories()[adj].setTroopsCount(1);
				GameConfig.updateTerritories(Game.getTerritories());
				break;
			}
		}
	}

	@Override
	public void distributedBonusTroops() {
		int bonusArmy = super.calculateBonusArmay();
		Territory weakestTerr = Game.getTerritories()[getWeakestTerritory()];
		weakestTerr.setTroopsCount(weakestTerr.getTroopsCount() + bonusArmy);
		GameConfig.updateTerritories(Game.getTerritories());
	}

	@Override
	public void performAttacks() {
		Territory territoryWithFewestArmy = this.getTerritoryWithFewestArmy();
		if (territoryWithFewestArmy != null) this.attack(territoryWithFewestArmy);
	}
}
