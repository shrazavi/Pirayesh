package com.shrazavi.pirayesh.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;

import com.sardari.daterangepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterTicket;
import com.shrazavi.pirayesh.Adapter.SpinnerINTAdapter;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Cash;
import com.shrazavi.pirayesh.DataClass.Date;
import com.shrazavi.pirayesh.DataClass.Holiday;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Nobat;

import com.shrazavi.pirayesh.DataClass.Rating;
import com.shrazavi.pirayesh.DataClass.Repitem;
import com.shrazavi.pirayesh.DataClass.Ticket;
import com.shrazavi.pirayesh.DataClass.User;

import com.shrazavi.pirayesh.DataClass.Week;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityProfileBr extends AppCompatActivity {
    //    String[] arrtime;
    ArrayList<String> arrtime = new ArrayList<>();
    ArrayList<String> arrblock = new ArrayList<>();
    ArrayList<String> newtime = new ArrayList<>();
    ArrayList<String> vocation = new ArrayList<>();
    ArrayList<String> visitdate = new ArrayList<>();
    ArrayList<String> arrweek = new ArrayList<>();
    ArrayList<Holiday> holidays = new ArrayList<>();
    ArrayList<Nobat> arrloop = new ArrayList<>();
    Thread thread;
    //    ArrayList<DayReserve> arrday = new ArrayList<>();
    //    String[] arrcal = {"متنی", "صوتی", "تصویری", "تصویری"};
//    AutoCompleteTextView timespin, calspin;
    CardView crdvisit, crdprof;
    //    String typecall = "";
    String content;
    String blockusers;
    String enk;
    SecretKeySpec Key;
    SecretKeySpec secretKey;
    RatingBar rate;
    //    LinearLayout lay_call;
    TextView txtname, txtexperience, txtttitle, txtbio, txtreserve, txtprof, txtostan, txtshahr, txtaddress, txtphone, txtemail;
    Button btnreserve, btnvip,btnback;
    ImageView imgprof;
    //    String time = "";
//    String call = "";
    int barbers = 0;
    String toid = "";
    String imgurl = "";
    String userid;
    int chattarrif = 0;
    String nameuser;
    String bu = "";
    String name, bio;
    private Boolean isLoading = false;
    String timeserver;
    int dateserver = 0;
    int experience = 0;
    int account = 0;
    int holiday = 0, dayoff = 0;
    int vip = 0, timework = 0;
    //    int pricevl = 0;
    int timer = 1500000;
    long nowst = 0;
    public Handler uploadHandler;
    private static final int PER_PAGE_SIZE_100 = 100;
    private static final String ORDER_RULE = "order";
    private static final String ORDER_DESC_UPDATED = "desc date updated_at";
    public static final String TOTAL_PAGES_BUNDLE_PARAM = "total_pages";
    private Boolean hasNextPage = true;
    //    private Socket socket;
    private int currentPage = 0;
//    public SharedPreferences preferences;
ConstraintLayout lay;

    Retrofitinformation RI;

//    {
//        try {
//            socket = IO.socket(G.nodeurl);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_profile_br);

//        socket.connect();
//        timespin = (AutoCompleteTextView) findViewById(R.id.spn_prof_vl_time);
//        calspin = (AutoCompleteTextView) findViewById(R.id.spn_prof_vl_type_call);
        txtname = (TextView) findViewById(R.id.txt_prof_br_name);
        txtprof = (TextView) findViewById(R.id.app_bar_txt_prof_br);
        txtostan = (TextView) findViewById(R.id.txt_prof_br_ostan);
        txtshahr = (TextView) findViewById(R.id.txt_prof_br_shahr);
        txtphone = (TextView) findViewById(R.id.txt_prof_br_phone);
        txtemail = (TextView) findViewById(R.id.txt_prof_br_email);
        txtaddress = (TextView) findViewById(R.id.txt_prof_br_address);
        txtttitle = (TextView) findViewById(R.id.txt_prof_br_title);
        lay= findViewById(R.id.lay_prof_br);

        txtexperience = (TextView) findViewById(R.id.txt_prof_br_experience);
        txtbio = (TextView) findViewById(R.id.txt_prof_br_bio);
        txtreserve = (TextView) findViewById(R.id.txt_prof_br_reserve);
        btnvip = (Button) findViewById(R.id.btn_prof_br_vip);
        btnback = (Button) findViewById(R.id.btn_prof_br_back);
        btnreserve = (Button) findViewById(R.id.btn_prof_br_reserve);
        rate = (RatingBar) findViewById(R.id.ratingbar_prof_br);
        btnvip.setTypeface(G.face);
        btnreserve.setTypeface(G.face);
        crdprof = (CardView) findViewById(R.id.app_bar_crd_prof_br);
        crdvisit = (CardView) findViewById(R.id.crd_prof_br_visit);
        imgprof = (ImageView) findViewById(R.id.app_bar_image_prof_br);

        toid = (String) getIntent().getExtras().get("userid");
        secretKey = SymmetricAlgorithmAES.setUpSecretKey();
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        userid = BasicActivity.userid;
        bu = BasicActivity.bu;
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        String[] listItems = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنجشنبه", "جمعه"};
//        for (int i = 0; i < listItems.length; i++) {
//            arrweek.add(listItems[i]);
//        }
        arrweek = new ArrayList<>(Arrays.asList(listItems));
//        crdcall.setVisibility(View.VISIBLE);
        crdvisit.setVisibility(View.VISIBLE);

        if (bu.equals("br")) {
//            crdcall.setVisibility(View.GONE);
            crdvisit.setVisibility(View.GONE);
            btnreserve.setVisibility(View.GONE);
//            btnactivedate.setVisibility(View.GONE);
            txtreserve.setVisibility(View.GONE);
        }
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
            btnvip.setText("دریافت نوبت ویژه (دامادی)");
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            btnvip.setText("دریافت نوبت ویژه (عروسی)");
            setTheme(R.style.Theme_mainwomen);
        }
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + userid + "-" + getResources().getString(R.string.developer), enk);
        content = BasicActivity.content;
        Log.e("content", content);

        Call<Rating> callrating = RI.getRating(toid);
        callrating.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
