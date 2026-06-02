package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.shrazavi.pirayesh.DownloadManager;
import com.shrazavi.pirayesh.FullImage;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.DownloadManager;
import com.shrazavi.pirayesh.FullImage;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ActivityImageView extends AppCompatActivity {
    FullImage imgchatview;
    String imgurl;
    Button btndownload,btnback;
    String path;
    String ac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_imageview);
        imgchatview=(FullImage) findViewById(R.id.img_chat_view);
        imgurl = (String) getIntent().getExtras().get("imgurl");
//        ac = (String) getIntent().getExtras().get("ac");
        Log.e("imgurl",imgurl);
        btndownload=(Button) findViewById(R.id.btn_iv_download);
        btnback=(Button) findViewById(R.id.btn_iv_back);
        if((Boolean) getIntent().getExtras().get("down")){
            btndownload.setVisibility(View.VISIBLE);
        }else {
            btndownload.setVisibility(View.GONE);
        }
        Picasso.get().load(imgurl).into(imgchatview);
//        Glide.with(ActivityImageView.this).load(imgurl).placeholder(R.drawable.backdown).error(R.drawable.backdown).into(imgchatview);


        if (Build.VERSION.SDK_INT >= 30) {
            path= G.DIR_IMAGEV30;
        }else {
            path= G.DIR_IMAGE;
        }
        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadManager(ActivityImageView.this, imgurl,path);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityImageView.this.finish();
            }
        });
    }
}
