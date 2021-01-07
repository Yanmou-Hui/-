import com.cdcas.coupon.pojo.User;
import com.cdcas.coupon.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestInstance {

    ApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("config/spring/spring.xml");
    }

    @Test
    public void testInstance() {
//        IOC(控制反转思想)
        User user = context.getBean("user", User.class);
        System.out.println(user);
    }

    @Test
    public void testInstance2() {
        UserService userService = context.getBean("userService", UserService.class);
        System.out.println(userService);
    }
}
