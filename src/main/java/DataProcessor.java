import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class DataProcessor {
    private final HashMap<String, Duration> result = new HashMap<>();
    private final Holidays holidays = new Holidays();

    HashMap<String, Duration> process(InputStream data) throws ParseException {
        Scanner scanner = new Scanner(data);
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

    private void sumDuration(String activity, Date previous, Date current) {
        if (previous != null) {
            Duration duration = calcDuration(previous, current);
            if (!result.containsKey(activity)) {
                result.put(activity, duration);
            } else {
                result.merge(activity, duration, Duration::plus);
            }
        }
    }

    private Duration calcDuration(Date previous, Date current) {
        Duration duration = Duration.between(previous.toInstant(), current.toInstant());
        if (holidays.isHoliday(previous)) {
            return duration.multipliedBy(2);
        }
        return duration;
    }
}
