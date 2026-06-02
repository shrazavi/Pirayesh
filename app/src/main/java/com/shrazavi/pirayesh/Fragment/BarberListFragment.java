package com.shrazavi.pirayesh.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import com.shrazavi.pirayesh.Activity.ActivityLocation;
import com.shrazavi.pirayesh.Activity.BasicActivity;
import com.shrazavi.pirayesh.Adapter.RecyclerAdapterBarber;
import com.shrazavi.pirayesh.Adapter.spinnerAdapter;
import com.shrazavi.pirayesh.DataClass.Barber;
import com.shrazavi.pirayesh.DataClass.Ostan;
import com.shrazavi.pirayesh.DataClass.Shahrestan;
import com.shrazavi.pirayesh.G;
import com.shrazavi.pirayesh.R;
import com.shrazavi.pirayesh.RetrofitFactory;
import com.shrazavi.pirayesh.RetrofitFactorynode;
import com.shrazavi.pirayesh.Retrofitinformation;


import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarberListFragment extends Fragment {
    ConstraintLayout lay;
    String[] lawyerlist;
    public static AutoCompleteTextView edtsearch, edtfilterostan, edtfiltershahr;
    int id_ostan = 0;
    int id_shahrestan = 0;
    RecyclerView recyclerView;
    String url = G.phpurl + "/getimageprofile.php";
    String company;
    Retrofitinformation RI;
    Retrofitinformation RInode;
    String content;
    String enk;
    SecretKeySpec Key;
    ArrayList<Barber> infos;
    //    public SharedPreferences preferences;
    public String ostan, shahr;
    public static Context context;
    String bu;
    public static TextInputLayout laysearch, layfilterostan, layfiltershahr;
    public String userid = "";
    public static SharedPreferences sharedPreferences;
    public RecyclerAdapterBarber recyclerAdapterbarber;


    LinearLayoutManager linearLayoutManager;

    public BarberListFragment() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RInode = RetrofitFactorynode.getclient().create(Retrofitinformation.class);
        RI = RetrofitFactory.getclient().create(Retrofitinformation.class);
        View myFragmentView = inflater.inflate(R.layout.fragment_barber, container, false);
        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.recycler_barber);
        laysearch = (TextInputLayout) myFragmentView.findViewById(R.id.lay_edt_search);
        layfilterostan = (TextInputLayout) myFragmentView.findViewById(R.id.lay_edt_filter_ostan);
        layfiltershahr = (TextInputLayout) myFragmentView.findViewById(R.id.lay_edt_filter_shahr);
        edtsearch = (AutoCompleteTextView) myFragmentView.findViewById(R.id.edt_search);
        edtfilterostan = (AutoCompleteTextView) myFragmentView.findViewById(R.id.edt_filter_ostan);
        edtfiltershahr = (AutoCompleteTextView) myFragmentView.findViewById(R.id.edt_filter_shahr);
        lay = (ConstraintLayout) myFragmentView.findViewById(R.id.lay_fr_barberlist);
        recyclerView.setLayoutManager(new GridLayoutManager(G.context, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        infos = new ArrayList<>();
//        preferences = PreferenceManager.getDefaultSharedPreferences(G.context);
        bu = BasicActivity.bu;
        userid = BasicActivity.userid;
        content = BasicActivity.content;
        if (BasicActivity.sx.equals("male")) {
            lay.setBackgroundResource(R.drawable.gradient);
        }else {
            lay.setBackgroundResource(R.drawable.gradient2);
        }
//        enk = preferences.getString("k5", "not");
//        company = getResources().getString(R.string.developer);
//        byte[] data = Base64.decode(enk, Base64.NO_WRAP);
//        Key = new SecretKeySpec(data, 0, data.length, "AES");
//        content = Base64.encodeToString(SymmetricAlgorithmAES.encryption(Key, vu+"-"+userid+"-"+R.string.developer), Base64.NO_WRAP);
        layfilterostan.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        layfilterostan.setBoxStrokeColor(getResources().getColor(R.color.white));
        edtfilterostan.setVisibility(View.GONE);
        layfilterostan.setVisibility(View.GONE);
        edtfilterostan.setThreshold(1);

        layfiltershahr.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        layfiltershahr.setBoxStrokeColor(getResources().getColor(R.color.white));
        edtfiltershahr.setVisibility(View.GONE);
        layfiltershahr.setVisibility(View.GONE);
        edtfiltershahr.setThreshold(1);

        laysearch.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        laysearch.setBoxStrokeColor(getResources().getColor(R.color.white));
        edtsearch.setVisibility(View.GONE);
        laysearch.setVisibility(View.GONE);
        edtsearch.setThreshold(1);

        Call<ArrayList<Barber>> callallbarber = RInode.getallbarbers(content, BasicActivity.number);
        callallbarber.enqueue(new Callback<ArrayList<Barber>>() {
            @Override
            public void onResponse(Call<ArrayList<Barber>> call, Response<ArrayList<Barber>> response) {

                infos = response.body();
                recyclerView.setAdapter(recyclerAdapterbarber = new RecyclerAdapterBarber(infos, G.context));
                recyclerAdapterbarber.notifyDataSetChanged();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line);
                for (int i = 0; i < response.body().size(); i++) {
                    adapter.add(response.body().get(i).getTitle());
                }
//                adapter.add("");
                edtsearch.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Barber>> call, Throwable t) {
                Log.e("Barber=", t + "");
            }
        });


        Call<ArrayList<Ostan>> callostan = RI.getostan();
        callostan.enqueue(new Callback<ArrayList<Ostan>>() {
            @Override
            public void onResponse(Call<ArrayList<Ostan>> call, Response<ArrayList<Ostan>> response) {
                spinnerAdapter Adapterostan = new spinnerAdapter(G.context, android.R.layout.simple_list_item_1);
                for (int i = 0; i < response.body().size(); i++) {
                    Adapterostan.add(response.body().get(i).getName());
                }
                Adapterostan.add("");
//                Adapterostan.add("استان");
                edtfilterostan.setAdapter(Adapterostan);
//                spinostan.setSelection(Adapterostan.getCount());


            }

            @Override
            public void onFailure(Call<ArrayList<Ostan>> call, Throwable t) {

            }
        });
        edtfilterostan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtfilterostan.clearListSelection();
//                edtfilterostan.setFocusableInTouchMode(true);
//                edtfiltershahr.clearListSelection();
//                edtfiltershahr.setFocusableInTouchMode(true);
//                edtfilterostan.requestFocus();
                edtfilterostan.showDropDown();
            }
        });
        edtfiltershahr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edtfiltershahr.clearListSelection();
