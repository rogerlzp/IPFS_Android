package com.blockchain.ipfs.base.contract.zhihu;

import com.blockchain.ipfs.model.bean.DailyBeforeListBean;
import com.blockchain.ipfs.model.bean.DailyListBean;
import com.blockchain.ipfs.base.BasePresenter;
import com.blockchain.ipfs.base.BaseView;
import com.blockchain.ipfs.model.bean.DailyBeforeListBean;
import com.blockchain.ipfs.model.bean.DailyListBean;
import com.blockchain.ipfs.model.bean.DailyBeforeListBean;
import com.blockchain.ipfs.model.bean.DailyListBean;

/**
 * Created by codeest on 16/8/11.
 */

public interface DailyContract {

    interface View extends BaseView {

        void showContent(DailyListBean info);

        void showMoreContent(String date,DailyBeforeListBean info);

        void doInterval(int currentCount);
    }

    interface Presenter extends BasePresenter<View> {

        void getDailyData();

        void getBeforeData(String date);

        void startInterval();

        void stopInterval();

        void insertReadToDB(int id);
    }
}
