package com.blockchain.ipfs.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhengpingli on 2018/6/14.
 */

@Entity
public class IpfsNodeBean {

    @Id(autoincrement = true)
    private Long id;

    private String name; //节点文件名称，可为空
    private String hash; //文件的hash地址

    @Generated(hash = 1039429660)
    public IpfsNodeBean(Long id, String name, String hash) {
        this.id = id;
        this.name = name;
        this.hash = hash;
    }

    @Generated(hash = 294055091)
    public IpfsNodeBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
