import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException, ParseException {
        URL pathURL = Main.class.getResource("data.txt");
        assert pathURL != null;
        File data = new File(pathURL.toURI());
        Scanner scanner = new Scanner(data);
        DataProcessor dataProcessor = new DataProcessor(new Holidays());
        dataProcessor.process(scanner);
        HashMap<String, Duration> result = dataProcessor.getResult();
        for (String activity : result.keySet()) {
            System.out.printf("Activity %s took %d minutes.\n", activity, result.get(activity).toMinutes());
        }
    }
}
