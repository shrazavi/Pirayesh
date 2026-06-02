package com.shrazavi.pirayesh.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.User;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityProfileUser extends AppCompatActivity {
    ImageView imgprof;
    CardView crdprof;
    String imgurl = "";
    String aname= "",anumber= "";
    String btn;
    String content;
    String enk;
    String bu;
    SecretKeySpec Key;
    int income = 0;
    int account = 0;
    int price = 0;
    TextView txtname, txtprof, txtemail, txtphone, txtswich, txtsickness;
    //    public SharedPreferences preferences;
    Retrofitinformation RI;
    Switch swtime;
    Button btnback, btnvoise, btnmore,btnfinish;
    private static final int PER_PAGE_SIZE_100 = 100;
    private static final String ORDER_RULE = "order";
    private static final String ORDER_DESC_UPDATED = "desc date updated_at";
    public static final String TOTAL_PAGES_BUNDLE_PARAM = "total_pages";
    private Boolean hasNextPage = true;
    //    private Socket socket;
    private int currentPage = 0;
    int time = 3610000;
    String timer = "3610000";
    String myid, name, room;
    int access = 0;
    private Boolean isLoading = false;
    String number="";
    public final int MY_REQUEST_CODE = 1;
//    Button btnback;
    ConstraintLayout lay;

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
        setContentView(R.layout.activity_profile_user);
//        socket.connect();
        txtname = (TextView) findViewById(R.id.txt_prof_user_name);
        txtemail = (TextView) findViewById(R.id.txt_prof_user_email);
        txtphone = (TextView) findViewById(R.id.txt_prof_user_phone);
        txtprof = (TextView) findViewById(R.id.app_bar_txt_prof_user);
        lay= findViewById(R.id.lay_prof_user);

        imgprof = (ImageView) findViewById(R.id.app_bar_image_prof_user);
        crdprof = (CardView) findViewById(R.id.app_bar_crd_prof_user);

        anumber = (String) getIntent().getExtras().get("number");
        aname = (String) getIntent().getExtras().get("name");
//        room = (String) getIntent().getExtras().get("room");
        btnmore = (Button) findViewById(R.id.btn_prof_user_more);
        btnfinish = (Button) findViewById(R.id.btn_prof_user_finish);
        btnvoise = (Button) findViewById(R.id.btn_prof_user_voise);
        btnback = (Button) findViewById(R.id.btn_prof_user_back);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + myid + "-" + getResources().getString(R.string.developer), enk);
        content = BasicActivity.content;

//        Log.e("userid", userid);
//        Log.e("my", myid);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityProfileBr.this, ActivityMain.class);
//                ActivityProfileBr.this.startActivity(intent);
                ActivityProfileUser.this.finish();
            }
        });
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {

                    if (ActivityCompat.checkSelfPermission(ActivityProfileUser.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(ActivityProfileUser.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

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
        txtphone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                ClipboardManager clipboard = (ClipboardManager) G.context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("phone", txtphone.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ActivityProfileUser.this, "کپی شد", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        txtemail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) G.context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("email", txtemail.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ActivityProfileUser.this, "کپی شد", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        imgprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgurl.isEmpty()) {
                } else {
                    Intent intent = new Intent(ActivityProfileUser.this, ActivityImageView.class);
                    intent.putExtra("imgurl", imgurl);
                    intent.putExtra("ac", "user");
                    intent.putExtra("down", false);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ActivityProfileUser.this.startActivity(intent);
                }
            }
        });
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        Call<User> calluser = RI.getusernumber(content, anumber, BasicActivity.number);
        calluser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                txtname.setText(response.body().getName());
                txtemail.setText(response.body().getEmail());
                txtphone.setText(response.body().getNumber());
                number = response.body().getNumber();
                Log.e("userprofile", G.nodeurl + response.body().getProfile());
                if (response.body().getName().isEmpty()) {
                    name=aname;
                    number=anumber;
                    char ch1 = name.toUpperCase().charAt(0);
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                  imgProf.setVisibility(View.GONE);
//                   holder.imgProf.setBackgroundResource(R.drawable.edtdetailkala);

                    txtprof.setText(ch1 + "");
                    crdprof.setCardBackgroundColor(color);
                } else {

                if (response.body().getProfile().equals("empty")) {
                    char ch1 = response.body().getName().toUpperCase().charAt(0);
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                  imgProf.setVisibility(View.GONE);
//                   holder.imgProf.setBackgroundResource(R.drawable.edtdetailkala);

                    txtprof.setText(ch1 + "");
                    crdprof.setCardBackgroundColor(color);
                } else {
                    imgurl = G.nodeurl + response.body().getProfile();
//                    Picasso.with(G.context).load(G.nodeurl + response.body().getProfile()).into(imgprof);
                    Picasso.with(G.context).load(G.nodeurl + response.body().getProfile()).transform(new ImageProfile()).into(imgprof);
                }
//                account = Integer.parseInt(response.body().getAccount());
                name = response.body().getName();
            }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("userprofile", "" + t);
            }
        });

        btnvoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                if (ContextCompat.checkSelfPermission(ActivityProfileUser.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ActivityProfileUser.this,
                            new String[]{ Manifest.permission.CALL_PHONE}, MY_REQUEST_CODE);
                }else {
                    startActivity(callIntent);
                }


