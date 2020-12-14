package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Main;

/*
 * This controller is responsible for the functions used in navigating the GUI menus
 */

public class ChangeScenesController {

	// This class is a singleton
	private static ChangeScenesController single_instance = null;

	// static method to create instance of the class
	public static ChangeScenesController getInstance() {
		if (single_instance == null)
			single_instance = new ChangeScenesController();
		return single_instance;
	}

	// close the game
	public void exitGame(ActionEvent event) {
		Stage stage = getStage(event);
		stage.close();
	}

	// go to the main menu
	public void goToMainMenu(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
		changeScene(loader, event);
	}

	// go to the ChooseAgentVAgent menu
	public void goToAgentVAgent(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ChooseAgentVAgent.fxml"));
		changeScene(loader, event);
	}

	// go to the ChooseAgent menu
	public void goToAgent(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ChooseAgent.fxml"));
		changeScene(loader, event);
	}

	// go to the ChooseMap menu
	public void goToMap(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("ChooseMap.fxml"));
		changeScene(loader, event);
	}

	// go to Egypt's map
	// In this case we need to set the game configuration to have the corresponding
	// GUI components to be dealt with interactively
	public void goToEgypt(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("EgyptMap.fxml"));
		changeSceneKeepConfig(loader, event, true);
	}

	private Stage getStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	private void changeScene(FXMLLoader loader, ActionEvent event) throws IOException {
		Parent root = loader.load();
		Scene chooseMapScene = new Scene(root);
		Stage stage = getStage(event);
		stage.setScene(chooseMapScene);
	}

	@SuppressWarnings("unused")
	private void changeScene(FXMLLoader loader, ActionEvent event, boolean fullScreen) throws IOException {
		Parent root = loader.load();
		Scene chooseMapScene = new Scene(root);
		Stage stage = getStage(event);
		stage.setScene(chooseMapScene);
		stage.setFullScreen(fullScreen);
	}

	// Changes the scene and also keeps the game configuration for the components of
	// the GUI to interact with
	private void changeSceneKeepConfig(FXMLLoader loader, ActionEvent event, boolean fullScreen) throws IOException {
		Parent root = loader.load();
		Scene chooseMapScene = new Scene(root);
		GameConfig.fillComponents(root);
		Stage stage = getStage(event);
		stage.setScene(chooseMapScene);
		stage.setFullScreen(fullScreen);
	}
}
