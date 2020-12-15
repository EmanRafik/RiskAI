package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import models.Game;
import models.Player;
import models.Territory;
import models.agents.HumanAgent;

public class MapController {

	@FXML
	Rectangle sideBar;
	@FXML
	Label stateNumberSideBarLabel;
	@FXML
	Label armyNumberSideBarLabel;
	@FXML
	Button addArmySideBarButton;
	@FXML
	Button attackSideBarButton;
	@FXML
	TextField armyNumberToAddSideBarTextField;
	@FXML
	ChoiceBox<Integer> adjacentStatesSideBarChoiceBox;
	@FXML
	Button nextTurnBtn;
	@FXML
	Label playerNameLbl;
	@FXML
	Label currentActionLbl;

	// The Game class used should be initialized
	Game game;

	// Indicates whether we are in the distributing the troops part of the turn or
	// not
	boolean distributing = false;
	int distTroops = 0;
	int distTerritory;

	// The current Player
	Player currentPlayer;

	// In case of attacking keep track of attackable territories and the attacking
	// territory Id
	ArrayList<Integer> attackableTerritories = new ArrayList<Integer>();
	int attackingId;

	// Function called at the beginning to initialize the game with random territory
	// distribution
	@FXML
	void start(ActionEvent event) {
		Player[] players = { GameConfig.getPlayer1(), GameConfig.getPlayer2() };
		game = Game.getInstance(GameConfig.getGameMap(), players, GameConfig.getGameMode());
		((Button) event.getSource()).setVisible(false);
		((Button) event.getSource()).setDisable(false);
		currentPlayer = Game.getCurrentPlayer();
		playerNameLbl.setText("player " + (currentPlayer.getPlayerID() + 1));
		nextTurnBtn.setVisible(true);
		// Begin with attacking
		startAttacking();
		// If player is AI perform its attack
		// Otherwise the attack is made by the GUI (Human agent)
		if (!isHuman(currentPlayer)) {
			Game.performAttacks();
		}
	}

	// Function to change turns
	@FXML
	void changeTurn() {
		// Check whether we reached the final state
		if (Game.isFinalState()) {
			currentActionLbl.setText("Player " + (currentPlayer.getPlayerID() + 1) + " won!");
			return;
		}
		changePlayer();
		startDist();
		// If not human, perform the turn
		// Otherwise, we depend on the GUI events to perform the distribution and then
		// the attacking
		if (!isHuman(currentPlayer)) {
			Game.distributeTroops();
			Game.performAttacks();
		}

	}

	@FXML
	void distribute(ActionEvent event) {
		if (Integer.parseInt(armyNumberToAddSideBarTextField.getText()) > distTroops) {
			// Tried to distribute more than he has so reject
			return;
		}
		hideSideBar();
		// Update the number of troops the current player has
		int added = Integer.parseInt(armyNumberToAddSideBarTextField.getText());
		Territory currentTerr = Game.getTerritories()[distTerritory];
		currentTerr.setTroopsCount(added + currentTerr.getTroopsCount());
		GameConfig.getArmyLabels().get(distTerritory).setText("" + currentTerr.getTroopsCount());
		// decrease number of possible distributed troops
		distTroops -= added;
		if (distTroops == 0) {
			startAttacking();
		} else {
			currentActionLbl.setText("Remaining: " + distTroops);
		}
	}

	// The main function for clicking on the polygons
	public void onStatePress(javafx.scene.input.MouseEvent event) {
		// Should work only if the current player is a human
		Polygon poly = (Polygon) event.getSource();
		int polyId = getNumberId(poly.getId());
		if (isHuman(currentPlayer)
				&& (currentPlayer.getTerritories().contains(polyId) || attackableTerritories.contains(polyId))) {
			// In case of attacking, just perform the attack
			if (attackableTerritories.contains(polyId)) {
				performTheAttack(attackingId, polyId);
			}

			// The side bar for data entry in case of adding troops
			else if (distributing) {
				// Set the distributing and wait for button press
				showSideBar(polyId);
				distTerritory = polyId;
			}
			// The action done in case of attacking using this territory
			else if (!distributing) {
				setAttackables(polyId);
			}
		}
	}

	// Checks whether the current player is a human
	private boolean isHuman(Player player) {
		return player instanceof HumanAgent;
	}

	private void changePlayer() {
		Game.changePlayer();
		currentPlayer = Game.getCurrentPlayer();
		playerNameLbl.setText("player " + (currentPlayer.getPlayerID() + 1));
	}

	private void startDist() {
		// Start the distribution phase of the turn
		distributing = true;
		// Obtain the number to be distributed
		distTroops = currentPlayer.calculateBonusArmay();
		currentActionLbl.setText("distributing " + distTroops);
	}

	private void startAttacking() {
		distributing = false;
		currentActionLbl.setText("attacking");
		hideSideBar();
	}

	private int getNumberId(String id) {
		return Integer.parseInt(id.replaceAll("[^0-9]", ""));
	}

	private void showSideBar(int territoryId) {
		String armyNumber = GameConfig.getArmyLabels().get(territoryId).getText();
		sideBar.setVisible(true);
		armyNumberSideBarLabel.setVisible(true);
		armyNumberSideBarLabel.setText("number " + armyNumber);
		addArmySideBarButton.setVisible(true);
		armyNumberToAddSideBarTextField.setVisible(true);
	}

	private void hideSideBar() {
		sideBar.setVisible(false);
		armyNumberSideBarLabel.setVisible(false);
		addArmySideBarButton.setVisible(false);
		armyNumberToAddSideBarTextField.setVisible(false);
	}

	private void setAttackables(int polyId) {
		attackingId = polyId;
		currentActionLbl.setText("Attacking with territory number " + polyId);
		List<Integer> adjacentTerritories = Game.getTerritories()[polyId].getAdjacentTerrs();
		// Loop on those territories and add them to the attackable territories if they
		// aren't this player's
		attackableTerritories.clear();
		for (int i = 0; i < adjacentTerritories.size(); i++) {
			int currentTerritory = adjacentTerritories.get(i);
			if (!currentPlayer.getTerritories().contains(currentTerritory)) {
				attackableTerritories.add(currentTerritory);
			}
		}
	}

	private void performTheAttack(int from, int to) {
		// Getting attacking and defending territories
		Territory attackingTerr = Game.getTerritories()[from];
		Territory defendingTerr = Game.getTerritories()[to];
		int attackingTroops = attackingTerr.getTroopsCount();
		int defendingTroops = defendingTerr.getTroopsCount();
		if (defendingTroops >= attackingTroops) {
			// Cannot perform the attack
			return;
		}
		// Otherwise perform the attack and update
		currentPlayer.getTerritories().add(to);
		Game.getOtherPlayer().removeTerritory(to);
		attackingTerr.setTroopsCount(1);
		defendingTerr.setTroopsCount(attackingTroops - 1);
		GameConfig.updateTerritory(to, defendingTerr.getTroopsCount(), currentPlayer.getPlayerID());
		GameConfig.updateTerritory(from, attackingTerr.getTroopsCount(), currentPlayer.getPlayerID());
	}

}
