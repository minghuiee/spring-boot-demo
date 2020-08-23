package com.home.pratice.bootstrap.http.controller;

import com.google.gson.Gson;
import com.home.pratice.bootstrap.common.constant.CustomStatus;
import com.home.pratice.bootstrap.common.helper.HttpResult;
import com.home.pratice.bootstrap.common.util.IpUtil;
import com.home.pratice.bootstrap.common.util.MacUtil;
import com.home.pratice.bootstrap.http.dto.LogDto;
import com.home.pratice.bootstrap.http.protocol.req.AccountReq;
import com.home.pratice.bootstrap.http.protocol.req.AdminReq;
import com.home.pratice.bootstrap.http.protocol.resp.AccountResp;
import com.home.pratice.bootstrap.http.service.AccountService;
import com.home.pratice.bootstrap.http.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String add(@RequestBody String jsonData, HttpServletRequest httpReq, UsernamePasswordAuthenticationToken auth) {
        String ip = IpUtil.getIp(httpReq);
        String mac = MacUtil.getMacInWindows(ip);
        int code = 0;
        try {
            AdminReq req = gson.fromJson(jsonData, AdminReq.class);
            code = service.add(req);
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "INFO", "increase account success", "管理者-增加帳號");
            logService.add(dto);
        } catch (Exception e) {
            //TODO:admin table error_login_count column +1
            log.error("[get] Access failed is due to {}", ExceptionUtils.getStackTrace(e));
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "ERROR", e.getMessage(), "管理者-增加帳號");
            logService.add(dto);
        }
        return HttpResult.result(CustomStatus.SUCCESS, "end execution", String.valueOf(code));
    }

    @DeleteMapping("/delete_admin/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") String id,HttpServletRequest httpReq, UsernamePasswordAuthenticationToken auth){
        String ip = IpUtil.getIp(httpReq);
        String mac = MacUtil.getMacInWindows(ip);
        int code = 0;
        try {
            code = service.delete(id);
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "INFO", "delete account success", "管理者-刪除帳號");
            logService.add(dto);
        } catch (Exception e) {
            //TODO:admin table error_login_count column +1
            log.error("[get] Access failed is due to {}", ExceptionUtils.getStackTrace(e));
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "ERROR", e.getMessage(), "管理者-刪除帳號");
            logService.add(dto);
        }
        return HttpResult.result(CustomStatus.SUCCESS, "end execution", String.valueOf(code));
    }

    @PutMapping("/update_admin")
    @ResponseBody
    public String update(@RequestBody String jsonData,HttpServletRequest httpReq, UsernamePasswordAuthenticationToken auth){
        String ip = IpUtil.getIp(httpReq);
        String mac = MacUtil.getMacInWindows(ip);
        int code = 0;
        try {
            AdminReq req = gson.fromJson(jsonData, AdminReq.class);
            code = service.update(req);
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "INFO", "update account success", "管理者-更新帳號");
            logService.add(dto);
        } catch (Exception e) {
            //TODO:admin table error_login_count column +1
            log.error("[get] Access failed is due to {}", ExceptionUtils.getStackTrace(e));
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "ERROR", e.getMessage(), "管理者-更新帳號");
            logService.add(dto);
        }
        return HttpResult.result(CustomStatus.SUCCESS, "end execution", String.valueOf(code));
    }

    @GetMapping("/get_admin/{id}")
    @ResponseBody
    public String get(@PathVariable("id") String id,HttpServletRequest httpReq, UsernamePasswordAuthenticationToken auth){
        String ip = IpUtil.getIp(httpReq);
        String mac = MacUtil.getMacInWindows(ip);
        AccountResp resp = null;
        try {
            resp = service.query(id);
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "INFO", "get account success", "管理者-取得帳號");
            logService.add(dto);
        } catch (Exception e) {
            //TODO:admin table error_login_count column +1
            log.error("[get] Access failed is due to {}", ExceptionUtils.getStackTrace(e));
            LogDto dto = LogDto.createUserLog(new LogDto(), auth.getName(), ip, mac, "", "");
            dto = LogDto.createServerLog(dto, "ERROR", e.getMessage(), "管理者-取得帳號");
            logService.add(dto);
        }
        return HttpResult.result(CustomStatus.SUCCESS, "end execution", resp);
    }
}
