package de.hhu.propra16;

import javafx.application.Platform;
import javafx.scene.control.Label;


public class TimerBaby {

    private static Label timelabel = new Label();
    private static Thread timeThread;
    private static boolean running = false;
    private static long time = 120000;

    public static Label start() {

        if (!running) {

            running = true;
            timeThread = new Thread(() -> {

                while (running) {

                    Platform.runLater(() -> timelabel.setText(String.format("%02d:%02d", time/60000, time/1000%60)));

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time -= 1000;
                }
            });
            timeThread.setDaemon(true);
            timeThread.start();

        }
        return timelabel;
    }

    public static void end() {
        running = false;
        time = 120000;
    }

    public static long getTime() {
        return time;
    }
}
