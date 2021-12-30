/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entity.Driver;
import entity.Employee;
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
import util.ActionUtil;
import util.MessagesUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean
@ViewScoped
public class DriverController extends ActionUtil implements Serializable {

    private Driver driver;
    private List<Driver> mlstDriver;
    private DriverModel driverModel;

    private List<TypeOto> mlstTypeOto;
    private TypeOtoModel typeOtoModel;

    private List<Oto> mlstOtoByType;
    private OtoModel otoModel;

    /**
     * Creates a new instance of DriverController
     */
    public DriverController() {
        driver = new Driver();
        driverModel = new DriverModel();

        typeOtoModel = new TypeOtoModel();
        otoModel = new OtoModel();
        try {
            mlstDriver = driverModel.getListDriver();
            mlstTypeOto = typeOtoModel.getListTypeOto();
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.toString());
        }
    }

    public void handSave() {
        try {
            driver.setChgWho(LoginController.getUserLogin());
            driver.setChgDate(new Date());
            if (isAdd) {
                driverModel.insert(driver);
                mlstDriver.add(driver);
                MessagesUtils.info("", "Bạn thêm mới thành công người lái xe: ");
                driver = new Driver();
            } else if (isEdit) {
                driverModel.update(driver);
                for (Driver driver1 : mlstDriver) {
                    if (driver1.getIdEmployee() == driver.getIdEmployee()) {
                        if (driver.getIdOto() == 0) {
                            driver1.setStatusOto(0);
                        } else {
                            driver1.setStatusOto(1);
                        }
                    }
                }
                MessagesUtils.info("", "Bạn thêm sửa thành công người lái xe: ");
                driver = new Driver();
            }
            handCancel();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void handDelete(Driver driver) {
        try {
            driver.setChgWho(LoginController.getUserLogin());
            driver.setChgDate(new Date());

            driverModel.remove(driver);
            mlstDriver.remove(driver);
            MessagesUtils.info("", "Chúc mừng bạn xóa thành công người lái xe");
            driver = new Driver();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void onRowSelectOtoByType() {
        try {
            mlstOtoByType = otoModel.getListOtoByIdTypeStatus2(driver.getIdTypeOto());
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateAdd() {
        driver = new Driver();
        super.changeStateAdd();
    }

    public void changeStateEdit(Driver driver) {
        super.changeStateEdit();
        this.driver = driver;
        try {
            Oto oto = new Oto();
            oto = otoModel.getOtoByOto(driver.getIdOto());
            mlstOtoByType = otoModel.getListOtoByIdTypeStatus2(this.driver.getIdTypeOto());
            if (oto.getIdOto() != 0 && (mlstOtoByType == null || mlstOtoByType.isEmpty())) {
                mlstOtoByType.add(oto);
            } else if (oto.getIdOto() != 0) {
                mlstOtoByType.add(1, oto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateView(Driver driver) {
        super.changeStateView();
        this.driver = driver;
        try {
            Oto oto = new Oto();
            oto = otoModel.getOtoByOto(this.driver.getIdOto());
            //mlstOtoByType = otoModel.getListOtoByIdTypeStatus2(this.driver.getIdTypeOto());
            if (oto.getIdOto() != 0) {
                mlstOtoByType = new ArrayList<>();
                mlstOtoByType.add(oto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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

    public List<Oto> getMlstOtoByType() {
        return mlstOtoByType;
    }

    public void setMlstOtoByType(List<Oto> mlstOtoByType) {
        this.mlstOtoByType = mlstOtoByType;
    }

    public OtoModel getOtoModel() {
        return otoModel;
    }

    public void setOtoModel(OtoModel otoModel) {
        this.otoModel = otoModel;
    }

}
