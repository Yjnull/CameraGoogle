package com.yjnull.cameragoogle;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowPhoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        Uri uri = getIntent().getData();
        ImageView imgView = (ImageView) findViewById(R.id.img_view);
        imgView.setImageURI(uri);

    }
}
