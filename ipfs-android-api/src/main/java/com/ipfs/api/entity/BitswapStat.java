package com.ipfs.api.entity;

import java.util.List;

public class BitswapStat {

    /** result:
     provides buffer: 0 / 256
     blocks received: 0
     blocks sent: 0
     data received: 0
     data sent: 0
     dup blocks received: 0
     dup data received: 0 B
     wantlist [0 keys]
     partners [3]
     QmSoLSafTMBsPKadTEgaXctDQVcqN88CNLHXMkTNwMKPnu
     QmSoLV4Bbm51jM9C4gDYZQ9Cy3U6aXMJDAbzgu2fzaDs64
     QmSoLer265NRgSp2LA3dPaeykiS1J6DifTC88f5uVQKNAd
     */

    private int ProvideBufLen;
    private int BlocksReceived;
    private int DataReceived;
    private int BlocksSent;
    private int DataSent;
    private int DupBlksReceived;
    private int DupDataReceived;
    private List<?> Wantlist;
    private List<String> Peers;


    public int getProvideBufLen() {
        return ProvideBufLen;
    }

    public void setProvideBufLen(int provideBufLen) {
        ProvideBufLen = provideBufLen;
    }

    public int getBlocksReceived() {
        return BlocksReceived;
    }

    public void setBlocksReceived(int blocksReceived) {
        BlocksReceived = blocksReceived;
    }

    public int getDataReceived() {
        return DataReceived;
    }

    public void setDataReceived(int dataReceived) {
        DataReceived = dataReceived;
    }

    public int getBlocksSent() {
        return BlocksSent;
    }

    public void setBlocksSent(int blocksSent) {
        BlocksSent = blocksSent;
    }

    public int getDataSent() {
        return DataSent;
    }

    public void setDataSent(int dataSent) {
        DataSent = dataSent;
    }

    public int getDupBlksReceived() {
        return DupBlksReceived;
    }

    public void setDupBlksReceived(int dupBlksReceived) {
        DupBlksReceived = dupBlksReceived;
    }

    public int getDupDataReceived() {
        return DupDataReceived;
    }

    public void setDupDataReceived(int dupDataReceived) {
        DupDataReceived = dupDataReceived;
    }

    public List<?> getWantlist() {
        return Wantlist;
    }

    public void setWantlist(List<?> wantlist) {
        Wantlist = wantlist;
    }

    public List<String> getPeers() {
        return Peers;
    }

    public void setPeers(List<String> peers) {
        Peers = peers;
    }
}
