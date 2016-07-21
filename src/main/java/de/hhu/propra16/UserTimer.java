package de.hhu.propra16;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Created by paul on 21.07.16.
 */
public class UserTimer {

        private static Thread timeThread;
        private static boolean running = false;
        private static long time = 0;
        private static Label timelabel = new Label();


        public static Label start() {

            if (!running) {

                running = true;
                timeThread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        while (running) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    timelabel.setText(String.format("%02d:%02d", time / 60000, time / 1000 % 60));
                                }
                            });

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            time += 1000;
                        }
                    }
                });
                timeThread.setDaemon(true);
                timeThread.start();

            }
            return timelabel;
        }


        public static void end() {
            running = false;
            time = 0;

        }

        public static long getUserTime() {

            return time;

        }
}
