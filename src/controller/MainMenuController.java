package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import models.agents.HumanAgent;

public class MainMenuController {

	// Called on clicking Human VS Human: game mode 0, player1,player2: human
	@FXML
	void goToMap(ActionEvent event) throws IOException {
		// Keep the choices made
		GameConfig.setGameMode(0);
		GameConfig.setPlayer1(new HumanAgent(0));
		GameConfig.setPlayer2(new HumanAgent(1));
		ChangeScenesController.getInstance().goToMap(event);
	}

	// Called on clicking Human VS AI: game mode 1, player1: human
	@FXML
	void goToAgent(ActionEvent event) throws IOException {
		// Keep the choices made
		GameConfig.setGameMode(1);
		GameConfig.setPlayer1(new HumanAgent(0));
		ChangeScenesController.getInstance().goToAgent(event);
	}

	// Called on clicking AI VS AI: game mode 2
	@FXML
	void goToAgentVAgent(ActionEvent event) throws IOException {
		// Keep the choices made
		GameConfig.setGameMode(2);
		ChangeScenesController.getInstance().goToAgentVAgent(event);
	}

	@FXML
	void exitGame(ActionEvent event) {
		ChangeScenesController.getInstance().exitGame(event);
	}
}
