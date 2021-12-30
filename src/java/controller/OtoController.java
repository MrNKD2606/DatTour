/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Driver;
import entity.Oto;
import entity.TypeOto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.DriverModel;
import model.OtoModel;
import model.TypeOtoModel;
import org.primefaces.event.SelectEvent;
import util.ActionInterface;
import util.ActionUtil;
import util.MessagesUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean
@ViewScoped
public class OtoController extends ActionUtil implements Serializable, ActionInterface {

    public Oto oto;
    public List<Oto> mlstOto;
    public OtoModel otoModel;

    public List<Driver> mlstDriver;
    public DriverModel driverModel;

    public TypeOto typeOto;
    public List<TypeOto> mlstTypeOto;
    public TypeOtoModel typeOtoModel;

    public TypeOto selectedType;

    /**
     * Creates a new instance of DriverController
     */
    public OtoController() {
        typeOto = new TypeOto();
        typeOtoModel = new TypeOtoModel();

        oto = new Oto();
        otoModel = new OtoModel();

        driverModel = new DriverModel();
        try {
            mlstTypeOto = typeOtoModel.getListTypeOto();
            mlstDriver = driverModel.getListDriverNotOto();
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.toString());
        }
    }

    @Override
    public void handSave() {
        try {
            if (isTypeOto) {
                if (isAdd) {
                    typeOtoModel.insert(typeOto);
                    mlstTypeOto.add(typeOto);
                    MessagesUtils.info("", "Bạn đã thêm mới thành công loại xe: ");
                    typeOto = new TypeOto();
                } else if (isEdit) {
                    typeOtoModel.update(typeOto);
                    MessagesUtils.info("", "Bạn đã sửa thành công loại xe: ");
                    typeOto = new TypeOto();
                }
            } else if (isOto) {
                oto.setChgWho(LoginController.getUserLogin());
                oto.setChgDate(new Date());
                if (isAdd) {
                    otoModel.insert(oto);
                    mlstOto.add(oto);
                    MessagesUtils.info("", "Bạn đã thêm mới thành công xe oto: ");
                    oto = new Oto();
                } else if (isEdit) {
                    otoModel.update(oto);
                    for (Oto oto1 : mlstOto) {
                        if (oto1.getIdOto() == oto.getIdOto()) {
                            if (oto.getIdDriver() == 0) {
                                oto1.setStatus(2);
                            } else {
                                oto1.setStatus(1);
                            }
                        }
                    }
                    MessagesUtils.info("", "Bạn thêm sửa thành công oto: ");
                    oto = new Oto();
                }
            }
            handCancel();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateAddOto() {
        super.changeStateAdd();
        super.changeStateOto();
    }

    public void changeStateEditOto(Oto oto) {
        super.changeStateEdit();
        super.changeStateOto();
        this.oto = oto;
        try {
            Driver driver = new Driver();
            driver = driverModel.getDriverById(this.oto.getIdDriver());
            mlstDriver = driverModel.getListDriverNotOto();
            if (driver.getIdEmployee() != 0 && (mlstDriver == null || mlstDriver.isEmpty())) {
                mlstDriver.add(driver);
            } else if (driver.getIdEmployee() != 0) {
                mlstDriver.add(1, driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateViewOto(Oto oto) {
        super.changeStateView();
        super.changeStateOto();
        this.oto = oto;
        try {
            Driver driver = new Driver();
            driver = driverModel.getDriverById(this.oto.getIdDriver());
            if (driver.getIdEmployee() != 0) {
                mlstDriver = new ArrayList<>();
                mlstDriver.add(driver);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void handDeleteOto(Oto oto) {
        try {
            oto.setChgWho(LoginController.getUserLogin());
            oto.setChgDate(new Date());
            otoModel.remove(oto);
            mlstOto.remove(oto);
            MessagesUtils.info("", "Chúc mừng bạn xóa thành công");
            typeOto = new TypeOto();
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.toString());
        }
    }

    public String getNameTypeOto(int idTypeOto) {
        try {
            if (mlstTypeOto != null && !mlstTypeOto.isEmpty()) {
                for (TypeOto typeOto : mlstTypeOto) {
                    if (typeOto.getIdTypeOto() == idTypeOto) {
                        return typeOto.getNameTypeOto();
                    }
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
        return "";
    }

    public void onRowSelect(SelectEvent<TypeOto> event) {
        try {
            mlstOto = otoModel.getListOtoByIdType(event.getObject().getIdTypeOto());
            oto = new Oto();
            oto.setIdTypeOto(event.getObject().getIdTypeOto());
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.toString());
        }

    }

    public void changeStateAddTypeOto() {
        super.changeStateAdd();
        super.changeStateTypeOto();
    }

    public void changeStateEditTypeOto(TypeOto typeOto) {
        super.changeStateEdit();
        super.changeStateTypeOto();
        this.typeOto = typeOto;
    }

    public void changeStateViewTypeOto(TypeOto typeOto) {
        super.changeStateView();
        super.changeStateTypeOto();
        this.typeOto = new TypeOto(typeOto);
    }

    public void handDeleteTypeOto(TypeOto typeOto) {
        try {
            typeOtoModel.remove(typeOto);
            mlstTypeOto.remove(typeOto);
            MessagesUtils.info("", "Chúc mừng bạn xóa thành công");
            typeOto = new TypeOto();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public Oto getOto() {
        return oto;
    }

    public void setOto(Oto oto) {
        this.oto = oto;
    }

    public OtoModel getOtoModel() {
        return otoModel;
    }

    public void setOtoModel(OtoModel otoModel) {
        this.otoModel = otoModel;
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

    public TypeOto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeOto selectedType) {
        this.selectedType = selectedType;
    }

    public List<Oto> getMlstOto() {
        return mlstOto;
    }

    public void setMlstOto(List<Oto> mlstOto) {
        this.mlstOto = mlstOto;
    }

    public List<Driver> getMlstDriver() {
        return mlstDriver;
    }

    public void setMlstDriver(List<Driver> mlstDriver) {
        this.mlstDriver = mlstDriver;
    }

    public DriverModel getDriverModel() {
        return driverModel;
    }

    public void setDriverModel(DriverModel driverModel) {
        this.driverModel = driverModel;
    }

}
