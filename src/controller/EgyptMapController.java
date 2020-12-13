package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class EgyptMapController {

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

	public void onStatePress(javafx.scene.input.MouseEvent event) {
		Polygon poly = (Polygon) event.getSource();
		String polyId = poly.getId();
		sideBar.setVisible(true);
		stateNumberSideBarLabel.setVisible(true);
		armyNumberSideBarLabel.setVisible(true);
		addArmySideBarButton.setVisible(true);
		attackSideBarButton.setVisible(true);
		armyNumberToAddSideBarTextField.setVisible(true);
		adjacentStatesSideBarChoiceBox.setVisible(true);
	}
	private int getNumberId(String id) {
		return Integer.parseInt(id.replaceAll("[^0-9]", ""));
	}
}
