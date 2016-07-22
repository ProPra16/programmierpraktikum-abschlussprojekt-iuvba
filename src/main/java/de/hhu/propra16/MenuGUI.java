package de.hhu.propra16;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuGUI extends Application {

	public static Stage primaryStage;


	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;

		Parent root = FXMLLoader.load(MenuGUI.class.getResource("/LayoutMenu.fxml"));
		
		Scene scene = new Scene(root);
		
		String stylesheet = getClass().getResource("/Style.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("TDD Trainer");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}


}
