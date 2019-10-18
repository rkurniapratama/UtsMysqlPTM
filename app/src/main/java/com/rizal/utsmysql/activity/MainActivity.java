package com.rizal.utsmysql.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rizal.utsmysql.R;
import com.rizal.utsmysql.api.BaseApiService;
import com.rizal.utsmysql.api.UtilsApi;
import com.rizal.utsmysql.helper.CommonView;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private CommonView commonView;
    private BaseApiService mApiService;
    private EditText etCari;
    private Button btnCari;
    private RecyclerView rvListMahasiswa;
    private FloatingActionButton fabTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        commonView = new CommonView(mContext);
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
        etCari = (EditText) findViewById(R.id.etCari);
        btnCari = (Button) findViewById(R.id.btnCari);
        rvListMahasiswa = (RecyclerView) findViewById(R.id.rvListMahasiswa);
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

            }
        });
    }
}
