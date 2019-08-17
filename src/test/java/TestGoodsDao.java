import base.BaseDAO;
import bean.Goods;
import constants.DAO;
import dao.GoodsDAO;
import org.junit.Test;

import java.util.Arrays;

/**
 * @description
 * @author: yanghz
 * @create: 2019-08-04 16:38
 **/
public class TestGoodsDao {

    private GoodsDAO goodsDAO= (GoodsDAO) BaseDAO.getAbilityDAO(DAO.GoodsDAO);

    @Test
    public void test1(){
        Goods goods = goodsDAO.selectOne(1);
        System.out.println(goods.getDesc());
    }

    @Test
    public void test2(){
        String[][] arr = goodsDAO.selectLikeName("1",null);

        System.out.println(arr.length);
    }

    @Test
    public void test3() throws Exception {
        System.out.println(goodsDAO.delete(1));
    }

    @Test
    public void test4() throws Exception {
        Goods goods=new Goods();
        goods.setDesc("pp");
        goods.setName("yy");
        System.out.println(goodsDAO.insert(goods));
    }

    @Test
    public void test5() throws Exception {
        Goods goods=new Goods();
        goods.setId(3);
        goods.setDesc("yy");
        goods.setName("yy");
        System.out.println(goodsDAO.update(goods));
    }
}
