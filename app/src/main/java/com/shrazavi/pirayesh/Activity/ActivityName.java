package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityName extends AppCompatActivity {
    Button btnsabt;
    TextInputEditText edtfamily, edtname;
    public String law = "", status = "", number = "", bu = "", sx = "", content = "", userid = "", name = "",profile="";
    public SharedPreferences preferences;
    Retrofitinformation RInode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        String masterKeyAlias = null;
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
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        law = (String) getIntent().getExtras().get("law");
        status = (String) getIntent().getExtras().get("status");
        profile = (String) getIntent().getExtras().get("imgprof");
        number = (String) getIntent().getExtras().get("number");
        name = (String) getIntent().getExtras().get("name");
        bu = (String) getIntent().getExtras().get("bu");
        sx = (String) getIntent().getExtras().get("sx");
        content = (String) getIntent().getExtras().get("content");
        userid = (String) getIntent().getExtras().get("userid");
        btnsabt = (Button) findViewById(R.id.btn_name_sabt);
        edtfamily = (TextInputEditText) findViewById(R.id.edt_name_family);
        edtname = (TextInputEditText) findViewById(R.id.edt_name_name);
        btnsabt.setTypeface(G.face);
        if (status.equals("login")) {
            if (law.equals("0")) {
                name = edtname.getText().toString() + " " + edtfamily.getText().toString();
                Intent intent2 = new Intent(ActivityName.this, ActivityLaw.class);
                intent2.putExtra("number", number);
                intent2.putExtra("bu", bu);
                intent2.putExtra("sx", sx);
                intent2.putExtra("content", content);
                intent2.putExtra("userid", userid);
                intent2.putExtra("status", status);
                intent2.putExtra("law", law);
                intent2.putExtra("name", name);
                intent2.putExtra("profile", profile);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                ActivityName.this.finish();
            } else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("login", "ok");
                editor.putString("userid", userid);
                editor.putString("number", number);
                editor.putString("type", bu);
                editor.putString("sx", sx);
                editor.putString("content", content);
                editor.putString("name", name);
                editor.putString("imgprofile",  G.nodeurl +profile);
                editor.commit();
                Intent intent2 = new Intent(ActivityName.this, ActivityMain.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                ActivityName.this.finish();
            }
        } else if (status.equals("name")) {

        }
        btnsabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtname.getText().toString().isEmpty() || edtfamily.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityName.this, "لطفا نام و نام خانوادگی خود را ورد کنید.", Toast.LENGTH_SHORT).show();
                } else {
                    Call<MessageSignup> setname = RInode.setname(content, name,bu, number, number);
                    setname.enqueue(new Callback<MessageSignup>() {
                        @Override
                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                            if (response.body().getStatus()) {
                                if (law.equals("1")) {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("login", "ok");
                                    editor.putString("userid", userid);
                                    editor.putString("number", number);
                                    editor.putString("type", bu);
                                    editor.putString("sx", sx);
                                    editor.putString("content", content);
                                    editor.putString("name", name);
                                    editor.putString("imgprofile",  G.nodeurl +profile);
                                    editor.commit();
                                    Intent intent2 = new Intent(ActivityName.this, ActivityMain.class);
                                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent2);
                                } else {
                                    name = edtname.getText().toString() + " " + edtfamily.getText().toString();
                                    Intent intent2 = new Intent(ActivityName.this, ActivityLaw.class);
                                    intent2.putExtra("number", number);
                                    intent2.putExtra("bu", bu);
                                    intent2.putExtra("sx", sx);
                                    intent2.putExtra("content", content);
                                    intent2.putExtra("userid", userid);
                                    intent2.putExtra("law", law);
                                    intent2.putExtra("status", status);
                                    intent2.putExtra("name", name);
                                    intent2.putExtra("profile", profile);
                                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent2);
                                    ActivityName.this.finish();
                                }
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
            }
        });


    }
}
