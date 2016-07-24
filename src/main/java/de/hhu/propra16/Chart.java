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
    private static ArrayList<Long> timeInGreen = new ArrayList<Long>();
    private static ArrayList<Long> timeInBlack = new ArrayList<Long>();
    private static ArrayList<Long> timeInRed = new ArrayList<Long>();

    public static void chart() {


        timePerRound = TimeTracking.getAllTimesPerRound();
        timeInRed = TimeTracking.getAllTimesInRed();
        timeInGreen = TimeTracking.getAllTimesInGreen();
        timeInBlack = TimeTracking.getAllTimesInBlack();



        Stage stage = new Stage();
        stage.setTitle("Chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Runden");

        yAxis.setLabel("Zeit (in s)");

        XYChart.Series bar1 = new XYChart.Series();
        XYChart.Series bar2 = new XYChart.Series();
        XYChart.Series bar3 = new XYChart.Series();
        XYChart.Series bar4 = new XYChart.Series();



        for (int i = 0; i< timePerRound.size(); i++) {          // hier wird bei 1 angefangen, da die erste Stelle in der Liste durch TimeTracking mit 0 befüllt wird.
            bar1.setName("Rundendauer");
            bar1.getData().add(new XYChart.Data("Runde " + (i+1), timePerRound.get(i)));
        }
        for (int i = 0; i< timeInRed.size()-1; i++) {
            bar2.setName("writing failure test");
            bar2.getData().add(new XYChart.Data("Runde: " + (i+1), timeInRed.get(i)));
        }
        for (int i = 0; i< timeInGreen.size()-1; i++) {
            bar3.setName("passing test");
            bar3.getData().add(new XYChart.Data("Runde: " + (i+1), timeInGreen.get(i)));
        }
        for (int i = 0; i< timeInBlack.size(); i++) {
            bar4.setName("refactoring");
            bar4.getData().add(new XYChart.Data("Runde: " + (i+1), timeInBlack.get(i)));
        }


        Scene scene  = new Scene(bc,800,600);
        bc.getData().add(bar1);
        bc.getData().add(bar2);
        bc.getData().add(bar3);
        bc.getData().add(bar4);
        stage.setScene(scene);
        stage.show();
    }

}
