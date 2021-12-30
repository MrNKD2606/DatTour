/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author DuyNguyen
 */
public class DateUtils implements Serializable {

    public static Date addYear(Date date, int year) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        return c.getTime();
    }

    public static String convertDate(Date dtDate, String formatDate) throws Exception {
        if (dtDate == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
            return formatter.format(dtDate);
        }
    }

    public static java.sql.Date getSQLDate(Date dtDate) throws Exception {
        if (dtDate != null) {
            return new java.sql.Date(dtDate.getTime());
        } else {
            return null;
        }
    }

    public static java.sql.Date convertLocalDateToSQLDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

    public static Date addDay(String date, int number) throws Exception {
        Date dt = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(date));
        cal.add(Calendar.DATE, number);
        dt = cal.getTime();
        return dt;
    }
}
