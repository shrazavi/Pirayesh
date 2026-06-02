package com.shrazavi.pirayesh.Activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.Adapter.SpinnerINTAdapter;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Date;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Nobat;

import com.shrazavi.pirayesh.DataClass.Shift;
import com.shrazavi.pirayesh.DataClass.Week;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

public class ActivityTimework extends AppCompatActivity {
    int holiday = 0, dayoff = 0;
    TextView txtswitchho, txtvip, txtswdayoff;
    Switch swtimeho, swdayoff;
    TextInputEditText edtdatevisit;
    AutoCompleteTextView edttimework, edtbarbers, edtvip;
    LinearLayout laytime, laybarbers, laycustomer, layvip, layweek, layholiday;
    Button btnok, btnback, btnshift;
    Thread thread;
    private ArrayList<Shift> shifts = new ArrayList<>();
    ArrayList<String> selectedItemsList = new ArrayList<>();
    private ArrayList<Week> week = new ArrayList();
    Integer[] arrtimework = {30, 45, 60, 90, 0};
    Integer[] arrbarbers = {1, 2, 3, 4, 0};
    Integer[] arrvip = {1, 2, 3, 5, 6, 7, 8, 9, 10, 0};
    String days = "";
    Retrofitinformation RInode;
    String content;
    String enk;
    SecretKeySpec Key;
    String username = "";
    String bu = "";
    String timeserver = "";
    int dateserver = 0;
    //    public SharedPreferences preferences;
    public static MaterialProgressBar prload;
    int resstatus1 = 0;
    int resstatus2 = 0;
    ConstraintLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_timewomen);
        }
        setContentView(R.layout.activity_time_work);
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        username = BasicActivity.userid;
        bu = BasicActivity.bu;
        content = BasicActivity.content;
        shifts = new ArrayList<>();
        prload = findViewById(R.id.pr_time_load);
        laytime = (LinearLayout) findViewById(R.id.lay_time_hour);
        laybarbers = (LinearLayout) findViewById(R.id.lay_time_barbers);
        layweek = (LinearLayout) findViewById(R.id.lay_time_week);
        layvip = (LinearLayout) findViewById(R.id.lay_time_vip);
        laycustomer = (LinearLayout) findViewById(R.id.lay_time_customer);
        lay = findViewById(R.id.lay_time_work);
        layholiday = (LinearLayout) findViewById(R.id.lay_time_holiday);
        btnok = (Button) findViewById(R.id.btn_time_ok);
        btnshift = (Button) findViewById(R.id.btn_time_shift);
        btnback = (Button) findViewById(R.id.btn_time_back);
        swtimeho = (Switch) findViewById(R.id.sw_time_holiday);
        swdayoff = (Switch) findViewById(R.id.sw_time_dayoff);
//        swtimefr = (Switch) findViewById(R.id.sw_time_friday);
        txtvip = (TextView) findViewById(R.id.txt_time_vip);
        txtswitchho = (TextView) findViewById(R.id.txt_time_switch);
        txtswdayoff = (TextView) findViewById(R.id.txt_time_dayoff);
        edttimework = (AutoCompleteTextView) findViewById(R.id.edt_time_work);
        edtbarbers = (AutoCompleteTextView) findViewById(R.id.edt_time_barbers);
        edtvip = (AutoCompleteTextView) findViewById(R.id.edt_time_vip);
        edtdatevisit = (TextInputEditText) findViewById(R.id.edt_time_visit_date);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
            txtvip.setText(R.string.vipman);
        } else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
            txtvip.setText(R.string.vipwoman);
        }
        prload.setVisibility(View.GONE);
        btnok.setVisibility(View.VISIBLE);
        Call<Date> calldate = RInode.getdate();
        calldate.enqueue(new Callback<Date>() {
            @Override
            public void onResponse(Call<Date> call, Response<Date> response) {
                timeserver = gettime(response.body().getDate());
                dateserver = daytstoint(response.body().getDate());
            }

            @Override
            public void onFailure(Call<Date> call, Throwable t) {

            }
        });
