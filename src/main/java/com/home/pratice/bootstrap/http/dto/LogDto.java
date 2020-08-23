package com.home.pratice.bootstrap.http.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LogDto {
    //server information
    //private int seq; //db auto
    //private String createDate; //db auto
    @SerializedName("ERRORLEVEL")
    private String errorLevel;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("DESCRIBE")
    private String describe;
    //user information
    @SerializedName("username")
    private String username;
    @SerializedName("IP")
    private String ip;
    @SerializedName("MAC")
    private String mac;
    @SerializedName("Platform")
    private String platform;
    @SerializedName("OsVersion")
    private String osVersion;
    //case information
    @SerializedName("IDNO")
    private String idNo;
    @SerializedName("CASEID")
    private String caseId;
    @SerializedName("FILENAME")
    private String fileName;

    public LogDto(){
        this.idNo = "";
        this.caseId = "";
        this.fileName = "";
    }

    public static LogDto createServerLog(LogDto dto, String errorLevel, String message, String describe) {
        if(dto == null) dto = new LogDto();
        dto.errorLevel = errorLevel;
        dto.message = message;
        dto.describe = describe;
        return dto;
    }

    public static LogDto createUserLog(LogDto dto,String username,String ip, String mac, String platform, String osVersion) {
        if(dto == null) dto = new LogDto();
        dto.username = username;
        dto.ip = ip;
        dto.mac = mac;
        dto.platform = platform;
        dto.osVersion = osVersion;
        return dto;
    }
    public static LogDto createCaseLog(LogDto dto,String idNo, String caseId, String fileName) {
        if(dto == null) dto = new LogDto();
        dto.idNo = idNo;
        dto.caseId = caseId;
        dto.fileName = fileName;
        return dto;
    }

    public static LogDto createLog(String errorLevel, String message, String describe, String ip, String mac, String platform, String osVersion, String idNo, String caseId, String fileName) {
        LogDto dto = new LogDto();
        dto.errorLevel = errorLevel;
        dto.message = message;
        dto.describe = describe;
        dto.ip = ip;
        dto.mac = mac;
        dto.platform = platform;
        dto.osVersion = osVersion;
        dto.idNo = idNo;
        dto.caseId = caseId;
        dto.fileName = fileName;
        return dto;
    }
}
