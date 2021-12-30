/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entity.City;
import entity.Customer;
import entity.Employee;
import entity.Oto;
import entity.Tour;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import model.CartModel;
import model.CityModel;
import model.CustomerModel;
import model.EmployeesModel;
import model.OtoModel;
import model.TourModel;
import util.ActionInterface;
import util.ActionUtil;
import util.MessagesUtils;
import util.SessionUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean
@ViewScoped
public class TourController extends ActionUtil implements ActionInterface, Serializable {

    private Tour tour;
    private TourModel tourModel;
    private List<Tour> mlstTour;
    private List<Tour> mlstTour3;

    private City city;
    private CityModel cityModel;
    private List<City> mlstCity;

    private Employee tourGuide;
    private EmployeesModel tourGuideModel;
    private List<Employee> mlstTourGuide;

    private List<Oto> mlstOtoByType;

    private List<Customer> mlstCustomer;
    private CustomerModel customerModel;
    private CartModel cartModel;
    private EmployeesModel employeesModel;

    private Date sysDate;

    /**
     * Creates a new instance of TourController
     */
    public TourController() {
        tour = new Tour();
        tourModel = new TourModel();

        city = new City();
        cityModel = new CityModel();

        tourGuide = new Employee();
        tourGuideModel = new EmployeesModel();

        customerModel = new CustomerModel();
        cartModel = new CartModel();
        employeesModel = new EmployeesModel();

        sysDate = new Date();
        try {
            mlstTour = tourModel.getListTour();
            mlstTour3 = tourModel.getListTour3();
            mlstCity = cityModel.getListCity();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    @Override
    public void handSave() {
        try {
//            tour.setIdAdminUser(LoginController.getIdAdminUser());
            if (valid()) {
                tour.setChgWho(LoginController.getUserLogin());
                tour.setChgDate(new Date());
                if (isAdd) {
                    tourModel.insert(tour);
                    mlstTour.add(tour);
                    MessagesUtils.info("", "Bạn đã thêm mới thành công tour");
                    tour = new Tour();
                } else if (isEdit) {
                    tourModel.update(tour);
                    int i = 0;
                    for (Tour tour1 : mlstTour) {
                        if (tour1.getIdTour() == tour.getIdTour()) {
                            break;
                        }
                        i = i + 1;
                    }
                    mlstTour.set(i, tour);
                    MessagesUtils.info("", "Chúc mừng bạn sửa thành công");
                    tour = new Tour();
                }
            }
            handCancel();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public boolean valid() throws Exception {
        //Valid dữ liệu nếu đúng thì trả về true sai thì trả về false
        if (tour.getNumberPeople() < customerModel.getNumberCusOkCart(tour.getIdTour())) {
            MessagesUtils.error("", "Số người thấp hơn số khách đã đặt");
            return false;
        }
        return true;
    }

    public void changeStateAdd() {
        super.changeStateAdd();
        try {
            mlstTourGuide = tourGuideModel.getListTourGuideNotTour();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateEdit(Tour tour) {
        super.changeStateEdit();
        this.tour = new Tour(tour);
        try {
            Employee tourGuide = new Employee();
            EmployeesModel employeesModel = new EmployeesModel();
            tourGuide = employeesModel.getTourGuideById(this.tour.getIdTourGuide());
            mlstTourGuide = employeesModel.getListTourGuideNotTour();
            if (tourGuide.getIdEmployee() != 0 && (mlstTourGuide == null || mlstTourGuide.isEmpty())) {
                mlstTourGuide.add(tourGuide);
            } else if (tourGuide.getIdEmployee() != 0) {
                mlstTourGuide.add(1, tourGuide);
            }

            OtoModel otoModel = new OtoModel();
            Oto oto = new Oto();
            oto = otoModel.getOtoByOto(tour.getIdOto());
            mlstOtoByType = otoModel.getListOtoByIdTypeStatus1(this.tour.getIdOto());
            if (oto.getIdOto() != 0 && (mlstOtoByType == null || mlstOtoByType.isEmpty())) {
                mlstOtoByType.add(oto);
            } else if (oto.getIdOto() != 0) {
                mlstOtoByType.add(1, oto);
            }
            this.tour.setIdTypeOto(otoModel.getIdTypeOtoByIdOto(tour.getIdOto()));
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateView(Tour tour) {
        super.changeStateView();
        this.tour = new Tour(tour);
        try {
            Employee tourGuide = new Employee();
            EmployeesModel employeesModel = new EmployeesModel();
            tourGuide = employeesModel.getTourGuideById(this.tour.getIdTourGuide());
            if (tourGuide.getIdEmployee() != 0) {
                mlstTourGuide = new ArrayList<>();
                mlstTourGuide.add(tourGuide);
            }

            OtoModel otoModel = new OtoModel();
            Oto oto = new Oto();
            oto = otoModel.getOtoByOto(tour.getIdOto());
            if (oto.getIdOto() != 0) {
                mlstOtoByType = new ArrayList<>();
                mlstOtoByType.add(oto);
                this.tour.setIdTypeOto(otoModel.getIdTypeOtoByIdOto(tour.getIdOto()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void handDelete(Tour tour) {
        try {
            if (tour.getStatus() == 1) {
                MessagesUtils.error("", "Tour đang diễn ra bạn không thể xóa");
                tour = new Tour();
                return;
            } else if (cartModel.getCheckTourToCart(tour.getIdTour())) {
                MessagesUtils.error("", "Tour đã có người đặt bạn không thể xóa");
                tour = new Tour();
                return;
            } else {
                tour.setChgWho(LoginController.getUserLogin());
                tour.setChgDate(new Date());

                tourModel.remove(tour);
                mlstTour.remove(tour);
                MessagesUtils.info("", "Chúc mừng bạn xóa thành công tour");
                tour = new Tour();
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public boolean checkPay(Tour tour) {
        LocalDate s = convertToLocalDate(tour.getDateStart()).plusDays(tour.getNumberDay());
        LocalDate e = convertToLocalDate(sysDate);
        if (e.isAfter(s) && tour.getStatus() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void handPay(Tour tour) {
        tour.setChgWho(LoginController.getUserLogin());
        tour.setChgDate(new Date());
        LocalDate s = convertToLocalDate(tour.getDateStart()).plusDays(tour.getNumberDay());
        LocalDate e = convertToLocalDate(sysDate);
        if (e.isAfter(s)) {
            try {
                tourModel.pay(tour);
                mlstTour.remove(tour);
            } catch (Exception ex) {
                Logger.getLogger(TourController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean checkDayToNow(Tour t) {
        LocalDate s = convertToLocalDate(t.getDateStart());
        LocalDate e = convertToLocalDate(sysDate);
        if (e.isAfter(s)) {
            return true;
        }
        return false;
    }

    public boolean chekAddCart(Tour t) {
        if (checkAdd(t.getIdTour()) && getDay(sysDate, t.getDateStart()) > 2) {
            return true;
        }
        return false;
    }

    public String addTour(Tour t) {
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("tour", t);
        if (checkAdd(t.getIdTour())) {
            return "/cart?faces-redirect=true";
        } else {
            t = new Tour();
            return "/tour?faces-redirect=true";
        }
    }

    public boolean checkAdd(int idTour) {
        int numberCus = getNumberCus(idTour);
        try {
            int numberPeople = 0;
            numberPeople = tourModel.getPeople(idTour);
            if ((numberPeople - numberCus) == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public int getNumberCus(int idTour) {
        try {
            return customerModel.getNumberCus(idTour);
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public String getCityByIdCity(int idCity) {
        try {
            return cityModel.getNameCity(idCity);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
        return "";
    }

    public void onRowSelectOtoByType() {
        OtoModel otoModel = new OtoModel();
        try {
            mlstOtoByType = otoModel.getListOtoByIdTypeStatus1(tour.getIdTypeOto());
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public TourModel getTourModel() {
        return tourModel;
    }

    public void setTourModel(TourModel tourModel) {
        this.tourModel = tourModel;
    }

    public List<Tour> getMlstTour() {
        return mlstTour;
    }

    public void setMlstTour(List<Tour> mlstTour) {
        this.mlstTour = mlstTour;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<City> getMlstCity() {
        return mlstCity;
    }

    public void setMlstCity(List<City> mlstCity) {
        this.mlstCity = mlstCity;
    }

    public CityModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CityModel cityModel) {
        this.cityModel = cityModel;
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public Employee getTourGuide() {
        return tourGuide;
    }

    public void setTourGuide(Employee tourGuide) {
        this.tourGuide = tourGuide;
    }

    public EmployeesModel getTourGuideModel() {
        return tourGuideModel;
    }

    public void setTourGuideModel(EmployeesModel tourGuideModel) {
        this.tourGuideModel = tourGuideModel;
    }

    public List<Employee> getMlstTourGuide() {
        return mlstTourGuide;
    }

    public void setMlstTourGuide(List<Employee> mlstTourGuide) {
        this.mlstTourGuide = mlstTourGuide;
    }

    public List<Oto> getMlstOtoByType() {
        return mlstOtoByType;
    }

    public void setMlstOtoByType(List<Oto> mlstOtoByType) {
        this.mlstOtoByType = mlstOtoByType;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public CartModel getCartModel() {
        return cartModel;
    }

    public void setCartModel(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    public List<Customer> getMlstCustomer() {
        return mlstCustomer;
    }

    public void setMlstCustomer(List<Customer> mlstCustomer) {
        this.mlstCustomer = mlstCustomer;
    }

    public List<Tour> getMlstTour3() {
        return mlstTour3;
    }

    public void setMlstTour3(List<Tour> mlstTour3) {
        this.mlstTour3 = mlstTour3;
    }

    public EmployeesModel getEmployeesModel() {
        return employeesModel;
    }

    public void setEmployeesModel(EmployeesModel employeesModel) {
        this.employeesModel = employeesModel;
    }

}
