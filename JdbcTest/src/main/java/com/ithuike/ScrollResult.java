package com.ithuike;

import java.sql.*;

public class ScrollResult {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc_demo?serverTimezone=GMT&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String SQL = "select * from teacher where id < ?";
    
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = connection.prepareStatement(SQL, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, 50);
            try {
                rs = ps.executeQuery();
                //移动到倒数第二行
                rs.absolute(-2);
                System.out.println("id: " + rs.getInt("id"));
                System.out.println("name: " + rs.getString("name"));
                System.out.println("course: " + rs.getString("course"));
                System.out.println("birthday: " + rs.getDate("birthday"));
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
