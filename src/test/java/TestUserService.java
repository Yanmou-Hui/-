import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@ContextConfiguration(locations = "classpath:config/spring/spring.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserService {

    @Resource
    private UserService userService;

    @Test
    public void testInstance() {
        System.out.println(userService);
    }

    @Test
    public void testLogins() {
        User login = userService.login("18507229690");
        System.out.println(login);
    }

    @Test
    public void testSelectAll() {

        Map all = userService.findAll(null, null);

        System.out.println(all);
    }
}
