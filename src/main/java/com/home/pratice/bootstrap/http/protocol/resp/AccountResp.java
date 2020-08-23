package com.home.pratice.bootstrap.http.protocol.resp;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountResp {
    //TODO:依頁面增加屬性
    private String id;
    private String name;
    private String password;
    @SerializedName("error_login_count")
    private String errorLoginCount;
    @SerializedName("home_address")
    private String homeAddress;
    @SerializedName("branch_name")
    private String branchName;
    @SerializedName("create_date")
    private Timestamp createDate;
    @SerializedName("update_date")
    private Timestamp updateDate;
}
