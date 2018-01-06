package com.vijay.demoapps.jsonresponseapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by vijay on 1/5/2018.
 */

public class NetworkOpertions {

    private static NetworkOpertions mInstance;
    private static final String TAG = NetworkOpertions.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private Context mContext;
    private ImageLoader mImageLoader;




    private NetworkOpertions(Context context) {
        this.mContext = context;
    }

    public RequestQueue getRequestQueue(Context context) {
        mContext = context;
        if(mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Context context, Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(context).add(request);
    }

    public <T> void addToRequestQueue(Context context, Request<T> request) {
        getRequestQueue(context).add(request);
    }

    public void cancelPendingRequests(Object object) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(object);
        }
    }

    public void updateImgRequestQueue() {
        mRequestQueue = getRequestQueue();

        if(mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue,
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap>
                                cache = new LruCache<String, Bitmap>(20);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
        }
    }

    public static synchronized NetworkOpertions getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkOpertions(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mContext.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        updateImgRequestQueue();
        return mImageLoader;
    }

}
