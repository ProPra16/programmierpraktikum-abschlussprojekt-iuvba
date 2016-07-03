import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
	
	private File selectedFile;
	private File selectedTestFile;
	
	@FXML
	protected void btPressedFCFile(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
		selectedFile = fileChooserTest.showOpenDialog(subStage);
	}
	
	@FXML
	protected void btPressedFCTestFile(ActionEvent event) throws IOException {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open Test File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
		selectedTestFile = fileChooserTest.showOpenDialog(subStage);
		if (selectedTestFile != null) {
            openTestFile(selectedTestFile);
        }
	}
	
	private void openTestFile(File selectedTestFile) throws IOException {
		
		GridPane subPaneRed = new GridPane();
		subPaneRed.setAlignment(Pos.TOP_LEFT);
		subPaneRed.setId("subPaneRed");
		StackPane subLayoutRed = new StackPane();
		subLayoutRed.getChildren().add(subPaneRed);
		Scene subSceneRed = new Scene(subLayoutRed, 600, 600);
		subSceneRed.getStylesheets().add(getClass().getResource("StyleMenu.css").toExternalForm());
		Stage subStageRed = new Stage();
		subStageRed.setScene(subSceneRed);
		subStageRed.setTitle("Red");
		subStageRed.setResizable(false);
		
		TextArea textfieldRed = new TextArea();
		textfieldRed.setId("textfieldRed");
		textfieldRed.setPrefSize(450.0, 600.0);
				
		StringBuilder sbRed = null;
		sbRed = readFile(selectedTestFile);
		textfieldRed.setText(sbRed.toString());
		
		subPaneRed.add(textfieldRed, 0, 0);
		
		Button goToGreen = new Button();
		goToGreen.setPrefSize(145.0, 50.0);
		goToGreen.setText("Go to Green");
		
		goToGreen.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					
					subStageRed.close();
					
					GridPane subPaneGreen = new GridPane();
					subPaneGreen.setAlignment(Pos.TOP_LEFT);
					subPaneGreen.setId("subPaneGreen");
					StackPane subLayoutGreen = new StackPane();
					subLayoutGreen.getChildren().add(subPaneGreen);
					Scene subSceneGreen = new Scene(subLayoutGreen, 600, 600);
					subSceneGreen.getStylesheets().add(getClass().getResource("StyleMenu.css").toExternalForm());
					Stage subStageGreen = new Stage();
					subStageGreen.setScene(subSceneGreen);
					subStageGreen.setTitle("Green");
					subStageGreen.setResizable(false);
					
					TextArea textfieldGreen = new TextArea();
					textfieldGreen.setId("textfieldGreen");
					textfieldGreen.setPrefSize(450.0, 600.0);
					// textfieldGreen braucht irgendeinen rowspan damit die Buttons ordentlich angezeigt werden können.
							
					StringBuilder sbGreen = null;
					sbGreen = readFile(selectedFile);
					textfieldGreen.setText(sbGreen.toString());
					
					subPaneGreen.add(textfieldGreen, 0, 0);
					
					Button backToRed = new Button();
					backToRed.setPrefSize(145.0, 50.0);
					backToRed.setText("Back to Red");
					
					Button goToBlack = new Button();
					goToBlack.setPrefSize(145.0, 50.0);
					goToBlack.setText("Go to Black");
					
					backToRed.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								// Richtige Implementierung statt print.
								System.out.println("Back to Red");
							}
						}
					});
					
					goToBlack.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								// Richtige Implementierung statt print.
								System.out.println("Go to Black");
							}
						}
					});
					
					subPaneGreen.add(backToRed, 1, 0);
					subPaneGreen.add(goToBlack, 1, 1);
					
					subStageGreen.show();
				}
			}
		});
		
		subPaneRed.add(goToGreen, 1, 0);
		
        subStageRed.show();
		
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
