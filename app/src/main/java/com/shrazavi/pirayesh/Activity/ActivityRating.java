package com.shrazavi.pirayesh.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shrazavi.pirayesh.DataClass.Rating;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRating extends AppCompatActivity {
    ArrayList<Entry> chartcat = new ArrayList<Entry>();
    ArrayList<String> name = new ArrayList<String>();
    Button btnback;
    Retrofitinformation RI;
    ConstraintLayout lay;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_rating);
        PieChart pieChart = findViewById(R.id.piechart);
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        btnback=(Button) findViewById(R.id.btn_rating_back);
        lay=findViewById(R.id.lay_rating);

        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);

            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        Call<Rating> callrating = RI.getRating(BasicActivity.userid);
        callrating.enqueue(new Callback<Rating>() {
            @Override
            public void onResponse(Call<Rating> call, Response<Rating> response) {

                chartcat.add(new Entry(response.body().getOne(), 1));
                chartcat.add(new Entry(response.body().getTwo(), 2));
                chartcat.add(new Entry(response.body().getThree(), 3));
                chartcat.add(new Entry(response.body().getFour(), 4));
                chartcat.add(new Entry(response.body().getFive(), 5));

                name.add("یک ستاره");
                name.add("دو ستاره");
                name.add("سه ستاره");
                name.add("چهار ستاره");
                name.add("پنج ستاره");

                PieDataSet dataSet = new PieDataSet(chartcat, "");
                dataSet.setValueTextSize(20f);
                dataSet.setValueTypeface(G.face);
                dataSet.setValueTextColor(ColorTemplate.rgb("#ffffff"));
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                PieData data = new PieData(name, dataSet);
                pieChart.setData(data);
                pieChart.setDescription("");
                pieChart.setCenterText("میزان امتیازدهی کاربران");
                pieChart.setCenterTextSize(20f);
//                pieChart.setHoleColor(R.color.charthole);

                pieChart.setCenterTextTypeface(G.face);
                pieChart.setDescriptionTypeface(G.face);
                pieChart.animateXY(3000, 3000);
            }

            @Override
            public void onFailure(Call<Rating> call, Throwable t) {
                Log.e("error rate", t + "");
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRating.this, ActivityMain.class);
                ActivityRating.this.startActivity(intent);
                ActivityRating.this.finish();
            }
        });
    }


}
