package com.home.pratice.bootstrap.http.dao;


import com.home.pratice.bootstrap.common.constant.Const;
import com.home.pratice.bootstrap.http.interfaces.BaseDao;
import com.home.pratice.bootstrap.http.protocol.req.AccountReq;
import com.home.pratice.bootstrap.http.protocol.req.AdminReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

//@Mapper
@Slf4j
@Repository
public class AccountDao implements BaseDao<AdminReq> {

    @Autowired
    @Qualifier("oracleJDBC")
    private JdbcTemplate factory;

    private String addSql(AdminReq req) {
        //TODO:change Stringbuffer
        return new StringBuffer()
                .append("Insert into ")
                .append(Const.SPRING_BOOT_DEMO_ADMIN)
                .append(" (id,name,password,error_login_count) values ('")
                .append(req.getId()).append("','")
                .append(req.getName()).append("','")
                .append(req.getPassword()).append("',0)")
                .toString();
    }

    private String querySql(){
        return "select * from spring_boot_demo_admin";
    }

    //@Insert
    @Override
    public int add(AdminReq req) {
        String sql = addSql(req);
        log.info(sql);
        return factory.update(sql);
    }

    //@Detete
    @Override
    public int delete(Long id) {
        return 1;
    }

    //@Update
    @Override
    public int update(AdminReq req) {
        return 1;
    }

    //@Select
    @Override
    public AccountReq query(Long id) {
        return null;
    }
}
