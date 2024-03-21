package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {

    private static Connection conn = null;

    public static Connection getConnection() {
        if ( conn == null ) {
            String className = "oracle.jdbc.driver.OracleDriver";
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String user = "scott";
            String password = "tiger";

            try {
                //1. 드라이버 로딩
                Class.forName(className);
                //2. conn 확인
                conn = DriverManager.getConnection(url,user,password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return conn;
    }

    // 오버로딩
    public static Connection getConnection(String url, String user, String password) {
        if ( conn == null ) {
            String className = "oracle.jdbc.driver.OracleDriver";
            try {
                //1. 드라이버 로딩
                Class.forName(className);
                //2. conn 확인
                conn = DriverManager.getConnection(url,user,password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return conn;
    }

    public static void close() {
        try {
            if ( conn != null && !conn.isClosed()) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        conn = null;
    }

}
