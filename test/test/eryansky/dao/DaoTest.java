package test.eryansky.dao;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lushapp.common.orm.jdbc.JdbcDao;
import com.lushapp.common.utils.io.PropertiesLoader;
import com.lushapp.modules.sys.service.RoleManager;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Dao单元测试.
 * @author honey.zhao@aliyun.com  
 * @date 2014-4-16 下午8:12:40 
 * @version 1.0
 */
public class DaoTest {
	
	@SuppressWarnings("unused")
    private static Properties pro;
	
	private static JdbcDao jdbcDao;
	
	private static RoleManager roleManager;
	
	@BeforeClass
	public static void init() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
		jdbcDao = (JdbcDao)context.getBean("jdbcDao");
		roleManager = context.getBean(RoleManager.class);
		pro = new PropertiesLoader("/appconfig.properties").getProperties();
	}
	
	
	@Test
    public void update(){
		try {
			String sql = "update T_SYS_ROLE set name = ? where id = ?";
			Object[] obj = new Object[]{"11",1};
			int count = 0;
			count = jdbcDao.update(sql, obj);
			System.out.println(count);

//		    roleManager.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    @Test
    public void page(){
        try {
            String sql = "select * from T_SYS_RESOURCE";
            List<Map<String,Object>> list1 = jdbcDao.queryForList(sql,5);
            for(Map<String,Object> map:list1){
                System.out.println(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
