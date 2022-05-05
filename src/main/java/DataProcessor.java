import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class DataProcessor {
    private final HashMap<String, Duration> result = new HashMap<>();
    private final Holidays holidays;

    public HashMap<String, Duration> getResult() {
        return result;
    }

    public DataProcessor(Holidays holidays) {
        this.holidays = holidays;
    }

    HashMap<String, Duration> process(Scanner scanner) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String previousActivity = null;
        Date previousDate = null;
        while (scanner.hasNext()) {
            Date dateTime = getDate(scanner, dateFormat);
            String activity = scanner.nextLine().trim();
            sumDuration(previousActivity, previousDate, dateTime);
            if ("*".equals(activity)) {
                previousDate = null;
            } else {
                previousDate = dateTime;
                previousActivity = activity;
            }
        }
        return result;
    }

    private Date getDate(Scanner scanner, SimpleDateFormat dateFormat) throws ParseException {
        String date = scanner.next("\\d+-\\d+-\\d+");
        String time = scanner.next("\\d+:\\d+");
        Date dateTime = dateFormat.parse(date + " " + time);
        return dateTime;
    }

    void sumDuration(String activity, Date previous, Date current) {
        if (previous != null) {
            // A
            Duration duration = calcDuration(previous, current);
            if (!result.containsKey(activity)) {
                // B
                result.put(activity, duration);
            } else {
                // C
                result.merge(activity, duration, Duration::plus);
            }
        } // else D

        // 100% path coverage
        // done: D
        // done: A -> B
        // done: A -> C
    }

    Duration calcDuration(Date previous, Date current) {
        Duration duration = Duration.between(previous.toInstant(), current.toInstant());
        if (holidays.isHoliday(previous)) {
            return duration.multipliedBy(2);
        }
        return duration;
    }
}
