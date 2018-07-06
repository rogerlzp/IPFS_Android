package com.blockchain.ipfs.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhengpingli on 2018/6/14.
 */

@Entity
public class ImageBean {

    @Id(autoincrement = true)
    private Long id;

    private String type; // 文件类型：后缀名
    private String name; // 文件名称
    private String desc; // 文件类型
    private int status;  // 文件状态
    private String hash; // 文件的Hash地址
    private Double price; //价格，几个自定义的Token
    @Generated(hash = 1147935167)
    public ImageBean(Long id, String type, String name, String desc, int status,
            String hash, Double price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.status = status;
        this.hash = hash;
        this.price = price;
    }
    @Generated(hash = 645668394)
    public ImageBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getHash() {
        return this.hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public Double getPrice() {
        return this.price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }


}


