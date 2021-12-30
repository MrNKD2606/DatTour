/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author DuyNguyen
 */
public class Oto {

    private int idOto;
    private String nameOto;
    private int idTypeOto;
    private String chgWho;
    private Date chgDate;
    private int status;
    private int idDriver;

    public Oto() {
    }

    public Oto(Oto obj) {
        this.idOto = obj.idOto;
        this.nameOto = obj.nameOto;
        this.idTypeOto = obj.idTypeOto;
        this.chgWho = obj.chgWho;
        this.chgDate = obj.chgDate;
        this.status = obj.status;
        this.idDriver = obj.idDriver;
    }

    
    public int getIdOto() {
        return idOto;
    }

    public void setIdOto(int idOto) {
        this.idOto = idOto;
    }

    public String getNameOto() {
        return nameOto;
    }

    public void setNameOto(String nameOto) {
        this.nameOto = nameOto;
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

    public int getIdTypeOto() {
        return idTypeOto;
    }

    public void setIdTypeOto(int idTypeOto) {
        this.idTypeOto = idTypeOto;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

}
