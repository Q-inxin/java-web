package com.ithuike;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestLoggingListener implements ServletRequestListener {
    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingListener.class);

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        long startTime = System.currentTimeMillis();
        // 存储开始时间
        request.setAttribute("startTime", startTime);

        // 获取并记录请求信息
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }

        logger.info("请求开始 - 时间: {}, IP: {}, 方法: {}, URI: {}, 查询: {}, 用户代理: {}",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(startTime)),
                clientIp,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getHeader("User-Agent"));
    }

    // 记录请求结束的日志
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        Long startTime = (Long) request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 请求结束和持续时间
        logger.info("请求完成时间: {}, 持续时间: {}ms, URI: {}",
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(endTime)),
        duration,
        request.getRequestURI());
    }
}