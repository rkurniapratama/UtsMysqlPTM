package com.rizal.utsmysql.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReturnListMahasiswaResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<MahasiswaResponseModel> data;

    public ReturnListMahasiswaResponseModel(String status, String message, List<MahasiswaResponseModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public List<MahasiswaResponseModel> getData() {
        return data;
    }

    public void setData(List<MahasiswaResponseModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnListMahasiswaResponseModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
