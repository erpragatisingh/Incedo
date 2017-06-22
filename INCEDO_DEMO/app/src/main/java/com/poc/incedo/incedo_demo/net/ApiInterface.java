package com.poc.incedo.incedo_demo.net;

import com.google.gson.JsonObject;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiInterface {
    @GET
    Call<JsonObject> getSnagfilmsList(@Url String subURL);

}
