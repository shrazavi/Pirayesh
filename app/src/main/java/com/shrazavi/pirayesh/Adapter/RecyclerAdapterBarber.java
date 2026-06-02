package com.shrazavi.pirayesh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Activity.ActivityProfileBr;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Rating;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterBarber extends RecyclerView.Adapter<RecyclerAdapterBarber.BarberViewHolder> {
    Retrofitinformation RI;
    String type;

    public ArrayList<Barber> BarberInfos = new ArrayList<>();
    Context context;
//    public SharedPreferences preferences;

    public RecyclerAdapterBarber(ArrayList<Barber> BarberInfos, Context context) {
        this.context = context;

        this.BarberInfos = BarberInfos;
    }

    @Override
    public BarberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barber, parent, false);
        return new BarberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BarberViewHolder holder, final int position) {
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        final Barber barberInfo = BarberInfos.get(position);
        Log.i("LOG", "recycler done");
        holder.txtname.setText(barberInfo.getTitle());
//        holder.txtexperience.setText("با" + barberInfo.getExperience() + "سال سابقه");

        holder.txtname.setTypeface(G.face);
//        holder.txtexperience.setTypeface(G.face);
//        holder.txttype.setTypeface(G.face);
//        holder.txtbase.setTypeface(G.face);
        Log.e("barber is=", barberInfo.get_id() + "");
        Call<Rating> callrating = RI.getRating(barberInfo.get_id());
        callrating.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {
//                holder.txtrating.setText(response.body().getTotal()+"");
                holder.rate.setRating(response.body().getTotal());
//                Log.e("rate is=", response.body().getTotal()+ "");
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.e("error rate", t + "");
            }
        });
//        holder.linearLawyer.setPadding(0,0,0,0);
//        holder.linearLawyer.setPaddingRelative(0,0,0,0);

//        type = preferences.getString("type", "not");
//        holder.txtrating.setText(lawyerInfo.getRating());
//        holder.txtrating.setText(lawyerInfo.getRating());

//        Log.e("img",G.nodeurl  +"/"+ lawyerInfo.getProfile()+"");
//        Picasso.with(context).load(G.nodeurl  +"/"+ lawyerInfo.getProfile()).into(holder.img);
        if (barberInfo.getProfile().equals("empty")) {
            char ch1 = barberInfo.getTitle().toUpperCase().charAt(0);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                  imgProf.setVisibility(View.GONE);
//                   holder.imgProf.setBackgroundResource(R.drawable.edtdetailkala);

            holder.txtprof.setText(ch1 + "");
            holder.crdprof.setCardBackgroundColor(color);
        } else {
            Picasso.with(G.context).load(G.nodeurl + "/" + barberInfo.getProfile()).transform(new ImageProfile()).into(holder.img);
        }
        holder.linearbarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(G.context, ActivityProfileBr.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userid", barberInfo.get_id());
//                    intent.putExtra("imgurl", G.nodeurl + "/" + lawyerInfo.getProfile());
                G.context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return BarberInfos.size();
    }

    public class BarberViewHolder extends RecyclerView.ViewHolder {


        TextView txtname;
        TextView txtprof;

        ImageView img;
        ConstraintLayout linearbarber;
//        TextView txtrating;
        RatingBar rate;
        CardView  crdprof;

        public BarberViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.txt_barber_name);
            txtprof = (TextView) itemView.findViewById(R.id.txt_barber_prof);
            crdprof = (CardView) itemView.findViewById(R.id.crd_barber_prof);

            img = (ImageView) itemView.findViewById(R.id.img_barber);
            linearbarber = (ConstraintLayout) itemView.findViewById(R.id.barber_layout);
            rate = (RatingBar) itemView.findViewById(R.id.ratingbar_barber);



        }


    }

}