//                holder.txtrating.setText(response.body().getTotal()+"");
               rate.setRating(response.body().getTotal());
//                Log.e("rate is=", response.body().getTotal()+ "");
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.e("error rate", t + "");
            }
        });
        //------------tarikh alan-------------
        Call<Date> calldate = RI.getdate();
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
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityProfileBr.this, ActivityMain.class);
//                ActivityProfileBr.this.startActivity(intent);
                ActivityProfileBr.this.finish();
            }
        });
        txtphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(ActivityProfileBr.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(ActivityProfileBr.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                        return;
                    }
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + txtphone.getText().toString().trim()));
                    startActivity(callIntent);
                } else {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+" + txtphone.getText().toString().trim()));
                    startActivity(callIntent);
                }
            }
        });
        //------------tatilat-------------
        Call<ArrayList<Holiday>> getholiday = RI.getholiday(content, BasicActivity.number);
        getholiday.enqueue(new Callback<ArrayList<Holiday>>() {
            @Override
            public void onResponse(Call<ArrayList<Holiday>> call, Response<ArrayList<Holiday>> response) {
                holidays = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<Holiday>> call, Throwable t) {
                Log.e("holdierror", t + "");
            }
        });
        //------------etelaat barber-------------

        Call<Barber> callbarber = RI.getbarber(content, toid, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                //------------rooze rezerve shode-------------
                barbers = response.body().getBarbers();
                arrtime = response.body().getArraytime();
                timework = response.body().getTimework();
                vip = response.body().getVip();
                holiday = response.body().getHoliday();
                dayoff = response.body().getDayoff();
                vocation = response.body().getVacation();
                 blockusers = response.body().getBlockusers();
//                visitdate=response.body().getVisitdate();
                txtaddress.setText(response.body().getAddress());
                txtostan.setText(response.body().getOstan());
                txtshahr.setText(response.body().getShahr());
                txtphone.setText(response.body().getPhone());
                txtttitle.setText(response.body().getTitle());
                txtemail.setText(response.body().getEmail());
                name = response.body().getName();
                experience = response.body().getExperience();
                bio = response.body().getBio();
                txtname.setText(name);
                String[] arrblack = blockusers.split(",");
                arrblock = new ArrayList<String>(Arrays.asList(arrblack));
                if (arrblock.contains(BasicActivity.number)) {
//                    crdcall.setVisibility(View.GONE);
                    crdvisit.setVisibility(View.GONE);
                    Toast.makeText(G.context, "شما مسدود شدید", Toast.LENGTH_SHORT).show();
                } else {

                }
                String[] arrv = response.body().getActivedate().split("  ");
                visitdate = new ArrayList<>(Arrays.asList(arrv));
//                txtdegree.setText(response.body().getDegree());
//                txteducation.setText(response.body().getEducation());
//                txtspecialty.setText(response.body().getSpecialty());

                txtexperience.setText("با " + experience + " سال سابقه");
                txtbio.setText(bio);
//                Log.e("type vl=", response.body().getType()+ "");


                imgurl = G.nodeurl + "/" + response.body().getProfile();
                if (imgurl.equals(G.nodeurl + "/" + "empty")) {
                    char ch1 = response.body().getTitle().toUpperCase().charAt(0);
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
                    txtprof.setText(ch1 + "");
                    crdprof.setCardBackgroundColor(color);
                } else {
                    Picasso.get().load(imgurl).transform(new ImageProfile()).into(imgprof);

                }

            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("vakilerr=", "" + t);

            }
        });
        //------------etelaat karbar-------------
        Call<User> calluser = RI.getuser(content, userid, BasicActivity.number);
        calluser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                G.account = Integer.parseInt(response.body().getAccount());
