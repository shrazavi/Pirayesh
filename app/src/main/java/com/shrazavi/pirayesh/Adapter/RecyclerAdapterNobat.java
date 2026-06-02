package com.shrazavi.pirayesh.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.Activity.ActivityNobat;
import com.shrazavi.pirayesh.Activity.ActivityProfileUser;
import com.shrazavi.pirayesh.Activity.ActivityShift;
import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.NameNum;
import com.shrazavi.pirayesh.DataClass.User;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterNobat extends RecyclerView.Adapter<RecyclerAdapterNobat.ChatViewHolder> {
    Retrofitinformation RI;
    public ArrayList<NameNum> userInfos = new ArrayList<>();
    Context context;
    int year, month, day;
    String time;
    Activity activity;

    public RecyclerAdapterNobat(ArrayList<NameNum> userInfos, Activity activity, Context context, int year, int month, int day, String time) {
        this.context = context;
        this.activity = activity;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
        this.userInfos = userInfos;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nobat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {
        NameNum userInfo = userInfos.get(position);

//        Log.i("LOG","recycler done");
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        if (userInfo.getNumber().equals(BasicActivity.number)) {
            holder.txtuser.setText("مرخصی");
            holder.crdprof.setVisibility(View.GONE);
        } else {
            Call<User> calluser = RI.getusernumber(BasicActivity.content, userInfo.getNumber(), BasicActivity.number);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {


                    if (response.body().getName().isEmpty()) {
                        if (userInfo.getVip().equals("1")) {
                            holder.txtuser.setText(userInfo.getName()+" (نوبت ویژه (داماد))");
                        }else {
                            holder.txtuser.setText(userInfo.getName());
                        }

                        holder.txtphone.setText(userInfo.getNumber());
                        char ch1 = userInfo.getName().toUpperCase().charAt(0);
                        Random rnd = new Random();
                        int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
                        holder.txtprof.setText(ch1 + "");
                        holder.crdprof.setCardBackgroundColor(color);
                    } else {
                        if (userInfo.getVip().equals("1")) {
                            holder.txtuser.setText(response.body().getName()+" (نوبت ویژه (داماد))");
                        }else {
                            holder.txtuser.setText(response.body().getName());
                        }

                        holder.txtphone.setText(response.body().getNumber());
                        if (response.body().getProfile().equals("empty")) {
                            char ch1 = response.body().getName().toUpperCase().charAt(0);
                            Random rnd = new Random();
                            int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
                            holder.txtprof.setText(ch1 + "");
                            holder.crdprof.setCardBackgroundColor(color);
                        } else {
                            Picasso.with(G.context).load(G.nodeurl + "/" + response.body().getProfile()).transform(new ImageProfile()).into(holder.imgprof);
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("userprofile", "" + t);
                }
            });
        }

        holder.linearnobat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (userInfo.equals(BasicActivity.userid)) {
                    PopupMenu popup = new PopupMenu(context, holder.linearnobat);
                    popup.getMenuInflater().inflate(R.menu.vacation_option, popup.getMenu());
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
                                case R.id.cancel:
                                    Call<MessageSignup> deletenobat = RI.deletenobat(BasicActivity.content, BasicActivity.userid, BasicActivity.number, year, month, day, time, BasicActivity.number);
                                    deletenobat.enqueue(new Callback<MessageSignup>() {
                                        @Override
                                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {

                                            ActivityNobat.getnobat(activity, context, year, month, day, time);

                                        }

                                        @Override
                                        public void onFailure(Call<MessageSignup> call, Throwable t) {

                                        }
                                    });
                                    break;
                            }
                            return true;
                        }
                    });
                    popup.show();
                } else {
                    Intent intent = new Intent(context, ActivityProfileUser.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("number", userInfo.getNumber());
                    intent.putExtra("name", userInfo.getName());
//                    intent.putExtra("imgurl", G.nodeurl + "/" + lawyerInfo.getProfile());
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout linearnobat;
        TextView txtuser;
        TextView txtphone;
        ImageView imgprof;
        CardView crdprof;
        TextView txtprof;

        public ChatViewHolder(View itemView) {
            super(itemView);
            txtuser = (TextView) itemView.findViewById(R.id.txt_nobat_item_name);
            txtphone = (TextView) itemView.findViewById(R.id.txt_nobat_item_phone);
            imgprof = (ImageView) itemView.findViewById(R.id.img_nobat_item_prof);
            crdprof = (CardView) itemView.findViewById(R.id.crd_nobat_item_prof);
            txtprof = (TextView) itemView.findViewById(R.id.txt_nobat_item_prof);
            linearnobat = (ConstraintLayout) itemView.findViewById(R.id.lay_nobat_item);

        }


    }
}
