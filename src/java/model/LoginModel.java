/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.ConnectionUtil;

/**
 *
 * @author DuyNguyen
 */
public class LoginModel extends ConnectionUtil {
    
    public boolean checkLogin(String username, String password) throws Exception {
        boolean blcheck = false;
        String sql = "SELECT * FROM user WHERE user_name = ? and password = ? and status = 1";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, username);
            mStmt.setString(2, password);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {                
                blcheck = true;
            }
        } finally {
            close();
        }
        return blcheck;
    }
}
