package com.shrazavi.pirayesh.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.shrazavi.pirayesh.AsyncaTaskSendNumber;
import com.shrazavi.pirayesh.DataClass.MessageLogin;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.MasterKeys;
import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;

import java.io.IOException;
import java.security.GeneralSecurityException;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {
    public  SharedPreferences preferences;
    public static Context dialogContext;
    Button btnlogin,btnhelp;
    public static TextInputEditText edtnumber;
    TextInputLayout laynumber;
    String content;
    public static MaterialProgressBar progresslogin;
    TextView txtsignup;
    RadioGroup rg;
    int rid;
    int radioid;
    String sx="male";
    Retrofitinformation RInode;
    CountryCodePicker ccp;
    private String TAG = ActivityLogin.class.getSimpleName();
    public static String code = "98";
    String number = "";
    private static final int MIN_LENGTH = 3;

    private EditText userLoginEditText;
    private EditText userFullNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialogContext = ActivityLogin.this;
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
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
        content = SymmetricAlgorithmAES.encrypt(preferences.getString("IM", "not"), preferences.getString("k5", "not"));

//        Log.e("id", id);
        edtnumber = (TextInputEditText) findViewById(R.id.edt_number);
//        edtpass = (TextInputEditText) findViewById(R.id.edt_pass_login);
        laynumber = (TextInputLayout) findViewById(R.id.lay_number);
//        laypass = (TextInputLayout) findViewById(R.id.lay_edt_pass_login);
//        txtsignup = (TextView) findViewById(R.id.txt_signup);
        btnlogin = (Button) findViewById(R.id.btn_login);
        btnhelp = (Button) findViewById(R.id.btn_help_login);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        rid = rg.getCheckedRadioButtonId();
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        btnlogin.setTypeface(G.face);
//        initUI();
//        laypass.setBoxBackgroundColor(getResources().getColor(R.color.defaultpass));
//        laypass.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//        laypass.setBoxStrokeColor(getResources().getColor(R.color.white));
//        layusername.setBoxBackgroundColor(getResources().getColor(R.color.defaultpass));
//        layusername.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//        layusername.setBoxStrokeColor(getResources().getColor(R.color.white));
//        sharedPrefsHelper = SharedPrefsHelper.getInstance();
//        progresslogin = findViewById(R.id.progressBarlogin);
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
//                Toast.makeText(ActivitySignup.this, "Updated " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();
                code = selectedCountry.getPhoneCode();

            }
        });
        ccp.setDefaultCountryUsingNameCode("ir");
        ccp.setDefaultCountryUsingPhoneCodeAndApply(98);
        ccp.setDefaultCountryUsingPhoneCode(98);
        ccp.setDefaultCountryUsingNameCodeAndApply("ir");
        if (rid == -1) {

            rg.check(R.id.radiomale);
            sx = "male";
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioid = checkedId;
                switch (checkedId) {

                    case R.id.radiomale:
                        sx = "male";
                        //   Log.e("iddr",rg.getCheckedRadioButtonId()+"");
                        break;

                    case R.id.radiofemale:
                        sx = "female";
                        //
                        break;
                }
            }
        });
//        txtsignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(G.context, ActivitySignup.class);
////                startActivity(intent);
//
//            }
//        });
        btnhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ActivityLogin.this, ActivityHelp.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("sx", sx);
                startActivity(intent2);
//                ActivityLogin.this.finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("conect", isConnected() + "");
                if (isConnected()) {
                    number = "+" + code + edtnumber.getText().toString();
//                    btnlogin.setVisibility(View.GONE);
//                    progresslogin.setVisibility(View.VISIBLE);
                    Call<MessageSignup> calldata = RInode.sendcode(content,
                            SymmetricAlgorithmAES.encrypt(number, preferences.getString("k5", "not")),
                            preferences.getString("k1", "not"),
                            preferences.getString("k2", "not"),
                            preferences.getString("k3", "not"),
                            preferences.getString("k4", "not"),
                            preferences.getString("k5", "not"));

                    calldata.enqueue(new Callback<MessageSignup>() {
                        @Override
                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                            Boolean status=false;
                            status=response.body().getStatus();
                        if(status){
                            Intent intent = new Intent(ActivityLogin.this, ActivityValidation.class);
                            intent.putExtra("number", number);
                            intent.putExtra("sx", sx);
                            intent.putExtra("content", content);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            ActivityLogin.this.finish();
                        }

                        }

                        @Override
                        public void onFailure(Call<MessageSignup> call, Throwable t) {
                            Log.e("errorlogin", t + "");
                        }
                    });
                    //   Log.e("edtnum", edtlogin.getText().toString());
                    //  edtNumber = (String) getIntent().getExtras().get("edtNumber");

