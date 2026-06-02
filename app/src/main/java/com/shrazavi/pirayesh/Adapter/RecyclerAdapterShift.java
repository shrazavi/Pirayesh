package com.shrazavi.pirayesh.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.InputType;
import android.util.Base64;
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

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.shrazavi.pirayesh.Activity.ActivityShift;
import com.shrazavi.pirayesh.Activity.ActivityTicketMessage;
import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.MessageSignup;
import com.shrazavi.pirayesh.DataClass.Shift;
import com.shrazavi.pirayesh.DataClass.Visitday;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapterShift extends RecyclerView.Adapter<RecyclerAdapterShift.ChatViewHolder> {
    String username = "";
    //    public SharedPreference preferences;
    Retrofitinformation RI;
    String vu = "";
    public ArrayList<Shift> shiftinfos = new ArrayList<>();
    Context context;
    Activity activity;

    public RecyclerAdapterShift(ArrayList<Shift> shiftInfos, Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.shiftinfos = shiftInfos;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shift, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChatViewHolder holder, final int position) {


        final Shift shiftinfo = shiftinfos.get(position);
//        Log.i("LOG","recycler done");

        holder.txtstart.setText(shiftinfo.getH1()+":"+shiftinfo.getM1());
        holder.txtend.setText(shiftinfo.getH2()+":"+shiftinfo.getM2());

        RI = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        holder.linearShift.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.linearShift);
                popup.getMenuInflater().inflate(R.menu.shift_option, popup.getMenu());
                try {
                    Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
                    mFieldPopup.setAccessible(true);
                    MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
                    mPopup.setForceShowIcon(true);
                } catch (Exception e) {
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.editshift:
                                //handle menu1 click
                                android.app.AlertDialog.Builder mBuild = new android.app.AlertDialog.Builder(context);
                                View mView = activity.getLayoutInflater().inflate(R.layout.fragment_shift, null);
                                TextInputEditText edtstart = (TextInputEditText) mView.findViewById(R.id.edt_time_shift_start);
                                TextInputEditText edtend = (TextInputEditText) mView.findViewById(R.id.edt_time_shift_end);
                                Button btnSubmit = (Button) mView.findViewById(R.id.btnSubShift);

                                edtstart.setInputType(InputType.TYPE_NULL);
                                edtstart.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        Calendar mcurrentTime = Calendar.getInstance();
                                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                        int minute = mcurrentTime.get(Calendar.MINUTE);
                                        TimePickerDialog mTimePicker;
                                        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                edtstart.setText(selectedHour + ":" + selectedMinute);
                                            }
                                        }, hour, minute, true);//Yes 24 hour time
                                        mTimePicker.setTitle("Select Time");
                                        mTimePicker.show();

                                    }
                                });

                                edtend.setInputType(InputType.TYPE_NULL);
                                edtend.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO Auto-generated method stub
                                        Calendar mcurrentTime = Calendar.getInstance();
                                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                        int minute = mcurrentTime.get(Calendar.MINUTE);
                                        TimePickerDialog mTimePicker;
                                        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                                edtend.setText(selectedHour + ":" + selectedMinute);
                                            }
                                        }, hour, minute, true);//Yes 24 hour time
                                        mTimePicker.setTitle("Select Time");
                                        mTimePicker.show();

                                    }
                                });
                                mBuild.setView(mView);
                                android.app.AlertDialog dialog = mBuild.create();
                                dialog.show();

                                btnSubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (edtstart.getText().toString().isEmpty() || edtend.getText().toString().isEmpty()) {
                                            Toast.makeText(context, "لطفا زمان مورد نظر را انتخاب کنید", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String[] partsstart = edtstart.getText().toString().split(":");
                                            int h1 = Integer.parseInt(partsstart[0]);
                                            int m1 = Integer.parseInt(partsstart[1]);
                                            String[] partsend = edtend.getText().toString().split(":");
                                            int h2 = Integer.parseInt(partsend[0]);
                                            int m2 = Integer.parseInt(partsend[1]);
                Log.e("newtime", "h1="+h1+"/"+"h2="+h2+"/"+"m1="+m1+"/"+"m2="+m2);
                                            Call<MessageSignup> upgradeshift = RI.upgradeshift(BasicActivity.content, BasicActivity.userid, shiftinfo.get_id(), h1,h2,m1,m2,  BasicActivity.number);
                                            upgradeshift.enqueue(new Callback<MessageSignup>() {
                                                @Override
                                                public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                                    dialog.dismiss();
                                                    ActivityShift.getshift(context,activity);
                                                }

                                                @Override
                                                public void onFailure(Call<MessageSignup> call, Throwable t) {

                                                }
                                            });

                                        }
                                    }
                                });


                                break;

                            case R.id.deleteshift:
                                Call<MessageSignup> upgradeshift = RI.deleteshift(BasicActivity.content, BasicActivity.userid, shiftinfo.get_id(), BasicActivity.number);
                                upgradeshift.enqueue(new Callback<MessageSignup>() {
                                    @Override
                                    public void onResponse(Call<MessageSignup> call, Response<MessageSignup> response) {
                                        ActivityShift.getshift(context,activity);


                                    }

                                    @Override
                                    public void onFailure(Call<MessageSignup> call, Throwable t) {

                                    }
                                });
                                break;
                        }
                        return true;
                    }
                });
                popup.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return shiftinfos.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {


        LinearLayout linearShift;
        TextView txtstart;
        TextView txtend;


        public ChatViewHolder(View itemView) {
            super(itemView);
            txtstart = (TextView) itemView.findViewById(R.id.txt_shift_start);
            txtend = (TextView) itemView.findViewById(R.id.txt_shift_end);
            linearShift = (LinearLayout) itemView.findViewById(R.id.lay_shift_item);
        }


    }
}
