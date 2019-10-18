package com.rizal.utsmysql.api;

import com.rizal.utsmysql.model.request.MahasiswaRequestModel;
import com.rizal.utsmysql.model.response.ReturnFirstMahasiswaResponseModel;
import com.rizal.utsmysql.model.response.ReturnListMahasiswaResponseModel;
import com.rizal.utsmysql.model.response.ReturnMahasiswaResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    @POST("mahasiswa/insert")
    @FormUrlEncoded
    Call<ReturnMahasiswaResponseModel> sendInsertMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);

    @POST("mahasiswa/update")
    @FormUrlEncoded
    Call<ReturnMahasiswaResponseModel> sendUpdateMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);

    @POST("mahasiswa/destroy")
    @FormUrlEncoded
    Call<ReturnMahasiswaResponseModel> sendDestroyMahasiswa(@Field("nbi") String nbi);

    @POST("mahasiswa/getbynbi")
    @FormUrlEncoded
    Call<ReturnFirstMahasiswaResponseModel> sendGetByNbiMahasiswa(@Field("nbi") String nbi);

    @POST("mahasiswa/getall")
    @FormUrlEncoded
    Call<ReturnListMahasiswaResponseModel> sendGetAllMahasiswa();

    @POST("mahasiswa/getsearch")
    @FormUrlEncoded
    Call<ReturnListMahasiswaResponseModel> sendGetSearchMahasiswa(@Field("search") String search);
}
