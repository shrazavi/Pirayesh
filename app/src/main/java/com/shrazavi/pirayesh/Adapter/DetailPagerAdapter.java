package com.shrazavi.pirayesh.Adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.DataClass.DayReserve;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Repitem;
import com.shrazavi.pirayesh.DataClass.Reserve;
import com.shrazavi.pirayesh.DataClass.Visitday;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<Visitday> Arr;
    ArrayList<String> arrtime;
    LayoutInflater mLayoutInflater;
    public static RecyclerView rcyclrday;
    public static int idcat;
    TextView txttitle;
    LinearLayoutManager linearLayoutManager;
    ArrayList<DayReserve> reserveItems = new ArrayList<>();
    Retrofitinformation RInode;
    static Handler threadHandler;
    Thread thread,thread2;
    public static RecyclerAdapterVisit recyclerAdapterVisit;
    // =======================================================================
    // METHOD : AnswerPagerAdapter
    // =======================================================================
    public DetailPagerAdapter(Context context, ArrayList<Visitday> arr,ArrayList<String> arrtime) {
        mContext = context;
        Arr = arr;
        this.arrtime=arrtime;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // =======================================================================
    // METHOD : getItemPosition
    // =======================================================================
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }



    // =======================================================================
    // METHOD : instantiateItem
    // =======================================================================
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        View view = mLayoutInflater.inflate(R.layout.multipager_detail_cell, null);
        rcyclrday = (RecyclerView) view.findViewById(R.id.rec_pager);
        linearLayoutManager = new LinearLayoutManager(mContext);
        rcyclrday.setHasFixedSize(true);
        rcyclrday.setLayoutManager(linearLayoutManager);
//        rcyclrday.setAdapter(ActivityVisit.recyclerAdapterVisit = new RecyclerAdapterVisit(ActivityVisit.reserveItems,mContext));
        Visitday cat = Arr.get(position);
//        idcat = Arr.get(position).getId();
        Call<ArrayList<Nobat>> callreserve = RInode.getreserve(BasicActivity.content,cat.getYear(), cat.getMonth(),cat.getDay(), BasicActivity.userid, BasicActivity.number);
        callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
            @Override
            public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {

                ArrayList<Nobat> arrnobat = new ArrayList<>();
//                arrnobat.clear();
//                reserveItems.clear();

                arrnobat = response.body();
//                                Log.e("nobat =", arrnobat.size()+ "");
                if (arrnobat.size() == 0) {
                    reserveItems.clear();

//                                    ActivityVisit.btnvacation.setVisibility(View.VISIBLE);
                    for (String t : arrtime) {
                        DayReserve day=new DayReserve();
                        day.setTime(t);
//                        day.setUsers("");
                        reserveItems.add(day);

                    }
                    rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext,""));
                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
//                                    rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                    recyclerAdapterVisit.notifyDataSetChanged();
//                                    rcyclrday.scrollToPosition(0);
                } else {
//                                    ActivityVisit.btnvacation.setVisibility(View.GONE);

//                    ArrayList<Repitem> arrrep = new ArrayList<>();
//                    for (Nobat i : arrnobat) {
//                        arrrep.add(getrepeat(arrnobat, i.getTime()));
//                    }
//                    reserveItems = setnobat(arrtime, arrrep);
//                    Log.e("nobat =", reserveItems.size()+ "");
//                    rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext,""));
//                    recyclerAdapterVisit.notifyDataSetChanged();
////                                    rcyclrday.scrollToPosition(0);


                }
//                                rcyclrday.setAdapter(recyclerAdapterVisit = new RecyclerAdapterVisit(reserveItems,mContext));
//                                recyclerAdapterVisit.notifyDataSetChanged();
//                                rcyclrday.scrollToPosition(0);
            }
            @Override
            public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

            }
        });

        threadHandler = new Handler();
//                RI = RetrofitFactory.getclient().create(Retrofitinformation.class);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {

