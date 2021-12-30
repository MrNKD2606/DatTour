/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Employee;
import entity.Oto;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author DuyNguyen
 */
public class EmployeesModel extends ConnectionUtil {

    public List<Employee> getListEmployees() throws Exception {
        List<Employee> listDriver = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE status != 0 ORDER BY id_employee";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Employee employee = new Employee();
                employee.setIdEmployee(mRs.getInt("id_employee"));
                employee.setNameEmployee(mRs.getString("name_employee"));
                employee.setBirthDay(mRs.getDate("birth_day"));
                employee.setGender(mRs.getInt("gender"));
                employee.setPhoneNumber(mRs.getString("phone_number"));
                employee.setIdNo(mRs.getString("id_no"));
                employee.setIssueDate(mRs.getDate("issue_date"));
                employee.setIssuePlace(mRs.getString("issue_place"));
                employee.setAddress(mRs.getString("address"));
                employee.setChgWho(mRs.getString("chg_who"));
                employee.setChgDate(mRs.getDate("chg_date"));
                employee.setStatus(mRs.getInt("status"));
                employee.setRole(mRs.getInt("role"));

                listDriver.add(employee);
            }
        } finally {
            close();
        }
        return listDriver;
    }
    
    public List<Employee> getListTourGuide() throws Exception {
        List<Employee> listTourGuide = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE status != 0 and role = 2 ORDER BY id_employee";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Employee employee = new Employee();
                employee.setIdEmployee(mRs.getInt("id_employee"));
                employee.setNameEmployee(mRs.getString("name_employee"));
                employee.setBirthDay(mRs.getDate("birth_day"));
                employee.setGender(mRs.getInt("gender"));
                employee.setPhoneNumber(mRs.getString("phone_number"));
                employee.setIdNo(mRs.getString("id_no"));
                employee.setIssueDate(mRs.getDate("issue_date"));
                employee.setIssuePlace(mRs.getString("issue_place"));
                employee.setAddress(mRs.getString("address"));
                employee.setChgWho(mRs.getString("chg_who"));
                employee.setChgDate(mRs.getDate("chg_date"));
                employee.setStatus(mRs.getInt("status"));
                employee.setRole(mRs.getInt("role"));

                listTourGuide.add(employee);
            }
        } finally {
            close();
        }
        return listTourGuide;
    }
    
    public List<Employee> getListTourGuideNotTour() throws Exception {
        List<Employee> listTourGuide = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE status = 2 and role = 2 ORDER BY id_employee";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Employee employee = new Employee();
                employee.setIdEmployee(mRs.getInt("id_employee"));
                employee.setNameEmployee(mRs.getString("name_employee"));
                employee.setBirthDay(mRs.getDate("birth_day"));
                employee.setGender(mRs.getInt("gender"));
                employee.setPhoneNumber(mRs.getString("phone_number"));
                employee.setIdNo(mRs.getString("id_no"));
                employee.setIssueDate(mRs.getDate("issue_date"));
                employee.setIssuePlace(mRs.getString("issue_place"));
                employee.setAddress(mRs.getString("address"));
                employee.setChgWho(mRs.getString("chg_who"));
                employee.setChgDate(mRs.getDate("chg_date"));
                employee.setStatus(mRs.getInt("status"));
                employee.setRole(mRs.getInt("role"));

                listTourGuide.add(employee);
            }
        } finally {
            close();
        }
        return listTourGuide;
    }

    public void insert(Employee employee) throws Exception {
        String sql = "insert into employees(name_employee, birth_day, gender, phone_number, id_no, issue_date, issue_place, address, chg_who, chg_date, status, role)"
                + "value(?,?,?,?,?,?,?,?,?,?,2,2)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, employee.getNameEmployee());
            mStmt.setDate(2, DateUtils.getSQLDate(employee.getBirthDay()));
            mStmt.setInt(3, employee.getGender());
            mStmt.setString(4, employee.getPhoneNumber());
            mStmt.setString(5, employee.getIdNo());
            mStmt.setDate(6, DateUtils.getSQLDate(employee.getIssueDate()));
            mStmt.setString(7, employee.getIssuePlace());
            mStmt.setString(8, employee.getAddress());
            mStmt.setString(9, employee.getChgWho());
            mStmt.setDate(10, DateUtils.getSQLDate(employee.getChgDate()));
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                employee.setIdEmployee(mRs.getInt(1));
                employee.setStatus(2);
            }
        } finally {
            close();
        }
    }

    public void update(Employee employee) throws Exception {
        String sql = "UPDATE employees "
                + "SET name_employee = ?, birth_day = ?, gender = ?, phone_number = ?, id_no = ?, issue_date = ?, issue_place = ?, address = ?, chg_who = ?, chg_date = ?"
                + "WHERE id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, employee.getNameEmployee());
            mStmt.setDate(2, DateUtils.getSQLDate(employee.getBirthDay()));
            mStmt.setInt(3, employee.getGender());
            mStmt.setString(4, employee.getPhoneNumber());
            mStmt.setString(5, employee.getIdNo());
            mStmt.setDate(6, DateUtils.getSQLDate(employee.getIssueDate()));
            mStmt.setString(7, employee.getIssuePlace());
            mStmt.setString(8, employee.getAddress());
            mStmt.setString(9, employee.getChgWho());
            mStmt.setDate(10, DateUtils.getSQLDate(employee.getChgDate()));
            mStmt.setInt(11, employee.getIdEmployee());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void remove(Employee employee) throws Exception {
        String sql = "UPDATE employees "
                + "SET chg_who = ?, chg_date = ?, status = 0 "
                + "WHERE id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, employee.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(employee.getChgDate()));
            mStmt.setInt(3, employee.getIdEmployee());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }
    
    public Employee getTourGuideById(int idTourGuide) throws Exception{
        Employee tourGuide = new Employee();
        String sql = "SELECT * FROM employees WHERE status != 0 and id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTourGuide);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                tourGuide.setIdEmployee(mRs.getInt("id_employee"));
                tourGuide.setNameEmployee(mRs.getString("name_employee"));
                tourGuide.setStatus(mRs.getInt("status"));
            }

        } finally {
            close();
        }
        return tourGuide;
    }
    
}
