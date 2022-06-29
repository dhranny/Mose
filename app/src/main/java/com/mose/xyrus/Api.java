package com.mose.mose;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface Api {

    String BASE_URL = "https://f13b-105-112-31-127.eu.ngrok.io/";
    @POST("send")
    Call<List<SendModel>> sendBTC(@Body SendModel sendModel);

    @GET("wallet")
    Call<CreateWalletResponse> createWallet();
}
