import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FensterController {

	@FXML
	private Label WelcomeLabel;
	
	@FXML
	private Button FileChooserBtFile;
	
	@FXML
	private Button FileChooserBtTestFile;
	
	@FXML
	private Label ChoosenFile;
	
	@FXML
	private Label ChoosenTestfile;
	
	@FXML
	private Button WeiterButton;
	
	private File selectedFile;
	private File selectedTestFile;
	
	@FXML
	protected void btPressedFCFile(ActionEvent event) {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
		selectedFile = fileChooserTest.showOpenDialog(subStage);
		ChoosenFile.setText(selectedFile.getName());
	}
	
	@FXML
	protected void btPressedFCTestFile(ActionEvent event) throws IOException {
		Stage subStage = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open Test File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("Java Files", "*.java"));
		selectedTestFile = fileChooserTest.showOpenDialog(subStage);
		ChoosenTestfile.setText(selectedTestFile.getName());
	}
	
	@FXML
	protected void btPressedWeiter(ActionEvent event) throws IOException {
		
		
		if (selectedFile != null && selectedTestFile != null) {
			
			Stage subStage = new Stage();
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Choose a directory");
	        File selectedDirectory = directoryChooser.showDialog(subStage);
	        
	        if(selectedDirectory == null) {
	        	Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde kein Ordner ausgewählt.");
				alert.showAndWait();
	        }
	        else {
	        
	        	GridPane subPaneDirName = new GridPane();
	    		subPaneDirName.setAlignment(Pos.TOP_LEFT);
	    		subPaneDirName.setId("subPaneDirName");
	    		subPaneDirName.setHgap(25.0);
	    		subPaneDirName.setVgap(10.0);
	    		subPaneDirName.setPadding(new Insets(25, 25, 25, 25));
	    		Stage subStageDirName = createSubStage(subPaneDirName, "Folder Name");
	    		
	    		TextField dirNameText = new TextField();
	    		dirNameText.setPromptText("Enter the directory name");
	    		
	    		Button submit = new Button();
	    		submit.setPrefSize(145.0, 50.0);
	    		submit.setId("submit");
	    		submit.setText("Submit");
	    		
	    		submit.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						String directoryName = dirNameText.getText();
						
						if (directoryName.length() == 0) {
							Alert alert = new Alert(Alert.AlertType.ERROR, "Kein Ordnername ausgewählt.");
							alert.showAndWait();
						}
						
						else {
							subStageDirName.close();
						
							File dir = new File(selectedDirectory.getAbsolutePath(), directoryName);
							if (!dir.exists()) {
								boolean result = false;
			    			
								try {
									dir.mkdirs();
									result = true;
								} catch (SecurityException se) {
			    			
								}
							}
							try {
								openTestFile(selectedTestFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
	    			
	    		});
	    		
	    		subPaneDirName.add(dirNameText, 0, 0);
	    		subPaneDirName.add(submit, 0, 1);
	    		
	    		subStageDirName.show();

	        }
		}
		
		else {
			if (selectedFile == null && selectedTestFile == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde nichts ausgewählt.");
				alert.showAndWait();
			}
			if (selectedFile == null && selectedTestFile != null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde keine Datei ausgewählt.");
				alert.showAndWait();
			}
			if (selectedFile != null && selectedTestFile == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde keine Testdatei ausgewählt.");
				alert.showAndWait();
			}
		}
	}
	
	private void openTestFile(File selectedTestFile) throws IOException {
		
		GridPane subPaneRed = new GridPane();
		subPaneRed.setAlignment(Pos.TOP_LEFT);
		subPaneRed.setId("subPaneRed");
		subPaneRed.setHgap(25.0);
		subPaneRed.setVgap(10.0);
		subPaneRed.setPadding(new Insets(25, 25, 25, 25));
		Stage subStageRed = createSubStage(subPaneRed, "Red");
		
		TextArea textfieldRed = new TextArea();
		textfieldRed.setId("textfieldRed");
		textfieldRed.setPrefSize(450.0, 600.0);
				
		StringBuilder sbRed = null;
		sbRed = readFile(selectedTestFile);
		textfieldRed.setText(sbRed.toString());
		
		subPaneRed.add(textfieldRed, 0, 0, 1, 20);
		
		Button goToGreen = new Button();
		goToGreen.setPrefSize(145.0, 50.0);
		goToGreen.setId("goToGreen");
		goToGreen.setText("Go to Green");
		
		goToGreen.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					
					subStageRed.close();
					
					GridPane subPaneGreen = new GridPane();
					subPaneGreen.setAlignment(Pos.TOP_LEFT);
					subPaneGreen.setId("subPaneGreen");
					subPaneGreen.setHgap(25.0);
					subPaneGreen.setVgap(10.0);
					subPaneGreen.setPadding(new Insets(25, 25, 25, 25));
					Stage subStageGreen = createSubStage(subPaneGreen, "Green");
					
					TextArea textfieldGreen = new TextArea();
					textfieldGreen.setId("textfieldGreen");
					textfieldGreen.setPrefSize(450.0, 600.0);
							
					StringBuilder sbGreen = null;
					sbGreen = readFile(selectedFile);
					textfieldGreen.setText(sbGreen.toString());
					
					subPaneGreen.add(textfieldGreen, 0, 0, 1, 20);
					
					Button backToRed = new Button();
					backToRed.setPrefSize(145.0, 50.0);
					backToRed.setId("backToRed");
					backToRed.setText("Back to Red");
					
					Button goToBlack = new Button();
					goToBlack.setPrefSize(145.0, 50.0);
					goToBlack.setId("goToBlack");
					goToBlack.setText("Go to Black");
					
					backToRed.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								
								subStageGreen.close();
								subStageRed.show();
							}
						}
					});
					
					goToBlack.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							if (event.getButton() == MouseButton.PRIMARY) {
								
								subStageGreen.close();
								GridPane subPaneBlack = new GridPane();
								subPaneBlack.setAlignment(Pos.TOP_LEFT);
								subPaneBlack.setId("subPaneBlack");
								subPaneBlack.setHgap(25.0);
								subPaneBlack.setVgap(10.0);
								subPaneBlack.setPadding(new Insets(25, 25, 25, 25));

								
								Stage subStageBlack = createSubStage(subPaneBlack, "Black");
								
								TextArea textfieldBlack = new TextArea();
								textfieldBlack.setId("textfieldBlack");
								textfieldBlack.setPrefSize(450.0, 600.0);
										
								StringBuilder sbBlack = null;
								sbBlack = readFile(selectedFile);
								textfieldBlack.setText(sbBlack.toString());
								
								Button goToRed = new Button();
								goToRed.setPrefSize(145.0, 50.0);
								goToRed.setId("goToRed");
								goToRed.setText("Go to Red");
								
								goToRed.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

									@Override
									public void handle(MouseEvent event) {
										
										subStageBlack.close();
										subStageRed.show();
									}
								});
								
								subPaneBlack.add(textfieldBlack, 0, 0, 1, 20);
								subPaneBlack.add(goToRed, 1, 0);
								
								subStageBlack.show();
								
							}
						}
					});
					
					subPaneGreen.add(backToRed, 1, 0);
					subPaneGreen.add(goToBlack, 1, 2);
					
					subStageGreen.show();
				}
			}
		});
		
		subPaneRed.add(goToGreen, 1, 0);
		
        subStageRed.show();
		
	}
	
	public Stage createSubStage (GridPane subPane1, String title){

		StackPane subLayout = new StackPane();
		subLayout.getChildren().add(subPane1);
		Scene subScene = new Scene(subLayout, 600, 600);
		subScene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
		Stage subStage1 = new Stage();
		subStage1.setScene(subScene);
		subStage1.setTitle(title);
		subStage1.setResizable(false);
		return subStage1;
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
