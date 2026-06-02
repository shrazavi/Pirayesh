package com.shrazavi.pirayesh.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Activity.ActivityMain;
import com.shrazavi.pirayesh.Activity.ActivityNobatDetail;
import com.shrazavi.pirayesh.Activity.ActivityPayment;
import com.shrazavi.pirayesh.Activity.ActivityRating;
import com.shrazavi.pirayesh.Activity.ActivityReserve;
import com.shrazavi.pirayesh.Activity.ActivityReserve2;
import com.shrazavi.pirayesh.Activity.ActivitySetting;
import com.shrazavi.pirayesh.Activity.ActivityTransaction;

import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Rating;
import com.shrazavi.pirayesh.DataClass.User;

import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.ImageProfile;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ImageView imgProfile;
    CardView crdprof;
    String name="";
    RecyclerView recexperiense;
    TextView txtuser, txtaccount, txtcash, txttransaction, txtprof;
    Button btnreserve, btedit, btnpost;
    ConstraintLayout lay;
//    ArrayList<Experience> arrex = new ArrayList<>();
    //    public static SharedPreferences preferences;
//    public RecyclerAdapterSpecialty recyclerAdapterex;

        LinearLayout layaccount;
    public ProfileFragment() {

    }

    Retrofitinformation RI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myFragmentView = inflater.inflate(R.layout.profile_fragment, container, false);
//        arrex.add(new Experience("کیفری", R.drawable.ic_jenai));
//        arrex.add(new Experience("حقوقی", R.drawable.ic_hoghoghi));
//        arrex.add(new Experience("خانواده", R.drawable.ic_family));
//        arrex.add(new Experience("ثبتی", R.drawable.ic_sabt));
//        arrex.add(new Experience("مهاجرت", R.drawable.ic_eghamat));
//        arrex.add(new Experience("ثبت شرکت", R.drawable.ic_sherkat));
//        arrex.add(new Experience("مالیات",R.drawable.ic_maliat));
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);


//        recexperiense = (RecyclerView) myFragmentView.findViewById(R.id.rec_experience);
//        recexperiense.setLayoutManager(new GridLayoutManager(G.context, 2));
//        recexperiense.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(1), true));
//        recexperiense.setItemAnimator(new DefaultItemAnimator());
//        recexperiense.setAdapter(recyclerAdapterex = new RecyclerAdapterSpecialty(arrex, G.context));
//        recyclerAdapterex.notifyDataSetChanged();
        imgProfile = (ImageView) myFragmentView.findViewById(R.id.img_item_profile);
        crdprof = (CardView) myFragmentView.findViewById(R.id.crd_item_profile);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        txtuser = (TextView) myFragmentView.findViewById(R.id.txt_profile_name);
        txtprof = (TextView) myFragmentView.findViewById(R.id.txt_item_profile);
        txtaccount = (TextView) myFragmentView.findViewById(R.id.txt_account_prof);
        txttransaction = (TextView) myFragmentView.findViewById(R.id.txt_transaction_prof);
        txtcash = (TextView) myFragmentView.findViewById(R.id.txt_cash_fr_prof);
        lay = (ConstraintLayout) myFragmentView.findViewById(R.id.lay_fr_profile);
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        String txtuserid = BasicActivity.name;
        btnreserve = (Button) myFragmentView.findViewById(R.id.btn_reserve_profile);
//        btntrans=(Button)myFragmentView.findViewById(R.id.btn_transaction);
//        btnpost = (Button) myFragmentView.findViewById(R.id.btn_prof_post);
        btedit = (Button) myFragmentView.findViewById(R.id.btn_edit_profile);
        btedit.setTypeface(G.face);
        btnreserve.setTypeface(G.face);
        layaccount=(LinearLayout)myFragmentView.findViewById(R.id.lay_item_profile_account);


        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
        }
        if (BasicActivity.imgprofile.equals(G.nodeurl  + "empty")) {
            char ch1 = txtuserid.toUpperCase().charAt(0);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200));
//                            holder.imgProf.setVisibility(View.GONE);
            txtprof.setText(ch1 + "");
            crdprof.setCardBackgroundColor(color);
        } else {
//            Picasso.with(G.context).load(imgurl).into(imgProfile);
            Picasso.with(G.context).load(BasicActivity.imgprofile).transform(new ImageProfile()).into(imgProfile);

        }

        btnreserve.setVisibility(View.GONE);
        layaccount.setVisibility(View.GONE);
        if (BasicActivity.bu.equals("br")) {
            Log.e("userinfo", BasicActivity.sx+"/"+BasicActivity.bu+"/"+BasicActivity.userid+"/"+BasicActivity.number);

            Call<Barber> callbarber = RI.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
            callbarber.enqueue(new Callback<Barber>() {
                @Override
                public void onResponse(Call<Barber> call, Response<Barber> response) {
                    name=response.body().getName();
                    txtcash.setText("زمان اتمام اشتراک : ");
                    btnreserve.setVisibility(View.VISIBLE);
                    txtuser.setText(response.body().getName());
                    layaccount.setVisibility(View.VISIBLE);
                    Log.e("name", response.body().getName());

                    Call<MessageSignup> callaccount = RI.getaccount(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
                    callaccount.enqueue(new Callback<MessageSignup>() {
                        @Override
                        public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                        txtaccount.setText(response.body().getMessage());

                        }

                        @Override
                        public void onFailure(Call<MessageSignup> call, Throwable t) {
                            Log.e("error???", t + "");
                        }
                    });
                    Call<Rating> callrating = RI.getRating(BasicActivity.userid);
                    callrating.enqueue(new Callback<Rating>() {
                        @Override
                        public void onResponse(Call<Rating> call, Response<Rating> response) {
                            String total=response.body().getTotal()+"";
                            String cutString =  total.substring(0, 3);
                            txttransaction.setText("امتیاز شما : " + cutString);
                        }

                        @Override
                        public void onFailure(Call<Rating> call, Throwable t) {
                            Log.e("error rate", t + "");
                        }
                    });
//                    Picasso.with(G.context).load(response.body().getProfile()).transform(new ImageProfile()).into(imgProfile);
                }

                @Override
                public void onFailure(Call<Barber> call, Throwable t) {
                    Log.e("error vl", t + "");
                }
            });

//

        } else {
            Call<User> calluser = RI.getuser(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
//                    txtaccount.setText(formatter.format(Integer.parseInt(response.body().getAccount())));
//                    txtcash.setText("اعتبار فعلی : ");
                    btnreserve.setVisibility(View.GONE);
                    layaccount.setVisibility(View.GONE);
                    txtuser.setText(response.body().getName());
                    txttransaction.setText("جزئیات نوبت رزرو شده");
                    name=response.body().getName();

//                    Picasso.with(G.context).load(imgurl).transform(new ImageProfile()).into(imgProfile);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("error user", t + "");
                }
            });
//
        }
        btedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(G.context, ActivitySetting.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

//        btnpost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent4 = new Intent(G.context, ActivityAddPost.class);
//                startActivity(intent4);
//            }
//        });
        btnreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(G.context, ActivityReserve2.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
            }
        });
        isConnected();
        if (isConnected()) {
            ActivityMain.txtconnection.setText("Connected");

        } else {

            ActivityMain.txtconnection.setText("Waiting For Connection");
        }

        txttransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BasicActivity.bu.equals("br")) {
                    Intent intent = new Intent(G.context, ActivityRating.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(G.context, ActivityNobatDetail.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
            }
        });


        return myFragmentView;
    }

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

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
