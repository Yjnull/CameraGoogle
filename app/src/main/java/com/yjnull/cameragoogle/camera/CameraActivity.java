package com.yjnull.cameragoogle.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yjnull.cameragoogle.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.yjnull.cameragoogle.camera.CameraBaseInfo.cameraId;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";

    private Camera mCamera;
    private CameraPreview2 mPreview2;
    private FaceView faceView;
    private FrameLayout preview;

    private int isRange = -1; //人脸是否在范围内
    private boolean isTakePicture = true; //是否可以拍照

    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
        }
    };

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = FileUtils.getOutputMediaFile(1);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file : " + e.getMessage());
            }

            Toast.makeText(CameraActivity.this, "take picture Success!\n" + pictureFile.getPath(),
                    Toast.LENGTH_LONG).show();

        }
    };

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case EventUtil.UPDATE_FACE_RECT:
                    if (msg.obj != null) {
                        Camera.Face[] faces = (Camera.Face[]) msg.obj;
                        isRange = msg.arg1;
                        faceView.setFaces(faces);
                        faceView.setFaceColor(isRange);
                    } else {
                        faceView.clearFaces();
                    }

                    preview.removeView(faceView);
                    preview.addView(faceView);
                    //mCamera.takePicture(null, null, mPictureCallback);

                    break;
            }

            super.handleMessage(msg);
        }
    };

    //1. 检查是否存在摄像头硬件
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //2. 获得Camera对象
    public static Camera getCameraInstance() {
        return getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public static Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId);
            CameraBaseInfo.cameraId = cameraId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    //3. 检查camera 功能
    public void checkCameraFeatures() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();

            //白平衡、自动聚焦、照片品质
            /*for (Camera.Size size : params.getSupportedPictureSizes()) {
                Log.d(TAG, "  照片支持大小: " + size.width + "x" + size.height);
            }
            for (Camera.Size size : params.getSupportedPreviewSizes()) {
                Log.d(TAG, "  预览支持大小: " + size.width + "x" + size.height);
            }*/


            //params.setPictureSize(3200, 2400); //后置摄像头
            params.setPictureSize(2592, 1944); //前置摄像头
            //params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            params.setJpegThumbnailQuality(100);
            params.setJpegQuality(100);
            params.setPictureFormat(ImageFormat.JPEG);
            Log.d(TAG, "  照片大小: " + params.getPictureSize().width + "x" + params.getPictureSize().height);
            Log.d(TAG, "  预览大小: " + params.getPreviewSize().width + "x" + params.getPreviewSize().height);
            Log.d(TAG, "  聚焦: " + params.getFocusMode());
            //Log.d(TAG, "  聚焦区域: " + params.getFocusAreas().toString());
            Log.d(TAG, "  白平衡: " + params.getWhiteBalance());
            Log.d(TAG, "  照片缩略图大小: " + params.getJpegThumbnailSize().width + "x" + params.getJpegThumbnailSize().height);
            Log.d(TAG, "  照片缩略图品质: " + params.getJpegThumbnailQuality());
            Log.d(TAG, "  照片品质: " + params.getJpegQuality());
            //Log.d(TAG, "  测光区域: " + params.getMeteringAreas().toString());
            /*Log.d(TAG, "  最大测光区域数量: " + params.getMaxNumMeteringAreas());
            Log.d(TAG, "  最大聚焦区域数量: " + params.getMaxNumFocusAreas());
            if (params.getMaxNumMeteringAreas() > 0) {
                List<Camera.Area> meteringAreas = new ArrayList<>();

                Rect area1 = new Rect(-100, -100, 100, 100);
                meteringAreas.add(new Camera.Area(area1, 600));
                params.setFocusAreas(meteringAreas);

                Rect area2 = new Rect(800, -1000, 1000, -800);
                meteringAreas.add(new Camera.Area(area2, 400));

                params.setMeteringAreas(meteringAreas);

            }*/

            mCamera.setParameters(params);
            mCamera.setFaceDetectionListener(new GoogleFaceDetect(CameraActivity.this, mMainHandler));

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCamera = getCameraInstance();
        checkCameraFeatures();

        // 创建Preview and set it as the content of our activity
        mPreview2 = new CameraPreview2(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        faceView = new FaceView(this);
        preview.addView(mPreview2);


        //拍照
        Button captureBtn = (Button) findViewById(R.id.btn_capture);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null) {
                    mCamera.takePicture(mShutterCallback, null, mPictureCallback);
                }
            }
        });

        //改变摄像头
        Button changeBtn = (Button) findViewById(R.id.btn_change_camera);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.stopPreview();
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }

                if (CameraBaseInfo.cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    mCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
                } else if (CameraBaseInfo.cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    mCamera = getCameraInstance(Camera.CameraInfo.CAMERA_FACING_FRONT);
                }

                mPreview2 = null;
                mPreview2 = new CameraPreview2(CameraActivity.this, mCamera);
                preview.removeAllViews();
                preview.addView(mPreview2);

                checkCameraFeatures();


            }
        });

        Button checkFeatureBtn = (Button) findViewById(R.id.btn_check_feature);
        checkFeatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkCameraFeatures();
                startActivity(new Intent(CameraActivity.this, TestActivity.class));
            }
        });

        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myAutoFocus();
            }
        }, 1000);
        Button autoFocus = (Button) findViewById(R.id.btn_focus);
        autoFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAutoFocus();
            }
        });

    }

    public void myAutoFocus() {
        if (mCamera != null) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    Log.d(TAG, "onAutoFocus: " + success);
                    if (isRange == 0 && success) {
                        Log.d(TAG, "------------------------>  拍照了...");
                        isRange = -1;
                        if (isTakePicture) {
                            isTakePicture = false;

                            //延迟500ms拍照
                            mMainHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mCamera.takePicture(mShutterCallback, null, mPictureCallback);
                                }
                            }, 500);

                            //3s后才能重新拍照
                            mMainHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isTakePicture = true;
                                }
                            }, 3000);
                        }
                    }
                    mMainHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myAutoFocus();
                        }
                    }, 1500);

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null)
            mCamera = getCameraInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
}
