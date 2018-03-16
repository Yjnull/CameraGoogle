package com.yjnull.cameragoogle.camera.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class MyUtils {

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void executeInThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 高效加载Bitmap
     */
    public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId,
                                                        int reqWidth, int reqHeigth)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();

        //1. 当此参数为true时, BitmapFactory 只会解析图片的原始宽/高,并不会真正加载图片
        options.inJustDecodeBounds = true;
        //2. 解析图片
        BitmapFactory.decodeResource(res, resId, options);

        //3. 计算 inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeigth);
        //4. shewei false 重新加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeigth) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > reqHeigth || width > reqWidth) {
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while ( (halfHeight / inSampleSize) >= reqHeigth
                    && (halfWidth / inSampleSize) >= reqWidth ) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
