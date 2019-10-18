package com.rizal.utsmysql.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnMahasiswaResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    public ReturnMahasiswaResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
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

    @Override
    public String toString() {
        return "ReturnMahasiswaResponseModel{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
