
package com.shrazavi.pirayesh.Activity;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.nkzawa.emitter.Emitter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.ClosingService;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.User;

import com.shrazavi.pirayesh.Fragment.BarberListFragment;
import com.shrazavi.pirayesh.Fragment.ProfileFragment;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import javax.crypto.spec.SecretKeySpec;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMain extends BasicActivity {
    AppBarLayout appbar;
    //    String content;
    String usernumber;
    public static MaterialProgressBar prload;
    TextView txtnumber, txtuser, txtprof, txtreserve;
    public static TextView txtconnection;
    //    BottomBar bottombar;
    public static BadgeDrawable badgechat;
    public static BadgeDrawable badgecall;
    ClosingService service;
    //    public static Socket socket;
//    public static SharedPreferences preferences;
    EditText edtsend;
    LinearLayout lay_nav, lay_reserve, lay_peyment, lay_setting, lay_logout, lay_support, lay_rules, lay_help;
    LinearLayout.LayoutParams layoutParams;
    public Handler handler;
    Thread datathread;
    ImageView writeNewMessage;
    ImageView imguserprofile;
    CardView crdprofile;
    public static Handler handlerchat;
    public Handler handlercash;
    Thread thread;
    public static int coanterfrag;
    FragmentManager fragmentManager = getSupportFragmentManager();
    public static Button btnmenu, btnnotify, btnnotifycount, btnsearch, btnfilter;
    String enk;
    String rating;
    int rate;
    public static Context dialogContext;
    SharedPreferences.Editor editor;
    NavigationView navigationView2;
    Retrofitinformation RI;
    SecretKeySpec secretKey;
    boolean isFromCall;
    int search = 0, filter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        if (sx.equals("male")) {
            super.setTheme(R.style.Theme_VakilSocialmain);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_home);
        handler = new Handler();
        Log.e("loginam", preferences.getString("login", "not") + "");
        if (preferences.getString("login", "not").equals("not")) {
            finish();

        }

        coanterfrag = 0;
        writeNewMessage = (ImageView) findViewById(R.id.img_profil);
//        btn_search = (Button) findViewById(R.id.btn_search);
//        bottombar = (BottomBar) findViewById(R.id.bottom_bar);
        btnnotify = (Button) findViewById(R.id.btn_notify);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        btnnotifycount = (Button) findViewById(R.id.btn_notify_count);
        btnmenu = (Button) findViewById(R.id.menu);
        btnsearch = (Button) findViewById(R.id.btn_main_search);
        btnfilter = (Button) findViewById(R.id.btn_main_filter);
        btnnotifycount.setTypeface(G.face);
        prload = findViewById(R.id.pr_main_load);
        lay_nav = (LinearLayout) findViewById(R.id.lay_nav_main);
        lay_peyment = (LinearLayout) findViewById(R.id.lay_nav_peyment);
        lay_reserve = (LinearLayout) findViewById(R.id.lay_nav_reserve);
        lay_setting = (LinearLayout) findViewById(R.id.lay_nav_setting);
        lay_logout = (LinearLayout) findViewById(R.id.lay_nav_logout);
        lay_support = (LinearLayout) findViewById(R.id.lay_nav_backup);
        lay_rules = (LinearLayout) findViewById(R.id.lay_nav_ruls);
        lay_help = (LinearLayout) findViewById(R.id.lay_nav_help);

        navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        View headerView = navigationView2.getHeaderView(0);
        txtconnection = (TextView) findViewById(R.id.txt_connection);
        txtreserve = (TextView) findViewById(R.id.txt_nav_reserve);
        imguserprofile = (ImageView) findViewById(R.id.img_prof);
        crdprofile = (CardView) findViewById(R.id.crd_prof);
        txtnumber = (TextView) findViewById(R.id.txt_num_head);
        txtuser = (TextView) findViewById(R.id.txt_user_head);
        txtprof = (TextView) findViewById(R.id.txt_prof);
        edtsend = (EditText) findViewById(R.id.edtTextMessage);

        if (sx.equals("male")) {
            appbar.setBackgroundResource(R.drawable.gradienttool);
            lay_nav.setBackgroundResource(R.drawable.gradientnav);
            super.setTheme(R.style.Theme_VakilSocialmain);
        } else {
            appbar.setBackgroundResource(R.drawable.gradienttool2);
            lay_nav.setBackgroundResource(R.drawable.gradientnav2);
            super.setTheme(R.style.Theme_mainwomen);
        }

        btnsearch.setVisibility(View.GONE);
        btnfilter.setVisibility(View.GONE);
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Log.e("img",imgprofile);
                if (imgprofile.equals(G.nodeurl + "empty")) {

                    char ch1 = name.toUpperCase().charAt(0);
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                            holder.imgProf.setVisibility(View.GONE);
                    txtprof.setText(ch1 + "");
                    crdprofile.setCardBackgroundColor(color);
                } else {
//            Picasso.with(G.context).load(imgurl).into(imguserprofile);
                    Picasso.with(G.context).load(imgprofile).transform(new ImageProfile()).into(imguserprofile);
                }
                JSONObject notify = new JSONObject();
                try {
                    notify.put("from", number);
                    notify.put("content", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("notify", notify);

            }
        }.start();
        FragmentTransaction frm = fragmentManager.beginTransaction().replace(R.id.frmlay, new ProfileFragment());
        frm.commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_bar);
