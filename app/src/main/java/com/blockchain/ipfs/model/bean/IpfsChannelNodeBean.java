package com.blockchain.ipfs.model.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * 记录监听到的 Node_BEAN
 */
@Entity
public class IpfsChannelNodeBean {

    @Id(autoincrement = true)
    private Long id;

    private String name; //节点文件名称，可为空
    private String hash; //文件的hash地址

    private String topic; // 监听到的Topic

    private Date getTime; // 监听到的Topic的时间

    private Double totalPrice; // 监听到的总共Price
    private String receiverAddress; // 发送去的receiverAddress
    @Generated(hash = 1753962406)
    public IpfsChannelNodeBean(Long id, String name, String hash, String topic,
            Date getTime, Double totalPrice, String receiverAddress) {
        this.id = id;
        this.name = name;
        this.hash = hash;
        this.topic = topic;
        this.getTime = getTime;
        this.totalPrice = totalPrice;
        this.receiverAddress = receiverAddress;
    }
    @Generated(hash = 1894174755)
    public IpfsChannelNodeBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
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
    public String getTopic() {
        return this.topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public Date getGetTime() {
        return this.getTime;
    }
    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
    public Double getTotalPrice() {
        return this.totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getReceiverAddress() {
        return this.receiverAddress;
    }
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }


}
