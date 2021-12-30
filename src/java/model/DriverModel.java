/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Driver;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author DuyNguyen
 */
public class DriverModel extends ConnectionUtil {

    public List<Driver> getListDriver() throws Exception {
        List<Driver> listDriver = new ArrayList<>();
        String sql = "select * from employees left join oto on employees.id_employee = oto.id_employee where role = 1 and employees.status != 0";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Driver driver = new Driver();
                driver.setIdEmployee(mRs.getInt("id_employee"));
                driver.setNameEmployee(mRs.getString("name_employee"));
                driver.setBirthDay(mRs.getDate("birth_day"));
                driver.setGender(mRs.getInt("gender"));
                driver.setPhoneNumber(mRs.getString("phone_number"));
                driver.setIdNo(mRs.getString("id_no"));
                driver.setIssueDate(mRs.getDate("issue_date"));
                driver.setIssuePlace(mRs.getString("issue_place"));
                driver.setAddress(mRs.getString("address"));
                driver.setChgWho(mRs.getString("chg_who"));
                driver.setChgDate(mRs.getDate("chg_date"));
                driver.setStatus(mRs.getInt("status"));
                driver.setRole(mRs.getInt("role"));
                driver.setIdTypeOto(mRs.getInt("id_type_oto"));
                driver.setIdOto(mRs.getInt("id_oto"));
                driver.setStatusOto(mRs.getInt("oto.status"));
                listDriver.add(driver);
            }
        } finally {
            close();
        }
        return listDriver;
    }

    public List<Driver> getListDriverNotOto() throws Exception {
        List<Driver> listDriver = new ArrayList<>();
        String sql = "select * from (select * from employees where status = 2 and role = 1) as A "
                + "where not exists (select * from oto where A.id_employee = oto.id_employee)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Driver driver = new Driver();
                driver.setIdEmployee(mRs.getInt("id_employee"));
                driver.setNameEmployee(mRs.getString("name_employee"));
                driver.setBirthDay(mRs.getDate("birth_day"));
                driver.setGender(mRs.getInt("gender"));
                driver.setPhoneNumber(mRs.getString("phone_number"));
                driver.setIdNo(mRs.getString("id_no"));
                driver.setIssueDate(mRs.getDate("issue_date"));
                driver.setIssuePlace(mRs.getString("issue_place"));
                driver.setAddress(mRs.getString("address"));
                driver.setChgWho(mRs.getString("chg_who"));
                driver.setChgDate(mRs.getDate("chg_date"));
                driver.setStatus(mRs.getInt("status"));
                driver.setRole(mRs.getInt("role"));
                listDriver.add(driver);
            }
        } finally {
            close();
        }
        return listDriver;
    }

    public Driver getDriverById(int idDriver) throws Exception{
        Driver driver = new Driver();
        String sql = "SELECT * FROM employees WHERE status != 0 and id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idDriver);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                driver.setIdEmployee(mRs.getInt("id_employee"));
                driver.setNameEmployee(mRs.getString("name_employee"));
                driver.setStatus(mRs.getInt("status"));
            }

        } finally {
            close();
        }
        return driver;
    }

    public void insert(Driver driver) throws Exception {
        String sql = "insert into employees(name_employee, birth_day, gender, phone_number, id_no, issue_date, issue_place, address, chg_who, chg_date, status, role)"
                + "value(?,?,?,?,?,?,?,?,?,?,2,1)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, driver.getNameEmployee());
            mStmt.setDate(2, DateUtils.getSQLDate(driver.getBirthDay()));
            mStmt.setInt(3, driver.getGender());
            mStmt.setString(4, driver.getPhoneNumber());
            mStmt.setString(5, driver.getIdNo());
            mStmt.setDate(6, DateUtils.getSQLDate(driver.getIssueDate()));
            mStmt.setString(7, driver.getIssuePlace());
            mStmt.setString(8, driver.getAddress());
            mStmt.setString(9, driver.getChgWho());
            mStmt.setDate(10, DateUtils.getSQLDate(driver.getChgDate()));
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                driver.setIdEmployee(mRs.getInt(1));
                driver.setStatus(2);
            }
        } finally {
            close();
        }
        String sql2 = "UPDATE oto SET id_employee = ?, status = ? WHERE id_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql2);
            mStmt.setInt(1, driver.getIdEmployee());
            mStmt.setInt(2, 1);
            mStmt.setInt(3, driver.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void update(Driver driver) throws Exception {
        String sql = "UPDATE employees "
                + "SET name_employee = ?, birth_day = ?, gender = ?, phone_number = ?, id_no = ?, issue_date = ?, issue_place = ?, address = ?, chg_who = ?, chg_date = ?"
                + "WHERE id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, driver.getNameEmployee());
            mStmt.setDate(2, DateUtils.getSQLDate(driver.getBirthDay()));
            mStmt.setInt(3, driver.getGender());
            mStmt.setString(4, driver.getPhoneNumber());
            mStmt.setString(5, driver.getIdNo());
            mStmt.setDate(6, DateUtils.getSQLDate(driver.getIssueDate()));
            mStmt.setString(7, driver.getIssuePlace());
            mStmt.setString(8, driver.getAddress());
            mStmt.setString(9, driver.getChgWho());
            mStmt.setDate(10, DateUtils.getSQLDate(driver.getChgDate()));
            mStmt.setInt(11, driver.getIdEmployee());
            mStmt.executeUpdate();
        } finally {
            close();
        }

        String sql2 = "UPDATE oto SET id_employee = ?, chg_who = ?, chg_date = ?, status = 1 WHERE id_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql2);
            mStmt.setInt(1, driver.getIdEmployee());
            mStmt.setString(2, driver.getChgWho());
            mStmt.setDate(3, DateUtils.getSQLDate(driver.getChgDate()));
            mStmt.setLong(4, driver.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }

        String sql3 = "UPDATE oto SET id_employee = 0, chg_who = ?, chg_date = ?, status = 2 WHERE id_employee = ? and id_oto != ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql3);
            mStmt.setString(1, driver.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(driver.getChgDate()));
            mStmt.setInt(3, driver.getIdEmployee());
            mStmt.setLong(4, driver.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void remove(Driver driver) throws Exception {
        String sql = "UPDATE employees "
                + "SET chg_who = ?, chg_date = ?, status = 0 "
                + "WHERE id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, driver.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(driver.getChgDate()));
            mStmt.setInt(3, driver.getIdEmployee());
            mStmt.executeUpdate();
        } finally {
            close();
        }

        String sql2 = "UPDATE oto SET chg_who = ?, chg_date = ?, id_employee = null, status = 2 WHERE id_employee = ? and id_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql2);
            mStmt.setString(1, driver.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(driver.getChgDate()));
            mStmt.setInt(3, driver.getIdEmployee());
            mStmt.setLong(4, driver.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }
}
