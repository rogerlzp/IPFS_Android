package com.ipfs.api.entity;

import java.util.List;

public class BootStrapEntity {

    //命令行 add：
    // http://127.0.0.1:5001/api/v0/bootstrap/add?arg=/ip4/10.194.162.3/tcp/4001/ipfs/QmWw62PF51zmKW8zWN3cm2vJToiTKzxL1hs357YDYbHpYq
    //结果：{"Peers":["/ip4/10.194.162.3/tcp/4001/ipfs/QmWw62PF51zmKW8zWN3cm2vJToiTKzxL1hs357YDYbHpYq"]}


    // rm: 删除：
    // 如果有的话，则显示为: {"Peers":["/ip4/10.194.162.3/tcp/4001/ipfs/QmWw62PF51zmKW8zWN3cm2vJToiTKzxL1hs357YDYbHpYq"]}
    // 没有的话，显示为空的： {"Peers":[]}
    // http://127.0.0.1:5001/api/v0/bootstrap/rm?arg=/ip4/10.194.162.3/tcp/4001/ipfs/QmWw62PF51zmKW8zWN3cm2vJToiTKzxL1hs357YDYbHpYq

    //错误的peers, 则显示为：
    // {"Message":"failed to parse ipfs: QmWw62PF51zmKW8zWN3cm2vJToiTKzxL1hs357YDYbHp failed to parse ipfs a
    // ddr: QmWw62PF51zmKW8zWN3cm2vJToiTKzxL1hs357YDYbHp multihash length inconsistent: \u0026
    // {1  97 [36 51 33 5 56 170 211 80 112 217 139 130 130 167 166 214 82 196 34 217 11 75 1 137 25
    // 247 58 76 192 190 15]}","Code":0,"Type":"error"}


    private List<String> Peers;

    public List<String> getPeers() {
        return Peers;
    }

    public void setPeers(List<String> peers) {
        Peers = peers;
    }
}
