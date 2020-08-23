package com.home.pratice.bootstrap.http.protocol.req;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AdminReq {
    private String id;
    private String name;
    private String password;
    @SerializedName("home_address")
    private String homeAddress;
    @SerializedName("home_address_id")
    private String homeAddressId;
    @SerializedName("branch_id")
    private String branchId;
}
