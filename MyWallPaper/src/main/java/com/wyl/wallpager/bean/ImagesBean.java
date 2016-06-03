package com.wyl.wallpager.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class ImagesBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private List<WallpaperListInfoBean> WallpaperListInfo;


        public List<WallpaperListInfoBean> getWallpaperListInfo() {
            return WallpaperListInfo;
        }


        public static class WallpaperListInfoBean {

            private String WallPaperMiddle;


            public String getWallPaperMiddle() {
                return WallPaperMiddle;
            }

            public void setWallPaperMiddle(String WallPaperMiddle) {
                this.WallPaperMiddle = WallPaperMiddle;
            }

            @Override
            public String toString() {
                return "WallpaperListInfoBean{" +
                        "WallPaperMiddle='" + WallPaperMiddle + '\'' +
                        '}';
            }
        }
    }
}
