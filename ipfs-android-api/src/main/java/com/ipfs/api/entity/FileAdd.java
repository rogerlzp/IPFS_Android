package com.ipfs.api.entity;

/**
 * Created by zhengpingli on 2018/6/13.
 */

public class FileAdd {

    private String Name;
    private String Hash;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Hash = hash;
    }
}
