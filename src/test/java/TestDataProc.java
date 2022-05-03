import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

public class TestDataProc {
    @Test
    public void testSum1() {
        DataProcessor dp = new DataProcessor(new Holidays());
        Date prev = new Date(122, Calendar.MARCH, 1, 12, 55);
        Date cur = new Date(122, Calendar.MARCH, 1, 13, 15);
        dp.sumDuration("abc", prev, cur);
        Assert.assertEquals(Duration.ofMinutes(20), dp.getResult().get("abc"));
    }

    @Test
    public void testSum2() {
        DataProcessor dp = new DataProcessor(new Holidays());
        Date prev = new Date(122, Calendar.MARCH, 1, 12, 55);
        Date cur = new Date(122, Calendar.MARCH, 1, 13, 15);
        Date next = new Date(122, Calendar.MARCH, 1, 13, 40);
        dp.sumDuration("abc", prev, cur);
        dp.sumDuration("abc", cur, next);
        Assert.assertEquals(Duration.ofMinutes(20+25), dp.getResult().get("abc"));
    }

    @Test
    public void testSum3() {
        DataProcessor dp = new DataProcessor(new Holidays());
        Date cur = new Date(122, Calendar.MARCH, 1, 12, 55);
        dp.sumDuration("abc", null, cur);
        Assert.assertFalse(dp.getResult().containsKey("abc"));
    }
}
