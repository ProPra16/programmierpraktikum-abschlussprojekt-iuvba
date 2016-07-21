package de.hhu.propra16;

import java.util.ArrayList;

import static de.hhu.propra16.UserTimer.getUserTime;

/**
 * Created by paul on 21.07.16.
 */

public class TimeTracking {

private static ArrayList<Long> timePerRound = new ArrayList<Long>();

    public static void timeTracking (){

        long usedTime = getUserTime();

        timePerRound.add(usedTime);

    }

    public static ArrayList getTimePerRound(){
        return timePerRound;
    }
}
