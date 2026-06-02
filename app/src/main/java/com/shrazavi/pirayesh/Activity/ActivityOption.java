package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.shrazavi.pirayesh.R;

import org.json.JSONException;
import org.json.JSONObject;


public class ActivityOption extends AppCompatActivity {
    LinearLayout laysetting,laytime,laylokation,laytariff,laypassword,layblockuser,laylaw,layhelp,layabout,laylogout;
    SharedPreferences.Editor editor;
//    public SharedPreferences preferences;
    CardView crdvl;
    String bu ="";
    Button btnback;
    ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_VakilSocialmain);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_option);
        laysetting= (LinearLayout) findViewById(R.id.lay_op_setting);
        laytime=    (LinearLayout) findViewById(R.id.lay_op_time_work);
        laylokation=(LinearLayout) findViewById(R.id.lay_op_location);
//        laytariff=(LinearLayout) findViewById(R.id.lay_op_tariff);
        lay= findViewById(R.id.lay_option);
        layblockuser= (LinearLayout) findViewById(R.id.lay_op_blockuser);
        btnback = (Button) findViewById(R.id.btn_op_back);
        laylaw=    (LinearLayout) findViewById(R.id.lay_op_law);
        layhelp=    (LinearLayout) findViewById(R.id.lay_op_help);
        layabout=(LinearLayout) findViewById(R.id.lay_op_about);
        laylogout=(LinearLayout) findViewById(R.id.lay_op_logout);
        crdvl=(CardView) findViewById(R.id.crd_op_vl);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_VakilSocialmain);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
//        preferences = PreferenceManager.getDefaultSharedPreferences(ActivityOption.this);
        editor = BasicActivity.preferences.edit();
        bu = BasicActivity.bu;
        if(bu.equals("br")){
            crdvl.setVisibility(View.VISIBLE);
        }else {
            crdvl.setVisibility(View.GONE);
        }
        laysetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ActivityOption.this, ActivitySetting.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
//                ActivityOption.this.finish();
            }
        });
        laytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ActivityOption.this, ActivityTimework.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
//                ActivityOption.this.finish();
            }
        });
        laylokation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ActivityOption.this, ActivityLocation.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
//                ActivityOption.this.finish();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityOption.this, ActivityMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityOption.this.startActivity(intent);
                ActivityOption .this.finish();
            }
        });
//        laytariff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent3 = new Intent(ActivityOption.this, ActivityTariff.class);
////                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                startActivity(intent3);
////                ActivityOption.this.finish();
//            }
//        });
//        laypassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent4 = new Intent(ActivityOption.this, ActivityPassword.class);
////                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//////                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                ActivityOption.this.startActivity(intent4);
////                ActivityOption.this.finish();
//            }
//        });
        layblockuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ActivityOption.this, ActivityBlockuser.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
//                ActivityOption.this.finish();
            }
        });
        laylaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ActivityOption.this, ActivityLaw.class);
                intent2.putExtra("status", "option");
                intent2.putExtra("sx", BasicActivity.sx);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
//                ActivityOption.this.finish();
            }
        });
        layhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(ActivityOption.this, ActivityHelp.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("sx", BasicActivity.sx);
                startActivity(intent2);
//                ActivityOption.this.finish();
            }
        });
        layabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ActivityOption.this, ActivityAbout.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
//                ActivityOption.this.finish();
            }
        });
        laylogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove("login");
                editor.remove("type");
                editor.remove("sx");
                editor.remove("userid");
                editor.remove("number");
                editor.remove("content");
                editor.remove("name");
                editor.remove("imgprofile");
                editor.apply();
                editor.commit();
                Log.e("loginop", BasicActivity.preferences.getString("login", "not") + "");
                Intent intent6 = new Intent(ActivityOption.this, ActivityStart.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent6);
                android.os.Process.killProcess(android.os.Process.myPid());
//                ActivityOption.this.finish();

//                finish();
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
            }
        });





    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            Intent intent3 = new Intent(ActivityOption.this, ActivityMain.class);
            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent3);
            ActivityOption.this.finish();

        }
        return super.onKeyDown(keycode, event);
    }


}
