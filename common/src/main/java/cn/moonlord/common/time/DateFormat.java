package cn.moonlord.common.time;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormat {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final String JAVA_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

    public static final List<String> SUPPORTED_DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", // UTC_DATE_FORMAT

            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSSXX",
            "yyyy-MM-dd'T'HH:mm:ss.SSSX",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",

            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ssXX",
            "yyyy-MM-dd'T'HH:mm:ssX",
            "yyyy-MM-dd'T'HH:mm:ss",

            "yyyy-MM-dd HH:mm:ss.SSSXXX",
            "yyyy-MM-dd HH:mm:ss.SSSXX",
            "yyyy-MM-dd HH:mm:ss.SSSX",
            "yyyy-MM-dd HH:mm:ss.SSS",

            "yyyy-MM-dd HH:mm:ssXXX",
            "yyyy-MM-dd HH:mm:ssXX",
            "yyyy-MM-dd HH:mm:ssX",
            "yyyy-MM-dd HH:mm:ss", // DEFAULT_DATE_FORMAT

            "yyyy-MM-dd HH:mmXXX",
            "yyyy-MM-dd HH:mmXX",
            "yyyy-MM-dd HH:mmX",
            "yyyy-MM-dd HH:mm",

            "yyyy-MM-dd HHXXX",
            "yyyy-MM-dd HHXX",
            "yyyy-MM-dd HHX",
            "yyyy-MM-dd HH",

            "yyyy-MM-dd", // SIMPLE_DATE_FORMAT
            "yyyy/MM/dd",
            "yyyy.MM.dd",

            "yyyyMMddHHmmssSSS",
            "yyyyMMddHHmmss",
            "yyyyMMddHHmm",
            "yyyyMMddHH",
            "yyyyMMdd",

            "EEE MMM dd HH:mm:ss zzz yyyy" // JAVA_DATE_FORMAT
    );

    public static Date parse(String sourceString) {
        if (sourceString == null) {
            throw new IllegalArgumentException("DateFormat parse error, sourceString must not be null");
        }
        if (sourceString.length() == 0) {
            throw new IllegalArgumentException("DateFormat parse error, sourceString must not be empty");
        }
        for (String supportedDateFormat : SUPPORTED_DATE_FORMATS) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(supportedDateFormat, Locale.ROOT);
                if (supportedDateFormat.equals(UTC_DATE_FORMAT)) {
                    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                }
                return dateFormat.parse(sourceString);
            } catch (Exception ignored) {
            }
        }
        throw new IllegalArgumentException("DateFormat parse error, the sourceString [ " + sourceString + " ] is not in supported date formats");
    }

    public static String format(Date sourceDate, String dateFormatPattern) {
        if (sourceDate == null) {
            throw new IllegalArgumentException("DateFormat format error, sourceDate must not be null");
        }
        if (dateFormatPattern == null) {
            throw new IllegalArgumentException("DateFormat format error, dateFormatPattern must not be null");
        }
        if (dateFormatPattern.length() == 0) {
            throw new IllegalArgumentException("DateFormat format error, dateFormatPattern must not be empty");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.ROOT);
        if (dateFormatPattern.equals(UTC_DATE_FORMAT)) {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
        return dateFormat.format(sourceDate);
    }

    public static String format(Date sourceDate) {
        return format(sourceDate, DEFAULT_DATE_FORMAT);
    }

    public static String formatUTC(Date sourceDate) {
        return format(sourceDate, UTC_DATE_FORMAT);
    }

    public static String formatJava(Date sourceDate) {
        return format(sourceDate, JAVA_DATE_FORMAT);
    }

    public static String formatSimple(Date sourceDate) {
        return format(sourceDate, SIMPLE_DATE_FORMAT);
    }

}
