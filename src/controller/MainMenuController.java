package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainMenuController {

	@FXML
	void goToMap(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMap(event);
	}

	@FXML
	void goToAgent(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToAgent(event);
	}

	@FXML
	void goToAgentVAgent(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToAgentVAgent(event);
	}

	@FXML
	void exitGame(ActionEvent event) {
		ChangeScenesController.getInstance().exitGame(event);
	}
}
