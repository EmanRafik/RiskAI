package models;

public class Territory {
	private static int count = 0;
	int territoryID, holderID, troopsCount;
	
	public Territory(int playerID, int troopsCount) {
		this.territoryID = Territory.count++;
		this.holderID = playerID;
		this.troopsCount = troopsCount;
	}
}
