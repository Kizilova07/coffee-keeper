package com.diploma.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class ProductDto {

    private IntegerProperty id;
    private StringProperty productName;
    private StringProperty supplierName;
    private StringProperty cost;
    private StringProperty rawCost;
    private StringProperty description;
    private Button detail;

    public ProductDto(Integer id, String productName, String supplierName,
                      String cost, String rawCost, String description, Button detail) {
        this.id = new SimpleIntegerProperty(id);
        this.productName = new SimpleStringProperty(productName);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.cost = new SimpleStringProperty(cost);
        this.rawCost = new SimpleStringProperty(rawCost);
        this.description = new SimpleStringProperty(description);
        this.detail = detail;
    }

    public String getRawCost() {
        return rawCost.get();
    }

    public StringProperty rawCostProperty() {
        return rawCost;
    }

    public void setRawCost(String rawCost) {
        this.rawCost.set(rawCost);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public String getSupplierName() {
        return supplierName.get();
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Button getDetail() {
        return detail;
    }

    public void setDetail(Button detail) {
        this.detail = detail;
    }
}
