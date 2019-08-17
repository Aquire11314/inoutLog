package dao;

import base.BaseDAO;
import bean.Goods;
import bean.GoodsLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description
 * @author: yanghz
 * @create: 2019-08-04 21:11
 **/
public class GoodsLogDAO extends BaseDAO {
    private final int fieldNum = 5;
    private final int showNum = 15;
    private static GoodsLogDAO ad = null;

    public static synchronized GoodsLogDAO getInstance() {
        if (ad == null) {
            ad = new GoodsLogDAO();
        }
        return ad;
    }

    public boolean insert(GoodsLog goodsLog) throws Exception{
        boolean result = false;
        if (goodsLog == null) {
            return result;
        }
        try {
            String sql = "insert into goodsLog(goodsId,desc,num,createDate) values(?,?,?,?)";
            Object[] param = { goodsLog.getGoodsId(), goodsLog.getDesc(),goodsLog.getNum(),goodsLog.getCreateDate()};
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
     * @param goodsLog
     * @return
     */
    public boolean update(GoodsLog goodsLog) throws Exception{
        boolean result = false;
        if (goodsLog == null) {
            return result;
        }
        try {
            String sql = "update goodsLog set goodsId=?,desc=?,num=?,createDate=? where id=?";
            Object[] param = { goodsLog.getGoodsId(),goodsLog.getDesc(),goodsLog.getNum(),goodsLog.getCreateDate(),goodsLog.getId()};
            int rowCount = db.executeUpdate(sql, param);
            if (rowCount == 1) {
                result = true;
            }
        }finally {
            destroy();
        }
        return result;
    }

    public boolean delete(Integer id) throws Exception{
        boolean result = false;
        String sql = "delete from goodsLog where id=?";
        Object[] param = { id };
        int rowCount = db.executeUpdate(sql, param);
        if (rowCount == 1) {
            result = true;
        }
        destroy();
        return result;
    }

    public String[][] selectByGoodsId(Integer id ,Integer pageNum) {
        String[][] result = null;

        List<GoodsLog> goodsLogList = new ArrayList<GoodsLog>();
        int i = 0;
        String sql = "select * from goodsLog where goodsId=? order by createDate desc limit ?,?";
        int beginNum = (pageNum - 1) * showNum;
        Integer[] param = {id, beginNum, showNum };

        rs = db.executeQuery(sql, param);

        try {
            while (rs.next()) {
                buildList(rs, goodsLogList);
            }
            if (goodsLogList.size() > 0) {
                result = new String[goodsLogList.size()][fieldNum];
                for (int j = 0; j < goodsLogList.size(); j++) {
                    buildResult(result, goodsLogList, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }

    // 将list中记录添加到二维数组中
    private void buildResult(String[][] result, List<GoodsLog> goodsLogList, int j) {
        GoodsLog goodsLog = goodsLogList.get(j);
        result[j][0] = String.valueOf(goodsLog.getId());
        result[j][1] = String.valueOf(goodsLog.getGoodsId());
        result[j][2] = String.valueOf(goodsLog.getNum());
        result[j][3] = String.valueOf(goodsLog.getCreateDate());
        result[j][4] = goodsLog.getDesc();

    }

    // 将rs记录添加到list中
    private void buildList(ResultSet rs, List<GoodsLog> list) throws SQLException {
        GoodsLog goodsLog = new GoodsLog();
        goodsLog.setId(rs.getInt("id"));
        goodsLog.setGoodsId(rs.getInt("goodsId"));
        goodsLog.setDesc(rs.getString("desc"));
        goodsLog.setNum(rs.getInt("num"));
        goodsLog.setCreateDate(rs.getTimestamp("createDate"));
        list.add(goodsLog);
    }

}
