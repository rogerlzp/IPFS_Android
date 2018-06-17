package com.ipfs.api.entity;

import java.util.List;

/**
 * Created by zhengpingli on 2018/6/15.
 */

public class Sub {
    /**
     * 接收到的是通过base64转义的，要在通过
     * >>> base64.b64encode('123')
     * 'MTIz'
     * >>> base64.encodestring('123')
     * 'MTIz\n'
     * >>> base64.decodestring('MTIz')
     * '123'
     * >>> base64.b64decode('MTIz\n')
     * '123'
     */
    // {"from":"EiB2hWgxOkc+KmCgRn+xnCw3jyzMiHgDn7LuvvpMl/riTA==",
    // "data":"aGVlbG8=",
    // "seqno":"FThCv7zRV+A=",
    // "topicIDs":["RussiaCup3"]}

    public String from;
    public String data;
    public String seqno;
//    public List<String> topicIDs;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

//    public List<String> getTopicIDs() {
//        return topicIDs;
//    }
//
//    public void setTopicIDs(List<String> topicIDs) {
//        this.topicIDs = topicIDs;
//    }
}