//                    new AsyncaTaskCheckPassword(G.phpurl+"/Checkpassword.php", edtuser.getText().toString(),
//                            edtpass.getText().toString(), du).execute();
//                    Log.e("password", edtpass.getText().toString());
//                    Log.e("du", du);
//                    SharedPreferences.Editor editor = G.preferences.edit();
//
//                    editor.putString("type", du);
//                    editor.commit();
//                    Log.e("contentlogin",content+"");
//                    if (vu.equals("user")) {
//
//                        Call<MessageLogin> calluser = RInode.loginuser(content,
//                                SymmetricAlgorithmAES.encrypt(edtuser.getText().toString()+"/"+edtpass.getText().toString(), preferences.getString("k5", "not")),
//                                preferences.getString("k1", "not"),
//                                preferences.getString("k2", "not"),
//                                preferences.getString("k3", "not"),
//                                preferences.getString("k4", "not"),
//                                preferences.getString("k5", "not"));
//                        calluser.enqueue(new Callback<MessageLogin>() {
//                            @Override
//                            public void onResponse(Call<MessageLogin> call, Response<MessageLogin> response) {
//                                btnlogin.setVisibility(View.VISIBLE);
//                                progresslogin.setVisibility(View.GONE);
//
//                                if (response.body().getUser().equals("ok")) {
//                                    if (response.body().getPassword().equals("ok")) {
////                                        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
//                                        SharedPreferences.Editor editor = preferences.edit();
//                                        editor.putString("login", "ok").apply();
//                                        editor.putString("username", edtuser.getText().toString()).apply();
//                                        editor.putString("content", response.body().getContent()).apply();
//                                        editor.putString("type", vu).apply();
//                                        editor.commit();
//                                        Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
//                                        intent.putExtra("username", edtuser.getText().toString());
//                                        ActivityLogin.this.startActivity(intent);
//                                        ActivityMain.restart(G.context,0);
//
//                                    } else {
////                                        edtuser.setBackgroundResource(R.drawable.corner_edt);
////                                        edtpass.setBackgroundResource(R.drawable.edt_error);
//                                        laypass.setBoxStrokeColor(getResources().getColor(R.color.red));
//                                        laypass.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//
//                                        edtpass.setFocusableInTouchMode(true);
//                                        edtpass.requestFocus();
//                                        layusername.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//                                        layusername.setBoxStrokeColor(getResources().getColor(R.color.white));
//                                        Toast.makeText(G.context, "اطلاعات وارد شده صحیح نمی باشد.", Toast.LENGTH_SHORT).show();
//
//                                    }
//
//                                } else {
//                                    laypass.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//                                    laypass.setBoxStrokeColor(getResources().getColor(R.color.white));
////                                    edtpass.setBackgroundResource(R.drawable.corner_edt);
////                                    edtuser.setBackgroundResource(R.drawable.edt_error);
//                                    layusername.setBoxStrokeColor(getResources().getColor(R.color.red));
//                                    layusername.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//                                    edtuser.setFocusableInTouchMode(true);
//                                    edtuser.requestFocus();
//                                    Toast.makeText(G.context, "اطلاعات وارد شده صحیح نمی باشد.", Toast.LENGTH_SHORT).show();
//                                }
//
////                            Log.e("signupuser", response.body().getMessage());
////                            Boolean status = response.body().getStatus();
////                            if (status) {
////                                Intent intent = new Intent(ActivitySignup.this, ActivityValidation.class);
////                                intent.putExtra("number", number);
////                                ActivitySignup.this.startActivity(intent);
////
////                            }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<MessageLogin> call, Throwable t) {
//                                Log.e("error login", t + "");
//                            }
//                        });
//
//                    } else {
//
//                        Call<MessageLogin> callvakil = RInode.loginvl(content,
//                                SymmetricAlgorithmAES.encrypt(edtuser.getText().toString()+"/"+edtpass.getText().toString(), preferences.getString("k5", "not")),
//                                preferences.getString("k1", "not"),
//                                preferences.getString("k2", "not"),
//                                preferences.getString("k3", "not"),
//                                preferences.getString("k4", "not"),
//                                preferences.getString("k5", "not"));
//                        callvakil.enqueue(new Callback<MessageLogin>() {
//                            @Override
//                            public void onResponse(Call<MessageLogin> call, Response<MessageLogin> response) {
//                                btnlogin.setVisibility(View.VISIBLE);
//                                progresslogin.setVisibility(View.GONE);
//                                if (response.body().getUser().equals("ok")) {
//                                    if (response.body().getPassword().equals("ok")) {
////                                        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
//                                        SharedPreferences.Editor editor = preferences.edit();
//                                        editor.putString("login", "ok");
//                                        editor.putString("username", edtuser.getText().toString());
//                                        editor.putString("content", response.body().getContent());
//                                        editor.putString("type", vu);
//                                        editor.commit();
//                                        Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
//                                        intent.putExtra("username", edtuser.getText().toString());
//                                        ActivityLogin.this.startActivity(intent);
//                                        ActivityMain.restart(G.context,0);
//
//                                    } else {
////                                        edtuser.setBackgroundResource(R.drawable.corner_edt);
////                                        edtpass.setBackgroundResource(R.drawable.edt_error);
//                                        edtpass.setFocusableInTouchMode(true);
//                                        edtpass.requestFocus();
//                                        laypass.setBoxStrokeColor(getResources().getColor(R.color.red));
//                                        laypass.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//                                        layusername.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//                                        layusername.setBoxStrokeColor(getResources().getColor(R.color.white));
//
//                                    }
//
//                                } else {
////                                    edtpass.setBackgroundResource(R.drawable.corner_edt);
////                                    edtuser.setBackgroundResource(R.drawable.edt_error);
//                                    laypass.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
//                                    laypass.setBoxStrokeColor(getResources().getColor(R.color.white));
//                                    layusername.setBoxStrokeColor(getResources().getColor(R.color.red));
//                                    layusername.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red)));
//                                    edtuser.setFocusableInTouchMode(true);
//                                    edtuser.requestFocus();
//                                    Toast.makeText(G.context, "اطلاعات وارد شده صحیح نمی باشد.", Toast.LENGTH_SHORT).show();
//                                }
//
//
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<MessageLogin> call, Throwable t) {
//                                Log.e("error login", t + "");
//                            }
//                        });
//
//
//                    }
//
//
                } else {
                    Toast.makeText(ActivityLogin.this, "لطفا اتصال دستگاه خود را به اینترنت بررسی کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean isConnected() {
        ConnectivityManager connect = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connect != null) {
            NetworkInfo[] information = connect.getAllNetworkInfo();
            if (information != null) {
                for (int x = 0; x < information.length; x++) {
                    if (information[x].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;


    }

}
