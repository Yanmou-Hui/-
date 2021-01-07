import com.cdcas.coupon.mapper.UserMapper;
import com.cdcas.coupon.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringMyBatis {
    ApplicationContext context;

    @Before
    public void before() {
        context = new ClassPathXmlApplicationContext("config/spring/spring.xml");
    }

    // 测试利用Spring的管理mapper能力
    @Test
    public void testGetMapper() {
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        User user = userMapper.selectByPhone("177");
        System.out.println(user);
    }
}
