import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException, ParseException {
        URL pathURL = Main.class.getResource("data.txt");
        assert pathURL != null;
        File data = new File(pathURL.toURI());
        FileInputStream dataStream = new FileInputStream(data);

        DataProcessor dataProcessor = new DataProcessor(new Holidays());
        HashMap<String, Duration> result = dataProcessor.process(dataStream);

        for (String activity : result.keySet()) {
            System.out.printf("Activity %s took %d minutes.\n", activity, result.get(activity).toMinutes());
        }
    }
}
