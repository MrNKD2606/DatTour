/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Cart;
import entity.Employee;
import entity.Tour;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author Admin
 */
public class CartModel extends ConnectionUtil {

    public void insert(Cart cart) throws Exception {
        String sql = "INSERT INTO cart(name_orderer, address, description, email, phone_number, gender, total_money, id_tour, chg_who, chg_date, status) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,1)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, cart.getNameOrderer());
            mStmt.setString(2, cart.getAddress());
            mStmt.setString(3, cart.getDescription());
            mStmt.setString(4, cart.getEmail());
            mStmt.setString(5, cart.getPhoneNumber());
            mStmt.setInt(6, cart.getGender());
            mStmt.setInt(7, cart.getTotalMoney());
            mStmt.setInt(8, cart.getIdTour());
            mStmt.setString(9, cart.getChgWho());
            mStmt.setDate(10, DateUtils.getSQLDate(cart.getChgDate()));
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                cart.setIdCart(mRs.getInt(1));
                cart.setStatus(1);
            }
        } finally {
            close();
        }
    }

    public void update(Cart cart) throws Exception {
        String sql = "UPDATE cart "
                + "SET name_orderer = ?, address = ?, description = ?, email = ?, phone_number = ?, gender = ?, total_money = ?, chg_who = ?, chg_date = ?"
                + "WHERE id_cart = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, cart.getNameOrderer());
            mStmt.setString(2, cart.getAddress());
            mStmt.setString(3, cart.getDescription());
            mStmt.setString(4, cart.getEmail());
            mStmt.setString(5, cart.getPhoneNumber());
            mStmt.setInt(6, cart.getGender());
            mStmt.setInt(7, cart.getTotalMoney());
            mStmt.setString(8, cart.getChgWho());
            mStmt.setDate(9, DateUtils.getSQLDate(cart.getChgDate()));
            mStmt.setInt(10, cart.getIdCart());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void remove(Cart cart) throws Exception {
        String sql = "UPDATE cart "
                + "SET status = 0, chg_who = ?, chg_date = ? "
                + "WHERE id_cart = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, cart.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(cart.getChgDate()));
            mStmt.setInt(3, cart.getIdCart());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public List<Cart> getListTour() throws Exception {
        List<Cart> list = new ArrayList<>();
        String sql = "SELECT * FROM cart WHERE status != 0 and status != 3";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Cart cart = new Cart();
                cart.setIdCart(mRs.getInt("id_cart"));
                cart.setNameOrderer(mRs.getString("name_orderer"));
                cart.setAddress(mRs.getString("address"));
                cart.setDescription(mRs.getString("description"));
                cart.setEmail(mRs.getString("email"));
                cart.setPhoneNumber(mRs.getString("phone_number"));
                cart.setGender(mRs.getInt("gender"));
                cart.setTotalMoney(mRs.getInt("total_money"));
                cart.setIdTour(mRs.getInt("id_tour"));
                cart.setChgWho(mRs.getString("chg_who"));
                cart.setChgDate(mRs.getDate("chg_date"));
                cart.setStatus(mRs.getInt("status"));
                list.add(cart);
            }
        } finally {
            close();
        }
        return list;
    }

    public Tour getTour(int idTour) throws Exception {
        String sql = "SELECT * FROM tour WHERE id_tour = ?";
        Tour tour = new Tour();
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                tour.setIdTour(mRs.getInt("id_tour"));
                tour.setNameTour(mRs.getString("name_tour"));
                tour.setDescription(mRs.getString("description"));
                tour.setDateStart(mRs.getDate("date_start"));
                tour.setNumberDay(mRs.getInt("number_day"));
                tour.setNumberPeople(mRs.getInt("number_people"));
                tour.setPrice(mRs.getInt("price"));
                tour.setIdDeparturePlace(mRs.getInt("id_departure_place"));
                tour.setIdDestinationPlace(mRs.getInt("id_destination_place"));
                tour.setIdTourGuide(mRs.getInt("id_tour_guide"));
                tour.setIdOto(mRs.getInt("id_oto"));
                tour.setChgWho(mRs.getString("chg_who"));
                tour.setChgDate(mRs.getDate("chg_date"));
                tour.setStatus(mRs.getInt("status"));
            }
        } finally {
            close();
        }
        return tour;
    }

    public boolean getCheckTourToCart(int idTour) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM cart where id_tour = ?";
        int number = 0;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                number = mRs.getInt("count");
            }
        } finally {
            close();
        }
        if (number == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getMoneyToMonth(int month, int year) throws Exception {
        List<Tour> listTour = new ArrayList<>();
        int total = 0;
        String sql = "select * from tour where status = 3 and year(date_start) = ? and month(date_start) = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, year);
            mStmt.setInt(2, month);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Tour tour = new Tour();
                tour.setIdTour(mRs.getInt("id_tour"));
                listTour.add(tour);
            }
        } finally {
            close();
        }
        for (Tour tour : listTour) {
            total = total + getTotalTour(tour.getIdTour());
        }
        return total;
    }
    
    public int getMoneyToQuarter(int monthMin, int monthMax, int year) throws Exception {
        List<Tour> listTour = new ArrayList<>();
        int total = 0;
        String sql = "select * from tour where status = 3 and year(date_start) = ? and month(date_start) >= ? and month(date_start) <= ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, year);
            mStmt.setInt(2, monthMin);
            mStmt.setInt(3, monthMax);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Tour tour = new Tour();
                tour.setIdTour(mRs.getInt("id_tour"));
                listTour.add(tour);
            }
        } finally {
            close();
        }
        for (Tour tour : listTour) {
            total = total + getTotalTour(tour.getIdTour());
        }
        return total;
    }

    public int getTotalTour(Employee employee) throws Exception {
        List<Tour> listTour = new ArrayList<>();
        int total = 0;
        if (employee.getRole() == 2) {
            String sql = "SELECT * FROM tour WHERE status = 3 and id_tour_guide = ?";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql);
                mStmt.setInt(1, employee.getIdEmployee());
                mRs = mStmt.executeQuery();
                while (mRs.next()) {
                    Tour tour = new Tour();
                    tour.setIdTour(mRs.getInt("id_tour"));
                    listTour.add(tour);
                }
            } finally {
                close();
            }
            for (Tour tour : listTour) {
                total = total + getTotalTour(tour.getIdTour());
            }
            return (int) total / 10;
        } else {
            String sql = "SELECT * FROM tour WHERE status = 3 and id_oto = (select id_oto from oto where id_employee = ?)";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql);
                mStmt.setInt(1, employee.getIdEmployee());
                mRs = mStmt.executeQuery();
                while (mRs.next()) {
                    Tour tour = new Tour();
                    tour.setIdTour(mRs.getInt("id_tour"));
                    listTour.add(tour);
                }
            } finally {
                close();
            }
            for (Tour tour : listTour) {
                total = total + getTotalTour(tour.getIdTour());
            }
            return (int) total / 5;
        }
    }

    public int getTotalTour(int idTour) throws Exception {
        String sql = "SELECT SUM(total_money) as sum FROM cart WHERE id_tour = ?";
        int sumTotal = 0;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                sumTotal = mRs.getInt("sum");
            }
        } finally {
            close();
        }
        return sumTotal;
    }
}
