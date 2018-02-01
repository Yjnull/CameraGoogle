package com.yjnull.cameragoogle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yjnull.cameragoogle.camera.CameraActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private CameraPreview mPreview;

    private FrameLayout cameraPreView;
    private ImageView mediaPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //权限控制
        checkMyPermission();


        //initCamera();

        mediaPreview = (ImageView) findViewById(R.id.media_preview);

        Button capture = (Button) findViewById(R.id.button_capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPreview.takePicture(mediaPreview);
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });

        Button setCapture = (Button) findViewById(R.id.button_capture2);
        setCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.camera_preview,
                        new SettingsFragment()).commit();
            }
        });

        mediaPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowPhoto.class);
                intent.setDataAndType(mPreview.getOutputMediaFileUri(), mPreview.getOutputMediaFileType());
                startActivityForResult(intent, 0);
            }
        });

    }

    public void initCamera() {
        mPreview = new CameraPreview(this);
        mPreview.setFaceDetectionListener(new CameraPreview.FaceDetetedListener() {
            @Override
            public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                mPreview.takePicture(mediaPreview);
            }
        });
        cameraPreView = (FrameLayout) findViewById(R.id.camera_preview);
        cameraPreView.addView(mPreview);

        SettingsFragment.passCamera(mPreview.getCameraInstance());
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SettingsFragment.setDefault(PreferenceManager.getDefaultSharedPreferences(this));
        SettingsFragment.init(PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview = null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPreview == null) {
            //initCamera();
        }
    }


    private void checkMyPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    0x001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0x001:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
