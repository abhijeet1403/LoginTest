package com.example.abhijeet.logintest;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.security.PublicKey;

/**
 * Created by abhij on 26-01-2016.
 */
public class VolleySingleton {
    private ImageLoader imageLoader;
    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;


    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader =new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache <String,Bitmap> cache= new LruCache<>((int)(Runtime.getRuntime().maxMemory())/1024/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }

        return sInstance;
    }
    public void tm(){

    }
    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
