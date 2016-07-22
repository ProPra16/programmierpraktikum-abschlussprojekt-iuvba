package de.hhu.propra16;

import java.util.ArrayList;

import static de.hhu.propra16.RoundTime.getRoundTime;

/**
 * Created by paul on 21.07.16.
 */

public class TimeTracking {

private static ArrayList<Long> timePerRound = new ArrayList<Long>();

    public static void startRecordTimeTracking(){

        RoundTime.start();

    }
    public static void endRecordTimeTracking(){

        long usedTime = getRoundTime();
        timePerRound.add(usedTime);

        RoundTime.end();
    }

    public static ArrayList getAllTimesPerRound(){
        return timePerRound;
    }
}
