package com.shrazavi.pirayesh.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.shrazavi.pirayesh.AsyncaTaskSendCode;
import com.shrazavi.pirayesh.AsyncaTaskSendNumber;
import com.shrazavi.pirayesh.DataClass.MessageLogin;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.AsyncaTaskSendCode;
import com.shrazavi.pirayesh.AsyncaTaskSendNumber;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.MasterKeys;
import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityValidation extends AppCompatActivity {
    public static EditText edtCode;
    private TextView timerValue;
    private TextView txtnumber;
    public int counter;
    int time;
    Retrofitinformation RInode;
    String number = "", sx = "";
//    int type = 0, base = 0;

    Button btnSend, btnback;
    static Context dialogContext;
    String content = "";
    //    String username = "";
    public SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        edtCode = (EditText) findViewById(R.id.edtcode);
        btnSend = (Button) findViewById(R.id.btnvalidation);
        btnback = (Button) findViewById(R.id.btn_validation_back);
        dialogContext = ActivityValidation.this;
        timerValue = (TextView) findViewById(R.id.txt_time);
        txtnumber = (TextView) findViewById(R.id.txt_num_validation);
        number = (String) getIntent().getExtras().get("number");
        sx = (String) getIntent().getExtras().get("sx");
        content = (String) getIntent().getExtras().get("content");
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        btnSend.setTypeface(G.face);
        txtnumber.setText(number);
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
//        vu = (String) getIntent().getExtras().get("vu");
//        if(vu.equals("vl")) {
//            username = (String) getIntent().getExtras().get("username");
//            name = (String) getIntent().getExtras().get("name");
//            melli = (String) getIntent().getExtras().get("melli");
//            ostan = (String) getIntent().getExtras().get("ostan");
//            shahr = (String) getIntent().getExtras().get("shahr");
//            parvane = (String) getIntent().getExtras().get("parvane");
//            experience = (String) getIntent().getExtras().get("experienc");
//            type = (Integer) getIntent().getExtras().get("type");
//            base = (Integer) getIntent().getExtras().get("base");
//            pass = (String) getIntent().getExtras().get("pass");

//        }else {
//            edtNumber = (String) getIntent().getExtras().get("number");
////            username = (String) getIntent().getExtras().get("username");
////            name = (String) getIntent().getExtras().get("name");
////            melli = (String) getIntent().getExtras().get("melli");
//
//        }

//        String id = G.preferences.getString("id", "not");
//        Log.e("userid",id);
        time = 0;
//message(ActivityValidation.this);
        new CountDownTimer(50000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerValue.setText(millisUntilFinished / 1000 + "");

            }

            public void onFinish() {
                timerValue.setText("ارسال مجدد کد");
                time = 1;
            }
        }.start();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityValidation.this, ActivityLogin.class);
                startActivity(intent);
                ActivityValidation.this.finish();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<MessageLogin> callvakil = RInode.loginuser(content,
                        SymmetricAlgorithmAES.encrypt(number + "/" + sx + "/" + edtCode.getText().toString(), preferences.getString("k5", "not")),
                        preferences.getString("k1", "not"),
                        preferences.getString("k2", "not"),
                        preferences.getString("k3", "not"),
                        preferences.getString("k4", "not"),
                        preferences.getString("k5", "not"));
                callvakil.enqueue(new Callback<MessageLogin>() {
                    @Override
                    public void onResponse(Call<MessageLogin> call, Response<MessageLogin> response) {
                        if (response.body().getNumber().equals("ok") && response.body().getCode().equals("ok")) {
//                            Toast.makeText(G.context, response.body().getSx(), Toast.LENGTH_SHORT).show();
                            Log.e("sx", response.body().getStatus() + "");
                            Intent intent2 = new Intent(ActivityValidation.this, ActivityName.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent2.putExtra("number", number);
                            intent2.putExtra("bu", response.body().getBu());
                            intent2.putExtra("sx", response.body().getSx());
                            intent2.putExtra("law", response.body().getLaw() + "");
                            intent2.putExtra("content", response.body().getContent());
                            intent2.putExtra("userid", response.body().getUserid());
                            intent2.putExtra("status", response.body().getStatus());
                            intent2.putExtra("imgprof", response.body().getProfile());
                            intent2.putExtra("name", response.body().getName());
                            startActivity(intent2);
                            ActivityValidation.this.finish();

                        } else {
                            Toast.makeText(G.context, "کد یا شماره ارسال شده اشتباه است.", Toast.LENGTH_SHORT).show();

                        }
                    }


                    @Override
                    public void onFailure(Call<MessageLogin> call, Throwable t) {
                        Log.e("error login", t + "");
                    }
                });

//                new AsyncaTaskSendCode(G.phpurl + "checkcode.php",
//                        edtNumber,
//                        edtCode.getText().toString(),
//                        username,
//                        name,
//                        vu,
//                        melli,
//                        ostan,
//                        shahr,
//                        parvane,
//                        experience,
//                        type,
//                        base,
//                        pass).execute();
//                Log.e("edtNumber", edtNumber);
//                Log.e("edtCode", edtCode.getText().toString());
            }
        });

        timerValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time == 1) {

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
                            if (response.body().getStatus()) {
//                                Intent intent = new Intent(ActivityValidation.this, ActivityValidation.class);
//                                intent.putExtra("number", number);
//                                intent.putExtra("content", content);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
                                Toast.makeText(G.context, "کد ارسال شد.", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<MessageSignup> call, Throwable t) {

                        }
                    });

                    new CountDownTimer(50000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            timerValue.setText(millisUntilFinished / 1000 + "");

                        }

                        public void onFinish() {
                            timerValue.setText("ارسال مجدد کد");
                            time = 1;
                        }
                    }.start();
                }

            }
        });


    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            Intent intent = new Intent(ActivityValidation.this, ActivityLogin.class);
            startActivity(intent);
            ActivityValidation.this.finish();
        }
        return super.onKeyDown(keycode, event);
    }
    public static void message(Context context) {

        Toast.makeText(context, "Network Available Do operations", Toast.LENGTH_LONG).show();
    }
}
