package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.textfield.TextInputEditText;


import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Adapter.DetailPagerAdapter;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterReserve;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterVisit;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Date;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Repitem;
import com.shrazavi.pirayesh.DataClass.Reserve;
import com.shrazavi.pirayesh.DataClass.Visitday;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.ScaleLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.spec.SecretKeySpec;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

public class ActivityVisit2 extends AppCompatActivity {
    private MaterialProgressBar progressBar;
    //    public static ArrayList<DayReserve> reserveItems = new ArrayList<>();
    ArrayList<Nobat> arrnobat = new ArrayList<>();
    public static ArrayList<Visitday> dayItems = new ArrayList<>();
    public static ArrayList<String> arrtime = new ArrayList<>();
    public static ArrayList<String> arrvacation = new ArrayList<>();
    TextView txtstatus,txtdate;
    Button btnsettime, btnvacation, btnback, btnrefresh;
    ArrayList<DayReserve> reserveItems = new ArrayList<>();
    RecyclerView rec;
    int yset = 0, mset = 0, dset = 0;
    String timeserver = "";
    int dateserver = 0;
    int status = 0;
    //    private RecyclerAdapterKala mRecyclerAdapter;
//    TextView txtprogress, txtwaiting;
    CardView crdprogress;
    Retrofitinformation RInode;
    String content;
    String enk;
    SecretKeySpec Key;
    String username = "";
    String vu = "";
    ArrayList<Visitday> Arr;
    static Handler threadHandler;
    Thread thread, thread2;
    DetailPagerAdapter mAdapter;
    private float MAX_SCALE = 0.0f;
    //    ViewPager mPager;
    long nowst = 0;
    FrameLayout frmlay;
    Retrofitinformation RI;
    final int paddingPx = 10;
    RecyclerAdapterReserve recyclerAdapterReserve;
    LinearLayoutManager linearLayoutManager;
    public MaterialProgressBar prload;
    LinearLayout laybtn;
    public RecyclerAdapterVisit recyclerAdapterVisit;
    ConstraintLayout lay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_visit2);
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        username = BasicActivity.userid;
        vu = BasicActivity.bu;
        content = BasicActivity.content;
        lay= findViewById(R.id.lay_visit);

        prload = (MaterialProgressBar) findViewById(R.id.pr_visit_load);
        rec = (RecyclerView) findViewById(R.id.rec_visit);
//        mPager = (ViewPager) findViewById(R.id.pager_visit);
        btnsettime = (Button) findViewById(R.id.btn_visit_set_time);
        btnvacation = (Button) findViewById(R.id.btn_visit_vacation);
        btnback = (Button) findViewById(R.id.btn_visit_back);
        btnrefresh = (Button) findViewById(R.id.btn_visit_refresh);
        crdprogress = (CardView) findViewById(R.id.crd_visit_progress);
        txtstatus = (TextView) findViewById(R.id.txt_visit_status);
        txtdate = (TextView) findViewById(R.id.txt_visit_date);
        laybtn = (LinearLayout) findViewById(R.id.lay_visit_btn);
        btnvacation.setTypeface(G.face);
        btnsettime.setTypeface(G.face);
        btnvacation.setVisibility(View.INVISIBLE);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        setTheme(R.style.Theme_mainwomen);
//        prload = findViewById(R.id.pr_visit_load);
        prload.setVisibility(View.GONE);
        txtstatus.setVisibility(View.GONE);
