package com.blockchain.ipfs.di.component;

import android.app.Activity;

import com.blockchain.ipfs.di.scope.FragmentScope;
import com.blockchain.ipfs.di.module.FragmentModule;
import com.blockchain.ipfs.ui.gank.fragment.GirlFragment;
import com.blockchain.ipfs.ui.gank.fragment.TechFragment;
import com.blockchain.ipfs.ui.gold.fragment.GoldMainFragment;
import com.blockchain.ipfs.ui.gold.fragment.GoldPagerFragment;
import com.blockchain.ipfs.ui.ipfs.fragment.IpfsFragment;
import com.blockchain.ipfs.ui.main.fragment.LikeFragment;
import com.blockchain.ipfs.ui.main.fragment.SettingFragment;
import com.blockchain.ipfs.ui.vtex.fragment.VtexPagerFragment;
import com.blockchain.ipfs.ui.wallet.fragment.WalletFragment;
import com.blockchain.ipfs.ui.wechat.fragment.WechatMainFragment;
import com.blockchain.ipfs.ui.zhihu.fragment.CommentFragment;
import com.blockchain.ipfs.ui.zhihu.fragment.DailyFragment;
import com.blockchain.ipfs.ui.zhihu.fragment.HotFragment;
import com.blockchain.ipfs.ui.zhihu.fragment.SectionFragment;
import com.blockchain.ipfs.ui.zhihu.fragment.ThemeFragment;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(DailyFragment dailyFragment);

    void inject(ThemeFragment themeFragment);

    void inject(SectionFragment sectionFragment);

    void inject(HotFragment hotFragment);

    void inject(CommentFragment longCommentFragment);

    void inject(TechFragment techFragment);

    void inject(GirlFragment girlFragment);

    void inject(LikeFragment likeFragment);

    void inject(WechatMainFragment wechatMainFragment);

    void inject(SettingFragment settingFragment);

    void inject(GoldMainFragment goldMainFragment);

    void inject(GoldPagerFragment goldPagerFragment);

    void inject(VtexPagerFragment vtexPagerFragment);

    // ipfs
    void inject(IpfsFragment ipfsFragment);

    // wallet
    void inject(WalletFragment walletFragment);
}
