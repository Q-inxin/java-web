package com.ithuike;

import java.sql.*;

public class Retrieve {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc_demo?serverTimezone=GMT&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String SQL = "select * from teacher;";


    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            ps = connection.prepareStatement(SQL);
            //执行查询语句
            rs = ps.executeQuery();
            //遍历输出查询结果
            while (rs.next()){
                System.out.println("id: " + rs.getInt("id"));
                System.out.println("name: " + rs.getString("name"));
                System.out.println("course: " + rs.getString("course"));
                System.out.println("birthday: " + rs.getDate("birthday"));
                System.out.println("===================================");
            }
            System.out.println("connection: " + connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if(ps !=null){
                try{
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
