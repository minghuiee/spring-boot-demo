package com.home.pratice.bootstrap.http.dto;

import com.google.gson.annotations.SerializedName;

public class LogDto {
    //private int seq;
    @SerializedName("IDNO")
    private String idNo;
    @SerializedName("ERRORLEVEL")
    private String errorLevel;
    @SerializedName("CASEID")
    private String caseId;
    @SerializedName("MESSAGE")
    private String message;
    //private String createDate;
    @SerializedName("FILENAME")
    private String fileName;
    @SerializedName("DESCRIBE")
    private String describe;


    public static LogDto create(String idNo, String errorLevel, String caseId, String message, String fileName, String describe){
        LogDto log = new LogDto();
        log.idNo = idNo;
        log.errorLevel = errorLevel;
        log.caseId = caseId;
        log.message = message;
        log.fileName = fileName;
        log.describe = describe;
        return log;
    }
}
