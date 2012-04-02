package de.paluch.burndown;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Sprint Days Calculator. Omits SATURDAY and SUNDAY. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 19.03.2012 <br>
 * <br>
 */
public class SprintDaysGenerator {

    private final boolean regularFreeDays[] = new boolean[Calendar.SATURDAY + 1];

    public SprintDaysGenerator() {

        regularFreeDays[Calendar.SUNDAY] = true;
        regularFreeDays[Calendar.SATURDAY] = true;
    }

    /**
     * @param count
     * @param firstDay
     * @return List<Date>
     */
    public List<Date> generateSprintDays(int count, Date firstDay) {

        int maxIterations = count * 14;
        int iteration = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDay);

        List<Date> result = new ArrayList<Date>();
        while (result.size() < count) {
            if (iteration++ > maxIterations) {
                throw new IllegalStateException("Cannot find requested amount of days, toFind: " + count + ", found: "
                        + result.size());
            }

            if (!regularFreeDays[cal.get(Calendar.DAY_OF_WEEK)]) {
                result.add(cal.getTime());
            }
            cal.add(Calendar.DATE, 1);
        }

        return Collections.unmodifiableList(result);
    }

}
