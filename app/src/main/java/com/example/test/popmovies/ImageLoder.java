package com.example.test.popmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Handler;

/**
 * Created by Administrator on 2015/12/8.
 */
public class ImageLoder {
    private LruCache<String, Bitmap> mCaches;
    public ImageLoder() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory /4;
        mCaches = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCaches.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return mCaches.get(url);
    }


    public void showImageByAsyncTask(ImageView imageView, String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap == null) {
            new ImageAsyncTask(imageView, url).execute(url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }


    public Bitmap getBitmapFromURL(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            in = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            connection.disconnect();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mImageView = null;
        private String url = null;

        ImageAsyncTask(ImageView imageView, String url) {
            mImageView = imageView;
            this.url = url;
        }


        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromURL(url);
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (mImageView.getTag().equals(url)) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
//    public Handler handler = new Handler() {
//        public void handleMessage(Message message) {
//            if (mImageView.getTag().equals(url)) {
//                mImageView.setImageBitmap((Bitmap) message.obj);
//            }
//        }
//    };

//    public Bitmap showImageByThread(ImageView imageView, final String url) {
//        mImageView = imageView;
//        this.url = url;
//            new Thread() {
//                @Override
//                public void run() {
//                    Bitmap bitmap = getBitmapFromURL(url);
//                    Message msg = Message.obtain();
//                    msg.obj = bitmap;
//                    handler.sendMessage(msg);
//                }
//            }.start();
//
//        return null;
//    }


}