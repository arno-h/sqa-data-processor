import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;

public class TestCalcDuration {
    @Test
    public void noHoliday() throws ParseException {
        // given
        Holidays mockHolidays = Mockito.mock(Holidays.class);
        Mockito.when(mockHolidays.isHoliday(Mockito.any())).thenReturn(false);
        DataProcessor dataProcessor = new DataProcessor(mockHolidays);
        String data =
                "2023-03-24 10:00 Pause\n" +
                "2023-03-24 10:15 *";
        InputStream testStream = new ByteArrayInputStream(data.getBytes());
        // when
        HashMap<String, Duration> result = dataProcessor.process(testStream);
        // then
        Assert.assertEquals(15, result.get("Pause").toMinutes());
    }

    @Test
    public void isHoliday() throws ParseException {
        // given
        Holidays mockHolidays = Mockito.mock(Holidays.class);
        Mockito.when(mockHolidays.isHoliday(Mockito.any())).thenReturn(true);
        DataProcessor dataProcessor = new DataProcessor(mockHolidays);
        String data =
                "2023-03-24 10:00 Pause\n" +
                "2023-03-24 10:15 *";
        InputStream testStream = new ByteArrayInputStream(data.getBytes());
        // when
        HashMap<String, Duration> result = dataProcessor.process(testStream);
        // then
        Assert.assertEquals(30, result.get("Pause").toMinutes());
    }
}
