package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ChooseMapController {

	// When Egypt's map is selected, GameMap: 0
	@FXML
	void goToEgypt(ActionEvent event) throws IOException {
		// Keep changes made
		GameConfig.setGameMap(0);
		ChangeScenesController.getInstance().goToEgypt(event);
	}

	@FXML
	void goToMainMenu(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMainMenu(event);
	}
}
