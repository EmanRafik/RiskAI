package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ChooseAgentVAgentController {

	@FXML
	void goToMap(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMap(event);
	}

	@FXML
	void goToMainMenu(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMainMenu(event);
	}
}
