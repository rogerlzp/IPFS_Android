package com.ipfs.api.entity;

public class NodeCat {
//    private String nodeJson;
//
//    [{"desc":"test1","hash":"QmfJyzXGYsmTo9phLDEtGpCzGKiaW3kJbQ3mNHAXTHiCur",
//            "id":1,"name":"/Annuity_0830.pn","status":0}]


    private String desc;
    private String hash;
    private int id;
    private String name;
    private int status;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//    public String getNodeJson() {
//        return nodeJson;
//    }
//
//    public void setNodeJson(String nodeJson) {
//        this.nodeJson = nodeJson;
//    }
}
