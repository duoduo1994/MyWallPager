package com.wyl.wallpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.wyl.wallpager.R;
import com.wyl.wallpager.UrlUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by samsung on 2016/5/30.
 */
public class RecommendFragment extends Fragment {
    //FragmentManager fm;
    MyFragmentAdapter myFAdapter;
    //int[] imgs={R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher};
    private ArrayList<Fragment> fraglist=new ArrayList<>();
    String[] titles={"最新","热门","随机"};
    @Bind(R.id.tablayout_1)
    TabLayout tablayout;
    @Bind(R.id.vp)
    ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  View view = inflater.inflate(R.layout.fragment_recommend, null);
        View view = inflater.inflate(R.layout.fragment_recommend,null,false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initListData();
        setAdapter();
        tablayout.setupWithViewPager(vp);
        myFAdapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setAdapter() {
        myFAdapter=new MyFragmentAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(myFAdapter);
    }

    private void initListData() {
        fraglist.add(RecommendItem.getInstance(UrlUtils.RECOMMEND_NEAR));
        fraglist.add(RecommendItem.getInstance(UrlUtils.RECOMMEND_HOT));
        fraglist.add(RecommendItem.getInstance(UrlUtils.RECOMMEND_RANDOM));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    class MyFragmentAdapter extends FragmentPagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fraglist.get(position);
        }

        @Override
        public int getCount() {
            return fraglist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }



}
