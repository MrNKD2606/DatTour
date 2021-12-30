/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author DuyNguyen
 */
public class TypeOto {
    private int idTypeOto;
    private String nameTypeOto;
    private int status;

    public TypeOto() {
    }

    public TypeOto(TypeOto obj) {
        this.idTypeOto = obj.idTypeOto;
        this.nameTypeOto = obj.nameTypeOto;
        this.status = obj.status;
    }
    
    public int getIdTypeOto() {
        return idTypeOto;
    }

    public void setIdTypeOto(int idTypeOto) {
        this.idTypeOto = idTypeOto;
    }

    public String getNameTypeOto() {
        return nameTypeOto;
    }

    public void setNameTypeOto(String nameTypeOto) {
        this.nameTypeOto = nameTypeOto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
