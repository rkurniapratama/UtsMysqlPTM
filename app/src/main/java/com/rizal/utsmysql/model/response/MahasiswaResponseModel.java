package com.rizal.utsmysql.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MahasiswaResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private MahasiswaModel data;

    @SerializedName("data_list")
    @Expose
    private List<MahasiswaModel> data_list;

    public MahasiswaResponseModel(String status, String message, MahasiswaModel data, List<MahasiswaModel> data_list) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.data_list = data_list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MahasiswaModel getData() {
        return data;
    }

    public void setData(MahasiswaModel data) {
        this.data = data;
    }

    public List<MahasiswaModel> getData_list() {
        return data_list;
    }

    public void setData_list(List<MahasiswaModel> data_list) {
        this.data_list = data_list;
    }

    @Override
    public String toString() {
        return "MahasiswaResponseModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", data_list=" + data_list +
                '}';
    }
}
