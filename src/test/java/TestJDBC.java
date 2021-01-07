import com.cdcas.coupon.pojo.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TestJDBC {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private ResultSetMetaData metaData = null;

    // 程序中遇到的异常一般使用抛出: 异常链不中断
    @Before
    public void before() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
//        String url = "jdbc:mysql://localhost:3306/coupon?useSSL=false&characterEncoding=utf8";
        String url = "jdbc:mysql://localhost:3306/coupon?useSSL=false&characterEncoding=utf8&serverTimeZone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        connection = DriverManager.getConnection(url,username,password);
    }

    @Test
    public void testInsert() throws SQLException {
        String sql = "INSERT INTO tb_user VALUES(NULL,?,?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"刘德华");
        preparedStatement.setString(2,"13567890987");
        preparedStatement.setString(3,"1990-12-31 11:06:30");
        preparedStatement.setString(4,"2020-11-30 11:06:30");

        int i = preparedStatement.executeUpdate();
        if(i>0){
            System.out.println("添加成功");
        }
    }

    @Test
    public void testSelect() throws SQLException {
        // 根据手机号查询用户信息
        String sql = "SELECT user_name,user_phone,user_regTime FROM tb_user WHERE user_phone=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,"13567890988");
        ResultSet resultSet = preparedStatement.executeQuery();

        // 结果集的操作
        if(resultSet.next()){
            // ctrl+d 快速复制一行到下一行上
            String user_name = resultSet.getString("user_name");
            String user_phone = resultSet.getString("user_phone");
            java.util.Date user_regTime = resultSet.getDate("user_regTime");

            // 将特征值注入到对象上
            User user = new User();
            user.setName(user_name);
            user.setPhone(user_phone);
            // String转化为Date
            System.out.println(user_regTime);
            user.setRegTime( user_regTime);

            System.out.println(user);



        }
    }

    @After
    public void after() throws SQLException {
        if(resultSet != null)
            resultSet.close();
        if(preparedStatement != null)
            preparedStatement.close();
        if(connection != null)
            connection.close();
    }
}
