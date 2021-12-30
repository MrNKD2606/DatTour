/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.TypeOto;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;

/**
 *
 * @author DuyNguyen
 */
public class TypeOtoModel extends ConnectionUtil {

    public List<TypeOto> getListTypeOto() throws Exception {
        List<TypeOto> list = new ArrayList<>();
        String sql = "SELECT * FROM type_oto WHERE status = 1 ORDER BY id_type_oto";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                TypeOto typeOto = new TypeOto();
                typeOto.setIdTypeOto(mRs.getInt("id_type_oto"));
                typeOto.setNameTypeOto(mRs.getString("name_type_oto"));
                typeOto.setStatus(mRs.getInt("status"));

                list.add(typeOto);
            }
        } finally {
            close();
        }
        return list;
    }

    public void insert(TypeOto typeOto) throws Exception {
        String sql = "INSERT INTO type_oto(name_type_oto, status) VALUE(?,1)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, typeOto.getNameTypeOto());
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                typeOto.setIdTypeOto(mRs.getInt(1));
                typeOto.setStatus(1);
            }
        } finally {
            close();
        }
    }

    public void update(TypeOto typeOto) throws Exception {
        String sql = "update type_oto "
                + "SET name_type_oto = ? "
                + "where id_type_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, typeOto.getNameTypeOto());
            mStmt.setLong(2, typeOto.getIdTypeOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void remove(TypeOto typeOto) throws Exception {
        String sql = "UPDATE type_oto SET status = 0 WHERE id_type_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, typeOto.getIdTypeOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }
}
