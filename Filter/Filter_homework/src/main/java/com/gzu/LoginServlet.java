package com.gzu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//用户登录
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 检查用户名和密码是否正确
        if ("admin".equals(username) && "123456".equals(password)){
            // 信息正确，将用户名存储在session中，并重定向到welcome页面
            req.getSession().setAttribute("user", username);
            resp.sendRedirect("welcome.html");
        } else {
            // 信息错误，重定向login页面
            resp.sendRedirect("login.html");
        }
    }
}
