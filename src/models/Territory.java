package models;

public class Territory {
	private static int count = 0;
	int territoryID, holderID, troopsCount;
	
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

	public Territory(int playerID, int troopsCount) {
		this.territoryID = Territory.count++;
		this.holderID = playerID;
		this.troopsCount = troopsCount;
	}
}
