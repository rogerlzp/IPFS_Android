package com.blockchain.ipfs.model.db;

import com.blockchain.ipfs.model.bean.GoldManagerBean;
import com.blockchain.ipfs.model.bean.RealmLikeBean;

import java.util.List;

import javax.inject.Inject;

import static org.greenrobot.greendao.test.DbTest.DB_NAME;

public class GreenDaoHelper implements DBHelper {


    @Inject
    public GreenDaoHelper() {
        // TODO:initialzie GREENDAO
    }

    @Override
    public void insertNewsId(int id) {

    }

    @Override
    public boolean queryNewsId(int id) {
        return false;
    }

    @Override
    public void insertLikeBean(RealmLikeBean bean) {

    }

    @Override
    public void deleteLikeBean(String id) {

    }

    @Override
    public boolean queryLikeId(String id) {
        return false;
    }

    @Override
    public List<RealmLikeBean> getLikeList() {
        return null;
    }

    @Override
    public void changeLikeTime(String id, long time, boolean isPlus) {

    }

    @Override
    public void updateGoldManagerList(GoldManagerBean bean) {

    }

    @Override
    public GoldManagerBean getGoldManagerList() {
        return null;
    }
}
