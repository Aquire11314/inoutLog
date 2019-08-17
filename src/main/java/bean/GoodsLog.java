package bean;

import java.util.Date;

/**
 * @description 进出单
 * @author: yanghz
 * @create: 2019-08-04 21:09
 **/
public class GoodsLog {
    private Integer id;
    private Integer goodsId;
    private String desc;
    private Integer num;
    private Date createDate;

    public GoodsLog() {
    }

    public GoodsLog(Integer id, Integer goodsId, String desc, Integer num,Date createDate) {
        this.id = id;
        this.goodsId = goodsId;
        this.desc = desc;
        this.num = num;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
