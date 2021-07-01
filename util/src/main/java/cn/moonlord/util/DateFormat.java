package cn.moonlord.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateFormat {

    public static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd";

    public static final String ISO_8601_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_TIMEZONE_X = ISO_8601_DATE_TIME_FORMAT + "X";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_TIMEZONE_ZZZ = ISO_8601_DATE_TIME_FORMAT + "ZZZ";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_TIMEZONE_XXX = ISO_8601_DATE_TIME_FORMAT + "XXX";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_X = ISO_8601_DATE_TIME_FORMAT_WITH_MS + "X";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_ZZZ = ISO_8601_DATE_TIME_FORMAT_WITH_MS + "ZZZ";

    public static final String ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_XXX = ISO_8601_DATE_TIME_FORMAT_WITH_MS + "XXX";

    public static final String JAVA_SQL_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.fffffffff";

    public static final String SPRING_LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static SimpleDateFormat formateISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private final static SimpleDateFormat formateHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat formateHMS1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private final static SimpleDateFormat formateHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat formateH = new SimpleDateFormat("yyyy-MM-dd HH");
    private final static SimpleDateFormat formateDate = new SimpleDateFormat("yyyy-MM-dd");

    public static final String DEFAULT_DATE_FORMAT1 = "dd-MM-yyyy";

    public static final String SIMPLE_FORMAT = "yyyyMMddHHmmss";

    public static final List<String> SUPPORTED_DATE_FORMATS = Arrays.asList(
            ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_XXX,
            ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_ZZZ,
            ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_X,
            ISO_8601_DATE_TIME_FORMAT_WITH_TIMEZONE_XXX,
            ISO_8601_DATE_TIME_FORMAT_WITH_TIMEZONE_ZZZ,
            ISO_8601_DATE_TIME_FORMAT_WITH_TIMEZONE_X,
            ISO_8601_DATE_TIME_FORMAT_WITH_MS,
            ISO_8601_DATE_TIME_FORMAT,
            ISO_8601_DATE_FORMAT
    );

    public static Date parse(String sourceString) {
        if(sourceString == null) {
            throw new IllegalArgumentException("DateFormat parse error, sourceString must not be null");
        }
        if(sourceString.length() == 0) {
            throw new IllegalArgumentException("DateFormat parse error, sourceString must not be empty");
        }
        for (String supportedDateFormat : SUPPORTED_DATE_FORMATS) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(supportedDateFormat, Locale.ROOT);
                return dateFormat.parse(sourceString);
            } catch (Exception ignored) { ignored.printStackTrace(); }
        }
        throw new IllegalArgumentException("DateFormat parse error, the sourceString [ " + sourceString + " ] is not in supported date formats");
    }

    public static String format(Date sourceDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_8601_DATE_TIME_FORMAT_WITH_MS_AND_TIMEZONE_XXX, Locale.ROOT);
        return dateFormat.format(sourceDate);
    }

}
