package models.agents;

import models.Player;
import models.Territory;

import java.util.PriorityQueue;

import controller.GameConfig;
import models.Game;

public class AgressiveAgent extends Player {

	public AgressiveAgent(int id) {
		super(id);
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	private int getStrongestTerritory() {
		int max = Integer.MIN_VALUE;
		int strongestTerritory = 0;
		for(Integer terr : this.getTerritories()) {
			int troops = Game.getTerritories()[terr].getTroopsCount();
			if(troops > max) {
				max = troops;
				strongestTerritory = terr;
			}
		}
		return strongestTerritory;
	}
	
	private PriorityQueue<entry> getSortedTerritories(){
		PriorityQueue<entry> pQueue = new PriorityQueue<entry>();
		for(int i=1; i < Game.getTerritories().length; i++){
			if(Game.getTerritories()[i].getHolderID() == this.getPlayerID()) continue;
			Territory terr = Game.getTerritories()[i];
			pQueue.add(new entry(terr.getTerritoryID(), terr.getTroopsCount()));
		}
		return pQueue;
	}
	
	@Override
	public void distributedBonusTroops() {
		int strongestTerritoryID = getStrongestTerritory();
		Territory strongestTerritory = Game.getTerritories()[strongestTerritoryID];
		int bonusTroops = this.calculateBonusArmay();
		int troopsCount = bonusTroops + strongestTerritory.getTroopsCount();
		strongestTerritory.setTroopsCount(troopsCount);
		GameConfig.updateTerritories(Game.getTerritories());
		}

	@Override
	public void performAttacks() {
		boolean attacked = true;
		while(attacked) {
			attacked = false;
			PriorityQueue<entry> opponentTerritories = getSortedTerritories();
			while(opponentTerritories.size() != 0) {
				Territory oppTerr = Game.getTerritories()[opponentTerritories.poll().id];
				for(int adjTerrId : oppTerr.getAdjacentTerrs()) {
					Territory terr = Game.getTerritories()[adjTerrId];
					if(terr.getHolderID() != this.getPlayerID()) continue;
					if(terr.getTroopsCount() > oppTerr.getTroopsCount()+1) {
						attacked = true;
						int oppTroops = oppTerr.getTroopsCount();
						oppTerr.setTroopsCount(terr.getTroopsCount() - oppTroops -1);
						terr.setTroopsCount(1);
						Game.getPlayers()[oppTerr.getHolderID()].removeTerritory(oppTerr.getTerritoryID());
						oppTerr.setHolderID(this.getPlayerID());
						this.getTerritories().add(oppTerr.getTerritoryID());
						GameConfig.updateTerritories(Game.getTerritories());
						break;
					}
				}
			}
		}
	}
	
	class entry implements Comparable<entry>{
		
		int troops;
		int id;
		
		entry(int id, int troops){
			this.id = id;
			this.troops = troops;
		}

		@Override
		public int compareTo(entry other) {
			if(troops < other.troops) {
				return 1;
			}
			else if(troops == other.troops) {
				return 1;
			}
			return -1;
		}
		
	}

}
