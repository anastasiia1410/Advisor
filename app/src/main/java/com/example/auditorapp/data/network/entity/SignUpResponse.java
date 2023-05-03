package com.example.auditorapp.data.network.entity;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

    @SerializedName("sessionToken")
    private final String token;

    public SignUpResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
