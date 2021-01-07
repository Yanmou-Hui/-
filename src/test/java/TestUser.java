import com.cdcas.coupon.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * 用来测试User类的相关方法的测试用例类
 *  使用单元测试为了替换main
 * @author 毕磊
 * @version 1.0
 * @date 2020/12/28 0028 9:46
 */
public class TestUser {
    private User user;

    // 添加了before注解的方法,会在所有含有@Test方法运行之前自动执行
    @Before
    public void before(){
        user = new User();
        user.setId(1);
        user.setName("张三丰");
        user.setPhone("133");
        user.setBirth(new Date());
    }

    // JUnit的单元测试的方法声明必须返回值为void,且空参
    @Test
    public void testToString(){
        System.out.println(user);
    }

    @Test
    public void testGetter(){
        System.out.println(user.getName());
    }


    @After
    public void after(){
        System.out.println("最终要执行的操作");
    }

}