//        Call<ArrayList<Nobat>> callreserve = RInode.getreserve(content, username);
//        callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {
////                String time = response.body().getTime();
//                String[] parttimeserver = timeserver.split(":");
////                HashMap<Integer,Integer> map=new HashMap<Integer, Integer>();
//
//                int hserver = Integer.parseInt(parttimeserver[0]);
//                int mserver = Integer.parseInt(parttimeserver[1]);
//                for (int i = 0; i < response.body().size(); i++) {
//                    String[] timereserve = response.body().get(i).getTime().split(":");
//                    int h = Integer.parseInt(timereserve[0]);
//                    int m = Integer.parseInt(timereserve[1]);
//                    if (getdayofweek(response.body().get(i).getDay()) < dateserver) {
////                        map.put(i,0);
//                    } else if (getdayofweek(response.body().get(i).getDay()) == dateserver) {
//                        if (h < hserver) {
//                            if (m < mserver) {
////                                map.put(i,0);
//                            } else {
////                                map.put(i,1);
//                                resstatus2++;
//                            }
//                        } else {
////                            map.put(i,1);
//                            resstatus2++;
//                        }
//                    } else {
////                        map.put(i,1);
//                        resstatus2++;
//                    }
////                    Log.e("arrday1", response.body().get(i).getHours()+"");
//                }
////                for (int j=0;j<=map.size();j++){
////                    if(map.get(j)==1){
////                        resstatus1++;
////                    }
////                }
//                Log.e("resstatus1", resstatus1 + "");
//                Log.e("resstatus2", resstatus2 + "");
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {
//
//            }
//        });
        Call<ArrayList<Shift>> callshift = RInode.getshift(content, username, BasicActivity.number);
        callshift.enqueue(new Callback<ArrayList<Shift>>() {
            @Override
            public void onResponse(Call<ArrayList<Shift>> call, Response<ArrayList<Shift>> response) {
                shifts = response.body();
                Comparator<Shift> comparator = new Comparator<Shift>() {
                    @Override
                    public int compare(Shift left, Shift right) {
                        return left.getH1() - right.getH1(); // use your logic
                    }
                };
                Collections.sort(shifts, comparator);


            }

            @Override
            public void onFailure(Call<ArrayList<Shift>> call, Throwable t) {

            }
        });
        Log.e("userid", BasicActivity.userid + "");

        Call<Barber> callbarber = RInode.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                edtbarbers.setText(response.body().getBarbers() + "");
                edttimework.setText(response.body().getTimework() + "");
                edtvip.setText(response.body().getVip() + "");
                holiday = response.body().getHoliday();
                dayoff = response.body().getDayoff();
                edtdatevisit.setText(response.body().getActivedate());
                SpinnerINTAdapter Adaptertimework = new SpinnerINTAdapter(ActivityTimework.this, android.R.layout.simple_list_item_1);
                Adaptertimework.addAll(arrtimework);
                edttimework.setAdapter(Adaptertimework);

                SpinnerINTAdapter Adapterbarbers = new SpinnerINTAdapter(ActivityTimework.this, android.R.layout.simple_list_item_1);
                Adapterbarbers.addAll(arrbarbers);
                edtbarbers.setAdapter(Adapterbarbers);

                SpinnerINTAdapter Adaptervip = new SpinnerINTAdapter(ActivityTimework.this, android.R.layout.simple_list_item_1);
                Adaptervip.addAll(arrvip);
                edtvip.setAdapter(Adaptervip);
                if (holiday == 1) {

                    swtimeho.setChecked(true);
                    txtswitchho.setText("در روز های تعطیل فعالیت دارم");

                } else {
                    swtimeho.setChecked(false);
                    txtswitchho.setText("در روز های تعطیل فعالیت ندارم");

                }
                if (dayoff == 1) {

                    swdayoff.setChecked(false);
                    txtswdayoff.setText("نوبت دهی غیر فعال");
                    laycustomer.setVisibility(View.GONE);
                    layvip.setVisibility(View.GONE);
                    laybarbers.setVisibility(View.GONE);
                    layweek.setVisibility(View.GONE);
                    layholiday.setVisibility(View.GONE);
                    laytime.setVisibility(View.GONE);

                } else {
                    swdayoff.setChecked(true);
                    txtswdayoff.setText("نوبت دهی فعال");
                    laycustomer.setVisibility(View.VISIBLE);
                    layvip.setVisibility(View.VISIBLE);
                    laybarbers.setVisibility(View.VISIBLE);
                    layweek.setVisibility(View.VISIBLE);
                    layholiday.setVisibility(View.VISIBLE);
                    laytime.setVisibility(View.VISIBLE);
                }
//                if (friday == 1) {
//
//                    swtimefr.setChecked(true);
//                    txtswitchfr.setText("در روز جمعه فعالیت دارم");
//
//                } else {
//                    swtimefr.setChecked(false);
//                    txtswitchfr.setText("در روز جمعه فعالیت ندارم");
//
//                }
            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("error???", t + "");
            }
        });
        btnshift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTimework.this, ActivityShift.class);
                startActivity(intent);

            }
        });
        swtimeho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    txtswitchho.setText("در روز های تعطیل فعالیت دارم");

                    holiday = 1;
                } else {
                    txtswitchho.setText("در روز های تعطیل فعالیت ندارم");

                    holiday = 0;
                }
            }
        });
        swdayoff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    txtswdayoff.setText("نوبت دهی فعال");
                    laycustomer.setVisibility(View.VISIBLE);
                    layvip.setVisibility(View.VISIBLE);
                    laybarbers.setVisibility(View.VISIBLE);
                    layweek.setVisibility(View.VISIBLE);
                    layholiday.setVisibility(View.VISIBLE);
                    laytime.setVisibility(View.VISIBLE);
                    dayoff = 0;
                } else {
                    txtswdayoff.setText("نوبت دهی غیر فعال");
                    laycustomer.setVisibility(View.GONE);
                    layvip.setVisibility(View.GONE);
                    laybarbers.setVisibility(View.GONE);
                    layweek.setVisibility(View.GONE);
                    layholiday.setVisibility(View.GONE);
                    laytime.setVisibility(View.GONE);
                    dayoff = 1;

                }
            }
        });

