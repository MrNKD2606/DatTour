/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DuyNguyen
 */
public class ConnectionUtil {

    private static String DB_URL = "jdbc:mysql://localhost:3306/tour_du_lich";
    private static String USER_NAME = "root";
    private static String PASSWORD = "123456";

    public Connection mConnection = null;
    public PreparedStatement mStmt = null;
    public ResultSet mRs = null;

    public Connection getConnection(String dbURL, String userName, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }

    public void open() throws Exception {
        mConnection = getConnection(DB_URL, USER_NAME, PASSWORD);
    }

    public void close(Connection cnn) throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public void close(PreparedStatement stmt) throws Exception {
        if (stmt != null) {
            stmt.close();
        }
    }

    public void close(ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
    }

    public void close() throws Exception {
        if (mRs != null) {
            mRs.close();
        }
        if (mStmt != null) {
            mStmt.close();
        }
        if (mConnection != null) {
            mConnection.close();
        }
    }
}
