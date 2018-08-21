package utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String YEAR_TO_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_TO_MINSECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public static int interval(String first, String last) {
        int inter = 0;
        Date dt_first = stringToDate(first, "yyyy-MM-dd");
        Date dt_last = stringToDate(last, "yyyy-MM-dd");
        inter = (int) ((dt_last.getTime() - dt_first.getTime()) / (3600 * 24000));
        return inter;
    }

    public static String dateToString(Date dt, String strFormat) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
        String str = "";
        try {
            str = sdFormat.format(dt);
        } catch (Exception e) {
            return "";
        }
        if (str.equals("1900-01-01 00:00")) {
            str = "";
        }
        return str;
    }

    public static Date stringToDate(String str, String strFormat) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
        Date date = null;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            date = sdFormat.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * 增加（减少）日
     *
     * @param date 日期
     * @param num  日值（可以为负数）
     * @return
     */
    public static Date addDate(Date date, int num) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);

        // 开始日期
        c.add(Calendar.DATE, num);

        return c.getTime();
    }


    public static Date getBeforeMonthToday(Date sourDate, int months, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourDate);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date datenew = calendar.getTime();
        return datenew;

    }


    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonths(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
                flag = 1;

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12
                        + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }


    /**
     * 根据输入的日期字符串 和 相隔天数 ，* 获得 指定日期提后几天的日期*
     *
     * @param dateString
     * @param afterDays
     * @return 指定日期后相隔天数后的日期
     * @throws ParseException
     * @author zcp
     */
    public static String getDate(String dateString, int afterDays) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = dateFormat.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear + afterDays);
        return dateFormat.format(cal.getTime());
    }


    /**
     * 获取日期月份
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getMonth(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return (calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期号
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static int getDay(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 增加（减少）月
     *
     * @param date 日期
     * @param num  月份值（可以为负数）
     * @return
     */
    public static Date addMonth(Date date, int num) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);

        // 开始日期
        c.add(Calendar.MONTH, num);
        return c.getTime();
    }

    /**
     * 判断日期大小
     *
     * @param srcDate 应种日期
     * @param comDate 基准日
     * @return
     */
    public static boolean compareDate(Date srcDate, Date comDate) {
        return srcDate.getTime() > comDate.getTime();
    }

    /**
     * 判断日期大小
     *
     * @param srcDate 应种日期
     * @param comDate 基准日
     * @return
     */
    public static boolean compareDate(Date srcDate, Date comDate, String formart) {
        Date dt_first = stringToDate(dateToString(srcDate, DATE_FORMAT), "yyyy-MM-dd");
        Date dt_last = stringToDate(dateToString(comDate, DATE_FORMAT), "yyyy-MM-dd");
        return dt_first.getTime() > dt_last.getTime();
    }

    /**
     * 由出生日期获得年龄
     *
     * @param birthDay
     * @return
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static Date getDateOffset(Date date, int intervalValue, int unit) {
        Date offsetDate = null;
        if (intervalValue == 0) {
            offsetDate = date;
        } else {
            switch (unit) {
                case 1:
                    offsetDate = DateUtil.getBeforeMonthToday(date, 0, intervalValue);
                    break;
                case 2:
                    offsetDate = DateUtil.getBeforeMonthToday(date, 0, intervalValue * 7);
                    break;
                case 3:
                    offsetDate = DateUtil.getBeforeMonthToday(date, intervalValue, 0);
                    break;
                case 4:
                    offsetDate = DateUtil.getBeforeMonthToday(date, intervalValue * 3, 0);
                    break;
                case 5:
                    offsetDate = DateUtil.getBeforeMonthToday(date, intervalValue * 12, 0);
                    break;
                default:
                    break;
            }
        }
        return offsetDate;
    }


    public static Date formatDate(Date date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            String dateStr = dateFormat.format(date);
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getCurrentDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
