package com.codeest.geeknews.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhengpingli on 2018/6/14.
 */

@Entity
public class DbBean {

    @Id(autoincrement = true)
    private Long id;

    private String name; //db文件名称
    private String hash; //db文件的hash地址
    private int version; //db版本名称， 从1往后加
    @Generated(hash = 1161646272)
    public DbBean(Long id, String name, String hash, int version) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.version = version;
    }
    @Generated(hash = 1953169116)
    public DbBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHash() {
        return this.hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
