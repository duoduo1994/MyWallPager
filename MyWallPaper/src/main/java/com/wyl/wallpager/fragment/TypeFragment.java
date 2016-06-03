package com.wyl.wallpager.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wyl.wallpager.R;
import com.wyl.wallpager.bean.TypeBean;
import com.wyl.wallpager.requests.MyRequest;
import com.wyl.wallpager.MyApp;
import com.wyl.wallpager.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//import adapter.MyTypeAdapter;

/**
 * Created by samsung on 2016/5/30.
 * 分类页面
 * 显示：
 * 一个标题栏，一个ListView
 */
public class TypeFragment extends Fragment {
    private static final String TAG = "TypeFragment";
    private RequestQueue queue;
    List<TypeBean> list = new ArrayList<>();
    MyTypeAdapter myAdapter;
    ImageLoader loader;
    @Bind(R.id.type_lv)
    ListView typeLv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
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
        loader = ((MyApp) getActivity().getApplication()).getLoader();
        //3. 通过ButterKnife初始化控件

        //4. 启动异步任务进行两网读取数据并解析
        //(解析时必须用到GsonFormat插件)
        initData();
        //5. 封装并设置适配器
        // （适配器必须使用外部类，适配器中的ViewHolder必须用ButterKnife创建）
        setAdapter();
    }

    private void setAdapter() {
        myAdapter = new MyTypeAdapter();
        typeLv.setAdapter(myAdapter);
    }

    private void initData() {
        StringRequest request = new StringRequest(UrlUtils.TYPE_ALL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        String msg = jsonObject.getString("msg");
                        if (msg.contains("操作成功")) {
                            String data = jsonObject.getString("data");
//                            Log.i(TAG, "onResponse: "+data);
                            list = JSON.parseArray(data, TypeBean.class);
//                            for (TypeBean bean :
//                                    list) {
//                                Log.i(TAG, "onResponse: "+bean.toString());
//                            }
                            myAdapter.notifyDataSetChanged();

                        } else {
                            Log.i(TAG, "onResponse: Json数据获取失败");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.typefragment_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }

            holder.tvBig.setText(list.get(position).getPicCategoryName());
            holder.tvSmall.setText(list.get(position).getDescWords());
            String categoryPicUrl = list.get(position).getCategoryPic();

            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
            DisplayImageOptions options = builder.cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(10))
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .showImageForEmptyUri(R.mipmap.iv_error)
                    .showImageOnFail(R.mipmap.iv_error)
                    .showImageOnLoading(R.mipmap.iv_loading)
                    .build();
            final ProgressBar pbProgress = holder.pbProgress;

            loader.displayImage(categoryPicUrl, holder.typeIv, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    pbProgress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    pbProgress.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    pbProgress.setVisibility(View.GONE);
                }
            });
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
        @Bind(R.id.pb_progress)
        ProgressBar pbProgress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
