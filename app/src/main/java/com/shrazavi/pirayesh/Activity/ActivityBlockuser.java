package com.shrazavi.pirayesh.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrazavi.pirayesh.Adapter.RecyclerAdapterBlackList;

import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBlockuser extends AppCompatActivity {
    static ArrayList<String> userlist;
    //    Retrofitinformation RInode;
    String content;
    String enk;
    SecretKeySpec Key;
    String username = "";
    String bu = "";
    Button btnback;
   // public SharedPreferences preferences;
    static RecyclerView recblock;
    public static TextView txtblock;
    public static RecyclerAdapterBlackList recyclerAdapterBlackList;
    LinearLayoutManager linearLayoutManager;
    ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_blockuser);


//        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
       // BasicActivity.preferences = PreferenceManager.getDefaultSharedPreferences(ActivityBlockuser.this);
        username = BasicActivity.userid;
        bu = BasicActivity.bu;
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + username + "-" + getResources().getString(R.string.developer), enk);
        content=BasicActivity.content;
        lay= findViewById(R.id.lay_block);
        btnback = (Button) findViewById(R.id.btn_block_back);

        recblock = (RecyclerView) findViewById(R.id.rec_blocklist);
        txtblock = (TextView) findViewById(R.id.txt_block_no);
        txtblock.setVisibility(View.GONE);
        userlist = new ArrayList<>();
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        linearLayoutManager = new LinearLayoutManager(G.context);
        recblock.setHasFixedSize(true);
        recblock.setLayoutManager(linearLayoutManager);
        setdata(content, username, ActivityBlockuser.this);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivityBlockuser.this, ActivityMain.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ActivityBlockuser.this.startActivity(intent);
                ActivityBlockuser .this.finish();
            }
        });

    }

    public static void setdata(String content, String username, Context context) {

        Retrofitinformation RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);

        Call<Barber> callbarber = RInode.getbarber(content, username, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                String blockusers = response.body().getBlockusers();
                if (blockusers.isEmpty()) {
                    txtblock.setVisibility(View.VISIBLE);
                    recblock.setVisibility(View.GONE);
                } else {
                    recblock.setVisibility(View.VISIBLE);
                    txtblock.setVisibility(View.GONE);
                    String[] arrblock = blockusers.split(",");
                    userlist = new ArrayList<String>(Arrays.asList(arrblock));
//                    Log.e("blacklist???", userlist.size() + "");
                    recblock.setAdapter(recyclerAdapterBlackList = new RecyclerAdapterBlackList(userlist, context, content));
                    recyclerAdapterBlackList.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("error???", t + "");
            }
        });


    }

}
