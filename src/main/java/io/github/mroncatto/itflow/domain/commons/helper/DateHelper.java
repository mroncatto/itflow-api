package io.github.mroncatto.itflow.domain.commons.helper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    private DateHelper() {}

    public static Date currentDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Date increaseDate(int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date increaseDate(Date date, int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }


}
