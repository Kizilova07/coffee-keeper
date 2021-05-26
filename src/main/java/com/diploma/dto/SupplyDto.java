package com.diploma.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class SupplyDto {

    private StringProperty date;
    private StringProperty supplierName;
    private StringProperty productName;
    private StringProperty comment;
    private StringProperty cost;
    private Button detail;

    public SupplyDto(String date, String supplierName, String productName,
                     String comment, String cost, Button detail) {
        this.date = new SimpleStringProperty(date);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.productName = new SimpleStringProperty(productName);
        this.comment = new SimpleStringProperty(comment);
        this.cost = new SimpleStringProperty(cost);
        this.detail = detail;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getCost() {
        return cost.get();
    }

    public StringProperty costProperty() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public Button getDetail() {
        return detail;
    }

    public void setDetail(Button detail) {
        this.detail = detail;
    }
}
