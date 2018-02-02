package com.yjnull.cameragoogle.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by yangy on 2018/2/2.
 */

public class GoogleFaceDetect implements Camera.FaceDetectionListener {
    private static final String TAG = "GoogleFaceDetect";
    private Context mContext;
    private Handler mHandler;

    public GoogleFaceDetect(Context mContext, Handler mHandler) {
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        Log.d(TAG, "onFaceDetection: 人脸检测回调");
        if (faces != null && faces.length > 0) {
            Message msg = mHandler.obtainMessage();
            msg.what = EventUtil.UPDATE_FACE_RECT;
            msg.obj = faces;
            msg.sendToTarget();
        }
    }
}
