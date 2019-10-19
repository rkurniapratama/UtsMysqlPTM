package com.rizal.utsmysql.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

public class UtilsApi {
    public static String BASE_URL = "http://137ace8f.ngrok.io/";
    private static boolean isConnected = false;

    public static BaseApiService getAPIService(String urlserver){
        return RetrofitClient.getClient(urlserver, false, 0).create(BaseApiService.class);
    }

    public static BaseApiService getAPIServiceWithDelay(String urlserver, long timeDelay){
        return RetrofitClient.getClient(urlserver, true, timeDelay).create(BaseApiService.class);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        cm.registerNetworkCallback(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                isConnected = true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                isConnected = false;
            }
        });

        return isConnected;
    }
}
