package com.rizal.utsmysql.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.rizal.utsmysql.R;
import com.rizal.utsmysql.api.BaseApiService;
import com.rizal.utsmysql.api.UtilsApi;
import com.rizal.utsmysql.helper.CommonView;
import com.rizal.utsmysql.model.request.MahasiswaRequestModel;
import com.rizal.utsmysql.model.response.MahasiswaResponseModel;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMahasiswaActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private CommonView commonView;
    private ImageView ivTextDrawable;
    private TextView tvLabelNbi, tvLabelNama, tvLabelAlamat, tvLabelTglLahir, tvLabelProdi, tvLabelJnsKelamin,
            tvLabelAgama, tvLabelHobi, tvNbi, tvNama, tvAlamat, tvTglLahir, tvProdi, tvJnsKelamin,
            tvAgama, tvHobi;
    private String[] mColors = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);

        mContext = this;
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
        commonView = new CommonView(mContext);
        ivTextDrawable = (ImageView) findViewById(R.id.ivTextDrawable);
        tvLabelNbi = (TextView) findViewById(R.id.tvLabelNbi);
        tvLabelNama = (TextView) findViewById(R.id.tvLabelNama);
        tvLabelAlamat = (TextView) findViewById(R.id.tvLabelAlamat);
        tvLabelTglLahir = (TextView) findViewById(R.id.tvLabelTglLahir);
        tvLabelProdi = (TextView) findViewById(R.id.tvLabelProdi);
        tvLabelJnsKelamin = (TextView) findViewById(R.id.tvLabelJnsKelamin);
        tvLabelAgama = (TextView) findViewById(R.id.tvLabelAgama);
        tvLabelHobi = (TextView) findViewById(R.id.tvLabelHobi);
        tvNbi = (TextView) findViewById(R.id.tvNbi);
        tvNama = (TextView) findViewById(R.id.tvNama);
        tvAlamat = (TextView) findViewById(R.id.tvAlamat);
        tvTglLahir = (TextView) findViewById(R.id.tvTglLahir);
        tvProdi = (TextView) findViewById(R.id.tvProdi);
        tvJnsKelamin = (TextView) findViewById(R.id.tvJnsKelamin);
        tvAgama = (TextView) findViewById(R.id.tvAgama);
        tvHobi = (TextView) findViewById(R.id.tvHobi);

        tvLabelNbi.setText("NBI");
        tvLabelNama.setText("Nama");
        tvLabelAlamat.setText("Alamat");
        tvLabelTglLahir.setText("Tgl Lahir");
        tvLabelProdi.setText("Prodi");
        tvLabelJnsKelamin.setText("Jenis Kelamin");
        tvLabelAgama.setText("Agama");
        tvLabelHobi.setText("Hobi");

        Bundle b = getIntent().getExtras();

        if(b != null) {
            MahasiswaRequestModel param = new MahasiswaRequestModel();
            param.setNbi(b.getString("nbi"));
            sendGetDetailMahasiswa(param);
        }
    }

    private int getColor() {
        String color;

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }

    private void sendGetDetailMahasiswa(MahasiswaRequestModel param) {
        if(UtilsApi.isNetworkAvailable(mContext)) {
            commonView.startProgressBarNonCancelable("Mohon tunggu...");
            mApiService.sendGetDetailMahasiswa(param)
                    .enqueue(new Callback<MahasiswaResponseModel>() {
                        @Override
                        public void onResponse(Call<MahasiswaResponseModel> call, Response<MahasiswaResponseModel> response) {
                            commonView.stopProgressBar();
                            if(response.isSuccessful()) {
                                if(response.body().getStatus().equalsIgnoreCase("S")) {
                                    TextDrawable drawable = TextDrawable.builder()
                                            .buildRound(response.body().getData().getNama().substring(0,1), getColor());
                                    ivTextDrawable.setImageDrawable(drawable);
                                    tvNbi.setText(response.body().getData().getNbi());
                                    tvNama.setText(response.body().getData().getNama());
                                    tvAlamat.setText(response.body().getData().getAlamat());
                                    tvTglLahir.setText(response.body().getData().getTgl_lahir());
                                    tvProdi.setText(response.body().getData().getProdi());
                                    tvJnsKelamin.setText(response.body().getData().getJns_kelamin());
                                    tvAgama.setText(response.body().getData().getAgama());

                                    String hobiConcat = "";
                                    int i = 0;
                                    for(String item : response.body().getData().getHobi()) {
                                        hobiConcat += item;
                                        if(i < response.body().getData().getHobi().size() - 1) {
                                            hobiConcat += ", ";
                                        }
                                        i++;
                                    }
                                    tvHobi.setText(hobiConcat);
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
            commonView.popUp("Mohon cek koneksi jaringan anda");
        }
    }
}
