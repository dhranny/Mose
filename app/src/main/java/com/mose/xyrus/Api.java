package com.mose.xyrus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface Api {

    String BASE_URL = "https://a647-105-112-179-55.eu.ngrok.io/";
    @POST("send")
    Call<List<SendModel>> sendBTC(@Body SendModel sendModel);

    @GET("wallet")
    Call<CreateWalletResponse> createWallet();
}
