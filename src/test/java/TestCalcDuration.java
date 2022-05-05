import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCalcDuration {
    @Test
    public void testNoHoliday() {
        // setup
        Holidays holidayMock = mock(Holidays.class);
        when(holidayMock.isHoliday(any())).thenReturn(false);
        Date previous = new Date(2022-1900, Calendar.MAY, 3, 13, 45);
        Date current = new Date(2022-1900, Calendar.MAY, 3, 14, 15);
        DataProcessor dp = new DataProcessor(holidayMock);
        // execute
        Duration result = dp.calcDuration(previous, current);
        // verify
        Assert.assertEquals(Duration.ofMinutes(30), result);
    }

    @Test
    public void testIsHoliday() {
        // setup
        Holidays holidayMock = mock(Holidays.class);
        when(holidayMock.isHoliday(any())).thenReturn(true);
        Date previous = new Date(2022-1900, Calendar.MAY, 3, 13, 45);
        Date current = new Date(2022-1900, Calendar.MAY, 3, 14, 15);
        DataProcessor dp = new DataProcessor(holidayMock);
        // execute
        Duration result = dp.calcDuration(previous, current);
        // verify
        Assert.assertEquals(Duration.ofMinutes(60), result);
    }
}
