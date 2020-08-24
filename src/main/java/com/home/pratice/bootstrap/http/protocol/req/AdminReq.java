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
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;
    @SerializedName("page_index")
    private int pageIndex;
    //有的專案size為固定不需要傳進此參數，但需要的專案則server必須要防呆
    //如傳入1000但server最大只給500則只能輸出500或告訴前端最大輸出500(依pm流程圖、文檔(redmine、confluence)設定)
    @SerializedName("page_size")
    private int pageSize;
}