//        txtprogress.setVisibility(View.GONE);
//        txtwaiting.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(ActivityVisit2.this);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(linearLayoutManager);
//        rec.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(1), true));
//        rec.setItemAnimator(new DefaultItemAnimator());
        Call<Date> calldate = RInode.getdate();
        calldate.enqueue(new Callback<Date>() {
            @Override
            public void onResponse(Call<Date> call, Response<Date> response) {
                timeserver = gettime(response.body().getDate());
                dateserver = daytstoint(response.body().getDate());
                nowst = response.body().getDate();
//                Log.e("nowst=", "" + nowst);

            }

            @Override
            public void onFailure(Call<Date> call, Throwable t) {

            }
        });
        Call<Barber> callbarber = RInode.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                arrtime = response.body().getArraytime();
                arrvacation = response.body().getVacation();
                Log.e("vac=", "" + arrvacation.size());
            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("vakilerr=", "" + t);

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityVisit2.this, ActivityMain.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityVisit2.this.startActivity(intent);
            }
        });
        btnvacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrnobat.size() == 0) {

                    if (yset == 0 || mset == 0 || dset == 0) {

                    } else {
                        if (status == 1) {
                            Call<MessageSignup> callvacation = RInode.cancelvacation(BasicActivity.content,
                                    BasicActivity.userid,
                                    yset,
                                    mset,
                                    dset,
                                    BasicActivity.number);
                            callvacation.enqueue(new Callback<MessageSignup>() {
                                @Override
                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                    if (response.body().getStatus()) {
                                        btnvacation.setBackgroundResource(R.drawable.corner_btn_blue);
                                        btnvacation.setText("مرخصی");
                                        txtstatus.setVisibility(View.GONE);
//                                        txtstatus.setText("در این روز مرخصی هستید.");
                                        rec.setVisibility(View.VISIBLE);
                                        status=0;
                                    }

                                }

                                @Override
                                public void onFailure(Call<MessageSignup> call, Throwable t) {

                                }
                            });
                        } else {
                            PersianCalendar now = new PersianCalendar(nowst);
                            Call<MessageSignup> callvacation = RInode.insertvacation(BasicActivity.content,
                                    BasicActivity.userid,
                                    yset,
                                    mset,
                                    dset,
                                    now.getPersianYear(),
                                    now.getPersianMonth() + 1,
                                    now.getPersianDay(),
                                    BasicActivity.number);
                            callvacation.enqueue(new Callback<MessageSignup>() {
                                @Override
                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                    if (response.body().getStatus()) {
                                        btnvacation.setBackgroundResource(R.drawable.corner_btn_green);
                                        btnvacation.setText("لغو مرخصی");
                                        txtstatus.setVisibility(View.VISIBLE);
                                        txtstatus.setText("در این روز مرخصی هستید.");
                                        rec.setVisibility(View.GONE);
                                        status=1;
                                    }

                                }

                                @Override
                                public void onFailure(Call<MessageSignup> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
            }
        });
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (yset == 0 || mset == 0 || dset == 0) {

                } else {
//                    Log.e("dataset =",dset+"/"+mset+"/"+yset + "");

                    Call<ArrayList<Nobat>> callreserve = RInode.getreserve(BasicActivity.content, yset, mset, dset, BasicActivity.userid, BasicActivity.number);
                    callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {

                            ArrayList<Nobat> arrnobat = new ArrayList<>();
//                arrnobat.clear();
                            reserveItems.clear();

                            arrnobat = response.body();
//                            Log.e("nobat =", arrnobat.size()+ "");
                            if (arrnobat.size() == 0) {
//                                reserveItems.clear();
                                btnvacation.setVisibility(View.VISIBLE);
                                if (checkvacation(arrvacation, yset, mset, dset)) {
                                    btnvacation.setBackgroundResource(R.drawable.corner_btn_green);
                                    btnvacation.setText("لغو مرخصی");
                                    rec.setVisibility(View.GONE);
                                    txtstatus.setVisibility(View.VISIBLE);
                                    txtstatus.setText("در این روز مرخصی هستید.");
                                    status = 1;
                                } else {
                                    status = 0;
                                    btnvacation.setBackgroundResource(R.drawable.corner_btn_blue);
                                    btnvacation.setText("مرخصی");
                                    rec.setVisibility(View.GONE);
                                    txtstatus.setVisibility(View.VISIBLE);
                                }
//                                    ActivityVisit.btnvacation.setVisibility(View.VISIBLE);
                                for (String t : arrtime) {
                                    DayReserve day = new DayReserve();
                                    day.setTime(t);
                                    day.setNumbers("");
                                    day.setNames("");
                                    day.setVip("");
                                    reserveItems.add(day);

                                }
//                                                         rec.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,ActivityVisit2.this));
//                                                         recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
//                                    rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
                            } else {
//                                    ActivityVisit.btnvacation.setVisibility(View.GONE);
                                btnvacation.setVisibility(View.INVISIBLE);

                                ArrayList<Repitem> arrrep = new ArrayList<>();
                                for (Nobat i : arrnobat) {
                                    arrrep.add(getrepeat(arrnobat, i.getTime()));
                                }
                                reserveItems = setnobat(arrtime, arrrep);

//                                    rcyclrday.scrollToPosition(0);


                            }
                            new CountDownTimer(3000, 10) {
                                public void onTick(long millisUntilFinished) {
                                    int i = Integer.parseInt(millisUntilFinished + "");
//                                    int percent = 100 ;
//                                    int progress = (i / 1000) * percent;
////                                    mPager.setCurrentItem(i / 1000);
//                                    prload.setProgress(progress);
//                                    int pr = 100 - progress;
//                                    txtprogress.setText(pr + "%");
//                                    btnvacation.setVisibility(View.GONE);
                                    rec.setVisibility(View.GONE);
                                    prload.setVisibility(View.VISIBLE);

                                }

                                public void onFinish() {
//                                    mPager.setVisibility(View.VISIBLE);
//                                    crdprogress.setVisibility(View.GONE);
                                    rec.setVisibility(View.VISIBLE);
                                    prload.setVisibility(View.GONE);
                                    laybtn.setVisibility(View.VISIBLE);

                                }
                            }.start();
                            rec.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems, ActivityVisit2.this,dset+"/"+mset+"/"+yset));
                            recyclerAdapterVisit.notifyDataSetChanged();
//                                rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                recyclerAdapterVisit.notifyDataSetChanged();
//                                rcyclrday.scrollToPosition(0);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

                        }
                    });
                }
            }
        });
        btnsettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar(nowst);


                DatePickerDialog datePickerDialog = DatePickerDialog
                        .newInstance(new DatePickerDialog.OnDateSetListener() {
                                         @Override
                                         public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                                             Log.e("dataset =",dayOfMonth+"/"+monthOfYear+"/"+year + "");
                                             yset = year;
                                             mset = monthOfYear + 1;
                                             dset = dayOfMonth;
                                             PersianCalendar dateset = new PersianCalendar();
                                             dateset.setPersianDate(yset,monthOfYear,dset);
                                             Call<ArrayList<Nobat>> callreserve = RInode.getreserve(BasicActivity.content, year, monthOfYear + 1, dayOfMonth, BasicActivity.userid, BasicActivity.number);
                                             callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
                                                 @Override
                                                 public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {

                                                     reserveItems.clear();
                                                     arrnobat = response.body();
//                                                     txtdate.setText(dset+"/"+mset+"/"+yset+" "+dateset.getPersianWeekDayName());
                                                     txtdate.setText(dateset.getPersianWeekDayName()+" "+dset+" "+dateset.getPersianMonthName()+" "+yset);

                                                     if (arrnobat.size() == 0) {
                                                         btnvacation.setVisibility(View.VISIBLE);
//                                                         Log.e("vac =", checkvacation(arrvacation, yset, mset, dset) + "");
                                                         if (checkvacation(arrvacation, yset, mset, dset)) {
                                                             btnvacation.setBackgroundResource(R.drawable.corner_btn_green);
                                                             btnvacation.setText("لغو مرخصی");
                                                             rec.setVisibility(View.GONE);
                                                             txtstatus.setVisibility(View.VISIBLE);
                                                             txtstatus.setText("در این روز مرخصی هستید.");
                                                             status = 1;
                                                         } else {
                                                             status = 0;
                                                             btnvacation.setBackgroundResource(R.drawable.corner_btn_blue);
                                                             btnvacation.setText("مرخصی");
                                                             rec.setVisibility(View.GONE);
                                                             txtstatus.setVisibility(View.VISIBLE);
                                                         }


                                                         for (String t : arrtime) {
                                                             DayReserve day = new DayReserve();
                                                             day.setTime(t);
                                                             day.setNumbers("");
                                                             day.setNames("");
                                                             reserveItems.add(day);

                                                         }
//                                                         rec.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,ActivityVisit2.this));
//                                                         recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
//                                    rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
                                                     } else {
//                                    ActivityVisit.btnvacation.setVisibility(View.GONE);
                                                         btnvacation.setVisibility(View.INVISIBLE);

                                                         ArrayList<Repitem> arrrep = new ArrayList<>();
                                                         for (Nobat i : arrnobat) {
//                                                             Log.e("noba =", getrepeat(arrnobat, i.getTime()).getUser()+"??"+getrepeat(arrnobat, i.getTime()).getTime());

                                                             arrrep.add(getrepeat(arrnobat, i.getTime()));
                                                         }
                                                         reserveItems = setnobat(arrtime, arrrep);

//                                    rcyclrday.scrollToPosition(0);


                                                     }
                                                     if (status == 0) {
                                                         new CountDownTimer(3000, 10) {
                                                             public void onTick(long millisUntilFinished) {
                                                                 int i = Integer.parseInt(millisUntilFinished + "");
//                                    int percent = 100 ;
//                                    int progress = (i / 1000) * percent;
////                                    mPager.setCurrentItem(i / 1000);
//                                    prload.setProgress(progress);
//                                    int pr = 100 - progress;
//                                    txtprogress.setText(pr + "%");
//                                    btnvacation.setVisibility(View.GONE);
                                                                 rec.setVisibility(View.GONE);
                                                                 prload.setVisibility(View.VISIBLE);
                                                                 txtstatus.setVisibility(View.GONE);

                                                             }

                                                             public void onFinish() {
//                                    mPager.setVisibility(View.VISIBLE);
//                                    crdprogress.setVisibility(View.GONE);
                                                                 rec.setVisibility(View.VISIBLE);
                                                                 prload.setVisibility(View.GONE);
                                                                 laybtn.setVisibility(View.VISIBLE);
                                                                 txtstatus.setVisibility(View.GONE);
                                                             }
                                                         }.start();
                                                     }
                                                     rec.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems, ActivityVisit2.this,dset+"/"+mset+"/"+yset));
                                                     recyclerAdapterVisit.notifyDataSetChanged();
//                                rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                recyclerAdapterVisit.notifyDataSetChanged();
//                                rcyclrday.scrollToPosition(0);
                                                 }

                                                 @Override
                                                 public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

                                                 }
                                             });
                                         }

                                     }, now.getPersianYear(),
                                now.getPersianMonth(),
                                now.getPersianDay());
                datePickerDialog.setThemeDark(false);

                datePickerDialog.show(getFragmentManager(), "tpd");
