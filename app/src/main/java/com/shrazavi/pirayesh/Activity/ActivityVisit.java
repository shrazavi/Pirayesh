package com.shrazavi.pirayesh.Activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.sardari.daterangepicker.customviews.DateRangeCalendarView;
import com.sardari.daterangepicker.dialog.DatePickerDialog;

import com.sardari.daterangepicker.utils.PersianCalendar;
import com.shrazavi.pirayesh.Adapter.DetailPagerAdapter;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterReserve;


import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Nobat;
import com.shrazavi.pirayesh.DataClass.Visitday;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;
import com.shrazavi.pirayesh.Util.ScaleLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;

public class ActivityVisit extends AppCompatActivity {
    private MaterialProgressBar progressBar;
    //    public static ArrayList<DayReserve> reserveItems = new ArrayList<>();
    public static ArrayList<Visitday> dayItems = new ArrayList<>();
    public static ArrayList<String> arrtime = new ArrayList<>();
    Button btnsettime, btnvacation, btnback;

RecyclerView rec;
    //    private RecyclerAdapterKala mRecyclerAdapter;
    TextView txtprogress,txtwaiting;
    CardView crdprogress;
    Retrofitinformation RInode;
    String content;
    String enk;
    SecretKeySpec Key;
    String username = "";
    String vu = "";
    ArrayList<Visitday> Arr;
    static Handler threadHandler;
    Thread thread, thread2;
    DetailPagerAdapter mAdapter;
    private float MAX_SCALE = 0.0f;
    ViewPager mPager;
    FrameLayout frmlay;
    Retrofitinformation RI;
    final int paddingPx = 10;
    RecyclerAdapterReserve recyclerAdapterReserve;
    LinearLayoutManager linearLayoutManager;
    public ProgressBar prload;
    LinearLayout laybtn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        username = BasicActivity.userid;
        vu = BasicActivity.bu;
        content = BasicActivity.content;

//        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        rec = (RecyclerView) findViewById(R.id.rec_visit);
        mPager = (ViewPager) findViewById(R.id.pager_visit);
        btnsettime = (Button) findViewById(R.id.btn_visit_set_time);
        btnvacation = (Button) findViewById(R.id.btn_visit_vacation);
        btnback = (Button) findViewById(R.id.btn_visit_back);
        crdprogress = (CardView) findViewById(R.id.crd_visit_progress);
        txtprogress = (TextView) findViewById(R.id.txt_visit_progress);
        txtwaiting = (TextView) findViewById(R.id.txt_visit_waiting);
        laybtn = (LinearLayout) findViewById(R.id.lay_visit_btn);
    btnvacation.setVisibility(View.INVISIBLE);
        prload = findViewById(R.id.pr_visit_load);
        prload.setVisibility(View.GONE);
        txtprogress.setVisibility(View.GONE);
        txtwaiting.setVisibility(View.GONE);
        rec.setLayoutManager(new ScaleLayoutManager(ActivityVisit.this,LinearLayout.HORIZONTAL,false));
//        rec.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(1), true));
//        rec.setItemAnimator(new DefaultItemAnimator());

        Call<Barber> callbarber = RInode.getbarber(BasicActivity.content, BasicActivity.userid, BasicActivity.number);
        callbarber.enqueue(new Callback<Barber>() {
            @Override
            public void onResponse(Call<Barber> call, Response<Barber> response) {
                arrtime = response.body().getArraytime();

            }

            @Override
            public void onFailure(Call<Barber> call, Throwable t) {
                Log.e("vakilerr=", "" + t);

            }
        });
        btnvacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnsettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityVisit.this);
                datePickerDialog.setSelectionMode(DateRangeCalendarView.SelectionMode.Range);
//datePickerDialog.setEnableTimePicker(true);
                datePickerDialog.setDisableDaysAgo(false);
//datePickerDialog.setShowGregorianDate(true);
                datePickerDialog.setTextSizeTitle(17.0f);
                datePickerDialog.setTextSizeWeek(17.0f);
                datePickerDialog.setTextSizeDate(20.0f);
