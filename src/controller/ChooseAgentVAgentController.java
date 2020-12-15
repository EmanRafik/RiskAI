package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import models.Player;
import models.agents.AStarAgent;
import models.agents.AgressiveAgent;
import models.agents.GreedyAgent;
import models.agents.MiniMaxAgent;
import models.agents.PacifistAgent;
import models.agents.Passive;
import models.agents.RealtimeAStarAgent;

public class ChooseAgentVAgentController {

	@FXML
	private ToggleGroup group2;

	@FXML
	private ToggleGroup group1;

	// When simulate is clicked, obtain player 1 and player 2 according to the
	// selected radio buttons
	@FXML
	void goToMap(ActionEvent event) throws IOException {
		// Keep the choice
		GameConfig.setPlayer1(getPlayer1());
		GameConfig.setPlayer2(getPlayer2());
		ChangeScenesController.getInstance().goToMap(event);
	}

	@FXML
	void goToMainMenu(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMainMenu(event);
	}

	private Player getPlayer1() {
		String type = ((RadioButton) group1.getSelectedToggle()).getText();
		return getPlayer(type, 0);
	}

	private Player getPlayer2() {
		String type = ((RadioButton) group2.getSelectedToggle()).getText();
		return getPlayer(type, 1);
	}

	private Player getPlayer(String type, int id) {
		switch (type.toLowerCase()) {
		case "aggressive agent":
			return new AgressiveAgent(1);
		case "passive agent":
			return new Passive(1);
		case "pacifist agent":
			return new PacifistAgent(1);
		case "greedy agent":
			return new GreedyAgent(1);
		case "a* agent":
			return new AStarAgent(1);
		case "real time a* agent":
			return new RealtimeAStarAgent(1);
		case "minimax agent":
			return new MiniMaxAgent(1);
		default:
			return null;
		}
	}
}
