package com.rizal.utsmysql.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.rizal.utsmysql.R;
import com.rizal.utsmysql.api.BaseApiService;
import com.rizal.utsmysql.api.UtilsApi;
import com.rizal.utsmysql.helper.CommonView;
import com.rizal.utsmysql.model.request.MahasiswaRequestModel;
import com.rizal.utsmysql.model.response.ReturnFirstMahasiswaResponseModel;
import com.rizal.utsmysql.model.response.ReturnMahasiswaResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMahasiswaActivity extends AppCompatActivity {
    private Context mContext;
    private CommonView commonView;
    private BaseApiService mApiService;
    private EditText etNbi, etNama, etAlamat, etTglLahir;
    private Spinner spProdi, spAgama;
    private RadioGroup rgJnsKelamin;
    private RadioButton checkedRadioButton;
    private CheckBox cbBermainBola, cbMembacaBuku, cbBermainGitar, cbMemasak, cbBermainGame;
    private Button btnSimpan, btnReset;
    private int insertOrUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa);

        mContext = this;
        commonView = new CommonView(mContext);
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
        etNbi = (EditText) findViewById(R.id.etNbi);
        etNama = (EditText) findViewById(R.id.etNama);
        etAlamat = (EditText) findViewById(R.id.etAlamat);
        etTglLahir = (EditText) findViewById(R.id.etTglLahir);
        spProdi = (Spinner) findViewById(R.id.spProdi);
        spAgama = (Spinner) findViewById(R.id.spAgama);
        rgJnsKelamin = (RadioGroup) findViewById(R.id.rgJnsKelamin);
        cbBermainBola = (CheckBox) findViewById(R.id.cbBermainBola);
        cbMembacaBuku = (CheckBox) findViewById(R.id.cbMembacaBuku);
        cbBermainGitar = (CheckBox) findViewById(R.id.cbBermainGitar);
        cbMemasak = (CheckBox) findViewById(R.id.cbMemasak);
        cbBermainGame = (CheckBox) findViewById(R.id.cbBermainGame);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnReset = (Button) findViewById(R.id.btnReset);

        initStartActivity();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rgJnsKelaminId = rgJnsKelamin.getCheckedRadioButtonId();
                if(rgJnsKelaminId != -1) checkedRadioButton = (RadioButton) findViewById(rgJnsKelaminId);

                List<String> listHobi = new ArrayList<>();
                if(cbBermainBola.isChecked()) listHobi.add(cbBermainBola.getText().toString());
                if(cbMembacaBuku.isChecked()) listHobi.add(cbMembacaBuku.getText().toString());
                if(cbBermainGitar.isChecked()) listHobi.add(cbBermainGitar.getText().toString());
                if(cbMemasak.isChecked()) listHobi.add(cbMemasak.getText().toString());
                if(cbBermainGame.isChecked()) listHobi.add(cbBermainGame.getText().toString());

                if(etNbi.getText().toString().isEmpty() || etNama.getText().toString().isEmpty() || etAlamat.getText().toString().isEmpty() ||
                        etTglLahir.getText().toString().isEmpty() || spProdi.getSelectedItem().toString().equals("Pilih Prodi") || spAgama.getSelectedItem().toString().equals("Pilih Agama") ||
                        listHobi.isEmpty() || rgJnsKelaminId == -1) {
                    commonView.popUp("Mohon lengkapi form");
                }
                else {
                    MahasiswaRequestModel param = new MahasiswaRequestModel(
                            etNbi.getText().toString(),
                            etNama.getText().toString(),
                            etAlamat.getText().toString(),
                            etTglLahir.getText().toString(),
                            spProdi.getSelectedItem().toString(),
                            checkedRadioButton.getText().toString(),
                            spAgama.getSelectedItem().toString(),
                            listHobi
                    );

                    if(insertOrUpdate == 0) {
                        Log.d("check insert", param.toString());
//                        sendInsertMahasiswa(param);
                    }
                    else {
                        Log.d("check update", param.toString());
//                        sendUpdateMahasiswa(param);
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(insertOrUpdate == 0) {
                    resetForm();
                }
                else {
                    sendGetByNbiMahasiswa(etNbi.getText().toString());
                }
            }
        });
    }

    private void initStartActivity() {
        Bundle b = getIntent().getExtras();

        if(b != null) {
            insertOrUpdate = 1;
            btnSimpan.setText("Edit");
            commonView.enableDisableEditText(false, etNbi);
            sendGetByNbiMahasiswa(b.getString("nbi"));
        }
    }

    private void sendInsertMahasiswa(MahasiswaRequestModel param) {
        if(UtilsApi.isNetworkAvailable(mContext)) {
            commonView.startProgressBarNonCancelable("Mohon tunggu...");
            mApiService.sendInsertMahasiswa(param)
                    .enqueue(new Callback<ReturnMahasiswaResponseModel>() {
                        @Override
                        public void onResponse(Call<ReturnMahasiswaResponseModel> call, Response<ReturnMahasiswaResponseModel> response) {
                            commonView.stopProgressBar();
                            if(response.isSuccessful()) {
                                commonView.popUp(response.body().getMessage());
                                resetForm();
                            }
                            else {
                                commonView.popUp(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ReturnMahasiswaResponseModel> call, Throwable t) {
                            commonView.stopProgressBar();
                            commonView.popUp(t.getMessage());
                        }
                    });
        }
        else {
            commonView.popUp("Mohon cek koneksi internet anda");
        }
    }

    private void sendUpdateMahasiswa(MahasiswaRequestModel param) {
        if(UtilsApi.isNetworkAvailable(mContext)) {
            commonView.startProgressBarNonCancelable("Mohon tunggu...");
            mApiService.sendUpdateMahasiswa(param)
                    .enqueue(new Callback<ReturnMahasiswaResponseModel>() {
                        @Override
                        public void onResponse(Call<ReturnMahasiswaResponseModel> call, Response<ReturnMahasiswaResponseModel> response) {
                            commonView.stopProgressBar();
                            if(response.isSuccessful()) {
                                commonView.popUp(response.body().getMessage());
                                resetForm();
                            }
                            else {
                                commonView.popUp(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ReturnMahasiswaResponseModel> call, Throwable t) {
                            commonView.stopProgressBar();
                            commonView.popUp(t.getMessage());
                        }
                    });
        }
        else {
            commonView.popUp("Mohon cek koneksi internet anda");
        }
    }

    private void sendGetByNbiMahasiswa(String param) {
        if(UtilsApi.isNetworkAvailable(mContext)) {
            commonView.startProgressBarNonCancelable("Mohon tunggu...");
            mApiService.sendGetByNbiMahasiswa(param)
                    .enqueue(new Callback<ReturnFirstMahasiswaResponseModel>() {
                        @Override
                        public void onResponse(Call<ReturnFirstMahasiswaResponseModel> call, Response<ReturnFirstMahasiswaResponseModel> response) {
                            commonView.stopProgressBar();
                            if(response.isSuccessful()) {
                                if(response.body().getStatus().equalsIgnoreCase("S")) {
                                    etNbi.setText(response.body().getData().getNbi());
                                    etNama.setText(response.body().getData().getNama());
                                    etAlamat.setText(response.body().getData().getAlamat());
                                    etTglLahir.setText(response.body().getData().getTgl_lahir());
                                    spProdi.setSelection(commonView.getIndexSpinner(spProdi, response.body().getData().getProdi()));
                                    spAgama.setSelection(commonView.getIndexSpinner(spAgama, response.body().getData().getAgama()));
                                    if(response.body().getData().getKelamin().equalsIgnoreCase("Laki-Laki")) {
                                        ((RadioButton)rgJnsKelamin.getChildAt(0)).setChecked(true);
                                    }
                                    else {
                                        ((RadioButton)rgJnsKelamin.getChildAt(1)).setChecked(true);
                                    }
                                    for(String item : response.body().getData().getHobi()) {
                                        if(item.equals("Bermain Bola")) cbBermainBola.setChecked(true);
                                        else if(item.equals("Membaca Buku")) cbMembacaBuku.setChecked(true);
                                        else if(item.equals("Bermain Gitar")) cbBermainGitar.setChecked(true);
                                        else if(item.equals("Memasak")) cbMemasak.setChecked(true);
                                        else if(item.equals("Bermain Game")) cbBermainGame.setChecked(true);
                                    }
                                }
                                commonView.popUp(response.body().getMessage());
                            }
                            else {
                                commonView.popUp(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ReturnFirstMahasiswaResponseModel> call, Throwable t) {
                            commonView.stopProgressBar();
                            commonView.popUp(t.getMessage());
                        }
                    });

        }
        else {
            commonView.popUp("Mohon cek koneksi internet anda");
        }
    }

    private void resetForm() {
        etNbi.setText("");
        etNama.setText("");
        etAlamat.setText("");
        etTglLahir.setText("");
        rgJnsKelamin.clearCheck();
        cbBermainBola.setChecked(false);
        cbMembacaBuku.setChecked(false);
        cbBermainGitar.setChecked(false);
        cbMemasak.setChecked(false);
        cbBermainGame.setChecked(false);
    }
}
