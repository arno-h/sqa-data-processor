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
    private final Holidays holidays = new Holidays();

    public HashMap<String, Duration> getResult() {
        return result;
    }

    HashMap<String, Duration> process(Scanner scanner) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String previousActivity = null;
        Date previousDate = null;
        while (scanner.hasNext()) {
            String date = scanner.next("\\d+-\\d+-\\d+");
            String time = scanner.next("\\d+:\\d+");
            Date dateTime = dateFormat.parse(date + " " + time);
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

    private Duration calcDuration(Date previous, Date current) {
        Duration duration = Duration.between(previous.toInstant(), current.toInstant());
        if (holidays.isHoliday(previous)) {
            return duration.multipliedBy(2);
        }
        return duration;
    }
}
