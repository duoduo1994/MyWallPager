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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.wyl.wallpager.R;
import com.wyl.wallpager.requests.MyRequest;
import com.wyl.wallpager.task.imageTask2;
import com.wyl.wallpager.bean.ImagesBean;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/1.
 */
public class RecommendItem extends Fragment {
    ImageLoader loader;

    private static final String TAG = "RecommendItem";
    @Bind(R.id.rv)
    RecyclerView rv;
    MyCvAdapter madapter;
    private RequestQueue queue;
    ArrayList<Bitmap> imagelist = new ArrayList<>();
    //封装一个方法，方便传入网址
    // 目的:
    public static RecommendItem getInstance(String url) {
        RecommendItem fragment = new RecommendItem();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommended_item, null);
        ButterKnife.bind(this, view);
        queue= Volley.newRequestQueue(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //init 控件
        rv.setLayoutManager(new GridLayoutManager(getActivity(),3));

        //联网下载数据并在下载完成后更新页面
        //设置recyclerview的适配器
        initNetData();
        setAdapter();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setAdapter() {
        madapter=new MyCvAdapter();
        rv.setAdapter(madapter);
    }

    private void initNetData() {
//        Log.i("2222222",getArguments().getString("url"));
        MyRequest<ImagesBean> request=new MyRequest<ImagesBean>(ImagesBean.class, getArguments().getString("url"),
                new Response.Listener<ImagesBean>() {
                    @Override
                    public void onResponse(ImagesBean response) {
                       List<ImagesBean.DataBean.WallpaperListInfoBean> data= response.getData().getWallpaperListInfo();
                        for (int i = 0; i < data.size(); i++) {
                            new imageTask2(new imageTask2.imgCallback() {
                                @Override
                                public void doCallback(Bitmap bitmap) {
                                    imagelist.add(bitmap);
                                }
                            }).execute(data.get(i).getWallPaperMiddle());
                            madapter.notifyDataSetChanged();
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

    class MyCvAdapter extends RecyclerView.Adapter<MyCvAdapter.MyHolder>{

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(getActivity()).inflate(R.layout.rv_item,parent));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.iv.setImageBitmap(imagelist.get(position));
        }

        @Override
        public int getItemCount() {
            return imagelist.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{
            private ImageView iv;

           public MyHolder(View itemView) {
               super(itemView);
               iv= (ImageView) itemView.findViewById(R.id.imageView2);
           }
       }
    }
}
