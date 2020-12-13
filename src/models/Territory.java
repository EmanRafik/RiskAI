package models;

import java.util.List;

public class Territory {
	int territoryID, holderID, troopsCount;
	List<Integer> adjacentTerrs;
	
	public List<Integer> getAdjacentTerrs() {
		return adjacentTerrs;
	}

	public void setAdjacentTerrs(List<Integer> adjacentTerrs) {
		this.adjacentTerrs = adjacentTerrs;
	}

	public int getTerritoryID() {
		return territoryID;
	}

	public void setTerritoryID(int territoryID) {
		this.territoryID = territoryID;
	}

	public int getHolderID() {
		return holderID;
	}

	public void setHolderID(int holderID) {
		this.holderID = holderID;
	}

	public int getTroopsCount() {
		return troopsCount;
	}

	public void setTroopsCount(int troopsCount) {
		this.troopsCount = troopsCount;
	}

	public Territory(int ID) {
		this.territoryID = ID;
		this.holderID = 0;
		this.troopsCount = 0;
	}
	
	public Territory(int ID, int playerID, int troopsCount) {
		this.territoryID = ID;
		this.holderID = playerID;
		this.troopsCount = troopsCount;
	}
	
	
	
	
}
