package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Adapter.RecyclerAdapterHelp;

import com.shrazavi.pirayesh.DataClass.Helpmore;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

public class ActivityHelp extends AppCompatActivity {
    static Retrofitinformation RI;
//    public SharedPreferences preferences;
    static ArrayList<Helpmore> title = new ArrayList<>();
    Button btnok,btnback;
    String myid;
    static String content;
    String enk;
    String bu,sx="";
    SecretKeySpec Key;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapterHelp recyclerAdapterHelp;
    CheckBox checkhelp;
TextView txtckeck,txttitle;
ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_law);
        title.clear();
        title.clone();
        title.add(new Helpmore("راهنمای ثبت نام", 0, "signup"));
        title.add(new Helpmore("راهنمای ورود", 0, "login"));
        title.add(new Helpmore("راهنمای کاربران", 0, "user"));
        title.add(new Helpmore("راهنمای وکلا", 0, "lawyer"));

        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        myid = BasicActivity.userid;
        bu = BasicActivity.bu;
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
        Log.e("info vu=", bu + "");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + myid + "-" + getResources().getString(R.string.developer), enk);
        content=BasicActivity.content;

        btnok = (Button) findViewById(R.id.btn_law_ok);
        btnback = (Button) findViewById(R.id.btn_law_back);
        checkhelp = (CheckBox) findViewById(R.id.check_law);
        txtckeck = (TextView) findViewById(R.id.txt_law_check);
        txttitle = (TextView) findViewById(R.id.txt_law_title);
        lay=findViewById(R.id.lay_law);
        sx = (String) getIntent().getExtras().get("sx");
        if (sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Law);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        txttitle.setText("راهنمای پیرایش");

        txtckeck.setVisibility(View.GONE);
        btnok.setVisibility(View.GONE);
        checkhelp.setVisibility(View.GONE);
//        checklaw.setChecked(false);
//        checklaw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//             @Override
//             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                 if (isChecked) {
//                     btnok.setVisibility(View.VISIBLE);
//                 } else {
//                     btnok.setVisibility(View.GONE);
//                 }
//
//             }
//         }
//        );
//        btnok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(G.context, ActivityMain.class);
//                startActivity(intent);
//                ActivityHelp.this.finish();
//                ActivityMain.restart(G.context,0);
//            }
//        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityHelp.this, ActivityOption.class);
//                ActivityHelp.this.startActivity(intent);
                ActivityHelp.this.finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rec_law_lawyer);
        linearLayoutManager = new LinearLayoutManager(G.context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapterHelp = new RecyclerAdapterHelp(ActivityHelp.this, title));

    }


}
