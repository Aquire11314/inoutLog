import org.junit.Test;
import util.DBUtil;

/**
 * @description
 * @author: yanghz
 * @create: 2019-08-04 14:17
 **/
public class TestDataConn {
    @Test
    public void test1(){
        DBUtil dbUtil =DBUtil.getDBUtil();
        System.out.println(dbUtil);
    }

    @Test
    public void initDB(){
        InOutLogApplication.initDB();
    }
}
