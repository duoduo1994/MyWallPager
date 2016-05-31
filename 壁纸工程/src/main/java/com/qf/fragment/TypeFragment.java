package com.qf.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Task.ImageTask;
import Task.imageTask2;
import Utils.HttpUitls;
import Utils.UrlUtils;
import bean.MyTypeBean;
import bean.TypeBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.com.bizhi1602.R;
import requests.MyRequest;

//import adapter.MyTypeAdapter;

/**
 * Created by samsung on 2016/5/30.
 * 分类页面
 * 显示：
 * 一个标题栏，一个ListView
 */
public class TypeFragment extends Fragment {
    private RequestQueue queue;
    ArrayList<TypeBean> list = new ArrayList<>();
    ArrayList<MyTypeBean> mylist=new ArrayList<>();
    MyTypeAdapter myAdapter;
    ImageView iv;


    @Bind(R.id.type_lv)
    ListView typeLv;
   private Bitmap bm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue= Volley.newRequestQueue(getActivity());
    }

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


        initData();
        //5. 封装并设置适配器
        // （适配器必须使用外部类，适配器中的ViewHolder必须用ButterKnife创建）
        setAdapter();

    }

    private void setAdapter() {
        myAdapter=new MyTypeAdapter();
        typeLv.setAdapter(myAdapter);
    }

    private void initData() {
        MyRequest<TypeBean> request=new MyRequest<TypeBean>(TypeBean.class, UrlUtils.TYPE_ALL_URL,
                new Response.Listener<TypeBean>() {
                    @Override
                    public void onResponse(TypeBean response) {
                        List<TypeBean.DataBean> data = response.getData();
                        for (int i = 0; i < data.size(); i++) {
                            mylist.add(new MyTypeBean(
                                    data.get(i).getPicCategoryName(),
                            data.get(i).getDescWords(),
                                    data.get(i).getCategoryPic()));
                            myAdapter.notifyDataSetChanged();
                        }
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

    class MyTypeAdapter extends BaseAdapter {

        @Bind(R.id.type_iv)
        ImageView typeIv;
        @Bind(R.id.tv_big)
        TextView tvBig;
        @Bind(R.id.tv_small)
        TextView tvSmall;

        @Override
        public int getCount() {
            return mylist.size();
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
           // MyTypeBean mtbean= (MyTypeBean) getItem(position);
           // View view;
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.typefragment_item, null);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder= (ViewHolder) convertView.getTag();
                holder.tvBig.setText(mylist.get(position).getPicCategoryName());
                holder.tvSmall.setText(mylist.get(position).getDescWords());

               //new ImageTask(holder.typeIv).execute(mylist.get(position).getCategoryPic());
                new imageTask2(new imageTask2.imgCallback() {
                    @Override
                    public void doCallback(Bitmap bitmap) {
                        holder.typeIv.setImageBitmap(bitmap);
                    }
                }).execute(mylist.get(position).getCategoryPic());
            }
            return convertView;
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
