package com.svmexample.rx_retrofit.network;

import com.google.gson.annotations.SerializedName;

public class User extends BaseResponse {

    @SerializedName("api_key")
    String apikey;

    public String getApikey() {
        return apikey;
    }
}
