package com.ipfs.api.entity;

import java.util.List;

/**
 * Created by zhengpingli on 2018/6/13.
 */

public class Id {

    // ipfs id的内容如下
//    {
//        "ID": "QmNQqGtk1sPH2seazGVmpXkteaGmzyQXAAXGF4xg1UGq79",
//            "PublicKey": "CAASpgIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCetSK0mxTnm7mf8gksEXwWy4uG2YtQ0m/6dZCXd6khWU6m70ChCwwc+NUS6fpAPY3QKhqgoWqjG41kJnPytzgO7kFf71H74j2CURcVs39DydD7/M2SuB1HL/MqS/Upu4jU+TqcrEqygYfEPXraVoMFWdWiyodgov+0Gmu4hCBqF+kwerlyaLMj3bvfSonFmTh7E3PUOcykVccNEp6QhfBjfNfL2uld+eG56+dmvdQDXf/lVslHTwuS7pdLVMYvs9BCXR0z6g610pIlMeTrwDfcPoV7uUvFYLBdZHHA94BERQrQcL49d/+QGADE2P5AjXU+F80O+1GdwMdBxrzQvtzvAgMBAAE=",
//            "Addresses": null,
//            "AgentVersion": "go-ipfs/0.4.15/",
//            "ProtocolVersion": "ipfs/0.1.0"
//    }
//}

    private String ID;
    private String PublicKey;
    private String AgentVersion;
    private String ProtocolVersion;
    private List<String> Addresses;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPublicKey() {
        return PublicKey;
    }

    public void setPublicKey(String publicKey) {
        PublicKey = publicKey;
    }

    public String getAgentVersion() {
        return AgentVersion;
    }

    public void setAgentVersion(String agentVersion) {
        AgentVersion = agentVersion;
    }

    public String getProtocolVersion() {
        return ProtocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        ProtocolVersion = protocolVersion;
    }

    public List<String> getAddresses() {
        return Addresses;
    }

    public void setAddresses(List<String> addresses) {
        Addresses = addresses;
    }

    @Override
    public String toString() {
        return "Id{" +
                "ID='" + ID + '\'' +
                ", PublicKey='" + PublicKey + '\'' +
                ", AgentVersion='" + AgentVersion + '\'' +
                ", ProtocolVersion='" + ProtocolVersion + '\'' +
                ", Addresses=" + Addresses +
                '}';
    }
}
