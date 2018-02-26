package com.yjnull.cameragoogle.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by yangy on 2018/1/31.
 */

public class CameraPreview2 extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview2";
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview2(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void startFaceDetection(){
        if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
            mCamera.startFaceDetection();
        }
    }
    public void stopFaceDetection(){
        if (mCamera.getParameters().getMaxNumDetectedFaces() > 0) {
            mCamera.stopFaceDetection();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: 预览创建了--------------");
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: 预览改变了--------------");
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // use the setDisplayOrientation() 来设置预览图像旋转

        //start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

            startFaceDetection();

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        Log.d(TAG, "surfaceDestroyed: 预览Destroy了--------------");
    }

}
