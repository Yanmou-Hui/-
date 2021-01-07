import com.cdcas.coupon.mapper.UserMapper;
import com.cdcas.coupon.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
*  每一个Mapper和Service方法都应该先执行单元测试，测试通过之后再和Controller对接
* */

// 使用注解的方式来加载Spring的配置文件
@ContextConfiguration(locations = "classpath:config/spring/spring.xml")
// 以Spring的方式来执行单元测试
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSpringMyBatis2 {

//    @Resource ByName和ByType  是Java自带的
    // 通过自动装配的方式完成对象的实例并注入
    @Autowired  // ByType 是Spring的
//    @Qualifier("userMapper") // 指定为ByName
    UserMapper userMapper = null;

    // 测试利用Spring的管理mapper能力
    @Test
    public void testGetMapper() {
        User user = userMapper.selectByPhone("177");
        System.out.println(user);
    }
}
