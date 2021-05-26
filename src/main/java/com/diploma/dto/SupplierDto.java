package com.diploma.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

public class SupplierDto {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty phone;
    private Button detail;

    public SupplierDto(Integer id, String name, String address,
                       String phone, Button detail) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address =  new SimpleStringProperty(address);
        this.phone =  new SimpleStringProperty(phone);
        this.detail = detail;
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public Button getDetail() {
        return detail;
    }

    public void setDetail(Button detail) {
        this.detail = detail;
    }
}
