package com.rizal.utsmysql.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.rizal.utsmysql.R;
import com.rizal.utsmysql.model.response.MahasiswaModel;

import java.util.List;
import java.util.Random;

public class RecycleViewAdapterMahasiswa extends RecyclerView.Adapter<RecycleViewAdapterMahasiswa.ListMahasiswaViewHolder> {
    private List<MahasiswaModel> dataMahasiswa;
    private Context mContext;
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
    }

    @Override
    public ListMahasiswaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_list_mahasiswa, parent, false);
        return new ListMahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListMahasiswaViewHolder holder, final int position) {
        holder.tvLabelNbi.setText("NBI");
        holder.tvLabelNama.setText("Nama");
        holder.tvNbi.setText(dataMahasiswa.get(position).getNbi());
        holder.tvNama.setText(dataMahasiswa.get(position).getNama());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(dataMahasiswa.get(position).getNama().substring(0,1), getColor());
        holder.ivTextDrawable.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return (dataMahasiswa != null) ? dataMahasiswa.size() : 0;
    }

    public class ListMahasiswaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLabelNbi, tvLabelNama, tvNbi, tvNama;
        private ImageView ivTextDrawable;

        public ListMahasiswaViewHolder(View itemView) {
            super(itemView);
            tvLabelNbi = (TextView) itemView.findViewById(R.id.tvLabelNbi);
            tvLabelNama = (TextView) itemView.findViewById(R.id.tvLabelNama);
            tvNbi = (TextView) itemView.findViewById(R.id.tvNbi);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            ivTextDrawable = (ImageView) itemView.findViewById(R.id.ivTextDrawable);
        }
    }

    public void refreshAll(List<MahasiswaModel> data) {
        dataMahasiswa = data;
        notifyDataSetChanged();
    }

    public int getColor() {
        String color;

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }

}
