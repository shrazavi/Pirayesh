package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Adapter.RecyclerAdapterTransAction;
import com.shrazavi.pirayesh.DataClass.Transaction;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTransaction extends AppCompatActivity {
    ArrayList<Transaction> trans;
    RecyclerAdapterTransAction recyclerAdaptertransaction;
    RecyclerView recyclerViewTransaction;
    LinearLayoutManager linearLayoutManager;
    Retrofitinformation RI;
    Button btnback;
    ConstraintLayout lay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_transaction);
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        linearLayoutManager = new LinearLayoutManager(G.context);
        btnback=(Button) findViewById(R.id.btn_transaction_back);
        trans = new ArrayList<>();
        recyclerViewTransaction = findViewById(R.id.transaction_list);
        lay= findViewById(R.id.lay_transaction);
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        recyclerViewTransaction.setHasFixedSize(true);
        recyclerViewTransaction.setLayoutManager(linearLayoutManager);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTransaction.this, ActivityMain.class);
                ActivityTransaction.this.startActivity(intent);
                ActivityTransaction.this.finish();
            }
        });
        Call<ArrayList<Transaction>> calltransaction = RI.getTransaction(BasicActivity.content, BasicActivity.userid);
        calltransaction.enqueue(new Callback<ArrayList<Transaction>>() {
            @Override
            public void onResponse(Call<ArrayList<Transaction>> call, Response<ArrayList<Transaction>> response) {
                trans = response.body();
                recyclerViewTransaction.setAdapter(recyclerAdaptertransaction = new RecyclerAdapterTransAction(trans, G.context));


                recyclerAdaptertransaction.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<Transaction>> call, Throwable t) {
                Log.e("transerror", t + "");
            }
        });


//        ChatDatabase chatDatabase = new ChatDatabase(G.context);
//        Cursor cursor = chatDatabase.getAllTransaction();
//        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//
//            final String price = cursor.getString(1);
//            final String time = cursor.getString(2);
//            final long date = cursor.getLong(3);
//            final String status = cursor.getString(4);
//
//            Transaction transaction = new Transaction();
////            transaction.read = "read";
//            transaction.price = price;
//            transaction.time = time;
//            transaction.date = date;
//            transaction.status = status;
//
//            trans.add(transaction);
//        }
//        recyclerAdaptertransaction = new RecyclerAdapterTransAction( trans,ActivityTransaction.this);
        recyclerViewTransaction.setAdapter(new RecyclerAdapterTransAction(trans, ActivityTransaction.this));





    }
}