package com.wyl.wallpager.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyl.wallpager.R;
import com.wyl.wallpager.fragment.MoreFragment;
import com.wyl.wallpager.fragment.RecommendFragment;
import com.wyl.wallpager.fragment.SearchFragment;
import com.wyl.wallpager.fragment.TypeFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {
    ArrayList<Fragment> list=new ArrayList<>();
    int[] res={R.drawable.recommend_item,
            R.drawable.type_item,
            R.drawable.search_item,
            R.drawable.more_item,};
    String[] names={"推荐","分类","搜索","更多"};
    String[] titles={"壁纸精选","分类","","更多"};
    FragmentManager manager;



    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.rl_title_bar)
    RelativeLayout rl_title_bar;
    @Bind(R.id.tv_title)
    TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //1. 通过ButterKnife初始化控件

        //2. 创建一个集合，初始化4个Fragment对象
        init();
        //3. 在屏幕上添加一个默认显示的Fragment
        manager=getSupportFragmentManager();
        manager.beginTransaction().add(R.id.framelayout,list.get(0)).commit();
        //4. 监控下方标签的监听事件，在监听中进行4个fragment之间show和hide判断
        //通过此判断实现切换效果
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int num=Integer.parseInt(tab.getTag().toString());
                if(num==2){
                    rl_title_bar.setVisibility(View.GONE);
                }else {
                    rl_title_bar.setVisibility(View.VISIBLE);
                    tv_title.setText(titles[num]);
                }

                if(!list.get(num).isAdded()){
                    manager.beginTransaction().add(R.id.framelayout, list.get(num)).commit();
                }
                manager.beginTransaction().show(list.get(num)).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int num=Integer.parseInt(tab.getTag().toString());
                manager.beginTransaction().hide(list.get(num)).commit();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //5. 不需要在此类中添加代码了，只需要针对4个对应的Fragment进行处理
    }

    private void init() {
        list.add(new RecommendFragment());
        list.add(new TypeFragment());
        list.add(new SearchFragment());
        list.add(new MoreFragment());
        for (int i = 0; i <list.size() ; i++) {
            TabLayout.Tab tab=tablayout.newTab().setIcon(res[i]).setTag(i).setText(names[i]);
            tablayout.addTab(tab);
        }
    }

}
