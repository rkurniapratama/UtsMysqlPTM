package com.rizal.utsmysql.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaResponseModel {
    @SerializedName("nbi")
    @Expose
    private String nbi;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("tgl_lahir")
    @Expose
    private String tgl_lahir;

    @SerializedName("prodi")
    @Expose
    private String prodi;

    @SerializedName("kelamin")
    @Expose
    private String kelamin;

    @SerializedName("agama")
    @Expose
    private String agama;

    @SerializedName("hobi")
    @Expose
    private List<String> hobi;

    public MahasiswaResponseModel(String nbi, String nama, String alamat, String tgl_lahir, String prodi, String kelamin, String agama, List<String> hobi) {
        this.nbi = nbi;
        this.nama = nama;
        this.alamat = alamat;
        this.tgl_lahir = tgl_lahir;
        this.prodi = prodi;
        this.kelamin = kelamin;
        this.agama = agama;
        this.hobi = hobi;
    }

    public String getNbi() {
        return nbi;
    }

    public void setNbi(String nbi) {
        this.nbi = nbi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public List<String> getHobi() {
        return hobi;
    }

    public void setHobi(List<String> hobi) {
        this.hobi = hobi;
    }

    @Override
    public String toString() {
        return "MahasiswaRequestModel{" +
                "nbi='" + nbi + '\'' +
                ", nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", tgl_lahir='" + tgl_lahir + '\'' +
                ", prodi='" + prodi + '\'' +
                ", kelamin='" + kelamin + '\'' +
                ", agama='" + agama + '\'' +
                ", hobi=" + hobi +
                '}';
    }
}
