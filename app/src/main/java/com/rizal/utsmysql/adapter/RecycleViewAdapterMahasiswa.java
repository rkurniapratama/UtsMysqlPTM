package com.rizal.utsmysql.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.rizal.utsmysql.R;
import com.rizal.utsmysql.activity.AddMahasiswaActivity;
import com.rizal.utsmysql.activity.DetailMahasiswaActivity;
import com.rizal.utsmysql.api.BaseApiService;
import com.rizal.utsmysql.api.UtilsApi;
import com.rizal.utsmysql.helper.CommonView;
import com.rizal.utsmysql.model.request.MahasiswaRequestModel;
import com.rizal.utsmysql.model.response.MahasiswaModel;
import com.rizal.utsmysql.model.response.MahasiswaResponseModel;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleViewAdapterMahasiswa extends RecyclerView.Adapter<RecycleViewAdapterMahasiswa.ListMahasiswaViewHolder> {
    private List<MahasiswaModel> dataMahasiswa;
    private Context mContext;
    private CommonView commonView;
    private BaseApiService mApiService;
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


    public RecycleViewAdapterMahasiswa(Context context, List<MahasiswaModel> data) {
        mContext = context;
        dataMahasiswa = data;
        commonView = new CommonView(mContext);
        mApiService = UtilsApi.getAPIService(UtilsApi.BASE_URL);
    }

    @Override
    public ListMahasiswaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_list_mahasiswa, parent, false);
        return new ListMahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListMahasiswaViewHolder holder, final int position) {
        holder.tvLabelNbi.setText("NBI");
        holder.tvLabelNama.setText("Nama");
        holder.tvNbi.setText(dataMahasiswa.get(position).getNbi());
        holder.tvNama.setText(dataMahasiswa.get(position).getNama());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(dataMahasiswa.get(position).getNama().substring(0,1), getColor());
        holder.ivTextDrawable.setImageDrawable(drawable);

        holder.tvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext, holder.tvOptions);
                popup.inflate(R.menu.options_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuDetail:
                                Intent intentDetail = new Intent(mContext, DetailMahasiswaActivity.class);
                                Bundle bDetail = new Bundle();
                                bDetail.putString("nbi", dataMahasiswa.get(position).getNbi());
                                intentDetail.putExtras(bDetail);
                                mContext.startActivity(intentDetail);
                                break;
                            case R.id.menuEdit:
                                Intent intentMain = new Intent(mContext, AddMahasiswaActivity.class);
                                Bundle bMain = new Bundle();
                                bMain.putString("nbi", dataMahasiswa.get(position).getNbi());
                                intentMain.putExtras(bMain);
                                mContext.startActivity(intentMain);
                                break;
                            case R.id.menuHapus:
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                alertDialog.setTitle("Konfirmasi");
                                alertDialog.setMessage("Apakah Anda Ingin Menghapus Data Mahasiswa Ini?");
                                alertDialog.setCancelable(true);
                                alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        MahasiswaRequestModel param = new MahasiswaRequestModel();
                                        param.setNbi(dataMahasiswa.get(position).getNbi());
                                        sendDeleteMahasiswa(param);
                                    }
                                });
                                alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        if (position + 1 == getItemCount()) {
            setBottomMargin(holder.itemView, (int) (85 * Resources.getSystem().getDisplayMetrics().density));
        } else {
            setBottomMargin(holder.itemView, 0);
        }
    }

    @Override
    public int getItemCount() {
        return (dataMahasiswa != null) ? dataMahasiswa.size() : 0;
    }

    public class ListMahasiswaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLabelNbi, tvLabelNama, tvNbi, tvNama, tvOptions;
        private ImageView ivTextDrawable;

        public ListMahasiswaViewHolder(View itemView) {
            super(itemView);
            tvLabelNbi = (TextView) itemView.findViewById(R.id.tvLabelNbi);
            tvLabelNama = (TextView) itemView.findViewById(R.id.tvLabelNama);
            tvNbi = (TextView) itemView.findViewById(R.id.tvNbi);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            tvOptions = (TextView) itemView.findViewById(R.id.tvOptions);
            ivTextDrawable = (ImageView) itemView.findViewById(R.id.ivTextDrawable);
        }
    }

    public void refreshAll(List<MahasiswaModel> data) {
        dataMahasiswa = data;
        notifyDataSetChanged();
    }

    private int getColor() {
        String color;

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }

    private void sendDeleteMahasiswa(MahasiswaRequestModel param) {
        commonView.startProgressBarNonCancelable("Mohon tunggu...");
        mApiService.sendDeleteMahasiswa(param)
                .enqueue(new Callback<MahasiswaResponseModel>() {
                    @Override
                    public void onResponse(Call<MahasiswaResponseModel> call, Response<MahasiswaResponseModel> response) {
                        commonView.stopProgressBar();
                        if(response.isSuccessful()) {
                            dataMahasiswa.remove(getItemCount() - 1);
                            commonView.popUp(response.body().getMessage());
                            notifyDataSetChanged();
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

    private static void setBottomMargin(View view, int bottomMargin) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, bottomMargin);
            view.requestLayout();
        }
    }
}
