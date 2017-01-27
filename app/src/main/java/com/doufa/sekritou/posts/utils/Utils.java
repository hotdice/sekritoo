package com.doufa.sekritou.posts.utils;

import java.text.DateFormat;

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
            return DateFormat.getDateTimeInstance().format(date);
        else if (diffDays != 0){
            if(diffDays == 1)
                return diffDays + " day ago";
            else
                return diffDays + " days ago";
        }

        else if (diffHours != 0){
            if(diffHours==1)
            return diffHours + " hour ago";
            else
                return diffHours + " hours ago";
        }

        else if (diffMinutes != 0){
            if(diffMinutes ==1)
                return diffMinutes + " minute ago";
            else
                return diffMinutes + " minutes ago";
        }


        else return diffSeconds + " seconds ago";

    }
}
