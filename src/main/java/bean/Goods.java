package bean;

/**
 * @description
 * @author: yanghz
 * @create: 2019-08-04 15:36
 **/
public class Goods {
    private Integer id;
    private String name;
    private String desc;

    public Goods() {
    }

    public Goods(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
