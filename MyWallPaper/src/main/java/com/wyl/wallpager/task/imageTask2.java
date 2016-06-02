package com.wyl.wallpager.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.wyl.wallpager.util.HttpUitls;


/**
 * Created by Administrator on 2016/5/31.
 */
public class imageTask2 extends AsyncTask<String,Void,Bitmap> {
    private Bitmap bm;
    public interface imgCallback{
        void doCallback(Bitmap bitmap);
    }
    imgCallback callback;
    public imageTask2(imgCallback callback){
        this.callback=callback;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        String url=params[0];
        byte[] imgdata = HttpUitls.doGet(url);
        bm= BitmapFactory.decodeByteArray(imgdata,0,imgdata.length);
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null){
            callback.doCallback(bitmap);
        }
    }
}
