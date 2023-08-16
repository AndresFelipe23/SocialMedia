package com.afes.socialmediagamer.providers;

import com.afes.socialmediagamer.models.FCMBody;
import com.afes.socialmediagamer.models.FCMResponse;
import com.afes.socialmediagamer.retrofit.IFCMApi;
import com.afes.socialmediagamer.retrofit.RetrofitClient;

import retrofit2.Call;

public class NotificationProvider {

    private String url = "https://fcm.googleapis.com";

    public NotificationProvider() {

    }

    public Call<FCMResponse> sendNotification(FCMBody body) {
        return RetrofitClient.getClient(url).create(IFCMApi.class).send(body);
    }

}
