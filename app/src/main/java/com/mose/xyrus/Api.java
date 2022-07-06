package com.mose.xyrus;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface Api {

    String BASE_URL = "https://208d-105-112-186-141.eu.ngrok.io/";
    @POST("send")
    Call<Void> sendBTC(@Body SendModel sendModel);

    @GET("wallet")
    Call<CreateWalletResponse> createWallet();

    @GET("wallet/update/{id}")
    Call<TransactionModel> getUpdate(@Path("id") long walletId);
}
