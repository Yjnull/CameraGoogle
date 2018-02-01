package com.yjnull.cameragoogle.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yjnull.cameragoogle.MainActivity;
import com.yjnull.cameragoogle.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.yjnull.cameragoogle.camera.FileUtils.getOutputMediaFile;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";

    private Camera mCamera;
    private CameraPreview2 mPreview2;

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

    //1. 检查是否存在摄像头硬件
    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //2. 获得Camera对象
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
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

            List<String> focusModes = params.getSupportedFocusModes();
            for (String modes : focusModes) {
                Log.d(TAG, "getSupportedFocusModes: " + modes);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mCamera = getCameraInstance();

        // 创建Preview and set it as the content of our activity
        mPreview2 = new CameraPreview2(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview2);


        //拍照
        Button captureBtn = (Button) findViewById(R.id.btn_capture);
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null) {
                    mCamera.takePicture(null, null, mPictureCallback);
                }
            }
        });

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
