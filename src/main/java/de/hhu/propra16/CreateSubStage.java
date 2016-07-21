package de.hhu.propra16;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by lorcan on 21.07.16.
 */
public class CreateSubStage {

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
}
