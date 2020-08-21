package com.home.pratice.bootstrap.http.dto;

import com.google.gson.annotations.SerializedName;

public class AdminDto {
    private String id;
    private String name;
    private String password;
    @SerializedName("error_login_count")
    private Long errorLoginCount;
}