//                datePickerDialog.setHeaderBackgroundColor(R.color.red);
                datePickerDialog.setCanceledOnTouchOutside(true);
                datePickerDialog.setOnRangeDateSelectedListener(new DatePickerDialog.OnRangeDateSelectedListener() {
                    @Override
                    public void onRangeDateSelected(PersianCalendar startDate, PersianCalendar endDate) {
                        PersianDate ps = new PersianDate();
                        PersianDate pe = new PersianDate();

                        ps.setShDay(startDate.getPersianDay());
                        ps.setShMonth(startDate.getPersianMonth() + 1);
                        ps.setShYear(startDate.getPersianYear());

                        pe.setShDay(endDate.getPersianDay());
                        pe.setShMonth(endDate.getPersianMonth() + 1);
                        pe.setShYear(endDate.getPersianYear());

                        Calendar cl1 = Calendar.getInstance();
                        Calendar cl2 = Calendar.getInstance();

                        cl1.set(ps.getGrgYear(), ps.getGrgMonth(), ps.getGrgDay());
                        cl2.set(pe.getGrgYear(), pe.getGrgMonth(), pe.getGrgDay());

//                        String startThatDay = ps.getGrgYear()+"/"+ ps.getGrgMonth()+"/"+ ps.getGrgDay();
//                        String endThatDay = pe.getGrgYear()+"/"+ pe.getGrgMonth()+"/"+ pe.getGrgDay();
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        Date start1 = new Date();
                        Date end1 = new Date();
                        start1 = cl1.getTime();
                        end1 = cl2.getTime();

                        dayItems = datesBetween(cl1.getTime(), cl2.getTime());
                        if (dayItems.size() < 7) {
                            Toast.makeText(ActivityVisit.this, "لطفا بازه زمانی بیش از یک هفته انتخاب کنید.", Toast.LENGTH_LONG).show();
                            crdprogress.setVisibility(View.GONE);
                            prload.setVisibility(View.GONE);
                            txtprogress.setVisibility(View.GONE);
                            txtwaiting.setVisibility(View.GONE);

                        } else {
                            laybtn.setVisibility(View.INVISIBLE);
                            prload.setVisibility(View.VISIBLE);
                            txtprogress.setVisibility(View.VISIBLE);
                            txtwaiting.setVisibility(View.VISIBLE);
                            crdprogress.setVisibility(View.VISIBLE);
                            mAdapter = new DetailPagerAdapter(ActivityVisit.this, dayItems, arrtime);
                            mPager.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
//                            rec.setAdapter(recyclerAdapterReserve = new RecyclerAdapterReserve(dayItems,ActivityVisit.this , arrtime));
//                            recyclerAdapterReserve.notifyDataSetChanged();
//                            Log.e("start", cl1.getTime().getDate() + "");
                            new CountDownTimer(dayItems.size() * 1000, 1000) {
                                public void onTick(long millisUntilFinished) {
                                    int i = Integer.parseInt(millisUntilFinished + "");
                                    int percent = 100 / dayItems.size();
                                    int progress = (i / 1000) * percent;
                                    mPager.setCurrentItem(i / 1000);
                                    prload.setProgress(progress);
                                    int pr = 100 - progress;
                                    txtprogress.setText(pr + "%");
//                                    btnvacation.setVisibility(View.GONE);
                                }

                                public void onFinish() {
                                    mPager.setVisibility(View.VISIBLE);
                                    crdprogress.setVisibility(View.GONE);
                                    prload.setVisibility(View.GONE);
                                    laybtn.setVisibility(View.VISIBLE);

                                }
                            }.start();
//                            mPager.setVisibility(View.VISIBLE);
//                                    crdprogress.setVisibility(View.GONE);
//                                    prload.setVisibility(View.GONE);
//                                    laybtn.setVisibility(View.VISIBLE);
                        }

                    }
                });

                datePickerDialog.showDialog();
            }
        });
        mPager.setClipToPadding(false);
        mPager.setPadding(paddingPx, 50, paddingPx, 50);

        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                if (MAX_SCALE == 0.0f && position > 0.0f && position < 1.0f) {
                    MAX_SCALE = position;
                }
                position = position - MAX_SCALE;
                float absolutePosition = Math.abs(position);
                if (position <= -1.0f || position >= 1.0f) {

                    // Page is not visible -- stop any running animations

                }
                if (position == 0.0f) {

                    // Page is selected -- reset any views if necessary
                    page.setScaleX((1 + MAX_SCALE));
                    page.setScaleY((1 + MAX_SCALE));
                    page.setAlpha(1);
                } else {
                    page.setScaleX(1 + MAX_SCALE * (1 - absolutePosition));
                    page.setScaleY(1 + MAX_SCALE * (1 - absolutePosition));

                }
            }
        });
