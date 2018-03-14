package com.yjnull.cameragoogle.camera.chapter3;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by yangy on 2018/3/13.
 */

public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.d(TAG, "onHandleIntent: " + action);
        SystemClock.sleep(3000);
        if ("com.ryg.action.TASK1".equals(action)){
            Log.d(TAG, "onHandleIntent: handle task: " + action);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: MyIntentService");
        super.onDestroy();
    }
}
