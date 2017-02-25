package com.hapi.sdk.HapiBot;

import data.model.HeartRate;
import retrofit2.http.GET;
import retrofit2.Call;


/**
 * Created by alok_shankar on 2/25/2017.
 */

public interface HeartRateApi {
    @GET("heartRate")
    Call<HeartRate> getHeartRate();
}
