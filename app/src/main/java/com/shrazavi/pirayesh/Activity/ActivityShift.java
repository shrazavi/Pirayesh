package com.shrazavi.pirayesh.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterShift;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterTicket;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Shift;
import com.shrazavi.pirayesh.DataClass.Ticket;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;

import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityShift extends AppCompatActivity {
    Button btnback, btnadd;
    static TextView txtsupport;
    static Retrofitinformation RI;
    String content;
    String enk;
    SecretKeySpec Key;
    //    public SharedPreferences preferences;
    String username = "";

    String bu = "";
    public static RecyclerAdapterShift recyclerAdaptershift;
    static RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Shift> infos;
    SecretKeySpec secretKey;
    ConstraintLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_shift);
        txtsupport = (TextView) findViewById(R.id.txt_shift);
        btnback = (Button) findViewById(R.id.btn_shift_back);
        btnadd = (Button) findViewById(R.id.btn_shift_add);
        recyclerView = (RecyclerView) findViewById(R.id.rec_shift);
        lay= findViewById(R.id.lay_shift);

        linearLayoutManager = new LinearLayoutManager(G.context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        btnadd.setTypeface(G.face);
        secretKey = SymmetricAlgorithmAES.setUpSecretKey();
        infos = new ArrayList<>();
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        username = BasicActivity.userid;
        bu = BasicActivity.bu;
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + username + "-" + getResources().getString(R.string.developer), enk);
        content = BasicActivity.content;
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        getshift(ActivityShift.this,ActivityShift.this);

//        btnok = (Button) findViewById(R.id.btn_support_ok);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder mBuild = new android.app.AlertDialog.Builder(ActivityShift.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_shift, null);
                TextInputEditText edtstart =(TextInputEditText) mView.findViewById(R.id.edt_time_shift_start);
                TextInputEditText edtend = (TextInputEditText) mView.findViewById(R.id.edt_time_shift_end);
                Button btnSubmit = (Button) mView.findViewById(R.id.btnSubShift);

                edtstart.setInputType(InputType.TYPE_NULL);
                edtstart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityShift.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtstart.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

                edtend.setInputType(InputType.TYPE_NULL);
                edtend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityShift.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtend.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
                mBuild.setView(mView);
                android.app.AlertDialog dialog = mBuild.create();
                dialog.show();

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edtstart.getText().toString().isEmpty() || edtend.getText().toString().isEmpty()) {
                            Toast.makeText(ActivityShift.this, "لطفا زمان مورد نظر را انتخاب کنید", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] partsstart = edtstart.getText().toString().split(":");
                            int h1 = Integer.parseInt(partsstart[0]);
                            int m1 = Integer.parseInt(partsstart[1]);
                            String[] partsend = edtend.getText().toString().split(":");
                            int h2 = Integer.parseInt(partsend[0]);
                            int m2 = Integer.parseInt(partsend[1]);
//                Log.e("newtime", newtime+"");
                            Call<MessageSignup> callshift = RI.insertshift(content, username, h1,h2,m1,m2, BasicActivity.number);
                            callshift.enqueue(new Callback<MessageSignup>() {
                                @Override
                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                    dialog.dismiss();
                                    getshift(ActivityShift.this,ActivityShift.this);
//                                    Call<ArrayList<Shift>> getshift = RI.getshift(content, username, BasicActivity.number);
//                                    getshift.enqueue(new Callback<ArrayList<Shift>>() {
//                                        @Override
//                                        public void onResponse(Call<ArrayList<Shift>> call, Response<ArrayList<Shift>> response) {
//                                            infos = response.body();
//                                            recyclerView.setAdapter(recyclerAdaptershift = new RecyclerAdapterShift(infos, ActivityShift.this));
//                                            recyclerAdaptershift.notifyDataSetChanged();
//                                            if (infos.size() == 0) {
//                                                txtsupport.setVisibility(View.VISIBLE);
//                                            } else {
//                                                txtsupport.setVisibility(View.GONE);
//
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<ArrayList<Shift>> call, Throwable t) {
//                                            Log.e("calllogerror", t + "");
//                                        }
//                                    });

                                }

                                @Override
                                public void onFailure(Call<MessageSignup> call, Throwable t) {

                                }
                            });

                        }
                    }
                });
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityShift.this, ActivityOption.class);
//                ActivityShift.this.startActivity(intent);
                ActivityShift.this.finish();
            }
        });
    }
    public static void getshift(Context context, Activity activity){

    Call<ArrayList<Shift>> callshift = RI.getshift(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
        callshift.enqueue(new Callback<ArrayList<Shift>>() {
        @Override
        public void onResponse(Call<ArrayList<Shift>> call, Response<ArrayList<Shift>> response) {

            recyclerView.setAdapter(recyclerAdaptershift = new RecyclerAdapterShift(response.body(), context,activity));
            recyclerAdaptershift.notifyDataSetChanged();
            if (response.body().size() == 0) {
                txtsupport.setVisibility(View.VISIBLE);
            } else {
                txtsupport.setVisibility(View.GONE);

            }
        }

        @Override
        public void onFailure(Call<ArrayList<Shift>> call, Throwable t) {
            Log.e("calllogerror", t + "");
        }
    });

    }
}
