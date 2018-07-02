package com.blockchain.ipfs.di.component;

import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.di.module.AppModule;
import com.blockchain.ipfs.di.module.HttpModule;
import com.blockchain.ipfs.model.DataManager;
import com.blockchain.ipfs.model.db.GreenDaoHelper;
import com.blockchain.ipfs.model.db.RealmHelper;
import com.blockchain.ipfs.model.http.RetrofitHelper;
import com.blockchain.ipfs.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    //    RealmHelper realmHelper();    //提供数据库帮助类
    GreenDaoHelper greenDaoHelper();

    ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
}
