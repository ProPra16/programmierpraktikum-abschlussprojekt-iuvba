package de.hhu.propra16;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

class TimerGreen{


    private static long time = 0;
    private static Label timelabel = new Label();

    private static Timeline timer;


    public static Label start() {


            timer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    time++;
                    timelabel.setText(String.format("%02d:%02d", time/60, time));
                    System.out.println(String.format("Green: " + "%02d:%02d", time/60, time));
                }
            }));

            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

        return timelabel;
    }


    public static void end() {
        timer.stop();
        time = 0;
    }

    public static long getGreenTime() {

        return time;

    }
    public static void pauseGreenTime(){
        timer.pause();
    }

    public static void continueGreenTime(){
        timer.jumpTo(Duration.seconds(time));
        timer.play();
    }
}

