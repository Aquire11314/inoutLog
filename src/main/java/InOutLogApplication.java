import util.DBUtil;
import view.GoodsView;
import view.LoginView;

import javax.swing.*;

/**
 * @description
 * @author: yanghz
 * @create: 2019-08-04 14:03
 **/
public class InOutLogApplication {

    public static void main(String [] args) {
        initDB();
        try
        {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false); //隐藏 设置
        } catch(Exception e) {
            e.printStackTrace();
        }
        new LoginView();
//        new GoodsView();
    }

    /**
     * 初始数据库
     */
    public static void initDB() {
        DBUtil dbUtil = DBUtil.getDBUtil();

        //检查数据库是否初始化
        if (dbUtil.exeute("select 1 from  admin")) {
            return;
        }

        //初始化数据库
        //admin表
        dbUtil.exeute("create table if not exists admin(id  INTEGER primary key," +
                "name varchar(32)," +
                "username varchar(32)," +
                "password varchar(32))");
        dbUtil.exeute("insert into admin(id, name, username, password) values(1, 'admin', 'test', 'test')");

        //goods
        dbUtil.exeute("create table if not exists goods(" +
                "id  INTEGER primary key," +
                "name varchar(50) unique not null," +
                "desc varchar(255))");

        //goodsLog
        dbUtil.exeute("create table if not exists goodsLog(" +
                "id  INTEGER primary key," +
                "goodsId int," +
                "num int," +
                "createDate datetime," +
                "desc varchar(255),FOREIGN KEY (goodsId) REFERENCES goods (id))");
    }
}
