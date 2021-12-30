/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import entity.Cart;
import entity.Customer;
import entity.Tour;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import model.CartModel;
import model.CustomerModel;
import model.TourModel;
import util.ActionUtil;
import util.MessagesUtils;
import util.SessionUtils;

/**
 *
 * @author DuyNguyen
 */
@ManagedBean
@ViewScoped
public class CartController extends ActionUtil implements Serializable {

    private Cart cart;
    private CartModel cartModel;
    private List<Cart> mlstCart;

    private Customer customer;
    private List<Customer> mlstCustomer;
    private CustomerModel customerModel;

    private TourModel tourModel;

    /**
     * Creates a new instance of CartController
     */
    public CartController() {
        cart = new Cart();
        cartModel = new CartModel();
        try {
            mlstCart = cartModel.getListTour();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }

        customer = new Customer();
        customerModel = new CustomerModel();
        mlstCustomer = new ArrayList<>();

        tourModel = new TourModel();
    }

    public void changeStateEditBill(Cart cart) {
        super.changeStateEditBill();
        this.cart = cart;
        try {
            this.mlstCustomer = customerModel.getListCustomerToCart(cart.getIdCart());
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeStateViewBill(Cart cart) {
        super.changeStateViewBill();
        this.cart = cart;
        try {
            this.mlstCustomer = customerModel.getListCustomerToCart(cart.getIdCart());
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Tour getTourClick() throws Exception {
        SessionUtils.getRequest();
        HttpSession session = SessionUtils.getSession();
        return (Tour) session.getAttribute("tour");
    }

    public boolean checkAdd(int idTour) {
        int numberCus = getNumberCus(idTour);
        try {
            int numberPeople = tourModel.getPeople(idTour);
            if ((numberPeople - numberCus - mlstCustomer.size()) == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean checkAddCusToCart(Cart cart) {
        if (isViewBill) {
            return false;
        }
        try {
            int numberPeople = tourModel.getPeople(cart.getIdTour());
            int numberCusNotCart = customerModel.getNumberCusTourNotCart(cart.getIdTour(), cart.getIdCart());
            if (numberPeople - numberCusNotCart - mlstCustomer.size() == 0) {
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

    public String handSaveToString() {
        cart.setChgWho(LoginController.getUserLogin());
        cart.setChgDate(new Date());
        try {
            cart.setIdTour(getTourClick().getIdTour());
            cart.setTotalMoney(getTongTien(mlstCustomer, getTourClick().getDateStart(), getTourClick().getPrice()));
            cartModel.insert(cart);
            //customerModel.insert(mlstCustomer, cart.getIdCart(), cart.getIdTour());
            customerModel.update(mlstCustomer, cart);
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessagesUtils.info("", "Bạn đã thêm mới thành công tour");
        return "/bill?faces-redirect=true";
    }

    public void handSave() {
        cart.setChgWho(LoginController.getUserLogin());
        cart.setChgDate(new Date());
        if (isEditBill) {
            Tour t;
            try {
                t = cartModel.getTour(cart.getIdTour());
                cart.setTotalMoney(getTongTien(mlstCustomer, t.getDateStart(), t.getPrice()));
                cartModel.update(cart);
                customerModel.update(mlstCustomer, cart);
                handCancelBill();
            } catch (Exception ex) {
                Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void handDeleteBill(Cart cart) {
        try {
            if (getDay(sysDate, tourModel.getStartDayToTour(cart.getIdTour())) <= 2) {
                MessagesUtils.error("", "Cart chỉ còn 2 ngày xuất phát bạn không thể xóa");
                cart = new Cart();
                return;
            }
            cart.setChgWho(LoginController.getUserLogin());
            cart.setChgDate(new Date());
            customerModel.deleteCart(cart.getIdCart());
            cartModel.remove(cart);
            mlstCart.remove(cart);
            MessagesUtils.info("", "Chúc mừng bạn xóa thành công cart");
            cart = new Cart();
        } catch (Exception ex) {
            Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handCancel() {
        super.handCancel();
        customer = new Customer();
    }

    public void handSaveCustomner() {
        if (isAdd) {
            mlstCustomer.add(customer);
            customer = new Customer();
        }
        handCancel();
    }

    public void handDeleteCustomer(Customer customer) {
        try {
            mlstCustomer.remove(customer);
            MessagesUtils.info("", "Chúc mừng bạn xóa thành công");
            customer = new Customer();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateEdit(Customer customer, Cart cart) {
        super.changeStateEdit();
        this.customer = customer;
        this.cart = cart;
    }

    public void changeStateView(Customer customer, Cart cart) {
        super.changeStateView();
        this.customer = customer;
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CartModel getCartModel() {
        return cartModel;
    }

    public void setCartModel(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getMlstCustomer() {
        return mlstCustomer;
    }

    public void setMlstCustomer(List<Customer> mlstCustomer) {
        this.mlstCustomer = mlstCustomer;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public TourModel getTourModel() {
        return tourModel;
    }

    public void setTourModel(TourModel tourModel) {
        this.tourModel = tourModel;
    }

    public List<Cart> getMlstCart() {
        return mlstCart;
    }

    public void setMlstCart(List<Cart> mlstCart) {
        this.mlstCart = mlstCart;
    }

}
