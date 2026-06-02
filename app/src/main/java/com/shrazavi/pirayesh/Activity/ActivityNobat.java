package com.shrazavi.pirayesh.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterNobat;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterReserve;

import com.shrazavi.pirayesh.Adapter.SpinnerINTAdapter;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.NameNum;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Repitem;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;
import java.util.Iterator;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityNobat extends AppCompatActivity {
    public static Button btnvacation, btnback, btnrefresh, btnreserve;
    public static TextView txtstatus, txtdate;
    public static RecyclerView rec;
    static Retrofitinformation RInode;
    LinearLayoutManager linearLayoutManager;
    String names = "", numbers = "", date = "", time = "", vip = "";
    int y = 0, m = 0, d = 0, barbers = 0, bselect = 0;
    String[] arrname, arrnumber, arrdate, arrvip;
    ArrayList<Integer> arrbarbers = new ArrayList<>();
    public static ArrayList<NameNum> arrayuser = new ArrayList<>();
    public MaterialProgressBar prload;
    static RecyclerAdapterNobat recyclerAdapterNobat;
    ConstraintLayout lay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_nobat);

        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        prload = (MaterialProgressBar) findViewById(R.id.pr_nobat_load);
        rec = (RecyclerView) findViewById(R.id.rec_nobat);
        lay =  findViewById(R.id.lay_nobat);
        btnvacation = (Button) findViewById(R.id.btn_nobat_vacation);
        btnback = (Button) findViewById(R.id.btn_nobat_back);
//        btnreserve = (Button) findViewById(R.id.btn_nobat_reserve);
//        lay=(LinearLayout) findViewById(R.id.lay_nobat);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        btnrefresh = (Button) findViewById(R.id.btn_nobat_refresh);
        txtstatus = (TextView) findViewById(R.id.txt_nobat_status);
        txtdate = (TextView) findViewById(R.id.txt_nobat_date);
        vip = (String) getIntent().getExtras().get("vip");
        names = (String) getIntent().getExtras().get("names");
        numbers = (String) getIntent().getExtras().get("numbers");
        date = (String) getIntent().getExtras().get("date");
        time = (String) getIntent().getExtras().get("time");
        if (numbers.isEmpty()) {
            btnvacation.setVisibility(View.VISIBLE);
            rec.setVisibility(View.GONE);
            txtstatus.setVisibility(View.VISIBLE);
            txtstatus.setText("لیستی برای نمایش موجود نیست.");
        } else {
            btnvacation.setVisibility(View.GONE);
            rec.setVisibility(View.VISIBLE);
            txtstatus.setVisibility(View.GONE);
        }

        arrname = names.split("/");
        arrvip = vip.split("/");
        arrnumber = numbers.split("/");
        arrdate = date.split("/");
        arrayuser.clear();
//        for (String u : arruser) {
//            if (!u.isEmpty()) {
//                arrayuser.add(u);
//            }
//        }
        int i = 0;
        for (String s : arrnumber) {
            if (!s.isEmpty()) {
                NameNum nn = new NameNum();
                nn.setNumber(s);
                nn.setId(i);
                i++;
                arrayuser.add(nn);
            }
        }
        for (NameNum n : arrayuser) {
            arrayuser.get(n.getId()).setName(arrname[n.getId() + 1]);
        }
        for (NameNum n : arrayuser) {
            arrayuser.get(n.getId()).setVip(arrvip[n.getId() + 1]);
        }
        d = Integer.parseInt(arrdate[0]);
        m = Integer.parseInt(arrdate[1]);
        y = Integer.parseInt(arrdate[2]);
        PersianCalendar dateset = new PersianCalendar();
        dateset.setPersianDate(y, m - 1, d);
        txtdate.setText(dateset.getPersianWeekDayName() + " " + d + " " + dateset.getPersianMonthName() + " " + y + " ساعت " + time);

        linearLayoutManager = new LinearLayoutManager(ActivityNobat.this);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(recyclerAdapterNobat = new RecyclerAdapterNobat(arrayuser, ActivityNobat.this, ActivityNobat.this, y, m, d, time));
        recyclerAdapterNobat.notifyDataSetChanged();

        Call<Barber> callbarber = RInode.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                //------------rooze rezerve shode-------------
                barbers = response.body().getBarbers();
                for (int i = 1; i <= barbers; i++) {
                    arrbarbers.add(i);
                }
                arrbarbers.add(0);
            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("vakilerr=", "" + t);

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityNobat.this, ActivityVisit2.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ActivityNobat.this.startActivity(intent);
                ActivityNobat.this.finish();
            }
        });
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getnobat(ActivityNobat.this, ActivityNobat.this, y, m, d, time);
            }
        });

        btnvacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder mBuild = new android.app.AlertDialog.Builder(ActivityNobat.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_vacation, null);


