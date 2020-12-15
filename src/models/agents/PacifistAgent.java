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
		this.attack(territoryWithFewestArmy);
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
					Game.getTerritories()[adj].getTroopsCount() > terr.getTroopsCount()) {
				terr.setTroopsCount(Game.getTerritories()[adj].getTroopsCount() - 1);
				Game.getPlayers()[terr.getHolderID()].getTerritories().remove(terr.getTerritoryID());
				terr.setHolderID(this.getPlayerID());
				this.getTerritories().add(terr.getTerritoryID());
				Game.getTerritories()[adj].setTroopsCount(1);
			}
		}
	}
}
