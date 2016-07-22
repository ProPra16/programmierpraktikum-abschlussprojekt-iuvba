package de.hhu.propra16;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by paul on 22.07.16.
 */
public class Chart{

    private static ArrayList<Long> timePerRound = new ArrayList<Long>();

    public static void chart() {


        timePerRound = TimeTracking.getAllTimesPerRound();


        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Runden");

        yAxis.setLabel("Zeit (ms)");

        XYChart.Series bar = new XYChart.Series();


        for (int i = 1; i< timePerRound.size(); i++) {          // hier wird bei 1 angefangen, da die erste Stelle in der Liste durch TimeTracking mit 0 befüllt wird.
            bar.setName("Runden");
            bar.getData().add(new XYChart.Data("Runde: " + i, timePerRound.get(i)));
        }

        Scene scene  = new Scene(bc,800,600);
        bc.getData().add(bar);
        stage.setScene(scene);
        stage.show();
    }

}
