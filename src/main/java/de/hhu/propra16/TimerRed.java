package de.hhu.propra16;

import javafx.scene.control.Label;

/**
 * Created by paul on 24.07.16.
 */
class TimerRed {
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

                        timelabel.setText(String.format("%02d:%02d", time / 60000, time / 1000 % 60));
                        System.out.println(String.format("Red" + "%02d:%02d", time / 60000, time / 1000 % 60));


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

    public static long getRedTime() {

        return time;

    }
}