//        bottomNavigationView.sty
        badgechat = bottomNavigationView.getOrCreateBadge(R.id.chat);
        badgecall = bottomNavigationView.getOrCreateBadge(R.id.call);


        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {


                case R.id.barber:
                    FragmentTransaction frm3 = fragmentManager.beginTransaction().replace(R.id.frmlay, new BarberListFragment());
                    frm3.commit();
                    item.setChecked(true);
                    btnsearch.setVisibility(View.VISIBLE);
                    btnfilter.setVisibility(View.VISIBLE);
                    btnsearch.setBackgroundResource(R.drawable.ic_search);
                    btnfilter.setBackgroundResource(R.drawable.ic_filter);
                    search = 0;
                    filter = 0;
                    break;

                case R.id.profile:
                    FragmentTransaction frm5 = fragmentManager.beginTransaction().replace(R.id.frmlay, new ProfileFragment());
                    frm5.commit();
                    item.setChecked(true);
                    btnsearch.setBackgroundResource(R.drawable.ic_search);
                    btnfilter.setBackgroundResource(R.drawable.ic_filter);
                    btnsearch.setVisibility(View.GONE);
                    btnfilter.setVisibility(View.GONE);
                    search = 0;
                    filter = 0;

                    break;
            }
            return false;
        });

//        progressDialog = findViewById(R.id.progressBarmain);
        dialogContext = ActivityMain.this;

        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        G.getInstance().setUsername(userid);

        JSONObject connected = new JSONObject();
        try {
            connected.put("from", userid);
            connected.put("message", "Connected");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.e("bu ", bu + "");
        if (bu.equals("br")) {
            lay_peyment.setVisibility(View.VISIBLE);
            txtreserve.setText("لیست نوبت");
            Call<Barber> callbarber = RI.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
            callbarber.enqueue(new Callback<Barber>() {
                @Override
                public void onResponse(Call<Barber> call, Response<Barber> response) {
//                    G.income = Integer.parseInt(response.body().getIncome());
                    Log.e("imgurl", G.nodeurl + response.body().getProfile());
//                    preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("imgprofile", G.nodeurl + response.body().getProfile());
                    editor.putString("number", response.body().getPhone());
                    editor.commit();
                    txtuser.setText(response.body().getName());

                }

                @Override
                public void onFailure(Call<Barber> call, Throwable t) {
                    Log.e("error vl", t + "");
                }
            });


        } else {
            lay_peyment.setVisibility(View.GONE);
            txtreserve.setText("دریافت نوبت");
            Call<User> calluser = RI.getuser(content, userid, BasicActivity.number);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
//                    G.account = Integer.parseInt(response.body().getAccount());
//                    preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("number", response.body().getNumber());
                    editor.putString("name", response.body().getName());
                    editor.putString("imgprofile", G.nodeurl + response.body().getProfile());
                    txtuser.setText(response.body().getName());
                    editor.commit();
//                    txtuser.setText(response.body().getName());

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("error user", t + "");
                }
            });
