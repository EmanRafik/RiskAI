package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import models.Player;
import models.agents.AStarAgent;
import models.agents.AgressiveAgent;
import models.agents.GreedyAgent;
import models.agents.MiniMaxAgent;
import models.agents.PacifistAgent;
import models.agents.Passive;
import models.agents.RealtimeAStarAgent;

public class ChooseAgentController {

	// Called when selecting an AI agent
	@FXML
	void goToMap(ActionEvent event) throws IOException {
		// Keep the choices made
		GameConfig.setPlayer2(getPlayer2(event));
		ChangeScenesController.getInstance().goToMap(event);
	}

	@FXML
	void goToMainMenu(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMainMenu(event);
	}

	// returns the correct player according to the clicked button
	private Player getPlayer2(ActionEvent event) {
		switch (((Button) event.getSource()).getText()) {
		case "Aggressive agent":
			return new AgressiveAgent(1);
		case "Passive agent":
			return new Passive(1);
		case "Pacifist agent":
			return new PacifistAgent(1);
		case "Greedy agent":
			return new GreedyAgent(1);
		case "A* agent":
			return new AStarAgent(1);
		case "Real time A* agent":
			return new RealtimeAStarAgent(1);
		case "Minimax agent":
			return new MiniMaxAgent(1);
		default:
			return null;
		}
	}
}
