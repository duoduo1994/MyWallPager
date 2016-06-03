package com.wyl.wallpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * ====================================
 * 作者：付明明
 * 版本：1.0
 * 创建日期：2016/6/1 18:02
 * 创建描述：
 * 更新日期：
 * 更新描述：
 * ====================================
 */
public class RecommendFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public RecommendFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
