package de.hhu.propra16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;

public class FensterController {

	@FXML
	private Label WelcomeLabel;
	
	@FXML
	private Button FileChooserBtFile;
	
	@FXML
	private Label ChoosenFile;
	
	@FXML
	private Button WeiterButton;
	
	private File selectedFile;

	private Gson gsonFile = new Gson();
	private ArrayList<Aufgabe> aufgabeArrayList = new ArrayList<>();

	private int choiceBoxFileIndex = 0;
	private int choiceBoxTestFileIndex = 0;

	private Label timelabel = new Label();
	private Thread timeThread;
	private boolean running = false;
	private long time = 120000;
	
	@FXML
	protected void btPressedFCFile(ActionEvent event) {

		Stage subStageFCFile = new Stage();
		FileChooser fileChooserTest = new FileChooser();
		fileChooserTest.setTitle("Open File");
		fileChooserTest.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
		selectedFile = fileChooserTest.showOpenDialog(subStageFCFile);

		Type type = new TypeToken<ArrayList<Aufgabe>>() {}.getType();
		try {
			aufgabeArrayList.addAll(gsonFile.fromJson(new JsonReader(new FileReader(selectedFile)),type));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//aufgabeArrayList.get(0);

		ChoosenFile.setText(selectedFile.getName());
	}
	
	@FXML
	protected void btPressedWeiter(ActionEvent event) throws IOException {
		
		
		if (selectedFile != null) {

			GridPane subPaneChoiceBox = new GridPane();
			subPaneChoiceBox.setAlignment(Pos.TOP_LEFT);
			subPaneChoiceBox.setId("subPaneChoiseBox");
			subPaneChoiceBox.setHgap(25.0);
			subPaneChoiceBox.setVgap(10.0);
			subPaneChoiceBox.setPadding(new Insets(25, 25, 25, 25));
			Stage subStageChoiceBox = createSubStage(200, 100, subPaneChoiceBox, "Folder Name");

			String[] aufgabenNamen = new String[aufgabeArrayList.size()/2];

			for (int i = 0; i < (aufgabeArrayList.size()/2); i++) {
				aufgabenNamen[i] = aufgabeArrayList.get(i).Name;
			}

			ChoiceBox choiceBox = new ChoiceBox();
			choiceBox.setItems(FXCollections.observableArrayList(aufgabenNamen));

			choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					choiceBoxFileIndex = newValue.intValue();
					choiceBoxTestFileIndex = (choiceBoxFileIndex + (aufgabeArrayList.size()/2));
				}
			});

			Button startWorking = new Button();
			startWorking.setPrefSize(150.0, 50.0);
			startWorking.setId("startWorking");
			startWorking.setText("Start Working");

			subPaneChoiceBox.add(choiceBox, 0, 0);
			subPaneChoiceBox.add(startWorking, 0, 1);

