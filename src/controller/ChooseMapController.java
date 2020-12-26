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

	// When USA's map is selected, GameMap: 1
	@FXML
	void goToUSA(ActionEvent event) throws IOException {
		// Keep changes made
		GameConfig.setGameMap(1);
		ChangeScenesController.getInstance().goToUSA(event);
	}

	@FXML
	void goToMainMenu(ActionEvent event) throws IOException {
		ChangeScenesController.getInstance().goToMainMenu(event);
	}
}
