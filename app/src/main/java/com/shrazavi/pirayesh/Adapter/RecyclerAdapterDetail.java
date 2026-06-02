package com.shrazavi.pirayesh.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Activity.ActivityNobat;
import com.shrazavi.pirayesh.Activity.ActivityNobatDetail;
import com.shrazavi.pirayesh.Activity.ActivityProfileUser;
import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.NameNum;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.User;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterDetail extends RecyclerView.Adapter<RecyclerAdapterDetail.ChatViewHolder> {
    Retrofitinformation RI;
    public ArrayList<Nobat> userInfos = new ArrayList<>();
    Context context;
    Activity activity;
    String name;
    public RecyclerAdapterDetail(ArrayList<Nobat> userInfos, Activity activity, Context context,String name) {
        this.context = context;
        this.activity = activity;
        this.userInfos = userInfos;
        this.name = name;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {
        Nobat userInfo = userInfos.get(position);

//        Log.i("LOG","recycler done");
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);


            Call<Barber> callbarber = RI.getbarber(BasicActivity.content, userInfo.getBarber(), BasicActivity.number);
            callbarber.enqueue(new Callback<Barber>() {
                @Override
                public void onResponse(Call<Barber> call, Response<Barber> response) {


//                    if (response.body().getName().isEmpty()) {
                        holder.txttitle.setText(response.body().getTitle());
                        PersianCalendar dateset = new PersianCalendar();
                        dateset.setPersianDate(userInfo.getYear(),userInfo.getMonth()-1,userInfo.getDay());
                        holder.txtdate.setText(dateset.getPersianWeekDayName()+" "+userInfo.getDay()+" "+dateset.getPersianMonthName()+" "+userInfo.getYear()+" ساعت "+userInfo.getTime());
                    if (response.body().getProfile().equals("empty")) {
                            char ch1 = response.body().getName().toUpperCase().charAt(0);
                            Random rnd = new Random();
                            int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
                            holder.txtprof.setText(ch1 + "");
                            holder.crdprof.setCardBackgroundColor(color);
                        } else {
                            Picasso.with(G.context).load(G.nodeurl + "/" + response.body().getProfile()).transform(new ImageProfile()).into(holder.imgprof);
                        }
//                    } else {
//                        holder.txtuser.setText(response.body().getName());
//                        holder.txtphone.setText(response.body().getNumber());
//                        if (response.body().getProfile().equals("empty")) {
//                            char ch1 = response.body().getName().toUpperCase().charAt(0);
//                            Random rnd = new Random();
//                            int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                            holder.txtprof.setText(ch1 + "");
//                            holder.crdprof.setCardBackgroundColor(color);
//                        } else {
//                            Picasso.with(G.context).load(G.nodeurl + "/" + response.body().getProfile()).transform(new ImageProfile()).into(holder.imgprof);
//                        }
//                    }
                }

                @Override
                public void onFailure(Call<Barber> call, Throwable t) {
                    Log.e("userprofile", "" + t);
                }
            });


        holder.linearnobat.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
//                if (userInfo.equals(BasicActivity.userid)) {
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
                                    Call<MessageSignup> deletenobat = RI.deletenobat(BasicActivity.content, userInfo.getBarber(), BasicActivity.number, userInfo.getYear(),userInfo.getMonth(), userInfo.getDay(), userInfo.getTime(), BasicActivity.number);
                                    deletenobat.enqueue(new Callback<MessageSignup>() {
                                        @Override
                                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {

                                            ActivityNobatDetail.getnobatdetail(activity, context, name);

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
//                } else {
//                    Intent intent = new Intent(context, ActivityProfileUser.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("number", userInfo.getNumber());
//                    intent.putExtra("name", userInfo.getName());
////                    intent.putExtra("imgurl", G.nodeurl + "/" + lawyerInfo.getProfile());
//                    context.startActivity(intent);
//                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearnobat;
        TextView txtdate;
        TextView txttitle;
        ImageView imgprof;
        CardView crdprof;
        TextView txtprof;

        public ChatViewHolder(View itemView) {
            super(itemView);
            txttitle = (TextView) itemView.findViewById(R.id.txt_detail_item_barber);
             txtdate = (TextView) itemView.findViewById(R.id.txt_detail_item_date);
            imgprof = (ImageView) itemView.findViewById(R.id.img_detail_item_prof);
             crdprof = (CardView) itemView.findViewById(R.id.crd_detail_item_prof);
             txtprof = (TextView) itemView.findViewById(R.id.txt_detail_item_prof);
            linearnobat = (LinearLayout) itemView.findViewById(R.id.lay_detail_item);

        }


    }
}
