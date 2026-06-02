package com.shrazavi.pirayesh.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterPayment;
import com.shrazavi.pirayesh.DataClass.Payment;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityPayment2 extends AppCompatActivity {

    ArrayList<Payment> arrpay = new ArrayList<>();
    Retrofitinformation RI;
    public RecyclerAdapterPayment recyclerAdapterpayment;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Button btnback;
    ConstraintLayout lay;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_payment2);

        recyclerView = findViewById(R.id.rec_payment);
        btnback = (Button) findViewById(R.id.btn_payment_back);
        lay=findViewById(R.id.lay_payment);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        linearLayoutManager = new LinearLayoutManager(ActivityPayment2.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        Call<ArrayList<Payment>> callpayment = RI.getpayment();
        callpayment.enqueue(new Callback<ArrayList<Payment>>() {
            @Override
            public void onResponse(Call<ArrayList<Payment>> call, Response<ArrayList<Payment>> response) {
                arrpay=response.body();
                recyclerView.setAdapter(recyclerAdapterpayment = new RecyclerAdapterPayment(arrpay, ActivityPayment2.this,ActivityPayment2.this));
                recyclerAdapterpayment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Payment>> call, Throwable t) {
                Log.e("tarrif error=",t+"");

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityLocation.this, ActivityOption.class);
//                ActivityLocation.this.startActivity(intent);
                ActivityPayment2.this.finish();
            }
        });
    }
}