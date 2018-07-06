package com.blockchain.ipfs.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class IpfsChannelPayNodeBean {
    @Id(autoincrement = true)
    private Long id;

    private String totalPrice; //节点文件名称，可为空
    private String imageBeanList; //文件的hash地址
    @Generated(hash = 656285663)
    public IpfsChannelPayNodeBean(Long id, String totalPrice,
            String imageBeanList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.imageBeanList = imageBeanList;
    }
    @Generated(hash = 1065884746)
    public IpfsChannelPayNodeBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTotalPrice() {
        return this.totalPrice;
    }
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getImageBeanList() {
        return this.imageBeanList;
    }
    public void setImageBeanList(String imageBeanList) {
        this.imageBeanList = imageBeanList;
    }

}