//        swtimefr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // do something, the isChecked will be
//                // true if the switch is in the On position
//                if (isChecked) {
//                    txtswitchfr.setText("در روز جمعه فعالیت دارم");
//
//                    friday = 1;
//                } else {
//                    txtswitchfr.setText("در روز جمعه فعالیت ندارم");
//
//                    friday = 0;
//                }
//            }
//        });
        edtdatevisit.setInputType(InputType.TYPE_NULL);
        edtdatevisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean[] checkedItems = new boolean[]{false, false, false, false, false, false, false};

                week.clear();

                String[] listItems = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};


                for (int i = 0; i < listItems.length; i++) {
                    Week w1 = new Week();
                    w1.setWeekname(listItems[i]);
                    w1.setChecked(false);
                    week.add(w1);

                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTimework.this);
                builder.setTitle("روزهای فعال بودن در اپلیکیشن");

                //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                        Toast.makeText(ActivitySetting.this, "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();


                        Week w = new Week();
                        w.setWeekname(listItems[which]);
                        w.setChecked(isChecked);
                        week.set(which, w);
//                        Log.e("cheked", isChecked + "");
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        StringBuilder result = new StringBuilder();

                        for (Week item : week) {
//                            Log.e("week", item.getWeekname() + item.isChecked);
                            if (item.isChecked) {
//                                selectedItemsList.add(item.getWeek());
//                                Log.e("weekcheked", item.getWeek() + "");

                                result.append(item.getWeekname());
                                result.append("  ");
//                                days += item.getWeek() + " ";
                            }
                        }

