/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entity.Customer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DuyNguyen
 */
public class ActionUtil {

    protected boolean isAdd;
    protected boolean isEdit;
    protected boolean isView;
    protected boolean isCopy;

    protected boolean isTypeOto;
    protected boolean isOto;

    protected boolean isEditBill;
    protected boolean isViewBill;

    protected Date sysDate;

    public ActionUtil() {
        isAdd = false;
        isEdit = false;
        isView = false;
        isCopy = false;

        isTypeOto = false;
        isOto = false;

        isEditBill = false;
        isViewBill = false;

        sysDate = new Date();
    }

    public void changeStateAdd() {
        isAdd = true;
        isEdit = false;
        isView = false;
        isCopy = false;
    }

    public void changeStateEdit() {
        isAdd = false;
        isEdit = true;
        isView = false;
        isCopy = false;
    }

    public void changeStateView() {
        isAdd = false;
        isEdit = false;
        isView = true;
        isCopy = false;
    }

    public void changeStateCopy() {
        isAdd = false;
        isEdit = false;
        isView = false;
        isCopy = true;
    }

    public void changeStateEditBill() {
        isEditBill = true;
        isViewBill = false;
    }

    public void changeStateViewBill() {
        isEditBill = false;
        isViewBill = true;
    }

    public void changeStateTypeOto() {
        isTypeOto = true;
        isOto = false;
    }

    public void changeStateOto() {
        isTypeOto = false;
        isOto = true;
    }

    public void handCancel() {
        isAdd = false;
        isEdit = false;
        isView = false;
        isCopy = false;

        isTypeOto = false;
        isOto = false;
    }

    public void handCancelBill() {
        isEditBill = false;
        isViewBill = false;
    }

    public boolean displayMainBill() {
        if (!isEditBill && !isViewBill) {
            return true;
        } else {
            return false;
        }
    }

    public boolean displayMain() {
        if (!isAdd && !isEdit && !isView && !isCopy) {
            return true;
        } else {
            return false;
        }
    }

    public boolean displayDetail() {
        if (isTypeOto) {
            return true;
        } else if (isOto) {
            return false;
        }
        return false;
    }

    public String getNgaySinhView(Date dateNgaySinh) {
        try {
            if (dateNgaySinh != null) {
                return DateUtils.convertDate(dateNgaySinh, "dd/MM/yyyy");
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
        return "";
    }

    public int getTongTien(List<Customer> mlstCustomer, Date end, int price) {
        int tongTien = 0;
        if (mlstCustomer == null) {
            return 0;
        }
        for (Customer customer : mlstCustomer) {
            tongTien = tongTien + getTien(getAge(customer.getBirthDay(), end), price);
        }
        return tongTien;
    }

    public String getDoTuoi(int age) {
        if (age >= 12) {
            return "Người lớn";
        } else if (age >= 5) {
            return "Trẻ em";
        } else if (age >= 2) {
            return "Trẻ nhỏ";
        } else {
            return "Em bé";
        }
    }

    public int getTien(int age, int price) {
        if (age >= 12) {
            return price;
        } else if (age >= 5) {
            return (int) (price * 0.5);
        } else if (age >= 2) {
            return (int) (price * 0.25);
        } else {
            return (int) (price * 0.1);
        }
    }

    public int getAge(Date start, Date end) {
        int tuoi = 0;
        //LocalDate s = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate s = convertToLocalDate(start);
        LocalDate e = convertToLocalDate(end);
        Period t = Period.between(s, e);
        tuoi = t.getYears();
        return tuoi;
    }

    public int getDay(Date start, Date end) {
        int day = 0;
        //LocalDate s = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate s = convertToLocalDate(start);
        LocalDate e = convertToLocalDate(end);
        Period t = Period.between(s, e);
        day = t.getDays();
        return day;
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public String getVND(long l) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(l) + " VNĐ";
    }

    public boolean isIsAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean isIsView() {
        return isView;
    }

    public void setIsView(boolean isView) {
        this.isView = isView;
    }

    public boolean isIsCopy() {
        return isCopy;
    }

    public void setIsCopy(boolean isCopy) {
        this.isCopy = isCopy;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public boolean isIsTypeOto() {
        return isTypeOto;
    }

    public void setIsTypeOto(boolean isTypeOto) {
        this.isTypeOto = isTypeOto;
    }

    public boolean isIsOto() {
        return isOto;
    }

    public void setIsOto(boolean isOto) {
        this.isOto = isOto;
    }

    public boolean isIsEditBill() {
        return isEditBill;
    }

    public void setIsEditBill(boolean isEditBill) {
        this.isEditBill = isEditBill;
    }

    public boolean isIsViewBill() {
        return isViewBill;
    }

    public void setIsViewBill(boolean isViewBill) {
        this.isViewBill = isViewBill;
    }

}
