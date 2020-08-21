package com.home.pratice.bootstrap.common.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    private static final String SPIT = ",";
    private static String UNKNOWN = "unknown";

    public static String getIp(HttpServletRequest req){
        String ip;
        ip = req.getHeader("x-forwarded-for");
        if (isIp(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) return filterFirstIP(ip.substring(0, index));
            return filterFirstIP(ip);
        }
        ip = req.getHeader("X-Real-IP");
        if (isIp(ip)) return filterFirstIP(ip);
        return filterFirstIP(req.getRemoteAddr());
    }

    private static boolean isIp(String ip){
        return !StringUtils.isEmpty(ip) && !UNKNOWN.equalsIgnoreCase(ip);
    }

    private static String filterFirstIP(String ip) {
        return nullToEmpty(ip).split(SPIT)[0];
    }

    public static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
