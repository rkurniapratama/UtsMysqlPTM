package com.rizal.utsmysql.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.rizal.utsmysql.R;
import com.rizal.utsmysql.api.BaseApiService;
import com.rizal.utsmysql.api.UtilsApi;
import com.rizal.utsmysql.helper.CommonView;

public class DetailMahasiswaActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private CommonView commonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);

        mContext = this;
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
        commonView = new CommonView(mContext);
    }
}
