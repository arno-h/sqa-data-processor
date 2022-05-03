import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Holidays {
    private static final String apiURL = "http://www.xmp.net:6081/v1/";

    public boolean isHoliday(Date day) {
        String url = buildURL(day);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            InputStream body = response.getEntity().getContent();
            // if we get an empty list, then the day is not a holiday
            // all other responses indicate a holiday, but we are not interested in the details
            String content = new String(body.readAllBytes());
            return !"[]".equals(content);
        } catch (IOException e) {
            System.out.println("Couldn't query Holiday-API");
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String buildURL(Date day) {
        LocalDate date = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return String.format("%s?country=AT&year=%d&month=%d&day=%d",
                apiURL, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
}
