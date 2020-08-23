package com.home.pratice.bootstrap.http.service;

import com.home.pratice.bootstrap.http.dao.AccountDao;
import com.home.pratice.bootstrap.http.protocol.req.AccountReq;
import com.home.pratice.bootstrap.http.protocol.req.AdminReq;
import com.home.pratice.bootstrap.http.protocol.resp.AccountResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountDao dao;

    public int add(AdminReq req) {
        return dao.add(req);
    }

    public int delete(String id) {
        return dao.delete(id);
    }

    public int update(AdminReq req) {
        return dao.update(req);
    }

    public AccountResp query(String id) {
        return dao.query(id);
    }

    public boolean isUser(String username,String password){
        //TODO: search user table -> match user list
        return false;
    }
}
