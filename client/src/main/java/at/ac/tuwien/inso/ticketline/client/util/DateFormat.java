package at.ac.tuwien.inso.ticketline.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateFormat {

    private static String DATE_FORMAT = "dd.MM.yyyy";
    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    private static String DATE_FORMAT2 = "dd.MM.yyyy HH:mm";
    private static SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT2);

    public static String getDateFormat(Date date) {
        return sdf.format(date);
    }

    public static String getDateFormatWithTime(Date date) {
        return sdf2.format(date);
    }

}
