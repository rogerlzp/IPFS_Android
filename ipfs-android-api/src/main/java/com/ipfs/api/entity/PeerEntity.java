package com.ipfs.api.entity;

public class PeerEntity {
//
//    {"Peers":[{"Addr":"/ip4/1.202.83.200/tcp/52612",
//        // "Peer":"QmeE8FjxEW7yBuuPEM9gZSMoMzuRv7pvy3ULV4pvWh6V1m",
//        // "Latency":"",
//        // "Muxer":"*sm_yamux.conn",
//        // "Streams":null},
//

    private String Addr;
    private String Peer;
    private String Latency;
    private String Muxer;
    private String Streams;

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getPeer() {
        return Peer;
    }

    public void setPeer(String peer) {
        Peer = peer;
    }

    public String getLatency() {
        return Latency;
    }

    public void setLatency(String latency) {
        Latency = latency;
    }

    public String getMuxer() {
        return Muxer;
    }

    public void setMuxer(String muxer) {
        Muxer = muxer;
    }

    public String getStreams() {
        return Streams;
    }

    public void setStreams(String streams) {
        Streams = streams;
    }
}
