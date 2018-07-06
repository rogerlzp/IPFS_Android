package com.ipfs.api.entity;

import java.util.List;

public class PeersEntity {

    private List<PeerEntity> Peers;

    public List<PeerEntity> getPeers() {
        return Peers;
    }

    public void setPeers(List<PeerEntity> peers) {
        Peers = peers;
    }

}
