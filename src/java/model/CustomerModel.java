/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Cart;
import entity.Customer;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author Admin
 */
public class CustomerModel extends ConnectionUtil {

    public void insert(List<Customer> mlstCustomer, int idCart, int idTour) throws Exception {
        for (Customer customer : mlstCustomer) {
            String sql = "INSERT INTO customer(name_customer, gender, birth_day, id_cart, id_tour) "
                    + "VALUES(?,?,?,?,?)";
            try {
                open();
                mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                mStmt.setString(1, customer.getNameCustomer());
                mStmt.setInt(2, customer.getGender());
                mStmt.setDate(3, DateUtils.getSQLDate(customer.getBirthDay()));
                mStmt.setInt(4, idCart);
                mStmt.setInt(5, idTour);
                mStmt.executeUpdate();
                mRs = mStmt.getGeneratedKeys();
                if (mRs.next()) {
                    customer.setIdCustomer(mRs.getInt(1));
                    customer.setIdCart(idCart);
                    customer.setIdTour(idTour);
                }
            } finally {
                close();
            }
        }
    }

    public void deleteCart(int idCart) throws Exception {
        String sql = "UPDATE customer "
                + "SET id_cart = 0 "
                + "WHERE id_cart = ?";
        Customer customer = new Customer();
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idCart);
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void insertCustomer(Customer customer, Cart cart) throws Exception {
        String sql = "INSERT INTO customer(name_customer, gender, birth_day, id_cart, id_tour) "
                + "VALUES(?,?,?,?,?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, customer.getNameCustomer());
            mStmt.setInt(2, customer.getGender());
            mStmt.setDate(3, DateUtils.getSQLDate(customer.getBirthDay()));
            mStmt.setInt(4, cart.getIdCart());
            mStmt.setInt(5, cart.getIdTour());
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                customer.setIdCustomer(mRs.getInt(1));
                customer.setIdCart(cart.getIdCart());
                customer.setIdTour(cart.getIdTour());
            }
        } finally {
            close();
        }
    }

    public void updateCustomer(Customer customer, Cart cart) throws Exception {
        String sql = "UPDATE customer "
                + "SET name_customer = ?, gender = ?, birth_day = ?, id_cart = ?, id_tour = ? "
                + "WHERE id_customer = ?";

        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, customer.getNameCustomer());
            mStmt.setInt(2, customer.getGender());
            mStmt.setDate(3, DateUtils.getSQLDate(customer.getBirthDay()));
            mStmt.setInt(4, cart.getIdCart());
            mStmt.setInt(5, cart.getIdTour());
            mStmt.setInt(6, customer.getIdCustomer());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void update(List<Customer> mlstCustomer, Cart cart) throws Exception {
        //update database có id_tour = cart.idtour về id_cart = 0
        deleteCart(cart.getIdCart());
        //Lấy list có id_tour và id_card = 0
        List<Customer> list = getListCustomerToTour(cart.getIdTour());

        if (mlstCustomer.size() <= list.size()) {
            for (int i = 0; i < mlstCustomer.size(); i++) {
                mlstCustomer.get(i).setIdCustomer(list.get(i).getIdCustomer());
                updateCustomer(mlstCustomer.get(i), cart);
            }
        } else {
            for (int i = 0; i < mlstCustomer.size(); i++) {
                if (i < list.size()) {
                    mlstCustomer.get(i).setIdCustomer(list.get(i).getIdCustomer());
                    updateCustomer(mlstCustomer.get(i), cart);
                } else {
                    insertCustomer(mlstCustomer.get(i), cart);
                }
            }
        }

    }

    public List<Customer> getListCustomerToTour(int idTour) throws Exception {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE id_cart = 0 and id_tour = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Customer customer = new Customer();
                customer.setIdCustomer(mRs.getInt("id_customer"));
                customer.setNameCustomer(mRs.getString("name_customer"));
                customer.setGender(mRs.getInt("gender"));
                customer.setBirthDay(mRs.getDate("birth_day"));
                customer.setIdCart(mRs.getInt("id_cart"));
                customer.setIdTour(mRs.getInt("id_tour"));
                list.add(customer);
            }
        } finally {
            close();
        }
        return list;
    }

    public List<Customer> getListCustomerToCart(int idCart) throws Exception {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE id_cart = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idCart);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Customer customer = new Customer();
                customer.setIdCustomer(mRs.getInt("id_customer"));
                customer.setNameCustomer(mRs.getString("name_customer"));
                customer.setGender(mRs.getInt("gender"));
                customer.setBirthDay(mRs.getDate("birth_day"));
                customer.setIdCart(mRs.getInt("id_cart"));
                customer.setIdTour(mRs.getInt("id_tour"));
                list.add(customer);
            }
        } finally {
            close();
        }
        return list;
    }

    public int getNumberCus(int idTour) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Customer where id_tour = ?";
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
        return number;
    }

    public int getNumberCusOkCart(int idTour) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Customer where id_tour = ?";
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
        return number;
    }

    public int getNumberCusToCart(int idCart) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Customer where id_cart = ?";
        int number = 0;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idCart);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                number = mRs.getInt("count");
            }
        } finally {
            close();
        }
        return number;
    }

    public int getNumberCusTourNotCart(int idTour, int idCart) throws Exception {
        String sql = "SELECT COUNT(*) as count FROM Customer where id_tour = ? and id_cart != ? and id_cart != 0;";
        int number = 0;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mStmt.setInt(2, idCart);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                number = mRs.getInt("count");
            }
        } finally {
            close();
        }
        return number;
    }
    
    public int getSumCustomer(int idTour) throws Exception {
        String sql = "SELECT COUNT(id_tour) as sum FROM customer WHERE id_tour = ?";
        int sumCus = 0;
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTour);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                sumCus = mRs.getInt("sum");
            }
        } finally {
            close();
        }
        return sumCus;
    }

}
