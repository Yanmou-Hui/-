import com.cdcas.coupon.pojo.User;
import org.json.JSONObject;
import org.junit.Test;

public class TestJson {

    @Test
    public void test1() {
        User user = new User();
        user.setName("ABC");

        JSONObject json = new JSONObject();

        json.put("Name", user.getName());
        System.out.println(json);
    }
}
