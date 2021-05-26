package com.diploma.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;


public class CheckDto {

    private StringProperty productName;
    private StringProperty amount;
    private StringProperty cost;
    private StringProperty sum;
    private Button delete;

    public CheckDto(String productName, String amount, String cost, String sum, Button delete) {
        this.productName = new SimpleStringProperty(productName);
        this.amount = new SimpleStringProperty(amount);
        this.cost = new SimpleStringProperty(cost);
        this.sum = new SimpleStringProperty(sum);
        this.delete = delete;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
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

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
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

    public String getSum() {
        return sum.get();
    }

    public StringProperty sumProperty() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum.set(sum);
    }
}