//                                    getkala(id);
//                                    dayItems.add(new Visitday(Visitday.TYPE.MORE.PROGRESS));

                    }
                });
            }

        });

        thread.start();
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread2.sleep(1000);
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
//                                    getkala(id);


                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

        thread2.start();
        if(cat.getType() == Visitday.TYPE.PROGRESS) {
            view.findViewById(R.id.content_layout).setVisibility(View.GONE);
            view.findViewById(R.id.loading_layout).setVisibility(View.VISIBLE);

        } else {
            view.findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.loading_layout).setVisibility(View.GONE);
            txttitle = (TextView) view.findViewById(R.id.txt_pager);
//            PersianCalendar pc=new PersianCalendar();
//            pc.setPersianDate(cat.getYear(),cat.getMonth(),cat.getDay());
//            String week= pc.getPersianWeekDayName();
            txttitle.setText((cat.getDay()+"/"+cat.getMonth()+"/"+cat.getYear()+" "+cat.getWeek()));
//            txtname.setText(cat.getName());
//            txtname.setTypeface(G.category);

            // ((TextView) view.findViewById(R.id.txt_fruit_name)).setText(cat.getName());
            //  ((TextView) view.findViewById(R.id.txt_fruit_no)).setText("" + cat.getId());
//            ((TextView) view.findViewById(R.id.txt_fruit_no)).setText("4.5");
            //txtPostTitle.setTypeface(G.faceBold);

            //   ((TextView) view.findViewById(R.id.txt_comment_cnt)).setText((100 + new Random().nextInt(99)) + "");
            // ((TextView) view.findViewById(R.id.txt_like_cnt)).setText((100 + new Random().nextInt(99)) + "");

            //    ImageView profile = (ImageView) view.findViewById(R.id.img_profile);

            //   Glide.with(mContext).load(fruit.getThumb()).into(profile);
            //    Log.i("TEST", "thumb" + fruit.getThumb());
//            String s = cat.getDescription();
//            StringTokenizer st = new StringTokenizer(s, "|");
//            String urlcat = G.phpurl+"/"+ cat.getImg();
//            ((TextView) view.findViewById(R.id.txt_desc)).setText(desc);
//            ImageView contentThumb = (ImageView) view.findViewById(R.id.content_thumb);
//            Picasso.with(G.context).load(urlcat).transform(new CircleTransform()).into(contentThumb);

//            Glide.with(mContext).load(urlcat).into(contentThumb);
        }



        container.addView(view);
        return view;
    }

    // =======================================================================
    // METHOD : destroyItem
    // =======================================================================
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    // =======================================================================
    // METHOD : getCount
    // =======================================================================
    @Override
    public int getCount() {
        return Arr.size();
    }

    // =======================================================================
    // METHOD : isViewFromObject
    // =======================================================================
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }



//    public ArrayList<DayReserve> setnobat(ArrayList<String> arrtime,ArrayList<Repitem> arrnobat){
//        ArrayList<DayReserve> reservearr=new ArrayList<>();
//        ArrayList<Reserve> resarr=new ArrayList<>();
//
//        for (String t : arrtime) {
//            DayReserve reserve=new DayReserve();
//            reserve.setTime(t);
//            for (Repitem r : arrnobat) {
//
//                if(r.getTime().equals(t)){
//
//                    reserve.setUsers(r.getUser());
//
//                }else {
//                    reserve.setUsers("");
//                }
//
//            }
//
//
//            reservearr.add(reserve);
//        }
//
//        return reservearr;
//    }
//    public Repitem getrepeat(ArrayList<Nobat> rep, String time) {
//        Repitem report = new Repitem();
//        String user="";
//        int tedad = 0;
////        String time = "";
//        for (Nobat i : rep) {
//
//            if (i.getTime().equals(time)) {
//
//                tedad++;
//                user = user +"/"+ i.getUser();
//
//
//            }
//        }
//
//        if (tedad != 0) {
//            report.setTime(time);
//            report.setTedad(tedad);
//            report.setUser(user);
//        }
//
//
//        return report;
//    }
}
