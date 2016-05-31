package bean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class TypeBean {
    private String PicCategoryName;
    private String DescWords;
    private String CategoryPic;

    public TypeBean(String picCategoryName, String descWords, String categoryPic) {
        PicCategoryName = picCategoryName;
        DescWords = descWords;
        CategoryPic = categoryPic;
    }

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

    @Override
    public String toString() {
        return "TypeBean{" +
                "PicCategoryName='" + PicCategoryName + '\'' +
                ", DescWords='" + DescWords + '\'' +
                ", CategoryPic='" + CategoryPic + '\'' +
                '}';
    }
}
