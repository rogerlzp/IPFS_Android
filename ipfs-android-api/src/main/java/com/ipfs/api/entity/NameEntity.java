package com.ipfs.api.entity;

public class NameEntity {


//    {"Name":"QmNQqGtk1sPH2seazGVmpXkteaGmzyQXAAXGF4xg1UGq79",
//            "Value":"/ipfs/QmcmphYYdhm7MWwaSCnRmdpG6R5GyWrueyY5ZBNB2EG7xv"}

    private String Name;
    private String Value;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
