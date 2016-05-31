package com.qf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import Utils.UrlUtils;
import bean.ImagesBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.com.bizhi1602.R;
import requests.MyRequest;

/**
 * Created by samsung on 2016/5/30.
 */
public class RecommendFragment extends Fragment {
    private RequestQueue queue;
   // ArrayList<ImageView> list = new ArrayList<>();
    ArrayList<ImagesBean> imgBeanlist=new ArrayList<>();
    String[] titles={"最新","热门","随机"};
   // ArrayList<GridView> viewlist=new ArrayList<>();
    @Bind(R.id.tablayout_1)
    TabLayout tablayout;
    @Bind(R.id.vp)
    ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recommend, null);
        ButterKnife.bind(this, view);
        queue= Volley.newRequestQueue(getActivity());
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();
       // tablayout.setupWithViewPager(vp);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initData() {
        MyRequest<ImagesBean> request=new MyRequest<ImagesBean>(ImagesBean.class, UrlUtils.RECOMMEND_NEAR,
                new Response.Listener<ImagesBean>() {
                    @Override
                    public void onResponse(ImagesBean response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "出错了", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    class myPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
