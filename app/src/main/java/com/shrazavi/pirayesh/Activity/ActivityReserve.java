package com.shrazavi.pirayesh.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityReserve extends AppCompatActivity {
    Spinner spinday, spintime;
    Retrofitinformation RInode;
    String content;
    String enk;
    SecretKeySpec Key;
    String bu;
    Button btnreserve;
    ArrayList<DayReserve> arrday = new ArrayList<>();
    String[] arrtime;
    ArrayList<String> newtime = new ArrayList<>();
    Thread thread, thread2;
    String time, userid, day;

    //    public SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
//        spinday = (Spinner) findViewById(R.id.spn_reserve_day);
//        spintime = (Spinner) findViewById(R.id.spn_reserve_time);
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

//        vakilid = (String) getIntent().getExtras().get("username");
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        userid = BasicActivity.userid;
        bu = BasicActivity.bu;
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key=new SecretKeySpec(data, 0, data.length, "AES");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + userid + "-" + getResources().getString(R.string.developer), enk);
        content = BasicActivity.content;

//        content = Base64.encodeToString(SymmetricAlgorithmAES.encryption(Key, vu+"-"+userid+"-"+R.string.developer), Base64.NO_WRAP);

        btnreserve = (Button) findViewById(R.id.btn_reserve);
//        Call<ArrayList<DayReserve>> callday = RInode.getday(content,vakilid,userid);
//        callday.enqueue(new Callback<ArrayList<DayReserve>>() {
//            @Override
//            public void onResponse(Call<ArrayList<DayReserve>> call, Response<ArrayList<DayReserve>> response) {
//                spinnerAdapter Adapterday = new spinnerAdapter(ActivityReserve.this, android.R.layout.simple_list_item_1);
//                for (int i = 0; i < response.body().size(); i++) {
//                    if (response.body().get(i).getHours().length == 0) {
//                    } else {
//                        Adapterday.add(response.body().get(i).getDay());
//                    }
////                    Log.e("arrday1", response.body().get(i).getHours()+"");
//                }
//                arrday = response.body();
//                Adapterday.add("روز");
//                spinday.setAdapter(Adapterday);
//                spinday.setSelection(Adapterday.getCount());
//                spinday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        // TODO Auto-generated method stub
//
//                        if (spinday.getSelectedItem() == "روز") {
//
//                            //Do nothing.
//                        } else {
////                            id_ostan = spinostan.getSelectedItemPosition() + 1;
//                             day = spinday.getSelectedItem().toString();
//                            for (int j = 0; j < arrday.size(); j++) {
//
//                                if (arrday.get(j).getDay().equals(day)) {
//                                    arrtime = response.body().get(j).getHours();
//
//                                }
//                            }
//                            spinnerAdapter Adaptertime = new spinnerAdapter(ActivityReserve.this, android.R.layout.simple_list_item_1);
//                            for (int k = 0; k < arrtime.length; k++) {
//                                Adaptertime.add(arrtime[k]);
////                                Log.e("arrday", arrtime[k]+"");
//                            }
//
//                            Adaptertime.add("ساعت");
//                            spintime.setAdapter(Adaptertime);
//                            spintime.setSelection(Adaptertime.getCount());
////                            thread = new Thread(new Runnable() {
////                                @Override
////                                public void run() {
////                                    try {
////                                        Thread.sleep(5000);
////
////                                    } catch (InterruptedException e) {
////                                        e.printStackTrace();
////                                    }
////
////
////                                }
////
////
////                            });
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<DayReserve>> call, Throwable t) {
//
//            }
//        });


        spintime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                if (spintime.getSelectedItem() == "ساعت") {

                    //Do nothing.
                } else {
                    time = spintime.getSelectedItem().toString();
                    Log.e("time", time);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        btnreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder mBuild = new android.app.AlertDialog.Builder(ActivityReserve.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_reserve, null);


                TextInputEditText edtday = (TextInputEditText) mView.findViewById(R.id.edt_reserve_day);
                AutoCompleteTextView spintime = (AutoCompleteTextView) mView.findViewById(R.id.spn_reserve_time);
                Button btnSubmit = (Button) mView.findViewById(R.id.btn_reserve);

                spinnerAdapter Adapterday = new spinnerAdapter(ActivityReserve.this, android.R.layout.simple_list_item_1);
//                Adapterday.addAll(arrtype);

                mBuild.setView(mView);
                android.app.AlertDialog dialog = mBuild.create();
                dialog.show();
                edtday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersianCalendar now = new PersianCalendar();
                        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                      @Override
                      public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                          Toast.makeText(MainActivity.this, "" + year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                      }
                  }, now.getPersianYear(),
                                now.getPersianMonth(),
                                now.getPersianDay());
                        datePickerDialog.setThemeDark(true);
                        datePickerDialog.show(getFragmentManager(), "tpd");
                        edtday.setText(now.getPersianYear() + "/" +
                                now.getPersianMonth() + "/" +
                                now.getPersianDay());

                    }
                });