//                account = Integer.parseInt(response.body().getAccount());
                Log.e("account", account + "");
                nameuser = response.body().getName();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        imgprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgurl.isEmpty()) {
                } else {
                    Intent intent = new Intent(ActivityProfileBr.this, ActivityImageView.class);
                    intent.putExtra("imgurl", imgurl);
                    intent.putExtra("ac", "br");
                    intent.putExtra("down", false);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ActivityProfileBr.this.startActivity(intent);
                }
            }
        });
        btnvip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder mBuild = new android.app.AlertDialog.Builder(ActivityProfileBr.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_reserve, null);


                TextInputEditText edtday = (TextInputEditText) mView.findViewById(R.id.edt_fr_reserve_day);
                AutoCompleteTextView spintime = (AutoCompleteTextView) mView.findViewById(R.id.spn_fr_reserve_time);
                Button btnSubmit = (Button) mView.findViewById(R.id.btn_fr_reserve);


                mBuild.setView(mView);
                android.app.AlertDialog dialog = mBuild.create();
                dialog.show();
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
                                                             Log.e("day", holidays.size() + "");
                                                             if (checkholiday(year, monthOfYear + 1, dayOfMonth, holidays, holiday)) {

                                                                 PersianCalendar res = new PersianCalendar();
//                                                                 Log.e("nobatset", dayOfMonth + "/" + monthOfYear + "/" + year);

                                                                 res.setPersianDate(year, monthOfYear, dayOfMonth);
//                                                                 Log.e("nobatweek", res.getPersianDay() + "/" + res.getPersianMonth() + "/" + res.getPersianYear() + "/" + res.getPersianWeekDayName());

                                                                 if (!checkweek(visitdate, arrweek)) {

//                                                                 } else {

                                                                     int mont = monthOfYear + 1;
                                                                     edtday.setText(year + "/" +
                                                                             mont + "/" +
                                                                             dayOfMonth);
                                                                     thread = new Thread(new Runnable() {
                                                                         @Override
                                                                         public void run() {


                                                                             Call<ArrayList<Nobat>> callreserve = RI.getreserve(content, year, mont, dayOfMonth, toid, BasicActivity.number);
                                                                             callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
                                                                                 @Override
                                                                                 public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {
                                                                                     ArrayList<Repitem> arrrep = new ArrayList<>();

                                                                                     arrloop = response.body();
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
                                                                                                     Log.e("tedad", k.getTedad() + "");
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
//                                                                             for (String j : arrtime) {
//                                                                                 for (Repitem k : arrrep) {
//                                                                                     if (k.getTime().equals(j)) {
//                                                                                         Log.e("tedad", k.getTedad() + "");
//                                                                                         if (k.getTedad() >= barbers) {
//                                                                                             arrtime.remove(j);
////                                                                                            break;
//                                                                                         }else {
//
//                                                                                         }
//                                                                                     }else {
//
//                                                                                     }
//                                                                                 }
//
//                                                                             }

                                                                                     }
                                                                                     spinnerAdapter Adapterbarbers = new spinnerAdapter(ActivityProfileBr.this, android.R.layout.simple_list_item_1);
                                                                                     Adapterbarbers.addAll(newtime);
                                                                                     Adapterbarbers.add("");
                                                                                     spintime.setAdapter(Adapterbarbers);
                                                                                     spintime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                         @Override
                                                                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                             String timeselect = newtime.get(position);

                                                                                             String[] parttime = timeselect.split(":");
                                                                                             int h = Integer.parseInt(parttime[0]);
                                                                                             int m = Integer.parseInt(parttime[1]);
                                                                                             if (!checkvip(arrloop, h, m, timework, vip)) {
                                                                                                 spintime.setText("");
                                                                                                 Toast.makeText(ActivityProfileBr.this, "زمان برای نوبت ویژه کافی نمی باشد.", Toast.LENGTH_LONG).show();

                                                                                             } else {
                                                                                                if (!checkposition(newtime,timeselect,vip)){
                                                                                                    spintime.setText("");
                                                                                                    Toast.makeText(ActivityProfileBr.this, "زمان برای نوبت ویژه کافی نمی باشد.", Toast.LENGTH_LONG).show();
                                                                                                }else {

                                                                                                }
                                                                                             }
                                                                                         }

                                                                                         @Override
                                                                                         public void onNothingSelected(AdapterView<?> parent) {

                                                                                         }
                                                                                     });
                                                                                 }

                                                                                 @Override
                                                                                 public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

                                                                                 }
                                                                             });


                                                                         }

                                                                     });

                                                                     thread.start();

                                                                 } else {
                                                                     Toast.makeText(ActivityProfileBr.this, "آرایشگر مورد نظر در این روز فعالیت ندارد.", Toast.LENGTH_LONG).show();
                                                                 }
                                                             } else {
                                                                 Toast.makeText(ActivityProfileBr.this, "آرایشگر مورد نظر در روز تعطیل فعالیت ندارد.", Toast.LENGTH_SHORT).show();

                                                             }

                                                         } else {
                                                             Toast.makeText(ActivityProfileBr.this, "آرایشگر مورد نظر در این روز فعالیت ندارد.", Toast.LENGTH_SHORT).show();

                                                         }
                                                     } else {
                                                         Toast.makeText(ActivityProfileBr.this, "تاریخ انتخابی گذشته است.", Toast.LENGTH_SHORT).show();
                                                     }

                                                 }


                                             }, now.getPersianYear(),
                                        now.getPersianMonth(),
                                        now.getPersianDay());
                        datePickerDialog.setThemeDark(false);
                        datePickerDialog.setSelectableDays(setselecteddays(visitdate, arrweek, holidays, vocation, holiday));
                        datePickerDialog.show(getFragmentManager(), "tpd");


                    }
                });


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        if (edtday.getText().toString().isEmpty() || spintime.getText().toString().isEmpty()) {
                            Toast.makeText(ActivityProfileBr.this, "لطفا زمان مورد نظر را انتخاب کنید", Toast.LENGTH_SHORT).show();
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
                            Log.e("edtday", "y=" + y + "/" + "m=" + m + "/" + "d=" + d);
                            String[] parttime2 = spintime.getText().toString().split(":");
                            int h1 = Integer.parseInt(parttime2[0]);
                            int m1 = Integer.parseInt(parttime2[1]);
                            ArrayList<String> arrvip=new ArrayList<>();
                            arrvip=settime(h1,m1,timework,vip);
                            for (String vi:arrvip) {

                            Call<MessageSignup> callreserve = RI.insertreserve(content, BasicActivity.userid, nameuser, BasicActivity.number, toid, y, m, d, vi,1, BasicActivity.number);
                            callreserve.enqueue(new Callback<MessageSignup>() {
                                @Override
                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                    Log.e("messagereserve", response.body().getMessage() + "");

//                                    dialog.dismiss();
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
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
        btnreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder mBuild = new android.app.AlertDialog.Builder(ActivityProfileBr.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_reserve, null);


                TextInputEditText edtday = (TextInputEditText) mView.findViewById(R.id.edt_fr_reserve_day);
                AutoCompleteTextView spintime = (AutoCompleteTextView) mView.findViewById(R.id.spn_fr_reserve_time);
                Button btnSubmit = (Button) mView.findViewById(R.id.btn_fr_reserve);


                mBuild.setView(mView);
                android.app.AlertDialog dialog = mBuild.create();
                dialog.show();
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
                                                             Log.e("day", holidays.size() + "");
                                                             if (checkholiday(year, monthOfYear + 1, dayOfMonth, holidays, holiday)) {

                                                                 PersianCalendar res = new PersianCalendar();
//                                                                 Log.e("nobatset", dayOfMonth + "/" + monthOfYear + "/" + year);

                                                                 res.setPersianDate(year, monthOfYear, dayOfMonth);
//                                                                 Log.e("nobatweek", res.getPersianDay() + "/" + res.getPersianMonth() + "/" + res.getPersianYear() + "/" + res.getPersianWeekDayName());

                                                                 if (!checkweek(visitdate, arrweek)) {

//                                                                 } else {

                                                                     int mont = monthOfYear + 1;
                                                                     edtday.setText(year + "/" +
                                                                             mont + "/" +
                                                                             dayOfMonth);
                                                                     thread = new Thread(new Runnable() {
                                                                         @Override
                                                                         public void run() {


                                                                             Call<ArrayList<Nobat>> callreserve = RI.getreserve(content, year, mont, dayOfMonth, toid, BasicActivity.number);
                                                                             callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
                                                                                 @Override
                                                                                 public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {
                                                                                     ArrayList<Repitem> arrrep = new ArrayList<>();
                                                                                     ArrayList<Nobat> arrloop = new ArrayList<>();
                                                                                     arrloop = response.body();
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
                                                                                                     Log.e("tedad", k.getTedad() + "");
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
//                                                                             for (String j : arrtime) {
//                                                                                 for (Repitem k : arrrep) {
//                                                                                     if (k.getTime().equals(j)) {
//                                                                                         Log.e("tedad", k.getTedad() + "");
//                                                                                         if (k.getTedad() >= barbers) {
//                                                                                             arrtime.remove(j);
////                                                                                            break;
//                                                                                         }else {
//
//                                                                                         }
//                                                                                     }else {
//
//                                                                                     }
//                                                                                 }
//
//                                                                             }

                                                                                     }
                                                                                     spinnerAdapter Adapterbarbers = new spinnerAdapter(ActivityProfileBr.this, android.R.layout.simple_list_item_1);
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

                                                                 } else {
                                                                     Toast.makeText(ActivityProfileBr.this, "آرایشگر مورد نظر در این روز فعالیت ندارد.", Toast.LENGTH_LONG).show();
                                                                 }
                                                             } else {
                                                                 Toast.makeText(ActivityProfileBr.this, "آرایشگر مورد نظر در روز تعطیل فعالیت ندارد.", Toast.LENGTH_SHORT).show();

                                                             }

                                                         } else {
                                                             Toast.makeText(ActivityProfileBr.this, "آرایشگر مورد نظر در این روز فعالیت ندارد.", Toast.LENGTH_SHORT).show();

                                                         }
                                                     } else {
                                                         Toast.makeText(ActivityProfileBr.this, "تاریخ انتخابی گذشته است.", Toast.LENGTH_SHORT).show();
                                                     }

                                                 }


                                             }, now.getPersianYear(),
                                        now.getPersianMonth(),
                                        now.getPersianDay());
                        datePickerDialog.setThemeDark(false);
                        datePickerDialog.setSelectableDays(setselecteddays(visitdate, arrweek, holidays, vocation, holiday));
                        datePickerDialog.show(getFragmentManager(), "tpd");


                    }
                });


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//
                        if (edtday.getText().toString().isEmpty() || spintime.getText().toString().isEmpty()) {
                            Toast.makeText(ActivityProfileBr.this, "لطفا زمان مورد نظر را انتخاب کنید", Toast.LENGTH_SHORT).show();
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
                            Log.e("edtday", "y=" + y + "/" + "m=" + m + "/" + "d=" + d);
                            Call<MessageSignup> callreserve = RI.insertreserve(content, BasicActivity.userid, nameuser, BasicActivity.number, toid, y, m, d, spintime.getText().toString(),0, BasicActivity.number);
                            callreserve.enqueue(new Callback<MessageSignup>() {
                                @Override
                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                    Log.e("messagereserve", response.body().getMessage() + "");

                                    dialog.dismiss();
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
        });

//        btnactivedate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ActivityProfileBr.this, ActivityVisit.class);
//                intent.putExtra("username", toid);
//                ActivityProfileBr.this.startActivity(intent);
//                ActivityProfileBr.this.finish();
//            }
//        });


    }


    private String gettime(long ts) {
        PersianDate cal = new PersianDate(ts);
        cal.getHour();

        return cal.getHour() + ":" + cal.getMinute();
    }

    private String getdayofweek(long ts1) {
        PersianDate cal1 = new PersianDate(ts1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int day = cal1.dayOfWeek();
        switch (day) {
            case 0:
                return "شنبه";
            case 1:
                return "یکشنبه";

            case 2:
                return "دوشنبه";

            case 3:
                return "سه شنبه";

            case 4:
                return "چهارشنبه";

            case 5:
                return "پنجشنبه";

            case 6:
                return "جمعه";


        }

        return "";
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


        return dayoff;
    }

    public boolean checkholiday(int y, int m, int d, ArrayList<Holiday> arrholiday, int holiday) {
        boolean holi = true;
        if (holiday == 0) {
            for (Holiday h : arrholiday) {
                Log.e("holliday", h.getDay() + "/" + h.getMonth() + "/" + h.getYear());
//                Log.e("day", d + "/" + m + "/" + y);
                if (y == h.getYear() && m == h.getMonth() && d == h.getDay()) {
                    holi = false;
                    break;
                }
            }
        } else {
            holi = true;
        }


        return holi;
    }

    public boolean checkweek(ArrayList<String> arractivdate, ArrayList<String> arrweek) {
        boolean weeki = true;

        for (String w : arrweek) {
            for (String a : arractivdate) {
                if (w.equals(a)) {
                    weeki = false;
                    break;
                }
            }
        }
        return weeki;
    }

    public PersianCalendar[] setselecteddays(ArrayList<String> arractivdate,
                                             ArrayList<String> arrweek,
                                             ArrayList<Holiday> arrholiday,
                                             ArrayList<String> arrdayoff,
                                             int holiday) {
        ArrayList<PersianCalendar> arrcal = new ArrayList<>();
        ArrayList<PersianCalendar> arrcal2 = new ArrayList<>();
        ArrayList<PersianCalendar> arrcal3 = new ArrayList<>();
        for (int i = 0; i <= 380; i++) {
            PersianCalendar n = new PersianCalendar();
            n.add(PersianCalendar.DATE, i);
            for (String w : arrweek) {
                for (String v : arractivdate) {

                    if (w.equals(v)) {
                        if (v.equals(n.getPersianWeekDayName())) {
                            arrcal.add(n);
                        } else if (v.equals("یکشنبه") && n.getPersianWeekDayName().equals("یک\u200Cشنبه")) {
                            arrcal.add(n);
                        } else if (v.equals("سه شنبه") && n.getPersianWeekDayName().equals("سه\u200Cشنبه")) {
                            arrcal.add(n);
                        } else if (v.equals("پنجشنبه") && n.getPersianWeekDayName().equals("پنج\u200Cشنبه")) {
                            arrcal.add(n);
                        }

                    }
                }
            }
//                                Log.e("ssd","arrcal"+n.getPersianWeekDayName());
        }

//        for (Iterator<PersianCalendar> it1 = arrcal.iterator(); it1.hasNext(); ) {
////            if(!checkdayvacation(it1.next().getPersianYear(),it1.next().getPersianMonth()+1,it1.next().getPersianDay(),arrdayoff)){
////                it1.remove();
////            }
////            Log.e("ssd","arrcal"+checkholiday(it1.next().getPersianYear(),it1.next().getPersianMonth()+1,it1.next().getPersianDay(),arrholiday,holiday));
//           if(!checkholiday(it1.next().getPersianYear(),it1.next().getPersianMonth(),it1.next().getPersianDay(),arrholiday,holiday)){
//                it1.remove();
//            }
//
//        }
        for (PersianCalendar pc : arrcal) {
            if (checkdayvacation(pc.getPersianYear(), pc.getPersianMonth() + 1, pc.getPersianDay(), arrdayoff)) {
                arrcal2.add(pc);
            }
//           else if(checkholiday(pc.getPersianYear(),pc.getPersianMonth()+1,pc.getPersianDay(),arrholiday,holiday)){
//                arrcal2.add(pc);
//            }
//            int m=pc.getPersianMonth()+1;
//            Log.e("ssd","arrcal"+m);

        }
        for (PersianCalendar pc : arrcal2) {
            if (checkholiday(pc.getPersianYear(), pc.getPersianMonth() + 1, pc.getPersianDay(), arrholiday, holiday)) {
                arrcal3.add(pc);
            }
        }
//        for (Iterator<PersianCalendar> it1 = arrcal.iterator(); it1.hasNext(); ) {
//
//             if(!checkholiday(it1.next().getPersianYear(),it1.next().getPersianMonth()+1,it1.next().getPersianDay(),arrholiday,holiday)){
//                it1.remove();
//            }
//
//        }
        int j = 0;
        PersianCalendar[] arrday = new PersianCalendar[arrcal3.size()];

        for (PersianCalendar pc : arrcal3) {
//                            Log.e("ssd","arrday"+pc.getPersianWeekDayName());
            arrday[j] = pc;
            j++;
        }
        return arrday;
    }

    private int getdayint(String day) {

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

    @Override
    protected void onResume() {
        super.onResume();

    }

    public Boolean checkvip(ArrayList<Nobat> arrnobat, int h1, int m1, int d, int vip) {
        Boolean check = true;
        ArrayList<Nobat> arrn = new ArrayList<>();
        int h, m, t, hm, hm1, hm2, hmax;
        //==========for rond============
        if (m1 == 0) {
            m1 = 0;
        } else if (m1 <= 15 && m1 > 0 && d == 45) {
            m1 = 15;

        } else if (m1 >= 30 && m1 > 0 && d == 45) {
            m1 = 45;

        } else if (m1 <= 30 && m1 > 0) {
            m1 = 30;

        } else if (m1 <= 45 && m1 > 0 && d == 45) {
            m1 = 45;

        } else if (m1 >= 45 && m1 < 59) {
            m1 = 0;
            h1++;
        } else if (m1 >= 30 && m1 > 0) {
            m1 = 0;
            h1++;
        }


        t = (h1 * 60) + m1;
        String time = "";
        for (int i = 1; i <= vip; i++) {
            // t = t) + d);
            h = t / 60;
            m = t % 60;
            t = t + d;

            time = h + ":" + m;
            for (Nobat n : arrnobat) {
                if (time.equals(n.getTime())) {
                    arrn.add(n);
                }
            }
            if (arrn.size() <= barbers) {

            } else {
                check = false;
                break;
            }
        }
//        hmax = 0;


        return check;

    }
    public Boolean checkposition(ArrayList<String> arrtime,String time,int vip){
        Boolean ch=true;
        int i=0,j=0;
        for (String t:arrtime){
            if(t.equals(time)){
                j=i;
            }
            i++;
        }
        if (arrtime.size()-j<vip){
            ch=false;
        }


        return ch;
    }
    public ArrayList<String> settime(int h1, int m1, int d, int vip){
        ArrayList<String>arrtime=new ArrayList<>();

        int h, m, t, hm, hm1, hm2, hmax;
        //==========for rond============
        if (m1 == 0) {
            m1 = 0;
        } else if (m1 <= 15 && m1 > 0 && d == 45) {
            m1 = 15;

        } else if (m1 >= 30 && m1 > 0 && d == 45) {
            m1 = 45;

        } else if (m1 <= 30 && m1 > 0) {
            m1 = 30;

        } else if (m1 <= 45 && m1 > 0 && d == 45) {
            m1 = 45;

        } else if (m1 >= 45 && m1 < 59) {
            m1 = 0;
            h1++;
        } else if (m1 >= 30 && m1 > 0) {
            m1 = 0;
            h1++;
        }


        t = (h1 * 60) + m1;
        String time = "";
        for (int i = 1; i <= vip; i++) {
            // t = t) + d);
            h = t / 60;
            m = t % 60;
            t = t + d;

            time = h + ":" + m;
          arrtime.add(time);
        }


        return arrtime;
    }
}