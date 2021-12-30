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
public class Tour implements Serializable {

    private int idTour;
    private String nameTour;
    private String description;
    private Date dateStart;
    private int numberDay;
    private int numberPeople;
    private int price;
    private int idDeparturePlace;
    private String chgWho;
    private Date chgDate;
    private int status;
    private int idDestinationPlace;
    private int idTourGuide;
    private int idTypeOto;
    private int idOto;

    public Tour() {
    }

    public Tour(Tour obj) {
        this.idTour = obj.idTour;
        this.nameTour = obj.nameTour;
        this.description = obj.description;
        this.dateStart = obj.dateStart;
        this.numberDay = obj.numberDay;
        this.numberPeople = obj.numberPeople;
        this.price = obj.price;
        this.idDeparturePlace = obj.idDeparturePlace;
        this.chgWho = obj.chgWho;
        this.chgDate = obj.chgDate;
        this.status = obj.status;
        this.idDestinationPlace = obj.idDestinationPlace;
        this.idTourGuide = obj.idTourGuide;
        this.idTypeOto = obj.idTypeOto;
        this.idOto = obj.idOto;
    }

    public int getIdTour() {
        return idTour;
    }

    public void setIdTour(int idTour) {
        this.idTour = idTour;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public int getNumberDay() {
        return numberDay;
    }

    public void setNumberDay(int numberDay) {
        this.numberDay = numberDay;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIdDeparturePlace() {
        return idDeparturePlace;
    }

    public void setIdDeparturePlace(int idDeparturePlace) {
        this.idDeparturePlace = idDeparturePlace;
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

    public int getIdDestinationPlace() {
        return idDestinationPlace;
    }

    public void setIdDestinationPlace(int idDestinationPlace) {
        this.idDestinationPlace = idDestinationPlace;
    }

    public int getIdTourGuide() {
        return idTourGuide;
    }

    public void setIdTourGuide(int idTourGuide) {
        this.idTourGuide = idTourGuide;
    }

    public int getIdOto() {
        return idOto;
    }

    public void setIdOto(int idOto) {
        this.idOto = idOto;
    }

    public int getIdTypeOto() {
        return idTypeOto;
    }

    public void setIdTypeOto(int idTypeOto) {
        this.idTypeOto = idTypeOto;
    }

}
