package com.shrazavi.pirayesh.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.Activity.ActivityFinalPayment;
import com.shrazavi.pirayesh.Activity.ActivityPayment;
import com.shrazavi.pirayesh.Activity.ActivityShift;
import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.Cash;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Payment;
import com.shrazavi.pirayesh.DataClass.Shift;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.SymmetricAlgorithmAES;
import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterPayment extends RecyclerView.Adapter<RecyclerAdapterPayment.ChatViewHolder> {
    String username = "";
    //    public SharedPreference preferences;
    Retrofitinformation RI;
    String vu = "";
    public ArrayList<Payment> paymentInfos = new ArrayList<>();
    Context context;
    Activity activity;
    String detail = "",detailcash = "";
    String transid = "";
    int random;
    final int min = 100000;
    final int max = 999999;
    String k = "";
    public RecyclerAdapterPayment(ArrayList<Payment> paymentInfos, Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
        this.paymentInfos = paymentInfos;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {


        final Payment paymentInfo = paymentInfos.get(position);
//        Log.i("LOG","recycler done");
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.txtprice.setText(formatter.format(Integer.parseInt(paymentInfo.getPrice()))+" تومان ");
        if (paymentInfo.getMonth().equals("12")) {
            holder.txtmonth.setText("ساله");
            holder.txtdoration.setText("1");

        } else {
            holder.txtmonth.setText("ماهه");
            holder.txtdoration.setText(paymentInfo.getMonth());
        }
        if (paymentInfo.getTakhfif().equals("0")) {
            holder.btntakhfif.setVisibility(View.INVISIBLE);
        } else {
            holder.btntakhfif.setText(paymentInfo.getTakhfif() + " % ");
        }
        holder.crdpayment.setCardBackgroundColor(Color.argb(255,paymentInfo.getRed() ,paymentInfo.getGreen() , paymentInfo.getBlue()));
        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        holder.btntakhfif.setTypeface(G.face);
        random = new Random().nextInt((max - min) + 1) + min;

        holder.linearpayment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                detail = SymmetricAlgorithmAES.encrypt(BasicActivity.userid + "/" + random + "/" + paymentInfo.getPrice() + "/" + "un"+"/"+BasicActivity.name, BasicActivity.preferences.getString("k5", "not"));

                Call<MessageSignup> calladdtrans = RI.AddTransaction(BasicActivity.content, BasicActivity.userid,BasicActivity.name, random, paymentInfo.getPrice(), "unsuccessful", detail,BasicActivity.number);
                calladdtrans.enqueue(new Callback<MessageSignup>() {
                    @Override
                    public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
//                    G.income = Integer.parseInt(response.body().getIncome());
                        transid = response.body().getMessage();
                        k="k"+response.body().getContent();


                        detailcash = SymmetricAlgorithmAES.encrypt(BasicActivity.userid + "/"  +  paymentInfo.getPrice() + "/" + transid, BasicActivity.preferences.getString(k, "not"));
                        if (paymentInfo.getPrice().equals("50000")) {
                            Call<Cash> callcash = RI.increaseaccount(BasicActivity.content, BasicActivity.userid, Long.valueOf(paymentInfo.getPrice()), transid, detailcash,BasicActivity.number);
                            callcash.enqueue(new Callback<Cash>() {
                                @Override
                                public void onResponse(Call<Cash> call, Response<Cash> response) {
                                    Intent intent18 = new Intent(activity, ActivityFinalPayment.class);
                                    intent18.putExtra("cash", response.body().getCash());
                                    intent18.putExtra("date", response.body().getDate());
                                    intent18.putExtra("code", response.body().getCode());
                                    intent18.putExtra("time", response.body().getTime());
                                    intent18.putExtra("price", paymentInfo.getPrice());
                                    intent18.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.startActivity(intent18);
                                    activity.finish();
                                }

                                @Override
                                public void onFailure(Call<Cash> call, Throwable t) {
                                    Log.e("peyment error=",t+"");

                                }
                            });


                        } else {
                            BillingClientStateListener listener = new BillingClientStateListener() {
                                @Override
                                public void onClientSetupFinished(@NotNull ClientState state) {
                                    //Observing client states
                                }

                                @Override
                                public void onClientServiceDisconnected() {
                                    Log.v("TAG_INAPP", "Billing client Disconnected");
                                    //When Service disconnect
                                }
                            };
                            ZarinPalBillingClient client = ZarinPalBillingClient.newBuilder(activity)
                                    .enableShowInvoice()
                                    .setListener(listener)
                                    .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                    .build();

                            Purchase purchase = Purchase.newBuilder().asPaymentRequest(
                                    "14f86589-4f47-433f-a318-7eaaa6d7a8d7",
                                    Long.valueOf(paymentInfo.getPrice()),
                                    G.nodeurl+"/zarinpayment",
                                    paymentInfo.getPrice()+" RR Purchase"
                            ).build();
                            client.launchBillingFlow(purchase, new FutureCompletionListener<Receipt>() {
                                @Override
                                public void onComplete(TaskResult<Receipt> task) {
                                    if (task.isSuccess()) {
                                        Receipt receipt = task.getSuccess();
                                        Log.v("ZP_RECEIPT", receipt.getTransactionID());
                                        //here you can send receipt data to your server
                                        //sentToServer(receipt)
                                        Call<Cash> callcash = RI.increaseaccount(BasicActivity.content, BasicActivity.userid, Long.valueOf(paymentInfo.getPrice()), transid, detailcash,BasicActivity.number);
                                        callcash.enqueue(new Callback<Cash>() {
                                            @Override
                                            public void onResponse(Call<Cash> call, Response<Cash> response) {
                                                Intent intent18 = new Intent(activity, ActivityFinalPayment.class);
                                                intent18.putExtra("cash", response.body().getCash());
                                                intent18.putExtra("date", response.body().getDate());
                                                intent18.putExtra("code", response.body().getCode());
                                                intent18.putExtra("time", response.body().getTime());
                                                intent18.putExtra("price", paymentInfo.getPrice());
                                                intent18.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                activity.startActivity(intent18);
                                                activity.finish();
                                            }

                                            @Override
                                            public void onFailure(Call<Cash> call, Throwable t) {
                                                Log.e("peyment error=",t+"");

                                            }
                                        });


                                    } else {
                                        task.getFailure().printStackTrace();
                                    }
                                }
                            });

                        }
                        }

                        @Override
                        public void onFailure(Call<MessageSignup> call, Throwable t) {
                            Log.e("error vl", t + "");
                        }
                    });

            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentInfos.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {


        LinearLayout linearpayment;
        TextView txtprice, txtmonth, txtdoration;
        Button btntakhfif;
        CardView crdpayment;

        public ChatViewHolder(View itemView) {
            super(itemView);
            txtprice = (TextView) itemView.findViewById(R.id.txt_item_payment_price);
            txtmonth = (TextView) itemView.findViewById(R.id.txt_item_payment_month);
            txtdoration = (TextView) itemView.findViewById(R.id.txt_item_payment_doration);
            btntakhfif = (Button) itemView.findViewById(R.id.btn_item_payment);
            linearpayment = (LinearLayout) itemView.findViewById(R.id.lay_item_payment);
            crdpayment = (CardView) itemView.findViewById(R.id.crd_item_payment);
        }


    }
    public String currentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());
        return formattedDate;


    }


    public long currentdate() {
        Calendar c = Calendar.getInstance();
//        SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
        long formattedDate = c.getTimeInMillis();
        return formattedDate;


    }
}
