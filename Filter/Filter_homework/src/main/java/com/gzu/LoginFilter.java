package com.gzu;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//登录过滤器，检查用户是否登录
@WebFilter("/*")
public class LoginFilter implements Filter {

    // 列出可以无限制放行的资源
    private static final List<String> STATIC_EXTENSIONS = Arrays.asList(
            ".css", ".js", ".jpg", ".png", ".gif", ".ico", ".jsp","login"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取URI路径
        String requestURI = request.getRequestURI();

        //判断是否为静态资源
        boolean isStatic = STATIC_EXTENSIONS.stream().anyMatch(
                extension -> {
                    return request.getRequestURI().contains(extension);
                }
        );

        if(isStatic){
            //放行静态资源
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            //获取用户名
            String username = (String)request.getSession().getAttribute("user");

            //如果用户名为空或不存在，则重定向到登录页面。其余的情况，直接放行
            if(username == null || "".equals(username)){
                response.sendRedirect(request.getContextPath() + "/login.html");
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
