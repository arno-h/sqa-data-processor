import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class DataProcessing {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException, ParseException {
        URL pathURL = DataProcessing.class.getResource("data.txt");
        if (pathURL == null) {
            return;
        }
        File data = new File(pathURL.toURI());
        Scanner scanner = new Scanner(data);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String previousActivity = null;
        Date previousDate = null;
        HashMap<String, Duration> result = new HashMap<>();
        while (scanner.hasNext()) {
            String date = scanner.next("\\d+-\\d+-\\d+");
            String time = scanner.next("\\d+:\\d+");
            Date dateTime = dateFormat.parse(date + " " + time);
            String activity = scanner.nextLine().trim();
            if (previousDate != null) {
                Duration duration = Duration.between(previousDate.toInstant(), dateTime.toInstant());
                if (!result.containsKey(previousActivity)) {
                    result.put(previousActivity, duration);
                } else {
                    result.merge(previousActivity, duration, Duration::plus);
                }
            }
            if ("*".equals(activity)) {
                previousDate = null;
            } else {
                previousDate = dateTime;
                previousActivity = activity;
            }
        }

        for (String activity : result.keySet()) {
            System.out.printf("Activity %s took %d minutes.\n", activity, result.get(activity).toMinutes());
        }
    }
}
