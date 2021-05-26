package com.diploma.dto;

import javafx.beans.property.*;

public class StaffDto {

    private StringProperty user;
    private DoubleProperty income;
    private DoubleProperty profit;
    private IntegerProperty amount;
    private DoubleProperty averageSale;

    public StaffDto(String user, Double income, Double profit,
                    Integer amount, Double averageSale) {
        this.user = new SimpleStringProperty(user);
        this.income = new SimpleDoubleProperty(income);
        this.profit = new SimpleDoubleProperty(profit);
        this.amount = new SimpleIntegerProperty(amount);
        this.averageSale = new SimpleDoubleProperty(averageSale);
    }

    public StaffDto(String user) {
        this.user = new SimpleStringProperty(user);
        this.income = new SimpleDoubleProperty(0.0);
        this.profit = new SimpleDoubleProperty(0.0);
        this.amount = new SimpleIntegerProperty(0);
        this.averageSale = new SimpleDoubleProperty(0.0);
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

    public double getIncome() {
        return income.get();
    }

    public DoubleProperty incomeProperty() {
        return income;
    }

    public void setIncome(double income) {
        this.income.set(income);
    }

    public double getProfit() {
        return profit.get();
    }

    public DoubleProperty profitProperty() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit.set(profit);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public double getAverageSale() {
        return averageSale.get();
    }

    public DoubleProperty averageSaleProperty() {
        return averageSale;
    }

    public void setAverageSale(double averageSale) {
        this.averageSale.set(averageSale);
    }
}
