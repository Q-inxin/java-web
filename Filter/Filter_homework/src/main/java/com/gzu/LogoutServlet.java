package com.gzu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取当前会话
        HttpSession session = req.getSession(false);

        // 清除session中的用户信息，使session失效
        session.removeAttribute("user");
        session.invalidate();

        // 重定向到登录页面
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}