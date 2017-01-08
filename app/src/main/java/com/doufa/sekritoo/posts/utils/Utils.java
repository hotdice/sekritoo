package com.doufa.sekritoo.posts.utils;

import java.util.Date;

/**
 * Created by rayen on 12/12/16.
 */

public class Utils {

    public static String CalPostTime(long date) {

        long diff = System.currentTimeMillis() - date;

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);


        if (diffDays > 3)
            return new Date(date) + " ";
        else if (diffDays != 0)
            return diffDays + " days";
        else if (diffHours != 0)
            return diffHours + " hours";
        else if (diffMinutes != 0)
            return diffMinutes + " minutes";
        else return diffSeconds + " seconds";

    }
}
