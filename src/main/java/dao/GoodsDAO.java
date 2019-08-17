package dao;

import base.BaseDAO;
import bean.Goods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 无聊增删改查dao
 * @author: yanghz
 * @create: 2019-08-04 15:31
 **/
public class GoodsDAO extends BaseDAO {
    private final int fieldNum = 4;
    private final int showNum = 15;

    private static GoodsDAO ad = null;

    public static synchronized GoodsDAO getInstance() {
        if (ad == null) {
            ad = new GoodsDAO();
        }
        return ad;
    }

    /**
     * 新增
     * @param goods
     * @return
     */
    public boolean insert(Goods goods) throws Exception{
        boolean result = false;
        if (goods == null) {
            return result;
        }
        try {
            String sql = "insert into goods(id,name,desc) values(null,?,?)";
            String[] param = { goods.getName(), goods.getDesc()};
            if (db.executeUpdate(sql, param) == 1) {
                result = true;
            }
        } finally {
            destroy();
        }
        return result;
    }

    /**
     * 更新
     * @param goods
     * @return
     */
    public boolean update(Goods goods) throws Exception{
        boolean result = false;
        if (goods == null) {
            return result;
        }
        try {
            String sql = "update goods set name=?,desc=? where id=?";
            Object[] param = { goods.getName(),goods.getDesc(),goods.getId() };
            int rowCount = db.executeUpdate(sql, param);
            if (rowCount == 1) {
                result = true;
            }
        }finally {
            destroy();
        }
        return result;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public boolean delete(Integer id) throws Exception{
        boolean result = false;
        String sql = "delete from goods where id=?";
        Object[] param = { id };
        int rowCount = db.executeUpdate(sql, param);
        if (rowCount == 1) {
            result = true;
        }
        destroy();
        return result;
    }


    public String[][] selectLikeName(String name,Integer pageNum) {
        String[][] result = null;

        List<Goods> goodsList = new ArrayList<Goods>();
        int i = 0;
        String sql = "select g.*,(select sum(num) from goodsLog where goodsId = g.id )sum from goods g ";
        if ((name==null||name.trim().length() <= 0)&&(pageNum==null||pageNum<0)) {
            rs = db.executeQuery(sql);
        }else if(name!=null&&name.trim().length() > 0){
            sql+="where name like ?";
            String[] param = { "%" + name + "%" };
            rs = db.executeQuery(sql, param);
        }else{
            int beginNum = (pageNum - 1) * showNum;
            sql+="limit ?,?";
            Integer[] param = { beginNum, showNum };
            rs = db.executeQuery(sql, param);
        }
        try {
            while (rs.next()) {
                buildList(rs, goodsList);
            }
            if (goodsList.size() > 0) {
                result = new String[goodsList.size()][fieldNum];
                for (int j = 0; j < goodsList.size(); j++) {
                    buildResult(result, goodsList, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }


    public Goods selectOne(Integer id){
        Goods goods = null;
        String checkSql = "select * from goods where id=?";
        Integer[] checkParam = { id };
        rs = db.executeQuery(checkSql, checkParam);
        try {
            if (rs.next()) {
                goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setName(rs.getString("name"));
                goods.setDesc(rs.getString("desc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }


    // 将list中记录添加到二维数组中
    private void buildResult(String[][] result, List<Goods> goodsList, int j) {
        Goods goods = goodsList.get(j);
        result[j][0] = String.valueOf(goods.getId());
        result[j][1] = goods.getName();
        result[j][2] = goods.getDesc();
        result[j][3] = String.valueOf(goods.getSum());

    }

    // 将rs记录添加到list中
    private void buildList(ResultSet rs, List<Goods> list) throws SQLException {
        Goods goods = new Goods();
        goods.setId(rs.getInt("id"));
        goods.setName(rs.getString("name"));
        goods.setDesc(rs.getString("desc"));
        goods.setSum(rs.getInt("sum"));
        list.add(goods);
    }

}
