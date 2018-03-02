package com.yjnull.cameragoogle.camera.chapter3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yjnull.cameragoogle.R;
import com.yjnull.cameragoogle.camera.CameraActivity;
import com.yjnull.cameragoogle.camera.utils.MyUtils;

import java.util.ArrayList;

public class DemoActivity_1 extends AppCompatActivity {
    private static final String TAG = "DemoActivity_1";
    private HorizontalScrollViewEx mListContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_1);

        Log.d(TAG, "onCreate: ");
        initView();

        CircleView cv = (CircleView) findViewById(R.id.circle_view);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Log.d(TAG, "onClick: " +Environment.getExternalStorageDirectory().getPath());
                throw new RuntimeException("自定义异常: 这是自己抛出的异常");*/

                Notification notification = new Notification();
                notification.icon = R.mipmap.ic_launcher;
                notification.tickerText = "hello world";
                notification.when = System.currentTimeMillis();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Intent intent = new Intent(DemoActivity_1.this, CameraActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(DemoActivity_1.this,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //notification.setLatestEventInfo(DemoActivity_1.this, "chapter_5", "this is notification.", pendingIntent);

                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(1, notification);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ------");
        mListContainer.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onStart: " + mListContainer.getMeasuredWidth() + ", " + mListContainer.getMeasuredHeight());
            }
        });

        ViewTreeObserver observer = mListContainer.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.d(TAG, "onGlobalLayout: " + mListContainer.getMeasuredWidth() + ", " + mListContainer.getMeasuredHeight());
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: hasFocus : " + hasFocus);
        if (hasFocus) {
            Log.d(TAG, "onWindowFocusChanged: " + mListContainer.getMeasuredWidth() + ", " + mListContainer.getMeasuredHeight());
        }
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255/(i+1), 255/(i+1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }

        //此时拿到的View的 宽、高 均为 0
        Log.d(TAG, "initView: " + mListContainer.getMeasuredWidth() + ", " + mListContainer.getMeasuredHeight());
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < (int)(Math.random() * 40 + 10); i++) {
            datas.add("data " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DemoActivity_1.this, "click item " + position,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
