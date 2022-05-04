import org.junit.Test;

import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestProcess {
    @Test
    public void testProcess() throws ParseException {
        Holidays holidaysMock = mock(Holidays.class);
        when(holidaysMock.isHoliday(any())).thenReturn(false);
        DataProcessor dp = new DataProcessor(holidaysMock);
        Scanner scanner = new Scanner("2022-01-01 10:22    A\n" +
                "2022-01-01 11:22    B\n" +
                "2022-01-01 11:52    *\n" +
                "2022-01-03 10:00    B\n" +
                "2022-01-03 11:30    *\n");
        dp.process(scanner);
        HashMap<String, Duration> result = dp.getResult();
        assertEquals(Duration.ofMinutes(60), result.get("A"));
        assertEquals(Duration.ofMinutes(120), result.get("B"));
    }

    @Test
    public void testProcess2() throws ParseException {
        Holidays holidaysMock = mock(Holidays.class);
        when(holidaysMock.isHoliday(any())).thenReturn(false).thenReturn(true).thenReturn(false);
        DataProcessor dp = new DataProcessor(holidaysMock);
        Scanner scanner = new Scanner("2022-01-01 10:22    A\n" +
                "2022-01-01 11:22    B\n" +
                "2022-01-01 11:52    *\n" +
                "2022-01-03 10:00    B\n" +
                "2022-01-03 11:30    *\n");
        dp.process(scanner);
        HashMap<String, Duration> result = dp.getResult();
        assertEquals(Duration.ofMinutes(60), result.get("A"));
        assertEquals(Duration.ofMinutes(150), result.get("B"));
    }
}
