package com.ithuike;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BatchInsert {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc_demo?serverTimezone=GMT&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String SQL = "insert into teacher(id,name,course,birthday) values(?,?,?,?)";
    
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
                int batchSize = 100;

                //设置插入数据
                for (int i = 1; i <= 500; i++) {
                    ps.setInt(1, i);
                    ps.setString(2, "name" + i);
                    ps.setString(3, "course" + i);

                    //插入Date类型数据
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date utilDate = sdf.parse("2000-01-01" + i);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    ps.setDate(4, sqlDate);

                    //每一百个提交
                    ps.addBatch();
                    if (i % batchSize == 0) {
                        ps.executeBatch();
                        connection.commit();
                    }
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
