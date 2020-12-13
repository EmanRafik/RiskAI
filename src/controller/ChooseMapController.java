package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ChooseMapController {

	@FXML
	void goToEgypt(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToEgypt(event);
	}

	@FXML
	void goToMainMenu(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMainMenu(event);
	}
}
