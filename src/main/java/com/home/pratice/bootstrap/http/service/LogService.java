package com.home.pratice.bootstrap.http.service;

import com.home.pratice.bootstrap.http.dao.LogDao;
import com.home.pratice.bootstrap.http.dto.LogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogDao dao;

    public int add(LogDto dto) {
        return dao.addLog(dto);
    }
}
