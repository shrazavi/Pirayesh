package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.shrazavi.pirayesh.Adapter.RecyclerAdapterLaw;

import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Lawmore;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.User;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import com.shrazavi.pirayesh.Util.MasterKeys;
import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLaw extends AppCompatActivity {
    public Retrofitinformation RI;
    public SharedPreferences preferences;
    static ArrayList<Lawmore> title = new ArrayList<>();
    Button btnok, btnback;
    String userid;
    String content;
    String enk;
    String bu;
    SecretKeySpec Key;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerAdapterLaw recyclerAdapterLaw;
    TextView txtcheck;
    CheckBox checklaw;
    String name = "", law = "", ostan = "", shahr = "", profile = "", experience = "", pass = "", number = "", data = "";
    int base = 0, type = 0;
    public String status = "";
    ConstraintLayout lay;
    String sx = "male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (sx.equals("male")) {
//            super.setTheme(R.style.Theme_Law);
//        } else {
//            super.setTheme(R.style.Theme_mainwomen);
//        }
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        setContentView(R.layout.activity_law);
        title.clear();
        title.clone();
        title.add(new Lawmore("قوانین آرایشگران", 0, "lawyer"));
        title.add(new Lawmore("قوانین کاربران", 0, "user"));
        title.add(new Lawmore("قوانین پرداخت", 0, "pay"));
        title.add(new Lawmore("قوانین تعیین وقت قبلی", 0, "reserve"));
        String masterKeyAlias = null;
        btnok = (Button) findViewById(R.id.btn_law_ok);
        btnback = (Button) findViewById(R.id.btn_law_back);
        checklaw = (CheckBox) findViewById(R.id.check_law);
        txtcheck = (TextView) findViewById(R.id.txt_law_check);
        lay = findViewById(R.id.lay_law);
        sx = (String) getIntent().getExtras().get("sx");
        status = (String) getIntent().getExtras().get("status");
        btnok.setTypeface(G.face);
        if (sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Law);
        } else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            preferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKeyAlias,
                    G.context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//       preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
//        myid = preferences.getString("username", "empty");
//        vu = preferences.getString("type", "empty");
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
        Log.e("status = ", status + "");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + myid + "-" + getResources().getString(R.string.developer), enk);
//        content = SymmetricAlgorithmAES.encrypt(preferences.getString("IM", "not"), preferences.getString("k5", "not"));

        btnback.setVisibility(View.GONE);

        switch (status) {

            case "sign":
                checklaw.setVisibility(View.VISIBLE);
                txtcheck.setVisibility(View.VISIBLE);
                btnback.setVisibility(View.GONE);
                number = (String) getIntent().getExtras().get("number");
                name = (String) getIntent().getExtras().get("name");
                bu = (String) getIntent().getExtras().get("bu");
                content = (String) getIntent().getExtras().get("content");
                userid = (String) getIntent().getExtras().get("userid");
                profile = (String) getIntent().getExtras().get("profile");
                break;
            case "login":

                SharedPreferences.Editor editor = preferences.edit();
                number = (String) getIntent().getExtras().get("number");
                name = (String) getIntent().getExtras().get("name");
                bu = (String) getIntent().getExtras().get("bu");
                content = (String) getIntent().getExtras().get("content");
                userid = (String) getIntent().getExtras().get("userid");
                profile = (String) getIntent().getExtras().get("profile");
                getuser(content,number,userid,bu);
                if(law.equals("1")) {
                    editor.putString("login", "ok");
                    editor.putString("userid", userid);
                    editor.putString("number", number);
                    editor.putString("type", bu);
                    editor.putString("sx", sx);
                    editor.putString("content", content);
                    editor.putString("name", name);
                    editor.putString("profile", G.nodeurl +profile);
                    editor.commit();
                    Intent intent2 = new Intent(ActivityLaw.this, ActivityMain.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    ActivityLaw.this.finish();
                }else {

                }
                break;
            case "option":
                btnback.setVisibility(View.VISIBLE);
                checklaw.setVisibility(View.GONE);
                txtcheck.setVisibility(View.GONE);
                number = BasicActivity.number;
                bu = BasicActivity.bu;
                content = BasicActivity.content;
                userid = BasicActivity.userid;
                break;
            case "name":
                btnback.setVisibility(View.VISIBLE);
                checklaw.setVisibility(View.GONE);
                txtcheck.setVisibility(View.GONE);
                number = (String) getIntent().getExtras().get("number");
                name = (String) getIntent().getExtras().get("name");
                bu = (String) getIntent().getExtras().get("bu");
                content = (String) getIntent().getExtras().get("content");
                userid = (String) getIntent().getExtras().get("userid");
                profile = (String) getIntent().getExtras().get("profile");
                break;
        }


//        if (status.equals("sign")) {
//
//
//        } else if (status.equals("login")) {
//
//        } else if (status.equals("option")) {
//
//        } else if (status.equals("name")) {
//
//            number = (String) getIntent().getExtras().get("number");
//            name = (String) getIntent().getExtras().get("name");
//            bu = (String) getIntent().getExtras().get("bu");
//            content = (String) getIntent().getExtras().get("content");
//            userid = (String) getIntent().getExtras().get("userid");

//        }


        btnok.setVisibility(View.GONE);
        checklaw.setChecked(false);
        checklaw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                    if (isChecked) {
                                                        btnok.setVisibility(View.VISIBLE);
                                                    } else {
                                                        btnok.setVisibility(View.GONE);
                                                    }

                                                }
                                            }
        );
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityLaw.this, ActivityOption.class);
//                ActivityLaw.this.startActivity(intent);
                ActivityLaw.this.finish();
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<MessageSignup> setlaw = RI.setlaw(content, bu, number, number);
                setlaw.enqueue(new Callback<MessageSignup>() {
                    @Override
                    public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                        if (response.body().getStatus()) {
                            Log.e("lawprof", G.nodeurl +profile + "");

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("login", "ok");
                            editor.putString("userid", userid);
                            editor.putString("number", number);
                            editor.putString("type", bu);
                            editor.putString("sx", sx);
                            editor.putString("name", name);
                            editor.putString("content", content);
                            editor.putString("imgprofile", G.nodeurl +profile);
                            editor.commit();
                            Intent intent2 = new Intent(ActivityLaw.this, ActivityMain.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                            ActivityLaw.this.finish();
                        } else {
                            Toast.makeText(G.context, "خطایی رخ داده لطفا مجددا تلاش نمایید.", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<MessageSignup> call, Throwable t) {
                        Toast.makeText(G.context, "خطادر ارتباط با سرور لطفا مجددا تلاش نمایید.", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rec_law_lawyer);
        linearLayoutManager = new LinearLayoutManager(G.context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapterLaw = new RecyclerAdapterLaw(ActivityLaw.this, title));

    }

    public void getuser(String content, String number, String userid, String bu) {
//        int lw=0;
        if (bu.equals("br")) {
            Call<Barber> callbarber = RI.getbarber(content, userid, number);
            callbarber.enqueue(new Callback<Barber>() {
                @Override
                public void onResponse(Call<Barber> call, Response<Barber> response) {
                    law= String.valueOf(response.body().getLaw());
                }

                @Override
                public void onFailure(Call<Barber> call, Throwable t) {
                    Log.e("vakilerr=", "" + t);

                }
            });
        } else {
            Call<User> calluser = RI.getuser(content, userid, BasicActivity.number);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    law= String.valueOf(response.body().getLaw());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

}
