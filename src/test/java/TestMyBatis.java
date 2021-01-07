import com.cdcas.coupon.mapper.UserMapper;
import com.cdcas.coupon.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author 毕磊
 * @version 1.0
 * @date 2020/12/28 0028 15:42
 */
public class TestMyBatis {
    private SqlSession sqlSession = null;
    private UserMapper mapper;
    @Before
    public void before() throws IOException {
        // 创建builder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        // 加载mybatis.xml配置文件 , 同时生成输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("config/mybatis/mybatis.xml");

        // 执行build方法,加载配置文件: 通过输入流, 生成SqlSession工厂对象
        SqlSessionFactory factory = builder.build(resourceAsStream);

        // factory通过openSession方法产生一个会话对象sqlSession
        sqlSession = factory.openSession();

        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testSelectByPhone() throws IOException {

        User user =  mapper.selectByPhone("13567890987");
        System.out.println(user);
    }

    @Test
    public void testDelete(){
        // 在执行增删改操作中, 默认事务是不会提交的, 需要主动执行commit()
        // 默认执行的是rollback
        Integer delete = mapper.delete("13567890988");
        System.out.println("delete:"+delete);
        sqlSession.commit();
    }

    @Test
    public void testInsert() {
        User user = new User(null, "zhangSan", "177", new Date(), new Date());
        mapper.insert(user);
        sqlSession.commit();
    }

    @Test
    public void testUpdate() {
        User user = new User(null, "WangWu", "18507229690", new Date(), new Date());
        mapper.update(user);
        sqlSession.commit();
    }

    @After
    public void after(){
        sqlSession.close();
    }
}
