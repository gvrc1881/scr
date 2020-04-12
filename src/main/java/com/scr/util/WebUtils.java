package com.scr.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

	private static final Logger log = LoggerFactory.getLogger(WebUtils.class);	

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        log.info("Request URL " + request.getRequestURL() + " Ip Address " + remoteAddr);
        return remoteAddr;
    }

}
