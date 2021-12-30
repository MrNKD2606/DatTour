/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import entity.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import model.LoginModel;
import util.MessagesUtils;
import util.SessionUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

    private User user;
    private LoginModel loginModel;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        user = new User();
        loginModel = new LoginModel();
    }

    public String handLogin() {
        try {
            boolean checkLogin = loginModel.checkLogin(user.getUsername(), user.getPassword());
            if (checkLogin) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", user.getUsername());
                return "/admin?faces-redirect=true";
            } else {
                MessagesUtils.error("Lỗi", "Tên đăng nhập hoặc mật khẩu không đúng");
                return "";
            }
        } catch (Exception ex) {
            MessagesUtils.error("", ex.getMessage());
        }
        return "";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public static String getUserLogin() {
        SessionUtils.getRequest();
        HttpSession session = SessionUtils.getSession();
        return (String) session.getAttribute("username");
    }
}
