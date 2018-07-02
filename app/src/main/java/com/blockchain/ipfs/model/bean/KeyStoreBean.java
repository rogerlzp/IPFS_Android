package com.blockchain.ipfs.model.bean;

public class KeyStoreBean {
    private String address;
    private String path;

    public KeyStoreBean(String address, String path) {
        this.address = address;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
