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
	protected void btPressedFCTestFile(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open Test File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
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
		
		textfield.setText(readFile(selectedFileTest));
		
		subPane.add(textfield, 0, 0);
		
		subStage.setTitle("Red");
		subStage.setResizable(false);
        subStage.show();
		
	}
	
	private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FensterControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FensterControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(FensterControllerMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
}
