package de.hhu.propra16;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class TimerBaby {

    private static Label timelabel = new Label();
    private static Timeline timer;

    private static long time = 120000;
    private static boolean running = true;

        public static Label start() {

            String oldText = FensterController.getOldText();

            running = true;

            timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    if (time <= 0) {
                        endTimer();
                        FensterController.setOldText(oldText);
                        String oldText = FensterController.getOldText();

                        //FensterController.changeTextArea();
                    }

                    time -= 1000;
                    timelabel.setText(String.format("%02d:%02d", time / 60000, time / 1000 % 60));

                }
            }));

            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

        return timelabel;
    }

    public static void endTimer() {
        timer.stop();
        time = 120000;
    }

    public static long getTime() {

        return time;
    }

    public static Label timerOff() {

        endTimer();
        timelabel.setText("TIMER OFF");
        return timelabel;
    }

    public static boolean checkTime(){
        if(time <= 0) {
            running = false;
            timer.stop();
        }
        return running;

    }
}
