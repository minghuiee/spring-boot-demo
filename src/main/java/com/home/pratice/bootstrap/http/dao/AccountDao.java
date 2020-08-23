package com.home.pratice.bootstrap.http.dao;


import com.home.pratice.bootstrap.common.constant.Const;
import com.home.pratice.bootstrap.http.interfaces.BaseDao;
import com.home.pratice.bootstrap.http.protocol.req.AdminReq;
import com.home.pratice.bootstrap.http.protocol.resp.AccountResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Slf4j
@Repository
public class AccountDao implements BaseDao<AdminReq, AccountResp> {
    @Autowired
    @Qualifier("oracleJDBC")
    private JdbcTemplate factory;

    private String addSql(AdminReq req) {
        return new StringBuffer()
                .append("Insert into ")
                .append(Const.SPRING_BOOT_DEMO_ADMIN)
                .append(" (id,name,password,error_login_count,home_address,home_address_id,branch_id,create_date) values ('")
                .append(req.getId()).append("','")
                .append(req.getName()).append("','")
                .append(req.getPassword()).append("',0,'")
                .append(req.getHomeAddress()).append("','")
                .append(req.getHomeAddressId()).append("','")
                .append(req.getBranchId()).append("',SYSTIMESTAMP)")
                .toString();
    }

    //@Insert
    @Override
    public int add(AdminReq req) {
        String sql = addSql(req);
        log.info(sql);
        return factory.update(sql);
    }

    private String deleteSql(String id) {
        return new StringBuffer()
                .append("DELETE FROM ")
                .append(Const.SPRING_BOOT_DEMO_ADMIN)
                .append(" WHERE id = '")
                .append(id).append("'")
                .toString();
    }

    //@Detete
    @Override
    public int delete(String id) {
        String sql = deleteSql(id);
        log.info(sql);
        return factory.update(sql);
    }

    private String updateSql(AdminReq req) {
        return new StringBuffer()
                .append("UPDATE ")
                .append(Const.SPRING_BOOT_DEMO_ADMIN)
                .append(" SET password = '")
                .append(req.getPassword()).append("',home_address = '")
                .append(req.getHomeAddress()).append("',home_address_id = '")
                .append(req.getHomeAddressId()).append("',branch_id = '")
                .append(req.getBranchId()).append("',update_date = SYSTIMESTAMP WHERE id = '")
                .append(req.getId()).append("' AND name = '")
                .append(req.getName()).append("'")
                .toString();
    }

    //@Update
    @Override
    public int update(AdminReq req) {
        String sql = updateSql(req);
        log.info(sql);
        return factory.update(sql);
    }

    private String querySql(String id) {
        return new StringBuffer()
                //TODO:column const
                .append("SELECT admin.id,admin.name,admin.password,admin.error_login_count,admin.home_address_id||admin.home_address AS home_address,idToName.name AS branch_name,admin.create_date,admin.update_date FROM ")
                .append(Const.SPRING_BOOT_DEMO_ADMIN).append(" admin ")
                .append("left join ")
                .append(Const.SPRING_BOOT_DEMO_IDTONAME).append(" idToName on idToName.id = admin.branch_id ")
                .append("where admin.id = '")
                .append(id).append("'")
                .toString();
    }

    //@Select
    @Override
    public AccountResp query(String id) {
        String sql = querySql(id);
        log.info(sql);
        return factory.queryForObject(sql, new BeanPropertyRowMapper<>(AccountResp.class));
    }

    @Override
    public List<AccountResp> queryList(int pageIndex, int pageSize) {
        //TODO:insert log table
        throw new UnsupportedOperationException();
    }
}
