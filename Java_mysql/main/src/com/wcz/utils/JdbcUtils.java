package com.wcz.utils;

import java.sql.*;

public class JdbcUtils {
    //连接数据库的工具类
    // mysql-connector-java-xxx.jar的作用是为了提供Java连接Mysql的能力

    //1.创建数据库连接
    private static Connection conn;

    //2.创建连接方法
    public static Connection getConnection() {

        try {
            //2.1 加载数据驱动"com.mysql.cj.jdbc.Driver"
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.2配置访问数据库的信息，包含url，username，password
            //2.3 mysql 8.0 以上需要配置全球时差
            String url = "jdbc:mysql://localhost:3306/shopmanager?serverTimezone=UTC&UserSSL=false";
            String username = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
//  3.关闭数据库  (连接数据库时会开启一个结果集ResultSet(查询的结果，更新-增-删-改 0或n)，
//                               数据申明PrepareStatement （where id = 1001 或者条件后面的赋值）
//                               连接对象Conn )

    public static void closeConnection(ResultSet rs, PreparedStatement ps, Connection conn) {

        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //public static void main(String[] args) {
//        System.out.print(JdbcUtils.getConnection());
 //   }
}
