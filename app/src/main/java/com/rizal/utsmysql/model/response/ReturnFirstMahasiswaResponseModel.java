package com.rizal.utsmysql.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnFirstMahasiswaResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private MahasiswaResponseModel data;

    public ReturnFirstMahasiswaResponseModel(String status, String message, MahasiswaResponseModel data) {
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

    public MahasiswaResponseModel getData() {
        return data;
    }

    public void setData(MahasiswaResponseModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReturnFirstMahasiswaResponseModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
