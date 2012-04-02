package de.paluch.burndown.sync.jira;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Compare two dates on Day-Base. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class DateDayComparator implements Comparator<Date> {

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Date one, Date other) {

        Calendar calOne = Calendar.getInstance();
        Calendar calOther = Calendar.getInstance();

        calOne.setTime(one);
        calOther.setTime(other);

        boolean year = calOne.get(Calendar.YEAR) == calOther.get(Calendar.YEAR);
        boolean month = calOne.get(Calendar.MONTH) == calOther.get(Calendar.MONTH);
        boolean day = calOne.get(Calendar.DATE) == calOther.get(Calendar.DATE);

        if (year && month && day) {
            return 0;
        }

        return one.compareTo(other);
    }

}