//
        }

        usernumber = preferences.getString("number", "not");

//        String imgurl = preferences.getString("imgprofile", "not");
        Log.e("name", name);
        if (imgprofile.equals(G.nodeurl + "empty")) {
            char ch1 = name.toUpperCase().charAt(0);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                            holder.imgProf.setVisibility(View.GONE);
            txtprof.setText(ch1 + "");
            crdprofile.setCardBackgroundColor(color);
        } else {
//            Picasso.with(G.context).load(imgurl).into(imguserprofile);
            Picasso.with(G.context).load(imgprofile).transform(new ImageProfile()).into(imguserprofile);
        }


        isConnected();
        if (isConnected()) {
            ActivityMain.txtconnection.setText("Connected");
            ActivityMain.prload.setVisibility(View.GONE);
        } else {
            ActivityMain.prload.setVisibility(View.VISIBLE);

            ActivityMain.txtconnection.setText("Waiting For Connection");
        }


        lay_peyment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityPayment2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bu", bu);
                startActivity(intent);
            }
        });
        lay_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (bu.equals("br")) {
//                    Intent intent = new Intent(G.context, ActivityVisit.class);
//                    startActivity(intent);
//
//                } else {
//                    Intent intent = new Intent(G.context, ActivityReserve.class);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(ActivityMain.this, ActivityVisit2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }

        });
        lay_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityLaw.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sx", sx);
                intent.putExtra("status", "option");
                startActivity(intent);
            }
        });
        lay_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityHelp.class);
                intent.putExtra("sx", sx);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        lay_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(ActivityMain.this, ActivityOption.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityMain.this.finish();
                startActivity(intent4);
            }
        });
        lay_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(ActivityMain.this, ActivitySupport.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
            }
        });
        lay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });
        txtnumber.setText("" + usernumber);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search == 0) {
                    BarberListFragment.edtsearch.setVisibility(View.VISIBLE);
                    BarberListFragment.laysearch.setVisibility(View.VISIBLE);
                    BarberListFragment.edtsearch.setFocusableInTouchMode(true);
                    BarberListFragment.edtsearch.requestFocus();
                    btnsearch.setBackgroundResource(R.drawable.ic_search_off);
                    search = 1;
                } else {
                    BarberListFragment.edtsearch.setText("");
                    BarberListFragment.edtsearch.setVisibility(View.GONE);
                    BarberListFragment.laysearch.setVisibility(View.GONE);
                    btnsearch.setBackgroundResource(R.drawable.ic_search);
                    search = 0;
                }


//                BarberListFragment.edtsearch.showDropDown();
            }
        });
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter == 0) {
//                    BarberListFragment.edtfilterostan.setText("");
//                    BarberListFragment.edtfiltershahr.setText("");
                    BarberListFragment.edtfilterostan.setVisibility(View.VISIBLE);
                    BarberListFragment.layfilterostan.setVisibility(View.VISIBLE);
                    BarberListFragment.edtfilterostan.setFocusableInTouchMode(true);
                    BarberListFragment.edtfilterostan.requestFocus();
                    BarberListFragment.edtfiltershahr.setVisibility(View.VISIBLE);
                    BarberListFragment.layfiltershahr.setVisibility(View.VISIBLE);
//                    BarberListFragment.edtfiltershahr.setFocusableInTouchMode(true);
//                    BarberListFragment.edtfiltershahr.requestFocus();
                    btnfilter.setBackgroundResource(R.drawable.ic_filter_off);
                    filter = 1;
                } else {
                    BarberListFragment.edtfilterostan.setText("");
                    BarberListFragment.edtfilterostan.setVisibility(View.GONE);
                    BarberListFragment.layfilterostan.setVisibility(View.GONE);
                    BarberListFragment.edtfiltershahr.setText("");
                    BarberListFragment.edtfiltershahr.setVisibility(View.GONE);
                    BarberListFragment.layfiltershahr.setVisibility(View.GONE);
                    btnfilter.setBackgroundResource(R.drawable.ic_filter);
                    filter = 0;
                }


            }
        });
        crdprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivityImageView.class);
                intent.putExtra("imgurl", imgprofile);
                intent.putExtra("down", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityMain.this.startActivity(intent);
            }
        });
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConnected();
                if (isConnected()) {
                    ActivityMain.txtconnection.setText("Connected");
                    ActivityMain.prload.setVisibility(View.GONE);

                } else {
                    ActivityMain.prload.setVisibility(View.VISIBLE);

                    ActivityMain.txtconnection.setText("Waiting For Connection");
                }
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
        btnnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this, ActivitySupport.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityMain.this.startActivity(intent);
            }
        });

