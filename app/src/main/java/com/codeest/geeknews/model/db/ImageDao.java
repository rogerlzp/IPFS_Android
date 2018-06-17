package com.codeest.geeknews.model.db;

import com.codeest.geeknews.model.bean.ImageBean;

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
