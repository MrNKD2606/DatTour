/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import entity.City;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;

/**
 *
 * @author DuyNguyen
 */
public class CityModel extends ConnectionUtil {

    public List<City> getListCity() throws Exception {
        List<City> list = new ArrayList<>();
        String sql = "SELECT * FROM city";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {                
                City city = new City();
                city.setIdCity(mRs.getInt("id_city"));
                city.setNameCity(mRs.getString("name_city"));
                list.add(city);
            }
        } finally {
            close();
        }
        return list;
    }

    public String getNameCity(int idCity) throws Exception {
        String sql = "SELECT * FROM city WHERE id_city = ?";
        String name = "";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idCity);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                name = mRs.getString("name_city");
            }
        } finally {
            close();
        }
        return name;
    }
}
