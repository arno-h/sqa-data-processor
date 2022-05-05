import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.Duration;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestProcess {
    Holidays holidaysMock;
    DataProcessor dp;

    @Before
    public void setUp() {
        holidaysMock = mock(Holidays.class);
        dp = new DataProcessor(holidaysMock);
    }

    @Test
    public void testProcess() throws ParseException {
        when(holidaysMock.isHoliday(any())).thenReturn(false);
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
        when(holidaysMock.isHoliday(any())).thenReturn(false).thenReturn(true).thenReturn(false);
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

    // old style of checking for exception
    @Test(expected = InputMismatchException.class)
    public void testProcess3() throws ParseException {
        Scanner scanner = new Scanner("2022-01-01 WRONG FORMAT!");
        // should throw an exception
        dp.process(scanner);
    }

    // new style of checking for exception
    @Test
    public void testProcess4() {
        Scanner scanner = new Scanner("2022-01-01 WRONG FORMAT!");
        assertThrows(InputMismatchException.class, () -> dp.process(scanner));
    }

    @Test
    public void testProcess5() throws ParseException {
        when(holidaysMock.isHoliday(any())).thenReturn(false);
        // if there's an entry without end-time, it is not stored
        Scanner scanner = new Scanner("2022-01-01 10:22    A\n");
        dp.process(scanner);
        HashMap<String, Duration> result = dp.getResult();
        assertTrue(result.isEmpty());
    }
}
