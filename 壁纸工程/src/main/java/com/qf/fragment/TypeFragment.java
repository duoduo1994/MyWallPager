package com.qf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bean.TypeBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.com.bizhi1602.R;

//import adapter.MyTypeAdapter;

/**
 * Created by samsung on 2016/5/30.
 * 分类页面
 * 显示：
 * 一个标题栏，一个ListView
 */
public class TypeFragment extends Fragment {
    ArrayList<TypeBean> list = new ArrayList<>();
    MyTypeAdapter myAdapter;


    @Bind(R.id.type_lv)
    ListView typeLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //1. 设置布局
        View view = inflater.inflate(R.layout.fragment_type, null);
        ButterKnife.bind(this, view);
        return view;
    }

    //2. 重写onViewCreated方法

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //3. 通过ButterKnife初始化控件

        //4. 启动异步任务进行两网读取数据并解析
        //(解析时必须用到GsonFormat插件)

        //5. 封装并设置适配器
        // （适配器必须使用外部类，适配器中的ViewHolder必须用ButterKnife创建）


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class MyTypeAdapter extends BaseAdapter {

        @Bind(R.id.type_iv)
        ImageView typeIv;
        @Bind(R.id.tv_big)
        TextView tvBig;
        @Bind(R.id.tv_small)
        TextView tvSmall;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TypeBean typebean = (TypeBean) getItem(position);
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.typefragment_item, null);
                holder=new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder= (ViewHolder) view.getTag();
                holder.tvBig.setText(null);
                holder.tvSmall.setText(null);
                holder.typeIv.setImageBitmap(null);
            }
            return view;
        }


    }

    static class ViewHolder {
        @Bind(R.id.type_iv)
        ImageView typeIv;
        @Bind(R.id.tv_big)
        TextView tvBig;
        @Bind(R.id.tv_small)
        TextView tvSmall;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
