package com.yjnull.cameragoogle.camera;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yjnull.cameragoogle.R;

import static android.R.attr.fraction;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private int mCount = 0;

    private FrameLayout testFrame;
    private TextView testTextView;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount <= FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * 100);
                        testTextView.scrollTo(scrollX, 0);
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                    } else {
                        mCount = 0;
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testFrame = (FrameLayout) findViewById(R.id.test_frame);

        testTextView = (TextView) findViewById(R.id.test_textview);
        testTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: test text view------=======");
                //mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO,DELAYED_TIME);
                Log.d(TAG, "onClick: test text view------=======  OVER");
            }
        });

        testTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "move, onTouch--------: ");
                return false;
            }
        });


        //FaceView faceView = new FaceView(this);
        //testFrame.addView(faceView);



        /*faceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "-------------faceView: is Clicked");
            }
        });

        Log.d(TAG, "-------------faceView: " + faceView.getScrollX() + ", " + faceView.getScrollY());
        //faceView.scrollTo(-200, -200);


        faceView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scroll_test));

        //ObjectAnimator.ofFloat(faceView, "translationX", 0, 100).setDuration(2000).start();
        Log.d(TAG, "-------------faceView After: " + faceView.getScrollX() + ", " + faceView.getScrollY());*/

    }
}
