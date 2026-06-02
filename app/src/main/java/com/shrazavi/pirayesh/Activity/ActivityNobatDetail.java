package com.shrazavi.pirayesh.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Adapter.RecyclerAdapterDetail;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterNobat;
import com.shrazavi.pirayesh.DataClass.NameNum;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityNobatDetail extends AppCompatActivity {
    String name="";
    Button btnback;
    static Retrofitinformation RInode;
    public static RecyclerView recdetail;
    public static ArrayList<Nobat> arr=new ArrayList<>();
    public static TextView txtstatus;
    public static RecyclerAdapterDetail recyclerAdapterDetail;
    LinearLayoutManager linearLayoutManager;
    ConstraintLayout lay;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_nobat_detail);

        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        name = (String) getIntent().getExtras().get("names");
        txtstatus = (TextView) findViewById(R.id.txt_n_detail_status);
        btnback = (Button) findViewById(R.id.btn_n_detail_back);
        recdetail=(RecyclerView) findViewById(R.id.rec_n_detail);
        linearLayoutManager = new LinearLayoutManager(ActivityNobatDetail.this);
        recdetail.setHasFixedSize(true);
        recdetail.setLayoutManager(linearLayoutManager);
        lay=findViewById(R.id.lay_n_detail);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);

        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);

        }
        getnobatdetail(ActivityNobatDetail.this,ActivityNobatDetail.this,name);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityNobatDetail.this, ActivityMain.class);
                ActivityNobatDetail.this.startActivity(intent);
                ActivityNobatDetail.this.finish();
            }
        });
        }

    public static void getnobatdetail(Activity activity, Context context, String name) {
        Call<ArrayList<Nobat>> callnobat = RInode.getnobatuser(BasicActivity.content, BasicActivity.userid, name, BasicActivity.number, BasicActivity.number);
        callnobat.enqueue(new Callback<ArrayList<Nobat>>() {
            @Override
            public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {
                arr.clear();
                arr=response.body();
                if (arr.size() == 0) {
                    recdetail.setVisibility(View.GONE);
                    txtstatus.setVisibility(View.VISIBLE);
                    txtstatus.setText("لیستی برای نمایش موجود نیست.");

                } else {
                    recdetail.setVisibility(View.VISIBLE);
                    txtstatus.setVisibility(View.GONE);

                }

                recdetail.setAdapter(recyclerAdapterDetail = new RecyclerAdapterDetail(arr, activity, context,name));
                recyclerAdapterDetail.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {
                Log.e("errnobat", t + "");

            }
        });


    }
    }
