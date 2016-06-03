package com.wyl.wallpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.wyl.wallpager.R;
import com.wyl.wallpager.UrlUtils;
import com.wyl.wallpager.adapter.RecommendFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by samsung on 2016/5/30.
 */
public class RecommendFragment extends Fragment {
    //int[] imgs={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private ArrayList<Fragment> fraglist = new ArrayList<>();
//    String[] titles = {"re", "new", "ran"};
    @Bind(R.id.tablayout_1)
    TabLayout tablayout;
    @Bind(R.id.vp)
    ViewPager vp;
    private FragmentManager fm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getActivity().getSupportFragmentManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recommend, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initListData();
        setAdapter();
        tablayout.setupWithViewPager(vp);
        tablayout.getTabAt(0).setText("推荐");
        tablayout.getTabAt(1).setText("热门");
        tablayout.getTabAt(2).setText("随机");
    }

    private void setAdapter() {
        RecommendFragmentPagerAdapter adapter = new RecommendFragmentPagerAdapter(fm, fraglist);
        vp.setAdapter(adapter);
    }

    private void initListData() {
        fraglist.add(RecommendItem.getInstance(UrlUtils.RECOMMEND_NEAR));
        fraglist.add(RecommendItem.getInstance(UrlUtils.RECOMMEND_HOT));
        fraglist.add(RecommendItem.getInstance(UrlUtils.RECOMMEND_RANDOM));
    }

}

