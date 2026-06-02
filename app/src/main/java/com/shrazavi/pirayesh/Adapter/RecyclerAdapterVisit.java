package com.shrazavi.pirayesh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Activity.ActivityNobat;
import com.shrazavi.pirayesh.Activity.ActivityProfileBr;
import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.NameNum;
import com.shrazavi.pirayesh.DataClass.User;
import com.shrazavi.pirayesh.DataClass.Visitday;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterVisit extends RecyclerView.Adapter<RecyclerAdapterVisit.ChatViewHolder> {
    String username = "";
    //    public SharedPreference preferences;
    Retrofitinformation RI;
    String date = "";
    public ArrayList<DayReserve> dayInfos = new ArrayList<>();
    Context context;
    ArrayList<NameNum> arrusers;
    String[] arrname, arrnumber, arrvip;
    Handler threadHandler;
    Thread thread, thread2;

    public RecyclerAdapterVisit(ArrayList<DayReserve> dayInfos, Context context, String date) {
        this.context = context;
        this.date = date;
        this.dayInfos = dayInfos;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visit, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {


        DayReserve dayInfo = dayInfos.get(position);

//        Log.i("LOG","recycler done");
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        arrusers = new ArrayList<>();
        holder.txttime.setText(dayInfo.getTime());
        holder.txtuser.setText("");
//        holder.txtday.setText(dayInfo.getVisitday());
//
//        holder.txtuser.setText( "");
//        Log.e("usernobat =", dayInfo.getUsers()+ "");

        if (!dayInfo.getNumbers().isEmpty()) {

//            holder.txtuser.setText("");
            arrnumber = null;
            arrname = null;
            arrnumber = dayInfo.getNumbers().split("/");
            arrname = dayInfo.getNames().split("/");
            arrvip = dayInfo.getVip().split("/");

            int i = 0;
            for (String s : arrnumber) {
                NameNum nn = new NameNum();
                nn.setNumber(s);
                nn.setId(i);
                i++;
                arrusers.add(nn);
            }
            for (NameNum n : arrusers) {
                arrusers.get(n.getId()).setName(arrname[n.getId()]);
            }
            for (NameNum n : arrusers) {
                arrusers.get(n.getId()).setVip(arrvip[n.getId()]);
            }
//            for (int j = 0; j <= arrusers.size(); j++) {
//
//            }
//            for (int i = 0; i <= arruesr.length; i++) {
//                Call<User> calluser = RI.getuser(BasicActivity.content, arruesr[i], BasicActivity.number);
//                calluser.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//
//                        arrusers.add(response.body().getName());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        Log.e("userprofile", "" + t);
//                    }
//                });
//            }

            for (NameNum s : arrusers) {
//                if (!s.isEmpty()) {
                if (s.getNumber().equals(BasicActivity.number)) {
                    holder.txtuser.setText("مرخصی");
                } else {
                    Call<User> calluser = RI.getusernumber(BasicActivity.content, s.getNumber(), BasicActivity.number);
                    calluser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
//                                            Log.e("userid", "/" + response.body().getName() + "/");
//                                            arrusers.add(response.body().getName());
                            if (s.getVip().equals("1")) {

                                if (response.body().getName().isEmpty()) {
                                    holder.txtuser.setText(holder.txtuser.getText() + "  " + s.getName()+" (نوبت ویژه (داماد))");
                                } else {
                                    holder.txtuser.setText(holder.txtuser.getText() + "  " + response.body().getName()+" (نوبت ویژه (داماد))");
                                }
                            }else {
                                if (response.body().getName().isEmpty()) {
                                    holder.txtuser.setText(holder.txtuser.getText() + "  " + s.getName());
                                } else {
                                    holder.txtuser.setText(holder.txtuser.getText() + "  " + response.body().getName());
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("userprofile", "" + t);
                        }
                    });


                }
            }


//            Log.e("usersize", "/" + arrusers.size() + "/");


        } else {
//            holder.txtuser.setText( "");

        }

        holder.layvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActivityNobat.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("names", dayInfo.getNames());
                intent.putExtra("numbers", dayInfo.getNumbers());
                intent.putExtra("vip", dayInfo.getVip());
                intent.putExtra("date", date);
                intent.putExtra("time", dayInfo.getTime());
//                    intent.putExtra("imgurl", G.nodeurl + "/" + lawyerInfo.getProfile());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dayInfos.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {


        ExpandableTextView txtuser;
        TextView txttime;
        LinearLayout layvisit;

        public ChatViewHolder(View itemView) {
            super(itemView);
            txtuser = (ExpandableTextView) itemView.findViewById(R.id.txt_visit_item_user);
            txttime = (TextView) itemView.findViewById(R.id.txt_visit_item_time);
            layvisit = (LinearLayout) itemView.findViewById(R.id.lay_visit_item);
        }


    }
}
