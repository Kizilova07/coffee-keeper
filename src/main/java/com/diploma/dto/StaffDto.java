package com.diploma.dto;

import javafx.beans.property.*;

public class StaffDto {

    private StringProperty user;
    private StringProperty income;
    private StringProperty profit;
    private StringProperty amount;
    private StringProperty averageSale;

    public StaffDto(String user, String income, String profit,
                    String amount, String averageSale) {
        this.user = new SimpleStringProperty(user);
        this.income = new SimpleStringProperty(income);
        this.profit = new SimpleStringProperty(profit);
        this.amount = new SimpleStringProperty(amount);
        this.averageSale = new SimpleStringProperty(averageSale);
    }

    public StaffDto(String user) {
        this.user = new SimpleStringProperty(user);
        this.income = new SimpleStringProperty(String.valueOf(0.0));
        this.profit = new SimpleStringProperty(String.valueOf(0.0));
        this.amount = new SimpleStringProperty(String.valueOf(0.0));
        this.averageSale = new SimpleStringProperty(String.valueOf(0.0));
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

    public String getIncome() {
        return income.get();
    }

    public StringProperty incomeProperty() {
        return income;
    }

    public void setIncome(String income) {
        this.income.set(income);
    }

    public String getProfit() {
        return profit.get();
    }

    public StringProperty profitProperty() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit.set(profit);
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

    public String getAverageSale() {
        return averageSale.get();
    }

    public StringProperty averageSaleProperty() {
        return averageSale;
    }

    public void setAverageSale(String averageSale) {
        this.averageSale.set(averageSale);
    }
}
