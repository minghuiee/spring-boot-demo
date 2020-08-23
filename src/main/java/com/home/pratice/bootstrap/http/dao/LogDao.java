package com.home.pratice.bootstrap.http.dao;

import com.home.pratice.bootstrap.common.constant.Const;
import com.home.pratice.bootstrap.http.dto.LogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class LogDao {
    @Autowired
    @Qualifier("oracleJDBC")
    private JdbcTemplate factory;

    private String addSql(LogDto dto){
        return new StringBuffer()
                .append("insert into ")
                .append(Const.SPRING_BOOT_DEMO_LOG)
                .append(" (seq,createDate,errorLevel,message,describe,username,ip,mac,platform,osVersion,idNo,caseId,fileName) values (")
                .append("SPRING_BOOT_DEMO_SEQ.nextval").append(",")
                .append("SYSTIMESTAMP").append(",'")
                .append(dto.getErrorLevel()).append("','")
                .append(dto.getMessage().replace("'","\"")).append("','")
                .append(dto.getDescribe()).append("','")
                .append(dto.getUsername()).append("','")
                .append(dto.getIp()).append("','")
                .append(dto.getMac()).append("','")
                .append(dto.getPlatform()).append("','")
                .append(dto.getOsVersion()).append("','")
                .append(dto.getIdNo()).append("','")
                .append(dto.getCaseId()).append("','")
                .append(dto.getFileName()).append("')")
                .toString();
    }

    public int addLog(LogDto dto){
        String sql = addSql(dto);
        log.info(sql);
        return factory.update(sql);
    }
}
