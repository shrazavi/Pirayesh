package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.graphics.Color;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Date;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.Holiday;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Repitem;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

public class ActivityReserve2 extends AppCompatActivity {
    Retrofitinformation RInode;
    String content;
    String bu;
    Button btnreserve,btnback;
    ArrayList<DayReserve> arrday = new ArrayList<>();
    ArrayList<String> arrtime = new ArrayList<>();
    ArrayList<String> newtime = new ArrayList<>();
    ArrayList<String> vocation = new ArrayList<>();
    ArrayList<Holiday> holidays = new ArrayList<>();
    String time, userid, day, timeserver;
    int dateserver = 0, holiday = 0, dayoff = 0, barbers = 0;
    long nowst = 0;
    Thread thread;
    TextInputEditText edtday, edtname, edtnumber;
    AutoCompleteTextView spintime;
    ConstraintLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_timewomen);
        }
        setContentView(R.layout.activity_reserve);
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        userid = BasicActivity.userid;
        bu = BasicActivity.bu;
        content = BasicActivity.content;
        edtname = findViewById(R.id.edt_reserve_name);
        edtnumber = findViewById(R.id.edt_reserve_number);
        edtday = findViewById(R.id.edt_reserve_day);
        spintime = findViewById(R.id.spn_reserve_time);
        btnreserve = findViewById(R.id.btn_reserve);
        btnback = findViewById(R.id.btn_reserve_back);
        lay= findViewById(R.id.lay_reserve);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        Call<Date> calldate = RInode.getdate();
        calldate.enqueue(new Callback<Date>() {
            @Override
            public void onResponse(Call<Date> call, Response<Date> response) {
                timeserver = gettime(response.body().getDate());
                dateserver = daytstoint(response.body().getDate());
                nowst = response.body().getDate();
            }

            @Override
            public void onFailure(Call<Date> call, Throwable t) {

            }
        });
        Call<Barber> callbarber = RInode.getbarber(content, BasicActivity.userid, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                //------------rooze rezerve shode-------------
                barbers = response.body().getBarbers();
                arrtime = response.body().getArraytime();
//                friday = response.body().getFriday();
                holiday = response.body().getHoliday();
                dayoff = response.body().getDayoff();
                vocation=response.body().getVacation();


            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("vakilerr=", "" + t);

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityReserve2.this, ActivityMain.class);
                ActivityReserve2.this.startActivity(intent);
                ActivityReserve2.this.finish();
            }
        });
        edtday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersianCalendar now = new PersianCalendar(nowst);

                DatePickerDialog datePickerDialog = DatePickerDialog
                        .newInstance(new DatePickerDialog.OnDateSetListener() {
                                         @Override
                                         public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                             if (checktime(now.getPersianYear(), now.getPersianMonth(),
                                                     now.getPersianDay(), year, monthOfYear, dayOfMonth)) {

                                                 if (checkdayvacation(year, monthOfYear + 1, dayOfMonth, vocation)) {
//                                                     Log.e("day",  holidays.size()+"");
                                                     if (checkholiday(year, monthOfYear + 1, dayOfMonth, holidays, holiday)) {

                                                         PersianCalendar res = new PersianCalendar();
//                                                                 Log.e("nobatset", dayOfMonth + "/" + monthOfYear + "/" + year);

                                                         res.setPersianDate(year, monthOfYear, dayOfMonth);
//                                                                 Log.e("nobatweek", res.getPersianDay() + "/" + res.getPersianMonth() + "/" + res.getPersianYear() + "/" + res.getPersianWeekDayName());

//                                                         if (friday == 0 && res.getPersianWeekDayName().equals("جمعه")) {
//                                                             Toast.makeText(ActivityReserve2.this, "آرایشگر مورد نظر در روز جمعه فعالیت ندارد.", Toast.LENGTH_LONG).show();
//
//                                                         } else {

                                                             int mont = monthOfYear + 1;
                                                             edtday.setText(year + "/" +
                                                                     mont + "/" +
                                                                     dayOfMonth);
                                                             thread = new Thread(new Runnable() {
                                                                 @Override
                                                                 public void run() {


                                                                     Call<ArrayList<Nobat>> callreserve = RInode.getreserve(content, year, mont, dayOfMonth, BasicActivity.userid, BasicActivity.number);
                                                                     callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
                                                                         @Override
                                                                         public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {
                                                                             ArrayList<Repitem> arrrep = new ArrayList<>();
                                                                             ArrayList<Nobat> arrloop = new ArrayList<>();
                                                                             arrloop = response.body();
//

                                                                             if (arrloop.size() == 0) {
                                                                                 newtime = arrtime;
                                                                             } else {
                                                                                 for (Nobat i : arrloop) {
                                                                                     arrrep.add(getrepeat(arrloop, i.getTime()));
                                                                                 }

                                                                                 for (Iterator<String> it1 = arrtime.iterator(); it1.hasNext(); ) {
                                                                                     String val1 = it1.next();

                                                                                     for (Repitem k : arrrep) {
                                                                                         if (k.getTime().equals(val1)) {
                                                                                             if (k.getTedad() >= barbers) {
                                                                                                 it1.remove();
                                                                                                 break;

                                                                                             } else {

                                                                                             }
                                                                                         } else {

                                                                                         }
                                                                                     }

                                                                                 }
                                                                                 newtime = arrtime;
                                                                                 Log.e("newtime", newtime.size() + "");

                                                                             }

                                                                             spinnerAdapter Adapterbarbers = new spinnerAdapter(ActivityReserve2.this, android.R.layout.simple_list_item_1);
                                                                             Adapterbarbers.addAll(newtime);
                                                                             spintime.setAdapter(Adapterbarbers);
                                                                         }

                                                                         @Override
                                                                         public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

                                                                         }
                                                                     });


                                                                 }

                                                             });

                                                             thread.start();

//                                                         }
                                                     } else {
                                                         Toast.makeText(ActivityReserve2.this, "آرایشگر مورد نظر در روز تعطیل فعالیت ندارد.", Toast.LENGTH_SHORT).show();

                                                     }

                                                 } else {
                                                     Toast.makeText(ActivityReserve2.this, "آرایشگر مورد نظر در این روز فعالیت ندارد.", Toast.LENGTH_SHORT).show();

                                                 }
                                             } else {
                                                 Toast.makeText(ActivityReserve2.this, "تاریخ انتخابی گذشته است.", Toast.LENGTH_SHORT).show();
                                             }

                                         }


                                     }, now.getPersianYear(),
                                now.getPersianMonth(),
                                now.getPersianDay());
                datePickerDialog.setThemeDark(false);

                datePickerDialog.show(getFragmentManager(), "tpd");


            }
        });
        btnreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtday.getText().toString().isEmpty() || spintime.getText().toString().isEmpty() || edtnumber.getText().toString().isEmpty() || edtname.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityReserve2.this, "لطفا اطلاعات مورد نظر را وارد کنید", Toast.LENGTH_SHORT).show();
                } else {
//                            for (int i = 0; i < arrtime.length; i++) {
//                                if (arrtime[i].equals(spintime.getText().toString())) {
//                                } else {
//                                    newtime.add(arrtime[i]);
//                                }
//                            }
//                Log.e("newtime", newtime+"");
                    String[] partday = edtday.getText().toString().split("/");
                    int y = Integer.parseInt(partday[0]);
                    int m = Integer.parseInt(partday[1]);
                    int d = Integer.parseInt(partday[2]);
//                    Log.e("edtday", "y=" + y + "/" + "m=" + m + "/" + "d=" + d);
                    Call<MessageSignup> callreserve = RInode.insertreserve(content, BasicActivity.userid, edtname.getText().toString(), "+98"+edtnumber.getText().toString(), BasicActivity.userid, y, m, d, spintime.getText().toString(),0, BasicActivity.number);
                    callreserve.enqueue(new Callback<MessageSignup>() {
                        @Override
                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
//                            Log.e("messagereserve", response.body().getMessage() + "");

                            Intent intent3 = new Intent(ActivityReserve2.this, ActivityMain.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent3);
                            ActivityReserve2.this.finish();
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

                }
            }
        });


    }


    private String gettime(long ts) {
        PersianDate cal = new PersianDate(ts);
        cal.getHour();

        return cal.getHour() + ":" + cal.getMinute();
    }


    public Repitem getrepeat(ArrayList<Nobat> rep, String time) {
        Repitem report = new Repitem();

        int tedad = 0;
//        String time = "";
        for (Nobat i : rep) {

            if (i.getTime().equals(time)) {

                tedad++;


            }
        }

        if (tedad != 0) {
            report.setTime(time);
            report.setTedad(tedad);
        }


        return report;
    }

    private int daytstoint(long ts) {
        PersianDate cal1 = new PersianDate(ts);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int day = cal1.dayOfWeek();
        return day;

    }

    public boolean checktime(int ny, int nm, int nd, int y, int m, int d) {
        boolean time = false;

        if (y < ny) {
            time = false;
        } else if (y == ny) {
            if (m < nm) {
                time = false;
            } else if (m == nm) {
                if (d < nd) {
                    time = false;
                } else if (d == nd) {
                    time = true;
                } else {
                    time = true;
                }
            } else {
                time = true;
            }
        } else {
            time = true;
        }


        return time;
    }

    public boolean checkdayvacation(int y, int m, int d, ArrayList<String> arrdayoff) {
        boolean dayoff = true;

        for (String f : arrdayoff) {
            String[] partday = f.split("/");
//            int yoff = Integer.parseInt(partday[0]);
//            int moff = Integer.parseInt(partday[1]);
//            int doff = Integer.parseInt(partday[2]);
            int doff = Integer.parseInt(partday[0]);
            int moff = Integer.parseInt(partday[1]);
            int yoff = Integer.parseInt(partday[2]);

            if (y == yoff && m == moff && d == doff) {
                dayoff = false;
                break;
            }
        }
        for (int i = 0; i <= arrdayoff.size(); i++) {


        }


        return dayoff;
    }

    public boolean checkholiday(int y, int m, int d, ArrayList<Holiday> arrholiday, int holiday) {
        boolean holi = true;
        if (holiday == 0) {
            for (Holiday h : arrholiday) {
                Log.e("holliday", h.getDay() + "/" + h.getMonth() + "/" + h.getYear());
                Log.e("day", d + "/" + m + "/" + y);
                if (y == h.getYear() && m == h.getMonth() && d == h.getDay()) {
                    holi = false;
                    break;
                }
            }
            for (int i = 0; i <= arrholiday.size(); i++) {


            }
        } else {
            holi = true;
        }


        return holi;
    }

}
