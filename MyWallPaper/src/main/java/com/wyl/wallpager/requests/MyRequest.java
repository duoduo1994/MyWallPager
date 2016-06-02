package com.wyl.wallpager.requests;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/5/31.
 */
public class MyRequest<T> extends Request<T> {
    private Class<T> clazz;
    public Response.Listener<T> listener;
    public MyRequest(Class<T> clazz, String url, Response.Listener<T> listener, Response.ErrorListener errorlistener) {
        this(clazz,Method.GET, url,listener ,errorlistener);
    }
    public MyRequest(Class<T> clazz, int method, String url, Response.Listener<T> listener, Response.ErrorListener errorlistener) {
        super(method, url,errorlistener);
        this.clazz=clazz;
        this.listener=listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String s ;
        try {
            s = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            s = new String(response.data);
        }
        T t = new Gson().fromJson(s,clazz);
        return Response.success(t,HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        if(listener!=null){
            listener.onResponse(response);
        }
    }

    @Override
    protected void onFinish() {
        listener=null;
        super.onFinish();

    }
}
