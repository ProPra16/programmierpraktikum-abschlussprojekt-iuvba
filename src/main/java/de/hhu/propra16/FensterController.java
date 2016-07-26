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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import vk.core.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static de.hhu.propra16.Chart.chart;
import static de.hhu.propra16.MenuGUI.primaryStage;
import static de.hhu.propra16.TimeTracking.*;
import static de.hhu.propra16.TimerBaby.endTimer;
import static de.hhu.propra16.TimerBaby.timerOff;


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

	private int choiceBoxFileIndex;
	private int choiceBoxTestFileIndex;
    private static TextArea textAreaGB;
    private static TextArea textAreaR;
    private Label timelabel = new Label();
	private Thread timeThread;
	private boolean running = false;
	private long time = 120000;
    private int a = 0;
    private int b = 0;
    private String babysteps;
	private TestResult tr;
	private int numberOfFailedTests = 0;
	private int numberOfSuccessfulTests = 0;
    private Collection<TestFailure> tf;
    private boolean baby;
	private String nameTestFile;
	private String nameFile;
    private Label timeLabel;
	private String oldTextInTextAreaGB;

    @FXML
	protected void btPressedFCFile(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
		selectedFile = fileChooser.showOpenDialog(primaryStage);

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
			Stage subStageChoiceBox = createSubStage(300, 200, subPaneChoiceBox, "Excercise Name");

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

            Label babyCheck = new Label();
            babyCheck.setText("BABYSTEPS ON");
            babyCheck.setAlignment(Pos.CENTER);
            babyCheck.setVisible(false);
            System.out.println(aufgabeArrayList.get(choiceBoxFileIndex).Babysteps);


            if (baby == true){
                babyCheck.setVisible(true);
            }
            else babyCheck.setVisible(false);


			subPaneChoiceBox.add(choiceBox, 0, 0);
			subPaneChoiceBox.add(startWorking, 0, 3);
			subPaneChoiceBox.add(babyCheck, 0, 2);


			startWorking.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
						if (event.getButton() == MouseButton.PRIMARY) {

							try {

                                if (Objects.equals(aufgabeArrayList.get(choiceBoxFileIndex).Babysteps, "on")){
                                    baby = true;
                                }
                                else baby = false;

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

        TimerRed.start();
        TimerRound.start();


		GridPane subPane = new GridPane();
		subPane.setAlignment(Pos.TOP_LEFT);
		subPane.setId("subPane");
		subPane.setHgap(25.0);
		subPane.setVgap(10.0);
		subPane.setPadding(new Insets(25, 25, 25, 25));
		Stage subStage = createSubStage(1150, 600, subPane, "Work");
		// subStage.setFullScreen(true);

		// Label Beschreibung Test
		Label beschreibungTest = new Label();
		beschreibungTest.setId("beschreibungTest");
		beschreibungTest.setText(aufgabeArrayList.get(choiceBoxTestFileIndex).Beschreibung);

		subPane.add(beschreibungTest, 0, 0, 1, 1);

		// Label Beschreibung Aufgabe
		Label beschreibung = new Label();
		beschreibung.setId("beschreibung");
		beschreibung.setText(aufgabeArrayList.get(choiceBoxFileIndex).Beschreibung);

		subPane.add(beschreibung, 4, 0, 1, 1);

		// TextArea Red
		textAreaR = new TextArea();
		textAreaR.setId("textAreaR");
		textAreaR.setPrefSize(450.0, 600.0);
		textAreaR.setText(aufgabeArrayList.get(choiceBoxTestFileIndex).Inhalt);

		textAreaR.setDisable(false);

		// TextArea Green & Black
		textAreaGB = new TextArea();
		textAreaGB.setId("textAreaGB");
		textAreaGB.setPrefSize(450.0, 600.0);
		textAreaGB.setText(aufgabeArrayList.get(choiceBoxFileIndex).Inhalt);

		textAreaGB.setDisable(true);



		// VBox Red
		VBox vBoxRed = new VBox();
		vBoxRed.setPadding(new Insets(10.0));
		vBoxRed.setPrefSize(460.0, 610.0);
		vBoxRed.setStyle("-fx-background-color: red;");

		vBoxRed.getChildren().addAll(textAreaR);

		subPane.add(vBoxRed, 0, 1, 1, 20);

		// VBox Green & Black
		VBox vBoxGB = new VBox();
		vBoxGB.setPadding(new Insets(10.0));
		vBoxGB.setPrefSize(460.0, 610.0);
		vBoxGB.setStyle("-fx-background-color: lightgrey;");

		vBoxGB.getChildren().addAll(textAreaGB);

		subPane.add(vBoxGB, 4, 1, 1, 20);

		// Label Timer
        if (baby == true) {
            timeLabel = TimerBaby.start(); //Da Klasse TimerBaby importiert wurde: Aufruf von TimerBaby -> runterzählen bis 2:00 min.

            timeLabel.setId("timeLabelBaby");
            timeLabel.setAlignment(Pos.CENTER);
            timeLabel.setPrefSize(145.0, 25.0);

            subPane.add(timeLabel, 1, 0, 3, 1);
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

        // GO TO BLACK
        Button goToBlack = new Button();
        goToBlack.setPrefSize(145.0, 50.0);
        goToBlack.setId("goToBlack");
        goToBlack.setText("Go to Black");

        subPane.add(goToBlack, 1, 7, 3, 2);

		// GO TO RED
		Button goToRed = new Button();
		goToRed.setPrefSize(145.0, 50.0);
		goToRed.setId("goToRed");
		goToRed.setText("Go to Red");

		subPane.add(goToRed, 1, 9, 3, 2);

		nameTestFile = aufgabeArrayList.get(choiceBoxTestFileIndex).Name;

		nameFile = aufgabeArrayList.get(choiceBoxFileIndex).Name;



        goToGreen.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {


				oldTextInTextAreaGB = textAreaGB.getText();
                if (baby == true) {
                    TimerBaby.endTimer();
                    TimerBaby.start();
                }
                System.out.println(nameTestFile);
                System.out.println(textAreaR.getText());

				CompilationUnit cTest = new CompilationUnit(nameTestFile, textAreaR.getText(), true);
				CompilationUnit cCode = new CompilationUnit(nameFile, textAreaGB.getText(), false);
				JavaStringCompiler scTest = CompilerFactory.getCompiler(cTest, cCode);
				try {
                    scTest.compileAndRunTests();
                } catch(NullPointerException npe){

                }
				CompilerResult cr = scTest.getCompilerResult();

                tr = scTest.getTestResult();
                CompileError ce;

               /* tf = tr.getTestFailures();

                List<TestFailure> list = new ArrayList<TestFailure>(tf);

                TestFailure elem;

                for (int i = 0; i<list.size(); i++){
                    elem = list.get(i);
                    System.out.println(elem.getMessage());
                }



                Collection failures = tf;

               for(TestFailure failure : failures) {
                    failure.getMessage();
               }


				try {
					numberOfFailedTests = tr.getNumberOfFailedTests();
				} catch (NullPointerException npe) {

					Alert alert = new Alert(Alert.AlertType.ERROR, "Es wurde keine fehlschlagenden Tests geschrieben!");
					alert.showAndWait();
				}*/

				//numberOfSuccessfulTests = tr.getNumberOfSuccessfulTests();
				//System.out.println("Anzahl fehlgeschlagener Tests: " + tr.getNumberOfFailedTests());
				//System.out.println("Anzahl erfolgreicher Tests: " + numberOfSuccessfulTests);


				if (cr.hasCompileErrors() == true /*&& tr.getNumberOfFailedTests() == 1*/) {

					if (a == 0){
						TimerGreen.start();
						a = 1;
					}
					else{
						TimerGreen.continueGreenTime();
					}

					TimerRed.pauseRedTime();
					textAreaR.setDisable(true);
					textAreaGB.setDisable(false);

					goToGreen.setDisable(true);
					backToRed.setDisable(false);
					goToRed.setDisable(true);
					goToBlack.setDisable(false);

					vBoxRed.setStyle("-fx-background-color: lightgrey");
					vBoxGB.setStyle("-fx-background-color: green");

					textAreaGB.setText(textAreaGB.getText());
				}
				else if (cr.hasCompileErrors() == true) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Test-Datei kompiliert nicht!");
					alert.showAndWait();
				}
				else if (numberOfFailedTests != 1) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Anzahl der fehlschlagenden Tests nicht gleich 1!");
					alert.showAndWait();
				}
			}
		});

        backToRed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

				textAreaGB.setText(oldTextInTextAreaGB);
                if( baby == true) {
                    TimerBaby.endTimer();
                    TimerBaby.start();
                }
                if (a == 1){
					TimerGreen.pauseGreenTime();
				}
                TimerRed.continueRedTime();

                textAreaR.setDisable(false);
                textAreaGB.setDisable(true);

                goToGreen.setDisable(false);
                backToRed.setDisable(true);
                goToBlack.setDisable(true);
                goToRed.setDisable(true);
                // das was bei Klick von BACK TO RED passiert

				vBoxRed.setStyle("-fx-background-color: red");
				vBoxGB.setStyle("-fx-background-color: lightgrey");
            }
        });

        goToBlack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {


				CompilationUnit cTest = new CompilationUnit(nameTestFile, textAreaR.getText(), true);
				CompilationUnit cCode = new CompilationUnit(nameFile, textAreaGB.getText(), false);
				JavaStringCompiler scTest = CompilerFactory.getCompiler(cTest, cCode);
				scTest.compileAndRunTests();
				CompilerResult cr = scTest.getCompilerResult();



				if (/*cr.hasCompileErrors() == false &&*/ numberOfFailedTests == 0) {

					TimerBlack.start();
					b = 1;
					TimerRed.continueRedTime();
					endRecordRedTime();
					TimerGreen.continueGreenTime();
					endRecordGreenTime();

					textAreaR.setDisable(true);
					textAreaGB.setDisable(false);

					goToGreen.setDisable(true);
					backToRed.setDisable(true);
					goToBlack.setDisable(true);
					goToRed.setDisable(false);

					vBoxRed.setStyle("-fx-background-color: lightgrey");
					vBoxGB.setStyle("-fx-background-color: black");

                    if (baby == true) {
                        endTimer();
                        timerOff();
                    }
					textAreaGB.setText(textAreaGB.getText());
				}
				else if (cr.hasCompileErrors() == true) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Code-Datei kompiliert nicht!");
					alert.showAndWait();
				}
				else if (numberOfFailedTests != 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Nicht alle Tests sind erfolgreich!");
					alert.showAndWait();
				}

            }
        });

		goToRed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				CompilationUnit cTest = new CompilationUnit(nameTestFile, textAreaR.getText(), true);
				CompilationUnit cCode = new CompilationUnit(nameFile, textAreaGB.getText(), false);
				JavaStringCompiler scTest = CompilerFactory.getCompiler(cCode, cTest);
				scTest.compileAndRunTests();
				CompilerResult cr = scTest.getCompilerResult();

				if (cr.hasCompileErrors() == false && numberOfFailedTests == 0) {

					a = 0;
					endRecordBlackTime();
					b = 0;
					endRecordRoundTime();
					TimerRed.start();
					TimerRound.start();

					textAreaR.setDisable(false);
					textAreaGB.setDisable(true);

					goToGreen.setDisable(false);
					backToRed.setDisable(true);
					goToBlack.setDisable(true);
					goToRed.setDisable(true);
					// das was bei Klick von GO TO RED passiert

					vBoxRed.setStyle("-fx-background-color: red");
					vBoxGB.setStyle("-fx-background-color: lightgrey");

					TimerBaby.start();

					textAreaR.setText(textAreaR.getText());
				}
				else if (cr.hasCompileErrors() == true) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Datei kompiliert nicht!");
					alert.showAndWait();
				}
				else if (numberOfFailedTests != 0) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Nicht alle Tests sind erfolgreich!");
					alert.showAndWait();
				}

			}
		});

		// Am Anfang ist man immer in Rot, daher nur goToGreen klickbar.
		goToGreen.setDisable(false);
		backToRed.setDisable(true);
		goToBlack.setDisable(true);
		goToRed.setDisable(true);

		subStage.show();

        subStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (b == 1) {
                    endRecordBlackTime();
                }
                if (a == 1){
                    TimerGreen.continueGreenTime();
                    endRecordGreenTime();
                }
                if (baby == true) endTimer();

                endRecordRedTime();
                endRecordRoundTime();


                chart();      // kann hier auch falsch platziert sein. Aufruf sollte dann dort geschehen wo es benötigt wird
            }
        });


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

    public static void changeTextArea(){
        if (textAreaR.isDisabled()==true){
            textAreaR.setDisable(false);
            textAreaGB.setDisable(true);
        }
        else {
            textAreaR.setDisable(true);
            textAreaGB.setDisable(false);
        }
    }

	public static String getOldText() {

		String oldText;

		if (textAreaR.isDisable() == false) {
			oldText = textAreaR.getText();
		}
		else {
			oldText = textAreaGB.getText();
		}
		return oldText;
	}

	public static void setOldText(String oldText) {

		if (textAreaR.isDisable() == false) {
			textAreaR.setText(oldText);
		}
		else {
			textAreaGB.setText(oldText);
		}
		TimerBaby.start();
	}
}
