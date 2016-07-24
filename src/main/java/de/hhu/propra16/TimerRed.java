package de.hhu.propra16;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by paul on 24.07.16.
 */
class TimerRed {
    private static Timeline timer;
    private static boolean running = false;
    private static long time;
    private static Label timelabel = new Label();


    public static Label start() {

            timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                        time++;
                    timelabel.setText(String.format("%02d:%02d", time/60, time));
                    System.out.println(String.format("Red: " + "%02d:%02d", time/60, time));
                }
            }));
        if (!running) {
            running = true;
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        }
        return timelabel;

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
        timer.jumpTo(Duration.seconds(time));
        timer.play();
    }
}
