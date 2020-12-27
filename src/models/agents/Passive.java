package models.agents;

import controller.GameConfig;
import models.Game;
import models.Player;
import models.Territory;

public class Passive extends Player {

	public Passive(int id) {
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
		Territory weakest = Game.getTerritories()[getWeakestTerritory()];
		weakest.setTroopsCount(weakest.getTroopsCount() + bonusArmy);
		GameConfig.updateTerritories(Game.getTerritories());
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
	
	@Override
	public void performAttacks() {
		return;
	}

}
