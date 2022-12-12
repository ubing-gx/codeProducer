import com.boot.JDBC.SingleMySql;
import com.boot.entity.user;
import com.boot.entity.users;
import com.boot.service.impl.ormImpl;
import com.boot.service.orm;
import com.boot.service.convertUtils;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName : test
 * @Description : test
 * @Author : ubing
 * @Date: 2022-12-02 10:14
 */
public class test {
    public static void main(String[] args) {
        SingleMySql mysql = SingleMySql.getInstance("jdbc:mysql://localhost:3306/gorm?charset=utf8mb4&parseTime=True&loc=Local&serverTimezone=UTC", "root", "123456");
        orm orm = new ormImpl();
        List<users> users = orm.selectList(mysql, users.class);
        try {
            users.get(0).setId("6");
            users.get(0).setMemberNumber("刹车");
            orm.insert(mysql,users.get(0));
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
//        System.out.println(user);
//        try {
//            List list = orm.selectList(mysql, users.class);
//            for (Object o : list) {
//                Class<com.boot.entity.users> var = (Class<users>) Class.forName("com.boot.entity.users");
//                users convert = convertUtils.convert(o, var);
//                System.out.println(convert.getCreatedAt());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            mysql.initCode("users","src/main/java/com/boot/entity");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        com.boot.entity.user user = new user();
//        userMa userMa = new userMa();
//        try {
//            mysql.createTab(userMa);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}

