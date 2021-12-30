/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Tour;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author DuyNguyen
 */
public class TourModel extends ConnectionUtil implements Serializable {

    public int getPeople(int idTour) throws Exception {
        String sql = "SELECT number_people FROM tour WHERE id_tour = ?";
        int a = 0;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                a = mRs.getInt("number_people");
            }
        } finally {
            close();
        }
        return a;
    }

    public List<Tour> getListTour() throws Exception {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM tour WHERE status != 0 and status != 3";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Tour tour = new Tour();
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
                Date now = new Date();
                LocalDate s = convertToLocalDate(tour.getDateStart());
                LocalDate e = convertToLocalDate(now);
                if (e.isAfter(s)) {
                    if (tour.getStatus() == 2) {
                        PreparedStatement stmtMap1 = null;
                        ResultSet rsMap1 = null;
                        String sql1 = "select id_cart from cart where id_tour = ?";
                        stmtMap1 = mConnection.prepareStatement(sql1);
                        stmtMap1.setInt(1, tour.getIdTour());
                        rsMap1 = stmtMap1.executeQuery();
                        int a = 0;
                        while (rsMap1.next()) {
                            a = rsMap1.getInt("id_cart");
                            break;
                        }
                        PreparedStatement stmtMap = null;
                        ResultSet rsMap = null;
                        if (a == 0) {
                            String sql2 = "update tour set status = 0 where id_tour = ?";
                            stmtMap = mConnection.prepareStatement(sql2);
                            stmtMap.setInt(1, tour.getIdTour());
                            stmtMap.executeUpdate();
                            tour.setStatus(0);
                        } else {
                            String sql2 = "update tour set status = 1 where id_tour = ?";
                            stmtMap = mConnection.prepareStatement(sql2);
                            stmtMap.setInt(1, tour.getIdTour());
                            stmtMap.executeUpdate();
                            tour.setStatus(1);
                            list.add(tour);
                        }
                    } else {
                        list.add(tour);
                    }
                } else {
                    list.add(tour);
                }
            }
        } finally {
            close();
        }
        return list;
    }

    public List<Tour> getListTour3() throws Exception {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM tour WHERE status = 3";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Tour tour = new Tour();
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
                list.add(tour);
            }
        } finally {
            close();
        }
        return list;
    }

    public void insert(Tour tour) throws Exception {
        String sql = "INSERT INTO tour(name_tour, description, date_start, number_day, number_people, price, id_departure_place, id_destination_place, id_tour_guide, id_oto, chg_who, chg_date, status) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,2)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, tour.getNameTour());
            mStmt.setString(2, tour.getDescription());
            mStmt.setDate(3, DateUtils.getSQLDate(tour.getDateStart()));
            mStmt.setInt(4, tour.getNumberDay());
            mStmt.setInt(5, tour.getNumberPeople());
            mStmt.setInt(6, tour.getPrice());
            mStmt.setInt(7, tour.getIdDeparturePlace());
            mStmt.setInt(8, tour.getIdDestinationPlace());
            mStmt.setInt(9, tour.getIdTourGuide());
            mStmt.setInt(10, tour.getIdOto());
            mStmt.setString(11, tour.getChgWho());
            mStmt.setDate(12, DateUtils.getSQLDate(tour.getChgDate()));
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                tour.setIdTour(mRs.getInt(1));
                tour.setStatus(2);
            }
        } finally {
            close();
        }
        if (tour.getIdOto() != 0) {
            updateStatusDriver(tour.getIdOto(), 1);
        }
        if (tour.getIdTourGuide() != 0) {
            updateStatusTourGuide(tour.getIdTourGuide(), 1);
        }
    }

    public void update(Tour tour) throws Exception {
        int idTourGuide = 0, idOto = 0;
        if (tour.getIdTourGuide() != 0 || tour.getIdOto() != 0) {
            String sql2 = "SELECT id_tour_guide, id_oto FROM tour WHERE id_tour = ?";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql2);
                mStmt.setInt(1, tour.getIdTour());
                mRs = mStmt.executeQuery();
                while (mRs.next()) {
                    idTourGuide = mRs.getInt("id_tour_guide");
                    idOto = mRs.getInt("id_oto");
                }
            } finally {
                close();
            }
        }
        String sql = "UPDATE tour SET name_tour = ?, description = ?, date_start = ?, number_day = ?, number_people = ?, price = ?, id_departure_place = ?, id_destination_place = ?, id_tour_guide = ?, id_oto = ?, chg_who = ?, chg_date = ? WHERE id_tour = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, tour.getNameTour());
            mStmt.setString(2, tour.getDescription());
            mStmt.setDate(3, DateUtils.getSQLDate(tour.getDateStart()));
            mStmt.setInt(4, tour.getNumberDay());
            mStmt.setInt(5, tour.getNumberPeople());
            mStmt.setInt(6, tour.getPrice());
            mStmt.setInt(7, tour.getIdDeparturePlace());
            mStmt.setInt(8, tour.getIdDestinationPlace());
            mStmt.setInt(9, tour.getIdTourGuide());
            mStmt.setInt(10, tour.getIdOto());
            mStmt.setString(11, tour.getChgWho());
            mStmt.setDate(12, DateUtils.getSQLDate(tour.getChgDate()));
            mStmt.setInt(13, tour.getIdTour());
            mStmt.executeUpdate();
        } finally {
            close();
        }
        if (idTourGuide != tour.getIdTourGuide()) {
            updateStatusTourGuide(idTourGuide, 2);
            updateStatusTourGuide(tour.getIdTourGuide(), 1);
        }
        if (idOto != tour.getIdOto()) {
            updateStatusDriver(idOto, 2);
            updateStatusDriver(tour.getIdOto(), 1);
        }
    }

    public void remove(Tour tour) throws Exception {
        int idTourGuide = 0, idOto = 0;
        if (tour.getIdTourGuide() != 0 || tour.getIdOto() != 0) {
            String sql2 = "SELECT id_tour_guide, id_oto FROM tour WHERE id_tour = ?";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql2);
                mStmt.setInt(1, tour.getIdTour());
                mRs = mStmt.executeQuery();
                while (mRs.next()) {
                    idTourGuide = mRs.getInt("id_tour_guide");
                    idOto = mRs.getInt("id_oto");
                }
            } finally {
                close();
            }
        }

        String sql = "UPDATE tour SET id_tour_guide = 0, id_oto = 0, status = 0, chg_who = ?, chg_date = ? WHERE id_tour = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, tour.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(tour.getChgDate()));
            mStmt.setInt(3, tour.getIdTour());
            mStmt.executeUpdate();
        } finally {
            close();
        }
        if (idTourGuide != 0) {
            updateStatusTourGuide(idTourGuide, 2);
        }
        if (idOto != 0) {
            updateStatusDriver(idOto, 2);
        }
    }

    public void updateStatusDriver(int idOto, int status) throws Exception {
        String sql = "UPDATE employees SET status = ? WHERE id_employee = (SELECT id_employee FROM oto WHERE id_oto = ?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, status);
            mStmt.setInt(2, idOto);
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void updateStatusTourGuide(int idTourGuide, int status) throws Exception {
        String sql = "UPDATE employees SET status = ? WHERE id_employee = ? and role = 2";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, status);
            mStmt.setInt(2, idTourGuide);
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public Date getStartDayToTour(int idTour) throws Exception {
        String sql = "SELECT date_start FROM tour where id_tour = ?";
        Date time = null;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                time = mRs.getDate("date_start");
            }
        } finally {
            close();
        }
        return time;
    }

    public void pay(Tour tour) throws Exception {
        String sql = "UPDATE tour SET status = 3, chg_who = ?, chg_date = ? WHERE id_tour = ? ";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, tour.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(tour.getChgDate()));
            mStmt.setInt(3, tour.getIdTour());
            mStmt.executeUpdate();
        } finally {
            close();
        }
        if (tour.getIdTourGuide() != 0) {
            String sql1 = "UPDATE employees SET status = 2, chg_who = ?, chg_date = ? WHERE id_employee = ? ";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql1);
                mStmt.setString(1, tour.getChgWho());
                mStmt.setDate(2, DateUtils.getSQLDate(tour.getChgDate()));
                mStmt.setInt(3, tour.getIdTourGuide());
                mStmt.executeUpdate();
            } finally {
                close();
            }
        }
        if (tour.getIdOto() != 0) {
            String sql2 = "UPDATE employees SET status = 2, chg_who = ?, chg_date = ? WHERE id_employee = (SELECT id_employee FROM oto WHERE id_oto = ?) ";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql2);
                mStmt.setString(1, tour.getChgWho());
                mStmt.setDate(2, DateUtils.getSQLDate(tour.getChgDate()));
                mStmt.setInt(3, tour.getIdOto());
                mStmt.executeUpdate();
            } finally {
                close();
            }
        }
    }

    public String getNameDriverIdTour(int idTour) throws Exception {
        String sql = "SELECT name_employee FROM employees WHERE id_employee = (SELECT id_employee FROM oto WHERE id_oto = (SELECT id_oto FROM tour where id_tour = ?))";
        String name = "";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                name = mRs.getString("name_employee");
            }
        } finally {
            close();
        }
        return name;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
