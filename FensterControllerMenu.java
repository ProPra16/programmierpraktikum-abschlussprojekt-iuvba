import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Button FileChooserBtFile;
	
	@FXML
	private Button FileChooserBtTestFile;
	
	@FXML
	protected void btPressedFCFile(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
		File selectedFile = fileChooserTest.showOpenDialog(subStage);
	}
	
	@FXML
	protected void btPressedFCTestFile(ActionEvent event) throws IOException {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open Test File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
		File selectedTestFile = fileChooserTest.showOpenDialog(subStage);
		if (selectedTestFile != null) {
            openTestFile(selectedTestFile);
        }
	}
	
	private void openTestFile(File selectedTestFile) throws IOException {
		
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
				
		StringBuilder sb = null;
		sb = readFile(selectedTestFile);
		textfield.setText(sb.toString());

		subPane.add(textfield, 0, 0);
		
		subStage.setTitle("Red");
		subStage.setResizable(false);
        subStage.show();
		
	}
	
	public StringBuilder readFile(File selectedFile){
		StringBuilder sb = new StringBuilder(1024);
		
		String line;
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))) {
			
			while( (line = bufferedReader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Datei existiert nicht.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb;
	}
}
