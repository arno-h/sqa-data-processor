import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;

public class TestSumDuration {
    @Test
    public void singleAction() throws ParseException {
        // given
        DataProcessor dataProcessor = new DataProcessor();
        String testData =
                "2023-03-24 11:30  Programmiert\n" +
                "2023-03-24 12:30  *";
        InputStream testStream = new ByteArrayInputStream(testData.getBytes());

        // when
        HashMap<String, Duration> result = dataProcessor.process(testStream);

        // then
        Assert.assertEquals(60, result.get("Programmiert").toMinutes());
    }

    @Test
    public void singleActionTwice() throws ParseException {
        // given
        DataProcessor dataProcessor = new DataProcessor();
        String testData =
                "2023-03-24 11:30  Programmiert\n" +
                "2023-03-24 11:50  Programmiert\n" +
                "2023-03-24 12:30  *";
        InputStream testStream = new ByteArrayInputStream(testData.getBytes());

        // when
        HashMap<String, Duration> result = dataProcessor.process(testStream);

        // then
        Assert.assertEquals(60, result.get("Programmiert").toMinutes());
    }
}
