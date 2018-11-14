package com.rbu.erp_wms.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;


import com.rbu.erp_wms.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import it.sephiroth.android.library.picasso.LruCache;
import it.sephiroth.android.library.picasso.MemoryPolicy;
import it.sephiroth.android.library.picasso.Picasso;


/**
 * 加载图片工具类
 */
public class PicassoUtil {

    //内存缓存加载
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static void display(String url, ImageView iv) {
        if (TextUtils.isEmpty(url))
            return;
        Picasso.with(UiUtil.getContext()).load(url).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_CACHE)
                .withCache(new LruCache(4 * 1024 * 1024))
                .withDiskCache(new LruCache(4 * 1024 * 1024)).into(iv);

    }

    //列表图片处理
    public static void tailorDisplay(Context context, String url, ImageView iv) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(context)
                    .load(url)
                    .error(R.mipmap.def_img)
                    .resize(UiUtil.dp2px(UiUtil.getContext(), 60), UiUtil.dp2px(UiUtil
                            .getContext(), 60)).placeholder(R.mipmap.def_img)
                    .centerCrop()
                    .into(iv);
        }
    }

    //二维码图片处理
    public static void erweimaDisplay(String url, ImageView iv) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(UiUtil.getContext())
                    .load(url)
                    .error(R.mipmap.def_img)
                    .resize(UiUtil.dp2px(UiUtil.getContext(), 60), UiUtil.dp2px(UiUtil
                            .getContext(), 60)).placeholder(R.mipmap.def_img)
                    .centerCrop()
                    .into(iv);
        }
    }

    /**
     * 压缩本地图片
     */
    public static void tailorDisplay2(String url, ImageView iv, int height, int width) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(iv.getContext())
                    .load(url)
                    .error(R.mipmap.def_img)
                    .fade(20)
                    .config(Bitmap.Config.RGB_565)
                    .resize(UiUtil.dp2px(iv.getContext(), width), UiUtil.dp2px(iv.getContext(),
                            height)).placeholder(R.mipmap.def_img)
                    .centerCrop()
                    .into(iv);
        }
    }

    /**
     *请求网络图片
     */
    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getBitmapFromByte(byte[] temp){   //将二进制转化为bitmap
        if(temp != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        }else{
            return null;
        }
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
