package com.diploma.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class CancelDto {

    private StringProperty date;
    private StringProperty productName;
    private StringProperty amount;
    private StringProperty user;
    private StringProperty comment;
    private Button detail;

    public CancelDto(String date, String productName, String amount,
                     String user, String comment, Button detail) {
        this.date = new SimpleStringProperty(date);
        this.productName = new SimpleStringProperty(productName);
        this.amount = new SimpleStringProperty(amount);
        this.user = new SimpleStringProperty(user);
        this.comment = new SimpleStringProperty(comment);
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

    public String getUser() {
        return user.get();
    }

    public StringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
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

    public Button getDetail() {
        return detail;
    }

    public void setDetail(Button detail) {
        this.detail = detail;
    }
}