//           /
            }
        });

        btnmore.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                if (BasicActivity.bu.equals("br")) {
                    Call<Barber> callblock = RI.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
                    callblock.enqueue(new Callback<Barber>() {

                        @Override
                        public void onResponse(Call<Barber> call, Response<Barber> response) {
                            String blockusers = response.body().getBlockusers();
                            String[] arrblock = blockusers.split(",");
//                            Log.e("","");
                            if (Arrays.asList(arrblock).contains(anumber)) {
                                PopupMenu popup = new PopupMenu(ActivityProfileUser.this, view);
                                popup.getMenuInflater().inflate(R.menu.pop_up_unblock, popup.getMenu());
                                try {
                                    Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
                                    mFieldPopup.setAccessible(true);
                                    MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
                                    mPopup.setForceShowIcon(true);
                                } catch (Exception e) {
                                }
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.unblock:
                                                //handle menu1 click
                                                Call<Barber> callbarber = RI.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
                                                callbarber.enqueue(new Callback<Barber>() {
                                                    @Override
                                                    public void onResponse(Call<Barber> call, Response<Barber> response) {
                                                        String blockuser = response.body().getBlockusers();
                                                        StringBuilder result = new StringBuilder();

                                                        String[] arrblock = blockuser.split(",");
                                                        ArrayList<String> blackList = new ArrayList<String>(Arrays.asList(arrblock));
                                                        blackList.remove(anumber);
                                                        for (int i = 0; i < blackList.size(); i++) {
                                                            result.append(blackList.get(i));
                                                            result.append(",");
                                                        }


                                                        Call<MessageSignup> callbarber = RI.upgradeblack(BasicActivity.content,BasicActivity.userid,
                                                                result.toString(),
                                                                BasicActivity.number);
                                                        callbarber.enqueue(new Callback<MessageSignup>() {
                                                            @Override
                                                            public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                                                Boolean status = response.body().getStatus();
                                                                if (status) {
                                                                    Toast.makeText(G.context, "مسدودی کاربر رفع شد", Toast.LENGTH_SHORT).show();

                                                                } else {

                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<MessageSignup> call, Throwable t) {
                                                                Log.e("error???", t + "");
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Barber> call, Throwable t) {
                                                        Log.e("error???", t + "");
                                                    }
                                                });

                                                break;
//
                                        }
                                        return true;
                                    }
                                });
                                popup.show();
                            } else {
                                PopupMenu popup = new PopupMenu(ActivityProfileUser.this, view);
                                popup.getMenuInflater().inflate(R.menu.pop_up_block, popup.getMenu());
                                try {
                                    Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
                                    mFieldPopup.setAccessible(true);
                                    MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
                                    mPopup.setForceShowIcon(true);
                                } catch (Exception e) {
                                }
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.block:

                                                Call<Barber> callblock = RI.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
                                                callblock.enqueue(new Callback<Barber>() {
                                                    @Override
                                                    public void onResponse(Call<Barber> call, Response<Barber> response) {
                                                        String blockuser = response.body().getBlockusers();
                                                        StringBuilder result = new StringBuilder();
                                                        if (blockuser.isEmpty()) {
                                                            result.append(anumber);
                                                            result.append(",");
                                                        } else {
                                                            String[] arrblock = blockuser.split(",");
                                                            ArrayList<String> blackList = new ArrayList<String>(Arrays.asList(arrblock));
                                                            blackList.add(anumber);
                                                            for (int i = 0; i < blackList.size(); i++) {
                                                                result.append(blackList.get(i));
                                                                result.append(",");
                                                            }
                                                        }

                                                        Call<MessageSignup> callbarber = RI.upgradeblack(BasicActivity.content,BasicActivity.userid,
                                                                result.toString(),
                                                                BasicActivity.number);
                                                        callbarber.enqueue(new Callback<MessageSignup>() {
                                                            @Override
                                                            public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                                                Boolean status = response.body().getStatus();
                                                                if (status) {
                                                                    Toast.makeText(G.context, "کاربر مسدود شد", Toast.LENGTH_SHORT).show();
                                                                } else {

                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<MessageSignup> call, Throwable t) {
                                                                Log.e("error???", t + "");
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Barber> call, Throwable t) {
                                                        Log.e("error???", t + "");
                                                    }
                                                });

                                                break;
//                            case R.id.copy:
//                                //handle menu2 click
//                                ClipboardManager clipboard = (ClipboardManager) G.context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                ClipData clip = ClipData.newPlainText("chat", chatsText.getText());
//                                clipboard.setPrimaryClip(clip);
//                                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
//                                break;
//                            case R.id.delete:
//                                //handle menu3 click
//
//                                ActivityChat.deleteitem(chatsText.getId(), context);
//
//                                break;
                                        }
                                        return true;
                                    }
                                });
                                popup.show();

                            }


                        }

                        @Override
                        public void onFailure(Call<Barber> call, Throwable t) {
                            Log.e("error???", t + "");
                        }
                    });


                } else {


                }
            }
        });
    }
    private void checkMyPermissions() {
        if (ContextCompat.checkSelfPermission(ActivityProfileUser.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ActivityProfileUser.this,
                    new String[]{ Manifest.permission.CALL_PHONE}, MY_REQUEST_CODE);


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ActivityProfileUser.this, "Thanks for your permission", Toast.LENGTH_SHORT).show();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + number));
                    startActivity(callIntent);
                } else {


                    Toast.makeText(ActivityProfileUser.this, "access dinied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }


}
