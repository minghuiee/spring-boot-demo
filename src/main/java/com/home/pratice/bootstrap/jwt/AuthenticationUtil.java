package com.home.pratice.bootstrap.jwt;

import com.home.pratice.bootstrap.common.util.Date8;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.management.timer.Timer;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Data
public class AuthenticationUtil implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    private String authority;
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

    public AuthenticationUtil(String authority) {
        this.authority = authority;
    }

    /**
     * JWT密碼
     */
    static private final String SECRET = "springbootdemo";
    /**
     * Token前缀
     */
    static private final String TOKEN_PREFIX = "springboot";

    static String buildJWT(String username) {
        return Jwts.builder()
                // 設置權限
                .claim(HttpHeaders.AUTHORIZATION, "ROLE_ADMIN,AUTH_WRITE")
                // 標題先放名字
                .setSubject(username)
                // 有效時間設定
                .setExpiration(new Date(Date8.now() + Timer.ONE_DAY))
                // 簽名
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isEmpty(token)) return null;
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, Strings.EMPTY))
                .getBody();
        String user = claims.getSubject();
        if (Strings.isEmpty(user)) return null;
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(claims.get(HttpHeaders.AUTHORIZATION)));
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    static Authentication setAdminAuthentication(String username, String password) {
        List<AuthenticationUtil> roleList = Arrays.asList(new AuthenticationUtil(ROLE_ADMIN), new AuthenticationUtil(ROLE_USER));
        List<GrantedAuthority> list = new ArrayList<>(roleList);
        return new UsernamePasswordAuthenticationToken(username, password, list);
    }

    static Authentication setUserAuthentication(String username, String password) {
        List<AuthenticationUtil> roleList = Collections.singletonList(new AuthenticationUtil(ROLE_USER));
        List<GrantedAuthority> list = new ArrayList<>(roleList);
        return new UsernamePasswordAuthenticationToken(username, password, list);
    }

    public static String buildToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(Date8.now() + Timer.ONE_DAY))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }
}
