package com.home.pratice.bootstrap.jwt;

import com.home.pratice.bootstrap.common.constant.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntry unauthorizedHandler;
    @Autowired
    private ApplicationContext context;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 開啟cors
        http.cors()
                // 關閉csrf
                .and().csrf().disable()
                // 驗證需求
                .authorizeRequests()
                // client底下都允許通過
                .antMatchers(Const.CLIENT_ALL)
                .permitAll()
                .antMatchers(Const.PUBLICAPI_ALL)
                .permitAll()
                .antMatchers(Const.MANAGER_ALL)
                .hasAuthority(AuthenticationUtil.ROLE_ADMIN)
                // 所有需求都需要驗證
                .anyRequest().authenticated()
                // exception處理
                .and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                // session處理
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 加入驗證登入的filter
                .and().addFilterBefore(new UserLoginFilter(Const.LOGIN, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 加入驗證token的filter
                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 使用自訂的身分驗證類別
        auth.authenticationProvider(new CustomAuthenticationProvider(context));
    }
}
