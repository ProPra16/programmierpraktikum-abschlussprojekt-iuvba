import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FensterControllerMenu {

	@FXML
	private Label WelcomeLabel;
	
	@FXML
	private Button FileChooserBtTestFile;
	
	@FXML
	private Button FileChooserBtFile;
	
	@FXML
	protected void btPressedFCFile(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open Recource File");
		fileChooserTest.getExtensionFilters().addAll(
				new ExtensionFilter("Java Files", "*.java"));
		File selectedFile = fileChooserTest.showOpenDialog(subStage);
	}
	
	@FXML
	protected void btPressedFCTestFile(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open Test File");
		fileChooserTest.getExtensionFilters().addAll(
				new ExtensionFilter("Java Files", "*.java"));
		File selectedTestFile = fileChooserTest.showOpenDialog(subStage);
		if (selectedTestFile != null) {
            openTestFile(selectedTestFile);
        }
	}
	
	private void openTestFile(File selectedFileTest) {
		
		GridPane subPane = new GridPane();
		subPane.setAlignment(Pos.TOP_LEFT);
		StackPane subLayout = new StackPane();
		subLayout.getChildren().add(subPane);
		Scene subScene = new Scene(subLayout, 600, 600);
		subScene.getStylesheets().add(getClass().getResource("StyleMenu.css").toExternalForm());
		Stage subStage = new Stage();
		subStage.setScene(subScene);
		
		TextArea textfield = new TextArea();
		textfield.setId("textfield");
		textfield.setPrefSize(450.0, 600.0);
		
		subPane.add(textfield, 0, 0);
		
		subStage.setTitle("Red");
		subStage.setResizable(false);
        subStage.show();
		
	}
	
	
	/*
	private Desktop desktop = Desktop.getDesktop();
	
	 private void openFile(File selectedFile) {
	        try {
	            desktop.open(selectedFile);
	        } catch (IOException ex) {
	            Logger.getLogger(FensterControllerMenu.class.getName()).log(
	                Level.SEVERE, null, ex
	            );
	        }
	    }
	
	public Stage newWindow () {
		
		GridPane subPane = new GridPane();
		subPane.setAlignment(Pos.CENTER);
		StackPane subLayout = new StackPane();
		subLayout.getChildren().add(subPane);
		Scene subScene = new Scene(subLayout, 500, 500);
		Stage subStage = new Stage();
		subStage.setScene(subScene);
		
		return subStage;
	}
	*/

}
