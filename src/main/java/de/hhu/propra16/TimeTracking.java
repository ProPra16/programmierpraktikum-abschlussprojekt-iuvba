package de.hhu.propra16;

import java.util.ArrayList;

import static de.hhu.propra16.TimerBlack.getBlackTime;
import static de.hhu.propra16.TimerGreen.getGreenTime;
import static de.hhu.propra16.TimerRed.getRedTime;
import static de.hhu.propra16.TimerRound.getRoundTime;

/**
 * Created by paul on 21.07.16.
 */

public class TimeTracking {

    private static ArrayList<Long> timePerRound = new ArrayList<Long>();
    private static ArrayList<Long> timeInGreen = new ArrayList<Long>();
    private static ArrayList<Long> timeInBlack = new ArrayList<Long>();
    private static ArrayList<Long> timeInRed = new ArrayList<Long>();

    public static void startRecordRoundTime() {

        TimerRound.start();

    }
    public static void startRecordGreenTime() {

        TimerGreen.start();

    }
    public static void startRecordBlackTime() {

        TimerBlack.start();

    }
    public static void startRecordRedTime() {

        TimerRed.start();

    }

    public static void endRecordRoundTime() {

        long usedTime = getRoundTime();
        timePerRound.add(usedTime);

        TimerRound.end();
    }

    public static void endRecordGreenTime() {

        long usedTime = getGreenTime();
        timeInGreen.add(usedTime);

        TimerGreen.end();
    }

    public static void endRecordBlackTime() {

        long usedTime = getBlackTime();
        timeInBlack.add(usedTime);

        TimerBlack.end();
    }

    public static void endRecordRedTime() {

        long usedTime = getRedTime();
        timeInRed.add(usedTime);

        TimerRed.end();
    }
    public static void endAllTimer(){
        TimerBlack.end();
        TimerRed.end();
        TimerGreen.end();
        TimerRound.end();
    }

    public static ArrayList getAllTimesPerRound() {
        return timePerRound;
    }

    public static ArrayList getAllTimesInGreen() {
        return timeInGreen;
    }

    public static ArrayList getAllTimesInBlack()  { return timeInBlack; }

    public static ArrayList getAllTimesInRed()  { return timeInRed; }
}
