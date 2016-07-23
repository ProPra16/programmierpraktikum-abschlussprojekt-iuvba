package de.hhu.propra16;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static de.hhu.propra16.Chart.chart;
import static de.hhu.propra16.MenuGUI.primaryStage;
import static de.hhu.propra16.TimeTracking.endRecordTimeTracking;
import static de.hhu.propra16.TimeTracking.startRecordTimeTracking;
import static de.hhu.propra16.TimerBaby.getTime;
import static de.hhu.propra16.TimerBaby.start;


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

	// Testet, ob etwas kompilierbar ist.
	/*
	CompilationUnit compile = new CompilationUnit("Test", "Das ist ein Test.", false);
	JavaStringCompiler sc = CompilerFactory.getCompiler(compile);
	sc.compileAndRunTests();
	CompilerResult cr = sc.getCompilerResult();
	cr.hasCompileErrors();
	*/
	
	@FXML
	protected void btPressedFCFile(ActionEvent event) {

		Stage subStageFileChooser = new Stage();

		/*
		 * Wenn der File Chooser geöffnet ist soll das Menü "deaktiviert" werden.
		 * (Wie bei der SubStage wo man die Aufgabe auswählen kann)
		 * Dort funktioniert es auch, hier allerdings nicht.
		 */

		subStageFileChooser.initModality(Modality.WINDOW_MODAL);
		subStageFileChooser.initOwner(primaryStage);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
		selectedFile = fileChooser.showOpenDialog(subStageFileChooser);

        if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde keine JSON Datei ausgewählt.");
            alert.showAndWait();
        }
        else {
            Type type = new TypeToken<ArrayList<Aufgabe>>() {}.getType();
            try {
                aufgabeArrayList.addAll(gsonFile.fromJson(new JsonReader(new FileReader(selectedFile)),type));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //aufgabeArrayList.get(0);

            ChoosenFile.setText(selectedFile.getName());
        }


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

			subStageChoiceBox.initModality(Modality.WINDOW_MODAL);
			subStageChoiceBox.initOwner(primaryStage);

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
								open();
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

	private void open() throws IOException {

		GridPane subPane = new GridPane();
		subPane.setAlignment(Pos.TOP_LEFT);
		subPane.setId("subPane");
		subPane.setHgap(25.0);
		subPane.setVgap(10.0);
		subPane.setPadding(new Insets(25, 25, 25, 25));
		Stage subStage = createSubStage(1150, 600, subPane, "Work");
		// subStage.setFullScreen(true);

		// Beschreibungs Label

		Label beschreibungTest = new Label();
		beschreibungTest.setId("beschreibungTest");
		beschreibungTest.setText(aufgabeArrayList.get(choiceBoxTestFileIndex).Beschreibung);

		subPane.add(beschreibungTest, 0, 0, 1, 1);

		Label beschreibung = new Label();
		beschreibung.setId("beschreibung");
		beschreibung.setText(aufgabeArrayList.get(choiceBoxFileIndex).Beschreibung);

		subPane.add(beschreibung, 4, 0, 1, 1);

		// TextArea

		TextArea textAreaR = new TextArea();
		textAreaR.setId("textAreaR");
		textAreaR.setPrefSize(450.0, 600.0);
		textAreaR.setText(aufgabeArrayList.get(choiceBoxTestFileIndex).Inhalt);

		textAreaR.setDisable(false);

		subPane.add(textAreaR, 0, 1, 1, 20);

		TextArea textAreaGB = new TextArea();
		textAreaGB.setId("textAreaGB");
		textAreaGB.setPrefSize(450.0, 600.0);
		textAreaGB.setText(aufgabeArrayList.get(choiceBoxFileIndex).Inhalt);

		textAreaGB.setDisable(true);

		subPane.add(textAreaGB, 4, 1, 1, 20);

		Label timeLabel = start();          //Da Klasse TimerBaby importiert wurde: Aufruf von TimerBaby -> runterzählen bis 2:00 min.
		timeLabel.setId("timeLabelBaby");
		timeLabel.setText("TimeBaby");
		timeLabel.setAlignment(Pos.CENTER);

		subPane.add(timeLabel, 1, 0, 3, 1);

        if ((getTime()) == 0){              // Aufruf von der Klasse TimerBaby
            // Aufruf von changeTextArea(); sofern checkFunktion true ausgibt.
        }

		// Buttons
        // GO TO GREEN
		Button goToGreen = new Button();
		goToGreen.setPrefSize(145.0, 50.0);
		goToGreen.setId("goToGreen");
		goToGreen.setText("Go to Green");

		subPane.add(goToGreen, 1, 3, 3, 2);

        // BACK TO RED
        Button backToRed = new Button();
        backToRed.setPrefSize(145.0, 50.0);
        backToRed.setId("backToRed");
        backToRed.setText("Back to Red");

        subPane.add(backToRed, 1, 5, 3, 2);

        // GO TO RED
        Button goToRed = new Button();
        goToRed.setPrefSize(145.0, 50.0);
        goToRed.setId("goToRed");
        goToRed.setText("Go to Red");

        subPane.add(goToRed, 1, 9, 3, 2);

        // GO TO BLACK
        Button goToBlack = new Button();
        goToBlack.setPrefSize(145.0, 50.0);
        goToBlack.setId("goToBlack");
        goToBlack.setText("Go to Black");

        subPane.add(goToBlack, 1, 7, 3, 2);


        goToGreen.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {

				String nameTF = aufgabeArrayList.get(choiceBoxTestFileIndex).Name;
				String inhaltTextAreaR = textAreaR.getText();

				// funktioniert nicht
				/*
				boolean check = checkRED(nameTF, inhaltTextAreaR, true);

				System.out.println(check);
				System.out.println(inhaltTextAreaR);

				if (check == true) {
					textAreaR.setDisable(true);
					textAreaGB.setDisable(false);
				}
				*/

				textAreaR.setDisable(true);
				textAreaGB.setDisable(false);

                goToGreen.setDisable(true);
                backToRed.setDisable(false);
                goToRed.setDisable(false);
                goToBlack.setDisable(false);

			}
		});



        backToRed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                endRecordTimeTracking();

                startRecordTimeTracking();

                textAreaR.setDisable(false);
                textAreaGB.setDisable(true);

                goToGreen.setDisable(false);
                backToRed.setDisable(true);
                goToBlack.setDisable(true);
                goToRed.setDisable(true);
                // das was bei Klick von BACK TO RED passiert
            }
        });


        goToRed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                endRecordTimeTracking();

                startRecordTimeTracking();

                textAreaR.setDisable(false);
                textAreaGB.setDisable(true);

                goToGreen.setDisable(false);
                backToRed.setDisable(true);
                goToBlack.setDisable(true);
                goToRed.setDisable(true);
                // das was bei Klick von GO TO RED passiert
            }
        });



        goToBlack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                endRecordTimeTracking();
                textAreaR.setDisable(true);
                textAreaGB.setDisable(false);

                goToGreen.setDisable(false);
                backToRed.setDisable(true);
                goToBlack.setDisable(true);
                goToRed.setDisable(true);

                chart();      // kann hier auch falsch platziert sein. Aufruf sollte dann dort geschehen wo es benötigt wird
            }
        });






		/*
		 * Unterscheidung zwischen Green und Black fehlt.
		 * Wenn man bei Green ist müssen backToRed & goToBlack klickbar sein
		 * und goTroGreen nicht klickbar.
		 * Wenn man bei Black ist müssen backToRed & goToBlack nicht klickbar sein
		 * und goToGreen klickbar.
		 */






		subStage.show();
	}


	public Stage createSubStage(int x, int y, GridPane subPane, String title){

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

}
