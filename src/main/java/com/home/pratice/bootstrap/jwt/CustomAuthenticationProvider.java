package com.home.pratice.bootstrap.jwt;

import com.home.pratice.bootstrap.http.service.AccountService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Data
@Slf4j
@PropertySource(value = { "file:${PROJECT_CONFIG_PATH}/project.properties" }, encoding = "UTF-8")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${admin.account}")
    private String adminAccount;

    private String username;
    private String password;
    private ApplicationContext context;

    @Autowired
    private AccountService service;

    public CustomAuthenticationProvider(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        username = auth.getName();
        password = auth.getCredentials().toString();
        boolean isConn = this.isConnected(username, password);
        if (isConn) {
            if ("admin".equals(username) || adminAccount.equals(username)) {
                return AuthenticationUtil.setAdminAuthentication(username, password);
            } else if (service.isUser(username, password)) {
                return AuthenticationUtil.setUserAuthentication(username, password);
            } else {
                //TODO:紀錄失敗次數 紀錄最後一次登入ip time  若是內網紀錄mac
                //TODO:log table
                throw new BadCredentialsException("驗證失敗");
            }
        } else {
            throw new BadCredentialsException("web service 無法連通...");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean isConnected(String username, String password) {
        return true;
    }
}
