package com.blockchain.ipfs.model.db;

import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.model.bean.ImageBean;
import com.blockchain.ipfs.model.bean.ImageBean;

import java.util.List;

/**
 * Created by zhengpingli on 2018/6/14.
 */

public class ImageDao  {

    private static List<ImageBean> imageBeanList;

    public static List<ImageBean> getAllImages() {
        return imageBeanList;
    }


}