//
            }
        });

    }

    public ArrayList<DayReserve> setnobat(ArrayList<String> arrtime, ArrayList<Repitem> arrnobat) {
        ArrayList<DayReserve> reservearr = new ArrayList<>();
        ArrayList<Reserve> resarr = new ArrayList<>();

        for (String t : arrtime) {
            DayReserve reserve = new DayReserve();
            reserve.setTime(t);
            for (Repitem r : arrnobat) {
//                Log.e("time =", r.getTime()+ "??"+t);

                if (r.getTime().equals(t)) {

                    reserve.setNumbers(r.getNumber());
                    reserve.setNames(r.getName());
                    reserve.setVip(r.getVip());
//                    Log.e("noba=", r.getTime()+ "??"+r.getUser());
                    break;

//                    Log.e("nobares =", reserve.getTime()+ "??"+reserve.getUsers());
                } else {
//                    if(r.getUser().isEmpty()){
                    reserve.setNumbers("");
                    reserve.setNames("");
                    reserve.setVip("");
//                    Log.e("nobaels=", r.getTime() + "??" + t + "??" + r.getUser());

//                    }
//                    Log.e("nobaej =", r.getTime()+ "??"+r.getUser());

                }

            }
//            Log.e("nobares =", reserve.getTime()+ "??"+reserve.getUsers());

            reservearr.add(reserve);
        }
//        for (DayReserve dr : reservearr) {
//            Log.e("nobatrep =", dr.getUsers()+ "");
//
//        }
        return reservearr;
    }

    public Repitem getrepeat(ArrayList<Nobat> rep, String time) {

        Repitem report = new Repitem();
        String number = "";
        String name = "";
        String vip = "";
        int tedad = 0;
//        String time = "";
        for (Nobat i : rep) {

            if (i.getTime().equals(time)) {

                tedad++;
                number = number + "/" + i.getNumber();
                name = name + "/" + i.getName();
                vip = vip + "/" + i.getVip();

            }
        }

        if (tedad != 0) {
            report.setTime(time);
            report.setTedad(tedad);
            report.setName(name);
            report.setNumber(number);
            report.setVip(vip);
        }


        return report;
    }

    private Boolean checkvacation(ArrayList<String> arrvac, int y, int m, int d) {

        boolean vacation = false;
        for (String v : arrvac) {
            String[] partday = v.split("/");
            int dv = Integer.parseInt(partday[0]);
            int mv = Integer.parseInt(partday[1]);
            int yv = Integer.parseInt(partday[2]);
//            Log.e("vaca =", "yv="+yv+"/"+"mv="+mv+"/"+"dv="+dv+"/"+"y="+y+"/"+"m="+m+"/"+"d="+d);

            if (yv == y && mv == m & dv == d) {
                vacation = true;
                break;
            }
        }

        return vacation;
    }

    private String gettime(long ts) {
        PersianDate cal = new PersianDate(ts);
        cal.getHour();

        return cal.getHour() + ":" + cal.getMinute();
    }

    private int daytstoint(long ts) {
        PersianDate cal1 = new PersianDate(ts);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int day = cal1.dayOfWeek();
        return day;

    }
}
