package com.home.pratice.bootstrap.http.controller;

import com.google.gson.Gson;
import com.home.pratice.bootstrap.common.constant.CustomStatus;
import com.home.pratice.bootstrap.common.helper.HttpResult;
import com.home.pratice.bootstrap.common.util.IpUtil;
import com.home.pratice.bootstrap.http.protocol.req.AdminReq;
import com.home.pratice.bootstrap.http.service.AccountService;
import com.home.pratice.bootstrap.http.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//若所有api都是json格式，將改為@RestController，然後刪除所有@ResponseBody
@Slf4j
@Controller
@RequestMapping("/demo_api")
public class AccountController {
    private Gson gson = new Gson();
    @Autowired
    private AccountService service;
    @Autowired
    private LogService logService;

    @PostMapping("/add_admin")
    @ResponseBody
    public String get(@RequestBody String jsonData, HttpServletRequest httpReq, UsernamePasswordAuthenticationToken auth) {
        String ip = IpUtil.getIp(httpReq);
        int code = 0;
        try {
            AdminReq req = gson.fromJson(jsonData, AdminReq.class);
            code = service.add(req);
            //TODO:create log table and insert into log
            //logService.insert(ip,mac,auth)
        } catch (Exception e) {
            log.error("[get] Access failed is due to {}", ExceptionUtils.getStackTrace(e));
//            logService.insert(LogDto.create("", "ERROR", "", e.getMessage(), "", "帳號-取得-意外錯誤"));
        }
        return HttpResult.result(CustomStatus.SUCCESS, "end execution", String.valueOf(code));
    }
}
