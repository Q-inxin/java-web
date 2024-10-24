package com.ithuike;

import java.sql.*;

public class Delete {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc_demo?serverTimezone=GMT&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String SQL = "delete from teacher where id = ?";


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
                //设置删除数据
                ps.setInt(1, 1);
                //进行删除
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
