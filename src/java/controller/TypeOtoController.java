/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entity.TypeOto;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.TypeOtoModel;
import util.ActionInterface;
import util.ActionUtil;
import util.MessagesUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean
@ViewScoped
public class TypeOtoController extends ActionUtil implements Serializable, ActionInterface {

    private TypeOto typeOto;
    private List<TypeOto> mlstTypeOto;

    private TypeOtoModel typeOtoModel;

    /**
     * Creates a new instance of TypeOtoController
     */
    public TypeOtoController() {
        typeOto = new TypeOto();
        typeOtoModel = new TypeOtoModel();
        try {
            mlstTypeOto = typeOtoModel.getListTypeOto();
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.toString());
        }
    }

    @Override
    public void handSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        super.changeStateTypeOto();
    }

    public void changeStateEdit(TypeOto typeOto) {
        super.changeStateEdit();
        super.changeStateTypeOto();
        this.typeOto = typeOto;
    }

    public TypeOto getTypeOto() {
        return typeOto;
    }

    public void setTypeOto(TypeOto typeOto) {
        this.typeOto = typeOto;
    }

    public List<TypeOto> getMlstTypeOto() {
        return mlstTypeOto;
    }

    public void setMlstTypeOto(List<TypeOto> mlstTypeOto) {
        this.mlstTypeOto = mlstTypeOto;
    }

    public TypeOtoModel getTypeOtoModel() {
        return typeOtoModel;
    }

    public void setTypeOtoModel(TypeOtoModel typeOtoModel) {
        this.typeOtoModel = typeOtoModel;
    }
}
