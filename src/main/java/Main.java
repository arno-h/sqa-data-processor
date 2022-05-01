import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException, ParseException {
        URL pathURL = Main.class.getResource("data.txt");
        assert pathURL != null;
        File data = new File(pathURL.toURI());
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.process(data);
    }
}
