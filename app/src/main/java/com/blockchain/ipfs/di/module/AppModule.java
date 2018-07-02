package com.blockchain.ipfs.di.module;

import com.blockchain.ipfs.app.App;
import com.blockchain.ipfs.model.DataManager;
import com.blockchain.ipfs.model.db.DBHelper;
import com.blockchain.ipfs.model.db.RealmHelper;
import com.blockchain.ipfs.model.http.HttpHelper;
import com.blockchain.ipfs.model.http.RetrofitHelper;
import com.blockchain.ipfs.model.prefs.ImplPreferencesHelper;
import com.blockchain.ipfs.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper DBHelper, PreferencesHelper preferencesHelper) {
        return new DataManager(httpHelper, DBHelper, preferencesHelper);
    }
}
