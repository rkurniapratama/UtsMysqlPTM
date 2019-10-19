package com.rizal.utsmysql.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
    private Button btnCari;
    private LinearLayoutManager mLayoutManager;
    private RecycleViewAdapterMahasiswa rvListMahasiswaAdapter;
    private RecyclerView rvListMahasiswa;
    private FloatingActionButton fabTambah;
    private List<MahasiswaModel> dataMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        commonView = new CommonView(mContext);
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
        etCari = (EditText) findViewById(R.id.etCari);
        btnCari = (Button) findViewById(R.id.btnCari);
        mLayoutManager = new LinearLayoutManager(mContext);
        rvListMahasiswaAdapter = new RecycleViewAdapterMahasiswa(mContext, dataMahasiswa);
        rvListMahasiswa = (RecyclerView) findViewById(R.id.rvListMahasiswa);
        rvListMahasiswa.setLayoutManager(mLayoutManager);
        rvListMahasiswa.setAdapter(rvListMahasiswaAdapter);
        fabTambah = (FloatingActionButton) findViewById(R.id.fabTambah);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddMahasiswaActivity.class);
                startActivity(intent);
            }
        });

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MahasiswaRequestModel param = new MahasiswaRequestModel();
                param.setSearch(etCari.getText().toString());
                sendGetSearchMahasiswa(param);
            }
        });

        MahasiswaRequestModel param = new MahasiswaRequestModel();
        param.setSearch(etCari.getText().toString());
        sendGetSearchMahasiswa(param);
    }

    private void sendGetSearchMahasiswa(MahasiswaRequestModel param) {
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
}
