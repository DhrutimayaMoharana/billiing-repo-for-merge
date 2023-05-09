package erp.billing.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static Date dateAfterAddingDays(Date date, int days) {

		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}

	public static Date dateAfterAddingHours(Date date, int hours) {

		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hours);
		return c.getTime();
	}

	public static Date dateWithoutTime(Date date) {

		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date nextDateWithoutTime(Date date) {

		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}

	public static Date previousDateWithoutTime(Date date) {

		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	public static Date[] getSameDateTomorrowDateWithoutTime(Date date) {

		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		Date fromDate = new GregorianCalendar(year, month, day).getTime();
		Date toDate = null;
		if (month == 11 && day == 31)
			toDate = new GregorianCalendar(year + 1, 0, 1).getTime();
		else
			toDate = new GregorianCalendar(year, month, day + 1).getTime();

		Date[] dates = { fromDate, toDate };
		return dates;
	}

	public static Date[] getSameDatePreviousDateWithoutTime(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		Date toDate = new GregorianCalendar(year, month, day).getTime();
		Date fromDate = null;
		if (month == 0 && day == 1)
			fromDate = new GregorianCalendar(year - 1, 11, 31).getTime();
		else
			fromDate = new GregorianCalendar(year, month, day - 1).getTime();

		Date[] dates = { fromDate, toDate };
		return dates;
	}

	public static Date[] getTomorrowDateNextDateWithoutTime(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		Date fromDate = null;
		if (month == 11 && day == 31)
			fromDate = new GregorianCalendar(year + 1, 0, 1).getTime();
		else
			fromDate = new GregorianCalendar(year, month, day + 1).getTime();
		Date toDate = nextDateWithoutTime(fromDate);
		Date[] dates = { fromDate, toDate };
		return dates;
	}

	public static Long getDiffYears(Date first, Date last) {
		Long diffInMilliSec = last.getTime() - first.getTime();

		Long years = (diffInMilliSec / (10000 * 60 * 60 * 24 * 365));
		return years;
	}

	public static boolean fromSameMonthAndYear(Date fromDate, Date toDate) {

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(fromDate);
		cal2.setTime(toDate);
		return cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

	}

	public static String getDayMonthYear(Date date, boolean isDayRequired) {

		String stringDate = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate currentDate = LocalDate.parse(df.format(date));
		int day = currentDate.getDayOfMonth();
		Month month = currentDate.getMonth();
		int year = currentDate.getYear();
		if (isDayRequired) {
			stringDate = day + "/" + month + "/" + year;
		} else {
			stringDate = month + "-" + year;
		}
		return stringDate;
	}

	public static Integer getNoOfDaysInTheCurrentMonth(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date dateAfterAddingMinutes(Date date, int minutes) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);
		return c.getTime();
	}

	public static Date dateAfterSubstractingMinutes(Date date, int minutes) {
		if (date == null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -minutes);
		return c.getTime();
	}

}
