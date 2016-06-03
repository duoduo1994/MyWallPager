package com.wyl.wallpager.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wyl.wallpager.MyApp;
import com.wyl.wallpager.R;
import com.wyl.wallpager.bean.ImagesBean;
import com.wyl.wallpager.requests.MyRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/6/1.
 */
public class RecommendItem extends Fragment {
    private static final String TAG = "RecommendItem";
    @Bind(R.id.rv)
    RecyclerView rv;
    MyCvAdapter madapter;
    private RequestQueue queue;
    List<ImagesBean.DataBean.WallpaperListInfoBean> list;
    private ImageLoader loader;

    //封装一个方法，方便传入网址
    // 目的:
    public static RecommendItem getInstance(String url) {
        RecommendItem fragment = new RecommendItem();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
        loader = ((MyApp) getActivity().getApplication()).getLoader();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommended_item, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //init 控件
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //联网下载数据并在下载完成后更新页面
        //设置recyclerview的适配器
        initNetData();
        setAdapter();
    }

    private void setAdapter() {
        list = new ArrayList<>();
        madapter = new MyCvAdapter();
        rv.setAdapter(madapter);
    }

    private void initNetData() {
        MyRequest<ImagesBean> request = new MyRequest<>(ImagesBean.class, getArguments().getString("url"),
                new Response.Listener<ImagesBean>() {
                    @Override
                    public void onResponse(ImagesBean response) {
                        list = response.getData().getWallpaperListInfo();
                        madapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "出错了", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);

    }

    class MyCvAdapter extends RecyclerView.Adapter<MyCvAdapter.MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.rv_item, null));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
            DisplayImageOptions options = builder.cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .showImageForEmptyUri(R.mipmap.iv_error)
                    .showImageOnFail(R.mipmap.iv_error)
                    .showImageOnLoading(R.mipmap.load2)
                    .build();
            String url = list.get(position).getWallPaperMiddle();
            loader.displayImage(url, holder.iv, options);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv;

            public MyHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.imageView2);
            }
        }
    }
}
