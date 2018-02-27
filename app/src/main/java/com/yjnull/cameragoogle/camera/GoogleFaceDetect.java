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
        Message msg = mHandler.obtainMessage();
        msg.what = EventUtil.UPDATE_FACE_RECT;
        if (faces != null && faces.length > 0) {
            msg.obj = faces;
            msg.arg1 = isRangeFaceDetectFront(faces[0]) ? 0 : -1; //0代表在区域内，-1代表不再区域内
        } else {
            msg.obj = null;
        }
        msg.sendToTarget();
    }

    public boolean isRangeFaceDetectBack(Camera.Face face) {
        if ((face.rect.left >= -150 && face.rect.left <= 150) &&
                (face.rect.top >= -500 && face.rect.left <= -10) &&
                (face.rect.right >= 120 && face.rect.left <= 480) &&
                (face.rect.bottom >= 120 && face.rect.left <= 480)) {
            Log.d(TAG, "------------------------>  在区域内了..." );
            return true;
        } else
            return false;
    }

    public boolean isRangeFaceDetectFront(Camera.Face face) {
        if ((face.rect.centerX() >= -260 && face.rect.centerX() <= 40) &&
                (face.rect.centerY() >= -150 && face.rect.left <= 150)) {
            Log.d(TAG, "------------------------>  前置摄像头 在区域内了..." );
            return true;
        } else
            return false;
    }
}