//                TextInputEditText edtday = (TextInputEditText) mView.findViewById(R.id.edt_reserve_day);
                AutoCompleteTextView spintime = (AutoCompleteTextView) mView.findViewById(R.id.spn_vacation_time);
                Button btnSubmit = (Button) mView.findViewById(R.id.btn_vacation);
                SpinnerINTAdapter Adapterbarbers = new SpinnerINTAdapter(ActivityNobat.this, android.R.layout.simple_list_item_1);
                Adapterbarbers.addAll(arrbarbers);
                spintime.setAdapter(Adapterbarbers);

                mBuild.setView(mView);
                android.app.AlertDialog dialog = mBuild.create();
                dialog.show();
//                edtday.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        edtday.setText();
//                    }
//                });


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        if (spintime.getText().toString().isEmpty()) {
                            Toast.makeText(ActivityNobat.this, "لطفا تعداد آرایشگران را تعیین کنید", Toast.LENGTH_SHORT).show();
                        } else {
                            bselect = Integer.parseInt(spintime.getText().toString());
                            for (int i = 1; i <= bselect; i++) {


                                Call<MessageSignup> callreserve = RInode.insertreserve(BasicActivity.content, BasicActivity.userid, BasicActivity.name, BasicActivity.number, BasicActivity.userid, y, m, d, time, 0, BasicActivity.number);
                                callreserve.enqueue(new Callback<MessageSignup>() {
                                    @Override
                                    public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                        Log.e("messagereserve", response.body().getMessage() + "");
                                        getnobat(ActivityNobat.this, ActivityNobat.this, y, m, d, time);

//                                            btnactivedate.setVisibility(View.GONE);
//                                            txtreserve.setVisibility(View.VISIBLE);
//                                            btnreserve.setVisibility(View.GONE);
//                                            txtreserve.setText("زمان رزرو شما : روز " + spinday.getText().toString() + " ساعت " + spintime.getText().toString());

//                                            Intent intent = new Intent(ActivityProfileVl.this, ActivityProfileVl.class);
//                                            intent.putExtra("username", vakilid);
//                                            ActivityProfileVl.this.startActivity(intent);
//                                            ActivityProfileVl.this.finish();

                                    }

                                    @Override
                                    public void onFailure(Call<MessageSignup> call, Throwable t) {

                                    }
                                });
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
    }

    public static void getnobat(Activity activity, Context context, int year, int month, int day, String time) {
        ArrayList<NameNum> arr = new ArrayList<>();
        Call<ArrayList<Nobat>> callnobat = RInode.getnobat(BasicActivity.content, BasicActivity.userid, year, month, day, time, BasicActivity.number);
        callnobat.enqueue(new Callback<ArrayList<Nobat>>() {
            @Override
            public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {
                arr.clear();
                for (Nobat n : response.body()) {
                    NameNum nn = new NameNum();
                    nn.setName(n.getName());
                    nn.setNumber(n.getNumber());
                    arr.add(nn);
                }
                if (arr.size() == 0) {
                    rec.setVisibility(View.GONE);
                    txtstatus.setVisibility(View.VISIBLE);
                    txtstatus.setText("لیستی برای نمایش موجود نیست.");
                    btnvacation.setVisibility(View.VISIBLE);
                } else {
                    rec.setVisibility(View.VISIBLE);
                    txtstatus.setVisibility(View.GONE);
                    btnvacation.setVisibility(View.GONE);
                }

                rec.setAdapter(recyclerAdapterNobat = new RecyclerAdapterNobat(arr, activity, context, year, month, day, time));
                recyclerAdapterNobat.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {
                Log.e("errnobat", t + "");

            }
        });


    }
}