//                        Log.e("week", week + "");


                        edtdatevisit.setText(result);
                        days = "";
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        edtdatevisit.setText(days + "");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("username", username);

//                Log.e("reserve=", reserve + "");
                if (dayoff == 0) {
                    if (edtbarbers.toString().isEmpty() || edtvip.toString().isEmpty() || edttimework.toString().isEmpty()) {
                        Toast.makeText(ActivityTimework.this, "لطفا فرم را تکمیل نمایید.", Toast.LENGTH_SHORT).show();
                    } else {
                        prload.setVisibility(View.VISIBLE);
                        btnok.setVisibility(View.GONE);

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    Thread.sleep(5000);
                                    String[] arrday = edtdatevisit.getText().toString().split("  ");
                                    selectedItemsList = new ArrayList<>(Arrays.asList(arrday));
//                                    Log.e("vakil", username + "/n" + edtvisitstart.getText().toString() + "/n" + edtvisitend.getText().toString() + "/n" + edtdatevisit.getText().toString() + "/n" + arrtime + "/n" + selectedItemsList + "/n");
                                    Call<MessageSignup> callvakil = RInode.settimebr(BasicActivity.content,
                                            BasicActivity.userid,
                                            shifts.size(),
                                            Integer.parseInt(edtbarbers.getText().toString()),
                                            Integer.parseInt(edttimework.getText().toString()),
                                            Integer.parseInt(edtvip.getText().toString()),
                                            holiday,
                                            dayoff,
                                            selectedItemsList,
                                            edtdatevisit.getText().toString(),
                                            BasicActivity.number);

                                    callvakil.enqueue(new Callback<MessageSignup>() {
                                        @Override
                                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                            Boolean status = response.body().getStatus();
                                            if (status) {
                                                Log.e("mess", response.body().getMessage());
                                                prload.setVisibility(View.GONE);
                                                btnok.setVisibility(View.VISIBLE);
                                                Intent intent = new Intent(ActivityTimework.this, ActivityOption.class);
                                                ActivityTimework.this.startActivity(intent);
                                                ActivityTimework.this.finish();
                                            } else {

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MessageSignup> call, Throwable t) {
                                            Log.e("error???", t + "");
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }


                            }

                        });

                        thread.start();

//
                    }
                } else {
                    Call<MessageSignup> calldayoff = RInode.insertdayoff(BasicActivity.content, BasicActivity.userid, dayoff, BasicActivity.number);
                    calldayoff.enqueue(new Callback<MessageSignup>() {
                        @Override
                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                            Boolean status = response.body().getStatus();
                            if (status) {
                                Log.e("mess", response.body().getMessage());
                                prload.setVisibility(View.GONE);
                                btnok.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(ActivityTimework.this, ActivityOption.class);
                                ActivityTimework.this.startActivity(intent);
                                ActivityTimework.this.finish();
                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<MessageSignup> call, Throwable t) {
                            Log.e("error???", t + "");
                        }
                    });

//                    AlertDialog.Builder ab = new AlertDialog.Builder(ActivityTimework.this);
//                    ab.setMessage(R.string.reserve).setNeutralButton("فهمیدم", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    }).show();
                }
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityTimework.this, ActivityOption.class);
//                ActivityTimework.this.startActivity(intent);
                ActivityTimework.this.finish();
            }
        });

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

    private int getdayofweek(String day) {

        switch (day) {
            case "شنبه":
                return 0;
            case "یکشنبه":
                return 1;

            case "دوشنبه":
                return 2;

            case "سه شنبه":
                return 3;

            case "چهارشنبه":
                return 4;

            case "پنجشنبه":
                return 5;

            case "جمعه":
                return 6;


        }

        return 0;
    }
}
