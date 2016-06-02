package com.wyl.wallpager.bean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class MyTypeBean {
    private String PicCategoryName;
    private String DescWords;
    private String CategoryPic;

    public String getPicCategoryName() {
        return PicCategoryName;
    }

    public void setPicCategoryName(String picCategoryName) {
        PicCategoryName = picCategoryName;
    }

    public String getDescWords() {
        return DescWords;
    }

    public void setDescWords(String descWords) {
        DescWords = descWords;
    }

    public String getCategoryPic() {
        return CategoryPic;
    }

    public void setCategoryPic(String categoryPic) {
        CategoryPic = categoryPic;
    }

    public MyTypeBean(String picCategoryName, String descWords, String categoryPic) {

        PicCategoryName = picCategoryName;
        DescWords = descWords;
        CategoryPic = categoryPic;
    }


}
