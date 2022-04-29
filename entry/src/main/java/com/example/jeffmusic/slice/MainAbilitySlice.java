package com.example.jeffmusic.slice;

import coil.Coil;
import com.example.jeffmusic.MyApplication;
import com.example.jeffmusic.ResourceTable;
import com.example.jeffmusic.ability.LoginAbility;
import com.example.jeffmusic.fraction.FavoriteFraction;
import com.example.jeffmusic.fraction.MusicFraction;
import com.example.jeffmusic.utils.PlayerUtils;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.fraction.Fraction;
import ohos.aafwk.ability.fraction.FractionAbility;
import ohos.aafwk.ability.fraction.FractionManager;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.TabList;
import ohos.agp.window.service.WindowManager;
import ohos.hiviewdfx.HiLog;

public class MainAbilitySlice extends AbilitySlice {
    private final static int TAB_MUSIC = 0;
    private final static int TAB_FAVORITE = 1;
    String[] str = new String[] {"Music", "Favorite"};

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        initSystemUi();
        initview();
    }

    private void initview() {
        Image navigationButton = findComponentById(ResourceTable.Id_navigation_button);
        SlidingRootNav slidingRootNav = new SlidingRootNavBuilder(getAbility())
                .withArbitrarilyView(navigationButton)
                .withMenuLayout(ResourceTable.Layout_nav_info)
                .withDragDistance(150)
                .withRootViewScale(0.7f)
                .inject();
        slidingRootNav.getLayout().findComponentById(ResourceTable.Id_nav_login_button).setClickedListener(
                component -> {
                    if(PlayerUtils.isLogin()) {

                    } else {
                        startLoginAbility();
                    }
                }
        );
        TabList tabList = findComponentById(ResourceTable.Id_tab_list);
        if(tabList!=null){
            for (int i = 0; i < str.length; i++) {
                TabList.Tab tab = tabList.new Tab(getContext());
                tab.setText(str[i]);
                tabList.addTab(tab);
            }
           /* tabList.setTabLength(60); // 设置Tab的宽度
            tabList.setTabMargin(26); // 设置两个Tab之间的间距*/
            addHomeFraction();
            tabList.selectTabAt(0);
            tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
                @Override
                public void onSelected(TabList.Tab tab) {
                    // 当某个Tab从未选中状态变为选中状态时的回调
                    System.out.println("当某个Tab从未选中状态变为选中状态时的回调    " + tab.getPosition());
                    layoutShow(tab.getPosition());
                }

                @Override
                public void onUnselected(TabList.Tab tab) {
                    // 当某个Tab从选中状态变为未选中状态时的回调
                    System.out.println("当某个Tab从选中状态变为未选中状态时的回调");
                }
                @Override
                public void onReselected(TabList.Tab tab) {
                    // 当某个Tab已处于选中状态，再次被点击时的状态回调
                    System.out.println("当某个Tab已处于选中状态，再次被点击时的状态回调");
                }
            });
        }
    }

    private FractionManager getFractionManager() {
        return ((FractionAbility)getAbility()).getFractionManager();
    }


    private void  addHomeFraction(){
        getFractionManager()
                .startFractionScheduler()
                .add(ResourceTable.Id_mainstack, new MusicFraction())
                .submit();
    }


    public  void  layoutShow(int  code){
        switch (code){
            case TAB_MUSIC:
                getFractionManager()
                        .startFractionScheduler()
                        .replace(ResourceTable.Id_mainstack, new MusicFraction())
                        .submit();
                break;
            case TAB_FAVORITE:
                getFractionManager()
                        .startFractionScheduler()
                        .replace(ResourceTable.Id_mainstack, new FavoriteFraction())
                        .submit();
                break;
            default:
                break;
        }
    }

    //登陆页面
    private void startLoginAbility() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withDeviceId("")
                .withBundleName(getBundleName())
                .withAbilityName(LoginAbility.class)
                .build();
        intent.setOperation(operation);
        startAbility(intent);
    }


    private void initSystemUi() {

        //状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutConfig.MARK_TRANSLUCENT_STATUS);
        //导航栏 ActionBar
        getWindow().addFlags(WindowManager.LayoutConfig.MARK_TRANSLUCENT_NAVIGATION);
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
