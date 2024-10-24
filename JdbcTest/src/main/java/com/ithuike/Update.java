package com.ithuike;

import java.sql.*;

public class Update {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc_demo?serverTimezone=GMT&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String SQL = "update teacher set course = ? where id = ?";


    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            //关闭自动提交
            connection.setAutoCommit(false);
            try {
                ps = connection.prepareStatement(SQL);
                //设置更新数据
                ps.setString(1, "WebUI");
                ps.setInt(2, 1);
                //进行更新
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
