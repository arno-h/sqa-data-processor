import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TestSumDuration {

    @Test
    public void testPreviousNull() {
        // setup
        DataProcessor dp = new DataProcessor();
        Date current = new Date(2022-1900, Calendar.MAY, 3);
        // execute
        dp.sumDuration("test", null, current);
        // verify
        HashMap<String, Duration> result = dp.getResult();
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSingleActivity() {
        // setup
        DataProcessor dp = new DataProcessor();
        Date previous = new Date(2022-1900, Calendar.MAY, 3, 13, 45);
        Date current = new Date(2022-1900, Calendar.MAY, 3, 14, 15);
        // execute
        dp.sumDuration("test", previous, current);
        // verify
        HashMap<String, Duration> result = dp.getResult();
        // Assert.assertTrue(result.get("test").equals(Duration.ofMinutes(30)));
        Assert.assertEquals(Duration.ofMinutes(30), result.get("test"));
    }

    @Test
    public void testMulitpleActivity() {
        // setup
        DataProcessor dp = new DataProcessor();
        Date previous = new Date(2022-1900, Calendar.MAY, 3, 13, 45);
        Date current = new Date(2022-1900, Calendar.MAY, 3, 14, 15);
        Date later = new Date(2022-1900, Calendar.MAY, 3, 15, 15);
        // execute
        dp.sumDuration("test", previous, current);
        dp.sumDuration("test", current, later);
        // verify
        HashMap<String, Duration> result = dp.getResult();
        Assert.assertEquals(Duration.ofMinutes(90), result.get("test"));
    }
}
