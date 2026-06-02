package com.shrazavi.pirayesh.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Repitem;
import com.shrazavi.pirayesh.DataClass.Reserve;
import com.shrazavi.pirayesh.DataClass.Visitday;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterReserve extends RecyclerView.Adapter<RecyclerAdapterReserve.VisitdayViewHolder> {
    Retrofitinformation RI;
    String type;
    LinearLayoutManager linearLayoutManager;
    public ArrayList<Visitday> VisitdayInfos = new ArrayList<>();
    Context context;
    ArrayList<DayReserve> reserveItems = new ArrayList<>();
    ArrayList<String> arrtime;

    //    public SharedPreferences preferences;
    public static RecyclerAdapterVisit recyclerAdapterVisit;
    public RecyclerAdapterReserve(ArrayList<Visitday> VisitdayInfos, Context context,ArrayList<String> arrtime) {
        this.context = context;
        this.arrtime=arrtime;
        this.VisitdayInfos = VisitdayInfos;
    }

    @Override
    public VisitdayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multipager_detail_cell, parent, false);
        VisitdayViewHolder holder = new VisitdayViewHolder(view);
        holder.setIsRecyclable(false);
        return new VisitdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VisitdayViewHolder holder, final int position) {
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        final Visitday VisitdayInfo = VisitdayInfos.get(position);
        Log.i("LOG", "recycler done");
        linearLayoutManager = new LinearLayoutManager(context);
        holder.rcyclrday.setHasFixedSize(true);
        holder.rcyclrday.setLayoutManager(linearLayoutManager);

        holder.txttitle.setText((VisitdayInfo.getDay()+"/"+VisitdayInfo.getMonth()+"/"+VisitdayInfo.getYear()+" "+VisitdayInfo.getWeek()));

        Call<ArrayList<Nobat>> callreserve = RI.getreserve(BasicActivity.content,VisitdayInfo.getYear(), VisitdayInfo.getMonth(),VisitdayInfo.getDay(), BasicActivity.userid, BasicActivity.number);
        callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
            @Override
            public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {

                ArrayList<Nobat> arrnobat = new ArrayList<>();
                arrnobat.clear();

                arrnobat = response.body();
//                                Log.e("nobat =", arrnobat.size()+ "");
                if (arrnobat.size() == 0) {
                    reserveItems.clear();

//                                    ActivityVisit.btnvacation.setVisibility(View.VISIBLE);
                    for (String t : arrtime) {
                        DayReserve day=new DayReserve();
                        day.setTime(t);
//                        day.setUsers("");
                        reserveItems.add(day);

                    }
                    holder.rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,context,""));
                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
//                                    rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
                } else {
//                                    ActivityVisit.btnvacation.setVisibility(View.GONE);

                    ArrayList<Repitem> arrrep = new ArrayList<>();
                    for (Nobat i : arrnobat) {
                        arrrep.add(getrepeat(arrnobat, i.getTime()));
                    }
                    reserveItems = setnobat(arrtime, arrrep);
                    Log.e("nobat =", reserveItems.size()+ "");
                    holder.rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,context,""));
                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);


                }
            }
            @Override
            public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

            }
        });

//        holder.linearvisitday.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {


//                Intent intent = new Intent(G.context, ActivityProfileBr.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("userid", VisitdayInfo.get_id());
////                    intent.putExtra("imgurl", G.nodeurl + "/" + lawyerInfo.getProfile());
//                G.context.startActivity(intent);


//            }
//        });
    }

    @Override
    public int getItemCount() {
        return VisitdayInfos.size();
    }

    public class VisitdayViewHolder extends RecyclerView.ViewHolder {

        TextView txttitle;
        TextView txtname;
        TextView txtprof;
        RecyclerView rcyclrday;
        ImageView img;
        ConstraintLayout linearvisitday;
        //        TextView txtrating;
        RatingBar rate;
        CardView  crdprof;

        public VisitdayViewHolder(View itemView) {
            super(itemView);
//            txtname = (TextView) itemView.findViewById(R.id.txt_visitday_name);
//            txtprof = (TextView) itemView.findViewById(R.id.txt_visitday_prof);
//            crdprof = (CardView) itemView.findViewById(R.id.crd_visitday_prof);
//
//            img = (ImageView) itemView.findViewById(R.id.img_visitday);
//            linearvisitday = (ConstraintLayout) itemView.findViewById(R.id.visitday_layout);
//            rate = (RatingBar) itemView.findViewById(R.id.ratingbar_visitday);
            rcyclrday = (RecyclerView) itemView.findViewById(R.id.rec_pager);
            txttitle = (TextView) itemView.findViewById(R.id.txt_pager);

        }


    }
    public ArrayList<DayReserve> setnobat(ArrayList<String> arrtime,ArrayList<Repitem> arrnobat){
        ArrayList<DayReserve> reservearr=new ArrayList<>();
        ArrayList<Reserve> resarr=new ArrayList<>();

        for (String t : arrtime) {
            DayReserve reserve=new DayReserve();
            reserve.setTime(t);
            for (Repitem r : arrnobat) {

                if(r.getTime().equals(t)){

//                    reserve.setUsers(r.getUser());

                }else {
//                    reserve.setUsers("");
                }

            }


            reservearr.add(reserve);
        }

        return reservearr;
    }
    public Repitem getrepeat(ArrayList<Nobat> rep, String time) {
        Repitem report = new Repitem();
        String user="";
        int tedad = 0;
//        String time = "";
        for (Nobat i : rep) {

            if (i.getTime().equals(time)) {

                tedad++;
                user = user +"/"+ i.getUser();


            }
        }

        if (tedad != 0) {
            report.setTime(time);
            report.setTedad(tedad);
//            report.setUser(user);
        }


        return report;
    }
}
