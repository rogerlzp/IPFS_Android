package com.blockchain.ipfs.di.component;

import android.app.Activity;

import com.blockchain.ipfs.di.scope.ActivityScope;
import com.blockchain.ipfs.di.module.ActivityModule;
import com.blockchain.ipfs.ui.main.activity.MainActivity;
import com.blockchain.ipfs.ui.main.activity.WelcomeActivity;
import com.blockchain.ipfs.ui.vtex.activity.NodeListActivity;
import com.blockchain.ipfs.ui.vtex.activity.RepliesActivity;
import com.blockchain.ipfs.ui.zhihu.activity.SectionActivity;
import com.blockchain.ipfs.ui.zhihu.activity.ThemeActivity;
import com.blockchain.ipfs.ui.zhihu.activity.ZhihuDetailActivity;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(WelcomeActivity welcomeActivity);

    void inject(MainActivity mainActivity);

    void inject(ZhihuDetailActivity zhihuDetailActivity);

    void inject(ThemeActivity themeActivity);

    void inject(SectionActivity sectionActivity);

    void inject(RepliesActivity repliesActivity);

    void inject(NodeListActivity nodeListActivity);
}
