package com.shrazavi.pirayesh.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterTicket;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Ticket;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;


import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySupport extends AppCompatActivity {
    String[] arrtype = {"امور فنی", "امور مالی", "امور مشاوره", "امور کاربری", "انتقادات و پیشنهادات", ""};
    Button btnback, btnadd;
    TextView txtsupport;
    Retrofitinformation RI;
    String content;
    String enk;
    SecretKeySpec Key;
//    public SharedPreferences preferences;
    String username = "";
    String bu = "";
    public RecyclerAdapterTicket recyclerAdapterTicket;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Ticket> infos;
    SecretKeySpec secretKey;
    ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BasicActivity.sx.equals("male")) {
            super.setTheme(R.style.Theme_Timework);
        } else {
            super.setTheme(R.style.Theme_mainwomen);
        }
        setContentView(R.layout.activity_support);
        txtsupport = (TextView) findViewById(R.id.txt_support);
        btnback = (Button) findViewById(R.id.btn_support_back);
        btnadd = (Button) findViewById(R.id.btn_support_add_ticket);
        recyclerView = (RecyclerView) findViewById(R.id.rec_support);
        lay= findViewById(R.id.lay_support);

        linearLayoutManager = new LinearLayoutManager(G.context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        btnadd.setTypeface(G.face);
        secretKey = SymmetricAlgorithmAES.setUpSecretKey();
        infos = new ArrayList<>();
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        username = BasicActivity.userid;
        bu = BasicActivity.bu;
//        enk = BasicActivity.preferences.getString("k5", "not");
//        byte[] data = Base64.decode(enk, Base64.DEFAULT);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + username + "-" + getResources().getString(R.string.developer), enk);
        content=BasicActivity.content;
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
            setTheme(R.style.Theme_Timework);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
            setTheme(R.style.Theme_mainwomen);
        }
        Call<ArrayList<Ticket>> getticket = RI.getticket(content, username, username);
        getticket.enqueue(new Callback<ArrayList<Ticket>>() {
            @Override
            public void onResponse(Call<ArrayList<Ticket>> call, Response<ArrayList<Ticket>> response) {
                infos = response.body();
                recyclerView.setAdapter(recyclerAdapterTicket = new RecyclerAdapterTicket(infos, G.context, content));
                recyclerAdapterTicket.notifyDataSetChanged();
                if (infos.size() == 0) {
                    txtsupport.setVisibility(View.VISIBLE);
                } else {
                    txtsupport.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ticket>> call, Throwable t) {
                Log.e("calllogerror", t + "");
            }
        });
//        btnok = (Button) findViewById(R.id.btn_support_ok);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuild = new AlertDialog.Builder(ActivitySupport.this);
                View mView = getLayoutInflater().inflate(R.layout.fragment_ticket, null);
                String type = "";

                TextInputEditText txttitle = (TextInputEditText) mView.findViewById(R.id.edt_ticket_title);
                TextInputEditText txtdesc = (TextInputEditText) mView.findViewById(R.id.edt_ticket_desc);
                AutoCompleteTextView spintype = (AutoCompleteTextView) mView.findViewById(R.id.spn_ticket_type);
                Button btnSubmit = (Button) mView.findViewById(R.id.btnSubTicket);

                spinnerAdapter AdapterType = new spinnerAdapter(ActivitySupport.this, android.R.layout.simple_list_item_1);
                AdapterType.addAll(arrtype);
                spintype.setAdapter(AdapterType);

                mBuild.setView(mView);
                AlertDialog dialog = mBuild.create();
                dialog.show();
                spintype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String type = spintype.getText().toString();
//                        Log.e("shahrname", shahrestan);


                    }
                });
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String encryptedMessage = Base64.encodeToString(SymmetricAlgorithmAES.encryption(secretKey, txtdesc.getText().toString()), Base64.DEFAULT);
                        if (txttitle.getText().toString().isEmpty() || txtdesc.getText().toString().isEmpty() || spintype.getText().toString().isEmpty()) {
                            Toast.makeText(ActivitySupport.this, "لطفا اطلاعاتتان را تکمیل کنید", Toast.LENGTH_SHORT).show();
                        } else {
                            Call<MessageSignup> callticket = RI.insertticket(BasicActivity.content, BasicActivity.userid,bu,"user", spintype.getText().toString(), txttitle.getText().toString(), encryptedMessage,Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT), BasicActivity.number);
                            callticket.enqueue(new Callback<MessageSignup>() {
                                @Override
                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                    Boolean status = response.body().getStatus();
                                    if (status) {
                                        dialog.dismiss();
                                        Call<ArrayList<Ticket>> getticket = RI.getticket(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
                                        getticket.enqueue(new Callback<ArrayList<Ticket>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<Ticket>> call, Response<ArrayList<Ticket>> response) {
                                                infos = response.body();
                                                recyclerView.setAdapter(recyclerAdapterTicket = new RecyclerAdapterTicket(infos, G.context, content));
                                                recyclerAdapterTicket.notifyDataSetChanged();
                                                txtsupport.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onFailure(Call<ArrayList<Ticket>> call, Throwable t) {
                                                Log.e("calllogerror", t + "");
                                            }
                                        });
                                    }

                                }

                                @Override
                                public void onFailure(Call<MessageSignup> call, Throwable t) {
                                    Log.e("error user", t + "");
                                }

                            });

                        }
                    }
                });
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ActivitySupport.this, ActivityOption.class);
//                ActivitySupport.this.startActivity(intent);
                ActivitySupport.this.finish();
            }
        });

    }

}
