package com.qf.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.com.bizhi1602.R;

/**
 * Created by samsung on 2016/5/30.
 * 一个标题栏以及n各选项
 */
public class MoreFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //1. 将返回值修改，加载fragment_more的布局
        View view = inflater.inflate(R.layout.fragment_more, null);
        ButterKnife.bind(this, view);
        return view;
    }

    //2. 重写onViewCreated方法

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //3. 通过ButterKnife初始化控件
        //4. 修改控件上的文字
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
