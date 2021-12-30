/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Oto;
import entity.TypeOto;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author DuyNguyen
 */
public class OtoModel extends ConnectionUtil {

    public List<Oto> getListOtoByIdTypeStatus2(int idTypeOto) throws Exception {
        List<Oto> list = new ArrayList<>();
        String sql = "SELECT * FROM oto WHERE status = 2 and id_type_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTypeOto);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Oto oto = new Oto();
                oto.setIdOto(mRs.getInt("id_oto"));
                oto.setNameOto(mRs.getString("name_oto"));
                oto.setIdTypeOto(mRs.getInt("id_type_oto"));
                oto.setStatus(mRs.getInt("status"));
                oto.setIdDriver(mRs.getInt("id_employee"));
                list.add(oto);
            }
        } finally {
            close();
        }
        return list;
    }

    public List<Oto> getListOtoByIdTypeStatus1(int idTypeOto) throws Exception {
        List<Oto> list = new ArrayList<>();
        String sql = "select * from oto inner join (select status, id_employee from employees where role = 1) as T on T.id_employee = oto.id_employee where oto.status = 1 and id_type_oto = ? and T.status = 2";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTypeOto);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Oto oto = new Oto();
                oto.setIdOto(mRs.getInt("id_oto"));
                oto.setNameOto(mRs.getString("name_oto"));
                oto.setIdTypeOto(mRs.getInt("id_type_oto"));
                oto.setStatus(mRs.getInt("status"));
                oto.setIdDriver(mRs.getInt("id_employee"));
                list.add(oto);
            }
        } finally {
            close();
        }
        return list;
    }

    public List<Oto> getListOtoByIdType(int idTypeOto) throws Exception {
        List<Oto> list = new ArrayList<>();
        String sql = "SELECT * FROM oto WHERE status != 0  and id_type_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idTypeOto);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Oto oto = new Oto();
                oto.setIdOto(mRs.getInt("id_oto"));
                oto.setNameOto(mRs.getString("name_oto"));
                oto.setIdTypeOto(mRs.getInt("id_type_oto"));
                oto.setStatus(mRs.getInt("status"));
                oto.setIdDriver(mRs.getInt("id_employee"));
                list.add(oto);
            }
        } finally {
            close();
        }
        return list;
    }

    public List<Oto> getListOto() throws Exception {
        List<Oto> list = new ArrayList<>();
        String sql = "SELECT * FROM oto WHERE status != 0";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Oto oto = new Oto();
                oto.setIdOto(mRs.getInt("id_oto"));
                oto.setNameOto(mRs.getString("name_oto"));
                oto.setIdTypeOto(mRs.getInt("id_type_oto"));
                oto.setStatus(mRs.getInt("status"));
                oto.setIdDriver(mRs.getInt("id_employee"));
                list.add(oto);
            }
        } finally {
            close();
        }
        return list;
    }

    public Oto getOtoByOto(int idOto) throws Exception {
        Oto oto = new Oto();
        String sql = "SELECT * FROM oto WHERE status != 0 and id_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idOto);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                oto.setIdOto(mRs.getInt("id_oto"));
                oto.setNameOto(mRs.getString("name_oto"));
                oto.setIdTypeOto(mRs.getInt("id_type_oto"));
                oto.setStatus(mRs.getInt("status"));
                oto.setIdDriver(mRs.getInt("id_employee"));
            }

        } finally {
            close();
        }
        return oto;
    }

    public Oto getOtoByDriver(int idDriver) throws Exception {
        Oto oto = new Oto();
        String sql = "SELECT * FROM oto WHERE status != 0 and id_employee = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idDriver);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                oto.setIdOto(mRs.getInt("id_oto"));
                oto.setNameOto(mRs.getString("name_oto"));
                oto.setIdTypeOto(mRs.getInt("id_type_oto"));
                oto.setStatus(mRs.getInt("status"));
                oto.setIdDriver(mRs.getInt("id_employee"));
            }

        } finally {
            close();
        }
        return oto;
    }

    public int getIdTypeOtoByIdOto(int idOto) throws Exception {
        int idTypeOto = 0;
        String sql = "SELECT id_type_oto FROM oto WHERE id_oto = ? and status = 1";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setInt(1, idOto);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                idTypeOto = mRs.getInt("id_type_oto");
            }
        } finally {
            close();
        }
        return idTypeOto;
    }

    public void insert(Oto oto) throws Exception {
        String sql = "INSERT INTO oto(name_oto, id_type_oto, chg_who, chg_date, status, id_employee) VALUE(?,?,?,?,?,?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, oto.getNameOto());
            mStmt.setInt(2, oto.getIdTypeOto());
            mStmt.setString(3, oto.getChgWho());
            mStmt.setDate(4, DateUtils.getSQLDate(oto.getChgDate()));
            if (oto.getIdDriver() == 0) {
                mStmt.setInt(5, 2);
            } else {
                mStmt.setInt(5, 1);
            }
            mStmt.setInt(6, oto.getIdDriver());
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                oto.setIdOto(mRs.getInt(1));
                if (oto.getIdDriver() == 0) {
                    oto.setStatus(2);
                } else {
                    oto.setStatus(1);
                }

            }
        } finally {
            close();
        }
    }

    public void update(Oto oto) throws Exception {
        String sql = "UPDATE oto SET name_oto = ?, id_employee = ?, chg_who = ?, chg_date = ?, status = ? where id_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, oto.getNameOto());
            mStmt.setInt(2, oto.getIdDriver());
            mStmt.setString(3, oto.getChgWho());
            mStmt.setDate(4, DateUtils.getSQLDate(oto.getChgDate()));
            if (oto.getIdDriver() == 0) {
                mStmt.setInt(5, 2);
            } else {
                mStmt.setInt(5, 1);
            }
            mStmt.setInt(6, oto.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }

        String sql2 = "UPDATE oto SET id_employee = 0, status = 2 WHERE id_employee = ? and id_oto != ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql2);
            mStmt.setInt(1, oto.getIdDriver());
            mStmt.setInt(2, oto.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }

    public void remove(Oto oto) throws Exception {
        String sql = "UPDATE oto "
                + "SET chg_who = ?, chg_date = ?, status = 0, id_employee = 0 "
                + "WHERE id_oto = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(sql);
            mStmt.setString(1, oto.getChgWho());
            mStmt.setDate(2, DateUtils.getSQLDate(oto.getChgDate()));
            mStmt.setInt(3, oto.getIdOto());
            mStmt.executeUpdate();
        } finally {
            close();
        }
    }
}
