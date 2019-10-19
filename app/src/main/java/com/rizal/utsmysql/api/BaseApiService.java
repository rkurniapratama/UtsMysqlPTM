package com.rizal.utsmysql.api;

import com.rizal.utsmysql.model.request.MahasiswaRequestModel;
import com.rizal.utsmysql.model.response.MahasiswaResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BaseApiService {
    @POST("mahasiswa/insert")
    Call<MahasiswaResponseModel> sendInsertMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);

    @POST("mahasiswa/update")
    Call<MahasiswaResponseModel> sendUpdateMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);

    @POST("mahasiswa/delete")
    Call<MahasiswaResponseModel> sendDeleteMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);

    @POST("mahasiswa/detail")
    Call<MahasiswaResponseModel> sendGetDetailMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);

    @POST("mahasiswa/show")
    Call<MahasiswaResponseModel> sendGetSearchMahasiswa(@Body MahasiswaRequestModel mahasiswaRequestModel);
}