//        navigationView2.setNavigationItemSelectedListener(this);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (bu.equals("br")) {
            lay_peyment.setVisibility(View.VISIBLE);
            lay_reserve.setVisibility(View.VISIBLE);
            txtreserve.setText("لیست نوبت");
            Call<Barber> callbarber = RI.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
            callbarber.enqueue(new Callback<Barber>() {
                @Override
                public void onResponse(Call<Barber> call, Response<Barber> response) {
//                    G.income = Integer.parseInt(response.body().getIncome());
                    Log.e("imgurl", G.nodeurl  + response.body().getProfile());
//                    preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("imgprofile", G.nodeurl + response.body().getProfile());
                    editor.putString("number", response.body().getPhone());
                    editor.commit();
                    txtuser.setText(response.body().getName());

                }

                @Override
                public void onFailure(Call<Barber> call, Throwable t) {
                    Log.e("error vl", t + "");
                }
            });


        } else {
            lay_peyment.setVisibility(View.GONE);
//            txtreserve.setText("دریافت نوبت");
            lay_reserve.setVisibility(View.GONE);
            Call<User> calluser = RI.getuser(content, userid, BasicActivity.number);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
//                    G.account = Integer.parseInt(response.body().getAccount());
//                    preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("number", response.body().getNumber());
                    editor.putString("imgprofile", G.nodeurl + response.body().getProfile());
                    txtuser.setText(response.body().getName());
                    editor.commit();
//                    txtuser.setText(response.body().getName());

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("error user", t + "");
                }
            });
//
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    public void open() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("How much do yo like this App?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);

                try {
                    startActivity(myAppLinkToMarket);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public Emitter.Listener handlerConnected = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = (JSONObject) args[0];
                    String connect = "";

                    try {
                        connect = jsonObject.getString("message").toString();


                        txtconnection.setText(connect);

//                            txtIsTyping.setText("");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            JSONObject disconnect = new JSONObject();
            try {
                disconnect.put("from", userid);
                disconnect.put("vu", bu);
                disconnect.put("message", "Last Seen Recently");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("disconnect", disconnect);
            // Handle application closing
            socket.disconnect();
//            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        return super.onKeyDown(keycode, event);
    }

    public static void restart(Context context, int delay) {
        if (delay == 0) {
            delay = 1;
        }
        Log.e("", "restarting app");
        Intent restartIntent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        PendingIntent intent = PendingIntent.getActivity(
                context, 0,
                restartIntent, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + delay, intent);
        System.exit(2);
    }

    //
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONObject disconnect = new JSONObject();
        try {
            disconnect.put("from", userid);
            disconnect.put("vu", bu);
            disconnect.put("message", "Last Seen Recently");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("disconnec", disconnect);
        // Handle application closing
        socket.disconnect();
    }

    private void creatnotification(Context context) {
        int notifyId = 1001;
        final Intent emptyIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifyId, emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.e("ejra", "notify");

        NotificationCompat.Builder notify = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.pirayeshlogo)
                .setContentTitle("New Message")
                .setContentText("New Missed Call")
                .setContentIntent(pendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId, notify.build());

    }

}