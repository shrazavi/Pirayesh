package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.G;

public class ActivityTransactionDetail extends AppCompatActivity {

    TextView txtprice, txtdate, txttime, txtcode, txtstatus;
    String price, date, time, code, status;
    Button btnback;
    LinearLayout lay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_transaction_detail);

        txtprice = (TextView) findViewById(R.id.txt_tr_dt_price);
        txtdate = (TextView) findViewById(R.id.txt_tr_dt_date);
        txttime = (TextView) findViewById(R.id.txt_tr_dt_time);
        txtcode = (TextView) findViewById(R.id.txt_tr_dt_code);
        txtstatus = (TextView) findViewById(R.id.txt_tr_dt_status);
        btnback = (Button) findViewById(R.id.btn_tr_dt_back);
        lay=(LinearLayout) findViewById(R.id.lay_tr_detail);

        price = (String) getIntent().getExtras().get("price");
        date = (String) getIntent().getExtras().get("date");
        time = (String) getIntent().getExtras().get("time");
        code = (String) getIntent().getExtras().get("code");
        status = (String) getIntent().getExtras().get("status");
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        txtprice.setText(price);
        txtdate.setText(date);
        txttime.setText(time);
        txtcode.setText(code);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityTransaction.this, ActivityMain.class);
//                ActivityTransaction.this.startActivity(intent);
                ActivityTransactionDetail.this.finish();
            }
        });
        if (status.equals("successful")) {
            txtstatus.setText("موفق");
            txtstatus.setTextColor(G.context.getResources().getColor(R.color.timemychat));
        } else {

            txtstatus.setTextColor(G.context.getResources().getColor(R.color.unsuccesful));
            txtstatus.setText("ناموفق");
        }
    }


}