//                edtfiltershahr.setFocusableInTouchMode(true);
//                edtfiltershahr.requestFocus();
                edtfiltershahr.showDropDown();
            }
        });
//        BarberListFragment.edtfilterostan.showDropDown();
//        BarberListFragment.edtfiltershahr.showDropDown();
        edtfilterostan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.e("getSelectionStart", spinostan.getSelectionStart() + "");
//        Log.e("getSelectionEnd", spinostan.getSelectionEnd() + "");
//        Log.e("getListSelection", spinostan.getListSelection() + "");
                Log.e("id ostan", id_ostan + "");
                edtfiltershahr.setText("");
                id_ostan = position + 1;
//                Log.e("id ostan", id_ostan + "");
                edtfiltershahr.requestFocus();
                edtfiltershahr.setFocusableInTouchMode(true);
                Call<ArrayList<Shahrestan>> callshahr = RI.getshahr(id_ostan);
                callshahr.enqueue(new Callback<ArrayList<Shahrestan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Shahrestan>> call, Response<ArrayList<Shahrestan>> response) {
                        spinnerAdapter Adaptershar = new spinnerAdapter(G.context, android.R.layout.simple_list_item_1);
                        for (int i = 0; i < response.body().size(); i++) {
                            Adaptershar.add(response.body().get(i).getName());
                        }
                        Adaptershar.add("");

//                Adaptershar.add("شهر");
                        edtfiltershahr.setAdapter(Adaptershar);


                    }

                    @Override
                    public void onFailure(Call<ArrayList<Shahrestan>> call, Throwable t) {

                    }
                });
            }
        });

        edtfiltershahr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String shahrestan = edtfiltershahr.getText().toString();
                Log.e("shahrname", shahrestan);
                Call<Shahrestan> calluser = RI.getidshahr(shahrestan);
                calluser.enqueue(new Callback<Shahrestan>() {
                    @Override
                    public void onResponse(Call<Shahrestan> call, Response<Shahrestan> response) {

                        id_shahrestan = Integer.parseInt(response.body().getId());
                        shahr = response.body().getName();
                        Call<Ostan> callostan = RI.getnameostan(Integer.parseInt(response.body().getId_ostan()));
                        callostan.enqueue(new Callback<Ostan>() {
                            @Override
                            public void onResponse(Call<Ostan> call, Response<Ostan> response) {

                                ostan = response.body().getName();
                                Log.e("shahrid", shahr + "/" + ostan);
                                Call<ArrayList<Barber>> callbarbers = RInode.getbarbers(content, ostan, shahr, BasicActivity.number);
                                callbarbers.enqueue(new Callback<ArrayList<Barber>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<Barber>> call, Response<ArrayList<Barber>> response) {

                                        infos = response.body();
                                        recyclerView.setAdapter(recyclerAdapterbarber = new RecyclerAdapterBarber(infos, G.context));
                                        recyclerAdapterbarber.notifyDataSetChanged();
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                        android.R.layout.simple_dropdown_item_1line);
//                for (int i = 0; i < response.body().size(); i++) {
//                    adapter.add(response.body().get(i).getName());
//                }
//                adapter.add("");
//                edtsearch.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<Barber>> call, Throwable t) {
                                        Log.e("Barber=", t + "");
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Ostan> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Shahrestan> call, Throwable t) {
                        Log.e("shahrid", t + "");
                    }
                });
            }
        });

        edtsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int posi = getItemPosition(edtsearch.getText().toString());
                if (posi >= 0) {
                    recyclerView.scrollToPosition(posi);
                }
            }
        });
//        content = SymmetricAlgorithmAES.encrypt(vu + "-" + userid + "-" + company, enk);

//        Log.e("enk1", enk);
//        Log.e("content1", content + "");


//        recyclerView.setAdapter(recyclerAdapterlawyer = new RecyclerAdapterlawyer(infos, G.context));
        return myFragmentView;
//        Log.e("sqluser",indfos+"");
    }

    public boolean isConnected() {
        ConnectivityManager connect = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connect != null) {
            NetworkInfo[] information = connect.getAllNetworkInfo();
            if (information != null) {
                for (int x = 0; x < information.length; x++) {
                    if (information[x].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
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


    }

    public int getItemPosition(String eventId) {
        for (int i = 0; i < infos.size(); i++) {
            if (infos.get(i).getName().equals(eventId)) {
                return i;
            }
        }
        return -1;
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
