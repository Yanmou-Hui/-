import com.cdcas.coupon.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class RedisTest {
    RedisTemplate redisTemplate = null;

    @Before
    public void before() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring/spring-redis.xml");
        redisTemplate = ctx.getBean(RedisTemplate.class);
    }

    @Test
    public void test1() {
        User user = new User();
        user.setName("鲁班七号");
        user.setPhone("19955555555");

        redisTemplate.opsForValue().set("user", user);

        User user2 = (User) redisTemplate.opsForValue().get("user");

        System.out.println(user2.toString());
    }

}