//                spinday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String day = spinday.getText().toString();
////                        Log.e("shahrname", shahrestan);
//                        for (int j = 0; j < arrday.size(); j++) {
//
//                            if (arrday.get(j).getDay().equals(day)) {
//                                arrtime = arrday.get(j).getHours();
//
//                            }
//                        }
//                        spinnerAdapter Adaptertime = new spinnerAdapter(ActivityReserve.this, android.R.layout.simple_list_item_1);
//                        for (int k = 0; k < arrtime.length; k++) {
//                            Adaptertime.add(arrtime[k]);
////                                Log.e("arrday", arrtime[k]+"");
//                        }
//
//                        Adaptertime.add("");
//                        spintime.setAdapter(Adaptertime);
////                        spintime.setSelection(Adaptertime.getCount());
//
//                    }
//                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (edtday.getText().toString().isEmpty() || spintime.getText().toString().isEmpty()) {
                            Toast.makeText(ActivityReserve.this, "لطفا زمان مورد نظر را انتخاب کنید", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < arrtime.length; i++) {
                                if (arrtime[i].equals(spintime.getText().toString())) {
                                } else {
                                    newtime.add(arrtime[i]);
                                }
                            }
//                Log.e("newtime", newtime+"");
//                            Call<MessageSignup> callreserve = RInode.insertreserve(content, userid, toid, spintime.getText().toString(), spinday.getText().toString());
//                            callreserve.enqueue(new Callback<MessageSignup>() {
//                                @Override
//                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
//                                    Log.e("messagereserve", response.body().getMessage() + "");
//
//                                    Call<MessageSignup> callupday = RInode.upgradetime(content, toid, newtime, spinday.getText().toString(), userid);
//                                    callupday.enqueue(new Callback<MessageSignup>() {
//                                        @Override
//                                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
//                                            Log.e("messagetime", response.body().getMessage() + "");
//                                            dialog.dismiss();
////                                            btnactivedate.setVisibility(View.GONE);
//                                            txtreserve.setVisibility(View.VISIBLE);
//                                            btnreserve.setVisibility(View.GONE);
//                                            txtreserve.setText("زمان رزرو شما : روز " + spinday.getText().toString() + " ساعت " + spintime.getText().toString());
//
////                                            Intent intent = new Intent(ActivityProfileVl.this, ActivityProfileVl.class);
////                                            intent.putExtra("username", vakilid);
////                                            ActivityProfileVl.this.startActivity(intent);
////                                            ActivityProfileVl.this.finish();
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<MessageSignup> call, Throwable t) {
//
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onFailure(Call<MessageSignup> call, Throwable t) {
//
//                                }
//                            });

                        }
                    }
                });
            }
        });

//        btnreserve.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < arrtime.length; i++) {
//                    if (arrtime[i].equals(time)) {
//                    } else {
//                        newtime.add(arrtime[i]);
//                    }
//                }
//                Log.e("newtime", newtime+"");
//                Call<MessageSignup> callreserve = RInode.insertreserve(content,userid,vakilid,time,day);
//                callreserve.enqueue(new Callback<MessageSignup>() {
//                    @Override
//                    public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
//                Log.e("messagereserve", response.body().getMessage()+"");
//
//                        Call<MessageSignup> callupday = RInode.upgradetime(content,vakilid,newtime,day,userid);
//                        callupday.enqueue(new Callback<MessageSignup>() {
//                            @Override
//                            public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
//                                Log.e("messagetime", response.body().getMessage()+"");
//
//
////                                Intent intent = new Intent(ActivityReserve.this, ActivityProfileVl.class);
////                                intent.putExtra("username", vakilid);
////                                ActivityReserve.this.startActivity(intent);
////                                ActivityReserve.this.finish();
//                            }
//
//                            @Override
//                            public void onFailure(Call<MessageSignup> call, Throwable t) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Call<MessageSignup> call, Throwable t) {
//
//                    }
//                });


//
//            }
//        });

    }


}
