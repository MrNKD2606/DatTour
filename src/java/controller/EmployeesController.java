/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entity.Employee;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.EmployeesModel;
import util.ActionInterface;
import util.ActionUtil;
import util.MessagesUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean
@ViewScoped
public class EmployeesController extends ActionUtil implements Serializable, ActionInterface {

    private Employee employee;
    private EmployeesModel employeesModel;
    private List<Employee> mlstEmployees;

    private List<Employee> mlstTourGuide;

    /**
     * Creates a new instance of DriverController
     */
    public EmployeesController() {
        employee = new Employee();
        employeesModel = new EmployeesModel();

        try {
            mlstEmployees = employeesModel.getListEmployees();
            mlstTourGuide = employeesModel.getListTourGuide();
        } catch (Exception ex) {
            ex.printStackTrace();
            MessagesUtils.error("", ex.toString());
        }
    }
    
    public void handSave() {
        try {
            employee.setChgWho(LoginController.getUserLogin());
            employee.setChgDate(new Date());
            if (isAdd) {
                employeesModel.insert(employee);
                mlstTourGuide.add(employee);
                MessagesUtils.info("", "Bạn thêm mới thành công nhân viên: ");
                employee = new Employee();
            } else if (isEdit) {
                employeesModel.update(employee);
                MessagesUtils.info("", "Bạn sửa thành công người nhân viên: ");
                employee = new Employee();
            }
            handCancel();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateEdit(Employee employee) {
        super.changeStateEdit();
        this.employee = employee;
    }

    public void changeStateView(Employee employee) {
        super.changeStateView();
        this.employee = employee;
    }

    public void handDelete(Employee employee) {
        try {
            employee.setChgWho(LoginController.getUserLogin());
            employee.setChgDate(new Date());
            employeesModel.remove(employee);
            mlstTourGuide.remove(employee);
            MessagesUtils.info("", "Chúc mừng bạn xóa thành công");
            employee = new Employee();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }

    }

    public Employee getEmployee() {
        return employee;
    }

    public void setDriver(Employee employee) {
        this.employee = employee;
    }

    public EmployeesModel getEmployeesModel() {
        return employeesModel;
    }

    public void setEmployeesModel(EmployeesModel employeesModel) {
        this.employeesModel = employeesModel;
    }

    public List<Employee> getMlstEmployees() {
        return mlstEmployees;
    }

    public void setMlstEmployees(List<Employee> mlstEmployees) {
        this.mlstEmployees = mlstEmployees;
    }

    public List<Employee> getMlstTourGuide() {
        return mlstTourGuide;
    }

    public void setMlstTourGuide(List<Employee> mlstTourGuide) {
        this.mlstTourGuide = mlstTourGuide;
    }

}
