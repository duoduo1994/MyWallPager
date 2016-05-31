package Task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Utils.HttpUitls;
import bean.TypeBean;

/**
 * Created by Administrator on 2016/5/31.
 */
public class TextTask extends AsyncTask<String,Void,ArrayList<TypeBean>> {
    @Override
    protected ArrayList<TypeBean> doInBackground(String... params) {
        ArrayList<TypeBean> list=new ArrayList<>();
        String s= HttpUitls.doGetToString(params[0]);
        try {
            JSONObject jo=new JSONObject(s);
            JSONArray ja=jo.getJSONArray("data");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj=ja.getJSONObject(i);
                TypeBean tb=new TypeBean(obj.getString("PicCategoryName"),obj.getString("DescWords"),obj.getString("CategoryPic"));
                Log.i("===",tb.toString());
                list.add(tb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