//rec.setOnScrollListener(new RecyclerView.OnScrollListener() {
//    @Override
//    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//    }
//
//    @Override
//    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
////        RecyclerAdapterReserve.recyclerAdapterVisit.notifyDataSetChanged();
//    }
//});
        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction frm =fm.beginTransaction().replace(R.id.frmlay, new Categorylist());
//                frm.commit();
//                Toast.makeText(G.context, "click", Toast.LENGTH_SHORT).show();
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Call<ArrayList<Nobat>> callreserve = RInode.getreserve(content, dayItems.get(position).getYear(), dayItems.get(position).getMonth(), dayItems.get(position).getDay(),BasicActivity.userid, BasicActivity.number);
                callreserve.enqueue(new Callback<ArrayList<Nobat>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Nobat>> call, Response<ArrayList<Nobat>> response) {

                        ArrayList<Nobat> arrnobat = new ArrayList<>();
                        arrnobat.clear();

                        arrnobat = response.body();
//                        mAdapter.recyclerAdapterVisit.notifyDataSetChanged();
                        if (arrnobat.size() == 0) {
                           btnvacation.setVisibility(View.VISIBLE);

                        } else {
//                            DetailPagerAdapter.recyclerAdapterVisit.notifyDataSetChanged();
                            btnvacation.setVisibility(View.INVISIBLE);

                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Nobat>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // mRecyclerAdapter.notifyDataSetChanged();
                Log.i("TEST", "onPageSelected : " + position);

            }

            @Override
            public void onPageSelected(int position) {
                //


//                id = Arr.get(position).getId();
//                urlpost=  G.urllocal+"wp-json/wp/v2/posts?per_page=10&&page=1&&categories=" + id ;
//                postItems.clear();
//                mRecyclerAdapter.notifyDataSetChanged();

//                getBlogContent();

//                if (position == (Arr.size() - 1)) {
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
//                    if (mIsLastItem)
//                        return;

//                    String url =  G.urllocal+"wp-json/wp/v2/categories?per_page=10&&page=" + (++mPage);

//                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new CustomSuccessListener(), new CustomErrorListener());
//                    mQueue.add(stringRequest);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

//        lnrContainerFail = (LinearLayout) myFragmentView.findViewById(R.id.lnrContainerFail1);



    }

    public ArrayList<Visitday> datesBetween(Date start, Date end) {
        ArrayList<Date> arrday = new ArrayList<>();
        ArrayList<Visitday> arrvisit = new ArrayList<>();

        Calendar calstart = Calendar.getInstance();
        Calendar calend = Calendar.getInstance();
//        Log.e("tedad",calstart.getTime().getDate() + "");
        Log.e("tedad", calend.getTime().getDate() + "");

        calstart.setTime(start);
        calend.setTime(end);
        while (calstart.getTime().before(calend.getTime())) {
            calstart.add(Calendar.DATE, 1);
            arrday.add(calstart.getTime());
        }
        for (Date d : arrday) {
            Visitday vd = new Visitday();
            PersianDate pd = new PersianDate();
            pd.setGrgDay(d.getDate());
            pd.setGrgMonth(d.getMonth());
            pd.setGrgYear(d.getYear() + 1900);
            vd.setDay(pd.getShDay());
            vd.setMonth(pd.getShMonth());
            vd.setYear(pd.getShYear());
            PersianCalendar pc = new PersianCalendar();
            pc.setPersianDate(d.getYear(), d.getMonth(), d.getDate());
            vd.setWeek(pc.getPersianWeekDayName());

            arrvisit.add(vd);
//                Log.e("tedad", k.getTedad() + "");

        }


        return arrvisit;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
