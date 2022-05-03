import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestWithMock {
    @Test
    public void testSum() {
        Holidays holidaysMock = mock(Holidays.class);
        when(holidaysMock.isHoliday(any())).thenReturn(false);
        DataProcessor dp = new DataProcessor(holidaysMock);
        Date prev = new Date(122, Calendar.MARCH, 1, 12, 55);
        Date cur = new Date(122, Calendar.MARCH, 1, 13, 15);
        dp.sumDuration("abc", prev, cur);
        Assert.assertEquals(Duration.ofMinutes(20), dp.getResult().get("abc"));
    }

    @Test
    public void testSumHoliday() {
        Holidays holidaysMock = mock(Holidays.class);
        when(holidaysMock.isHoliday(any())).thenReturn(true);
        DataProcessor dp = new DataProcessor(holidaysMock);
        Date prev = new Date(122, Calendar.MARCH, 1, 12, 55);
        Date cur = new Date(122, Calendar.MARCH, 1, 13, 15);
        dp.sumDuration("abc", prev, cur);
        Assert.assertEquals(Duration.ofMinutes(40), dp.getResult().get("abc"));
    }
}
