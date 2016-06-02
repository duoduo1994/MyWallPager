package com.wyl.wallpager.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.wyl.wallpager.util.HttpUitls;


/**
 * Created by Administrator on 2016/5/31.
 */
public class ImageTask extends AsyncTask<String,Void,Bitmap> {
    ImageView iv;
    private Bitmap bm;

    public ImageTask(ImageView iv ) {
        this.iv = iv;
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
        iv.setImageBitmap(bm);
    }
}
