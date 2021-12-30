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
public class Driver extends Employee implements Serializable {

    private int idTypeOto;
    private int idOto;
    private int statusOto;

    public Driver() {
    }

    public int getIdTypeOto() {
        return idTypeOto;
    }

    public void setIdTypeOto(int idTypeOto) {
        this.idTypeOto = idTypeOto;
    }

    public int getIdOto() {
        return idOto;
    }

    public void setIdOto(int idOto) {
        this.idOto = idOto;
    }

    public int getStatusOto() {
        return statusOto;
    }

    public void setStatusOto(int statusOto) {
        this.statusOto = statusOto;
    }

}
