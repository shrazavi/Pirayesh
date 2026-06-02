package com.shrazavi.pirayesh.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;

public class ActivityAbout extends AppCompatActivity {
    ImageView imgshamed;
    TextView txtemail, txttelegram;
    Button btnback;
    LinearLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_about);
        lay=(LinearLayout) findViewById(R.id.lay_about);
        txtemail = (TextView) findViewById(R.id.txt_about_email);
        txttelegram = (TextView) findViewById(R.id.txt_about_telegram);
        btnback = (Button) findViewById(R.id.btn_about_back);
        imgshamed = (ImageView) findViewById(R.id.img_about_shamed);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        txttelegram.setText("@shrazavi510");
        txttelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://t.me/Shrazavi510"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        imgshamed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://logo.saramad.ir/verify.aspx?CodeShamad=1-1-869641-63-0-2"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityTimework.this, ActivityOption.class);
//                ActivityTimework.this.startActivity(intent);
                ActivityAbout.this.finish();
            }
        });
        txtemail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) G.context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("pirayesh_email", txtemail.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ActivityAbout.this, "کپی شد", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }
}