			startWorking.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {

							try {
								openFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
				}
			});
			subStageChoiceBox.show();
		}
		else {
			if (selectedFile == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde keine JSON Datei ausgewählt.");
				alert.showAndWait();
			}
		}
	}
	
	private void openFile() throws IOException {
		
		GridPane subPaneRed = new GridPane();
		subPaneRed.setAlignment(Pos.TOP_LEFT);
		subPaneRed.setId("subPaneRed");
		subPaneRed.setHgap(25.0);
		subPaneRed.setVgap(10.0);
		subPaneRed.setPadding(new Insets(25, 25, 25, 25));
		Stage subStageRed = createSubStage(600, 600, subPaneRed, "Red");
		Label timelabel = new Label();
		end();
		timelabel = start();
		timelabel.setId("timelabelRed");
		timelabel.setAlignment(Pos.CENTER);

		if (timelabel.getText() == "00:00") {
			end();
			subStageRed.close();
			//subStageGreen.show(); // geht nicht.
		}

		subPaneRed.add(timelabel, 1, 0);

		TextArea textfieldRed = new TextArea();
		textfieldRed.setId("textfieldRed");
		textfieldRed.setPrefSize(450.0, 600.0);

		textfieldRed.setText(aufgabeArrayList.get(choiceBoxTestFileIndex).Inhalt);
		
		subPaneRed.add(textfieldRed, 0, 0, 1, 20);
		
		Button goToGreen = new Button();
		goToGreen.setPrefSize(145.0, 50.0);
		goToGreen.setId("goToGreen");
		goToGreen.setText("Go to Green");

		subPaneRed.add(goToGreen, 1, 1);
		
		goToGreen.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {


					// Testet, ob etwas kompilierbar ist.
					/*
					CompilationUnit compile = new CompilationUnit("Test", "Das ist ein Test.", false);
					JavaStringCompiler sc = CompilerFactory.getCompiler(compile);
					sc.compileAndRunTests();
					CompilerResult cr = sc.getCompilerResult();
					cr.hasCompileErrors();
					*/

					subStageRed.close();
					
					GridPane subPaneGreen = new GridPane();
					subPaneGreen.setAlignment(Pos.TOP_LEFT);
					subPaneGreen.setId("subPaneGreen");
					subPaneGreen.setHgap(25.0);
					subPaneGreen.setVgap(10.0);
					subPaneGreen.setPadding(new Insets(25, 25, 25, 25));
					Stage subStageGreen = createSubStage(600, 600, subPaneGreen, "Green");
					
					TextArea textfieldGreen = new TextArea();
					textfieldGreen.setId("textfieldGreen");
					textfieldGreen.setPrefSize(450.0, 600.0);

					textfieldGreen.setText(aufgabeArrayList.get(choiceBoxFileIndex).Inhalt);
					
					subPaneGreen.add(textfieldGreen, 0, 0, 1, 20);
					end();
					Label timelabel = start();
					timelabel.setId("timelabelGreen");
					timelabel.setAlignment(Pos.CENTER);

					subPaneGreen.add(timelabel, 1, 0);
					
					Button backToRed = new Button();
					backToRed.setPrefSize(145.0, 50.0);
					backToRed.setId("backToRed");
					backToRed.setText("Back to Red");
					
					Button goToBlack = new Button();
					goToBlack.setPrefSize(145.0, 50.0);
					goToBlack.setId("goToBlack");
					goToBlack.setText("Go to Black");
					
					subPaneGreen.add(backToRed, 1, 1);
					subPaneGreen.add(goToBlack, 1, 3);
					
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

								
								Stage subStageBlack = createSubStage(600, 600, subPaneBlack, "Black");
								
								TextArea textfieldBlack = new TextArea();
								textfieldBlack.setId("textfieldBlack");
								textfieldBlack.setPrefSize(450.0, 600.0);

								textfieldBlack.setText(aufgabeArrayList.get(0).Inhalt);
								
								Button goToRed = new Button();
								goToRed.setPrefSize(145.0, 50.0);
								goToRed.setId("goToRed");
								goToRed.setText("Go to Red");
								
								subPaneBlack.add(textfieldBlack, 0, 0, 1, 20);
								subPaneBlack.add(goToRed, 1, 0);
								
								goToRed.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

									@Override
									public void handle(MouseEvent event) {
										
										subStageBlack.close();
										subStageRed.show();
									}
								});
								
								
								
								subStageBlack.show();
								
							}
						}
					});
					
					subStageGreen.show();
				}
			}
		});
		
        subStageRed.show();
		
	}
	
	public Stage createSubStage (int x, int y, GridPane subPane, String title){

		StackPane subLayout = new StackPane();
		subLayout.getChildren().add(subPane);
		Scene subScene = new Scene(subLayout, x, y);
		subScene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
		Stage subStage = new Stage();
		subStage.setScene(subScene);
		subStage.setTitle(title);
		subStage.setResizable(false);
		return subStage;
	}

	public Label start() {

		if (!running) {

			running = true;
			timeThread = new Thread(new Runnable() {

				@Override
				public void run() {

					while (running) {

						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								timelabel.setText(String.format("%02d:%02d", time/60000, time/1000%60));
							}
						});

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						time -= 1000;
					}
				}
			});
			timeThread.setDaemon(true);
			timeThread.start();
		}
		return timelabel;
	}

	public void end() {
		running = false;
		time = 120000;
	}
}
