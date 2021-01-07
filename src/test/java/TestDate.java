import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    @Test
    public void format() {
        Date date = new Date();
        System.out.println(date);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String formatString = format.format(date);
        System.out.println(formatString);

        String dateString = "2020-02-01";
        java.sql.Date date1 = java.sql.Date.valueOf(dateString);
        System.out.println(date1);
    }
}
