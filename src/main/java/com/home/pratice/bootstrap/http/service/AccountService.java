package com.home.pratice.bootstrap.http.service;

import com.home.pratice.bootstrap.http.dao.AccountDao;
import com.home.pratice.bootstrap.http.protocol.req.AdminReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountDao dao;

    public int add(AdminReq req) {
        return dao.add(req);
    }

    public boolean isUser(String username,String password){
        //TODO: search user table -> match user list
        return false;
    }
}
