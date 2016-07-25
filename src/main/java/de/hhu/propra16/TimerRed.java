package de.hhu.propra16;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * Created by paul on 24.07.16.
 */
class TimerRed {
    private static Timeline timer;
    private static long time;



    public static void start() {

            timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                        time += 1000;

                    System.out.println(String.format("Red: " + "%02d:%02d", time/60000, time/1000 % 60));
                }
            }));

            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();


    }


    public static void end() {
        timer.stop();
        time = 0;
    }

    public static long getRedTime() {

        return time;

    }
    public static void pauseRedTime(){
        timer.pause();
    }

    public static void continueRedTime(){
        timer.play();
    }
}
