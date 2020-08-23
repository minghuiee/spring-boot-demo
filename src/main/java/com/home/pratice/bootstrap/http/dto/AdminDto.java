package com.home.pratice.bootstrap.http.dto;

import com.google.gson.annotations.SerializedName;
import oracle.sql.TIMESTAMP;

public class AdminDto {
    private String id;
    private String name;
    private String password;
    @SerializedName("error_login_count")
    private Long errorLoginCount;
    @SerializedName("create_date")
    private TIMESTAMP createDate;
    @SerializedName("update_date")
    private TIMESTAMP updateDate;
    @SerializedName("home_address")
    private String homeAddress;
    @SerializedName("home_address_id")
    private Integer homeAddressId;
}