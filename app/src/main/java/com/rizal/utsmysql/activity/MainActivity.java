package com.rizal.utsmysql.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rizal.utsmysql.R;
import com.rizal.utsmysql.adapter.RecycleViewAdapterMahasiswa;
import com.rizal.utsmysql.api.BaseApiService;
import com.rizal.utsmysql.api.UtilsApi;
import com.rizal.utsmysql.helper.CommonView;
import com.rizal.utsmysql.model.request.MahasiswaRequestModel;
import com.rizal.utsmysql.model.response.MahasiswaModel;
import com.rizal.utsmysql.model.response.MahasiswaResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private CommonView commonView;
    private BaseApiService mApiService;
    private EditText etCari;
//    private Button btnCari;
    private LinearLayoutManager mLayoutManager;
    private RecycleViewAdapterMahasiswa rvListMahasiswaAdapter;
    private RecyclerView rvListMahasiswa;
    private FloatingActionButton fabTambah;
    private List<MahasiswaModel> dataMahasiswa;
    private Handler handlerAutoContainer;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        commonView = new CommonView(mContext);
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
        etCari = (EditText) findViewById(R.id.etCari);
//        btnCari = (Button) findViewById(R.id.btnCari);
        mLayoutManager = new LinearLayoutManager(mContext);
        rvListMahasiswaAdapter = new RecycleViewAdapterMahasiswa(mContext, dataMahasiswa);
        rvListMahasiswa = (RecyclerView) findViewById(R.id.rvListMahasiswa);
        rvListMahasiswa.setLayoutManager(mLayoutManager);
        rvListMahasiswa.setAdapter(rvListMahasiswaAdapter);
        fabTambah = (FloatingActionButton) findViewById(R.id.fabTambah);

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handlerAutoContainer.removeMessages(TRIGGER_AUTO_COMPLETE);
                handlerAutoContainer.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        handlerAutoContainer = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    MahasiswaRequestModel param = new MahasiswaRequestModel();
                    param.setSearch(etCari.getText().toString());
                    sendGetSearchMahasiswa(param);
                }
                return false;
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddMahasiswaActivity.class);
                startActivity(intent);
            }
        });

//        btnCari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MahasiswaRequestModel param = new MahasiswaRequestModel();
//                param.setSearch(etCari.getText().toString());
//                sendGetSearchMahasiswa(param);
//            }
//        });

        MahasiswaRequestModel param = new MahasiswaRequestModel();
        param.setSearch(etCari.getText().toString());
        sendGetSearchMahasiswa(param);
    }

    private void sendGetSearchMahasiswa(MahasiswaRequestModel param) {
        if(UtilsApi.isNetworkAvailable(mContext)) {
            commonView.startProgressBarNonCancelable("Mohon tunggu...");
            mApiService.sendGetSearchMahasiswa(param)
                    .enqueue(new Callback<MahasiswaResponseModel>() {
                        @Override
                        public void onResponse(Call<MahasiswaResponseModel> call, Response<MahasiswaResponseModel> response) {
                            commonView.stopProgressBar();
                            if(response.isSuccessful()) {
                                if(response.body().getStatus().equalsIgnoreCase("S")) {
                                    dataMahasiswa = response.body().getData_list();
                                    rvListMahasiswaAdapter.refreshAll(dataMahasiswa);
                                }
                                else {
                                    commonView.popUp(response.body().getMessage());
                                }
                            }
                            else {
                                commonView.popUp(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<MahasiswaResponseModel> call, Throwable t) {
                            commonView.stopProgressBar();
                            commonView.popUp(t.getMessage());
                        }
                    });
        }
        else {
            commonView.popUp("Mohon cek koneksi internet anda");
        }
    }
}
