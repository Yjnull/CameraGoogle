package com.yjnull.cameragoogle.camera.chapter3;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjnull.cameragoogle.R;
import com.yjnull.cameragoogle.camera.utils.MyUtils;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * 注意权限问题！！！
 */
public class MyWindowActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final String TAG = "MyWindowActivity";
    
    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_window);

        ImageView bitmapImg = (ImageView) findViewById(R.id.bitmap_img);
        bitmapImg.setImageBitmap(MyUtils.decodeSampleBitmapFromResource(getResources(),
                R.mipmap.pay_type_alipay, 50, 50));

        /*Dialog dialog = new Dialog(this.getApplicationContext());
        TextView tv = new TextView(this);
        tv.setText("My Dialog");
        dialog.setContentView(tv);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED, 0);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();*/

        /*mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mFloatingButton = new Button(this);
        mFloatingButton.setText("click me");
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.gravity = Gravity.START | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mFloatingButton.setOnTouchListener(this);
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: AAAAAAAAAAAAAAAAAAAA");
                *//*new MyAsyncTask("Async#1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new MyAsyncTask("Async#2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new MyAsyncTask("Async#3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*//*
                Intent service = new Intent(MyWindowActivity.this, MyIntentService.class);
                service.putExtra("task_action", "com.ryg.action.TASK1");
                startService(service);
                service.putExtra("task_action", "com.ryg.action.TASK2");
                startService(service);
                service.putExtra("task_action", "com.ryg.action.TASK3");
                startService(service);
            }
        });
        mWindowManager.addView(mFloatingButton, mLayoutParams);*/

    }

    @Override
    protected void onDestroy() {
        try {
            Log.d(TAG, "onDestroy: mWindowManager.removeView(mFloatingButton);");
            if (mFloatingButton != null)
                mWindowManager.removeView(mFloatingButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        Log.d(TAG, "onTouch: rawX: "+ rawX + " , rawY: " + rawY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        
        return false;
    }

    private static class MyAsyncTask extends AsyncTask<URL, Integer, String>{

        private String mName = "";

        MyAsyncTask(String mName) {
            super();
            this.mName = mName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mName;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d(TAG, aVoid + " onPostExecute: " + df.format(new Date()));
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
