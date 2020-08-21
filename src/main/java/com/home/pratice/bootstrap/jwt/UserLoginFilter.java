package com.home.pratice.bootstrap.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.pratice.bootstrap.common.helper.HttpResult;
import com.home.pratice.bootstrap.http.protocol.req.AccountReq;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginFilter extends AbstractAuthenticationProcessingFilter {

    public UserLoginFilter(String url, AuthenticationManager manager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {
        AccountReq req = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountReq.class);
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        String jwt = AuthenticationUtil.buildJWT(auth.getName());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().println(HttpResult.result(HttpServletResponse.SC_OK, "please come in", jwt));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().println(HttpResult.result(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error!!!", ""));
    }
}
