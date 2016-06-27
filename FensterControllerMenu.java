
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FensterControllerMenu {

	@FXML
	private Label WelcomeLabel;
	
	@FXML
	private Button FileChooserBt;
	
	@FXML
	protected void btPressedFC(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Recource File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Java Files", "*.java"));
		File selectedFile = fileChooser.showOpenDialog(subStage);
		if (selectedFile != null) {
            openFile(selectedFile);
        }
	}
	
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

}
