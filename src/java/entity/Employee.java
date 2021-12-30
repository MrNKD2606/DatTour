/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author DuyNguyen
 */
public class Employee implements Serializable {

    private int idEmployee;
    private String nameEmployee;
    private Date birthDay;
    private int gender;
    private String idNo;
    private Date issueDate;
    private String issuePlace;
    private String address;
    private String phoneNumber;
    private String chgWho;
    private Date chgDate;
    private int status;
    private int role;

    public Employee() {
    }

    public Employee(Employee obj) {
        this.idEmployee = obj.idEmployee;
        this.nameEmployee = obj.nameEmployee;
        this.birthDay = obj.birthDay;
        this.gender = obj.gender;
        this.idNo = obj.idNo;
        this.issueDate = obj.issueDate;
        this.issuePlace = obj.issuePlace;
        this.address = obj.address;
        this.phoneNumber = obj.phoneNumber;
        this.chgWho = obj.chgWho;
        this.chgDate = obj.chgDate;
        this.status = obj.status;
        this.role = obj.role;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getChgWho() {
        return chgWho;
    }

    public void setChgWho(String chgWho) {
        this.chgWho = chgWho;
    }

    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
