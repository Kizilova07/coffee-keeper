package com.diploma.dto;

import javafx.beans.property.*;

public class PaymentDto {

    private StringProperty date;
    private IntegerProperty amount;
    private DoubleProperty cashSum;
    private DoubleProperty cardSum;
    private DoubleProperty totalSum;

    public PaymentDto(String date, Integer amount, Double cashSum,
                      Double cardSum, Double totalSum) {
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleIntegerProperty(amount);
        this.cashSum = new SimpleDoubleProperty(cashSum);
        this.cardSum = new SimpleDoubleProperty(cardSum);
        this.totalSum = new SimpleDoubleProperty(totalSum);
    }

    public PaymentDto(String date){
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleIntegerProperty(0);
        this.cashSum = new SimpleDoubleProperty(0);
        this.cardSum = new SimpleDoubleProperty(0);
        this.totalSum = new SimpleDoubleProperty(0);
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

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public double getCashSum() {
        return cashSum.get();
    }

    public DoubleProperty cashSumProperty() {
        return cashSum;
    }

    public void setCashSum(double cashSum) {
        this.cashSum.set(cashSum);
    }

    public double getCardSum() {
        return cardSum.get();
    }

    public DoubleProperty cardSumProperty() {
        return cardSum;
    }

    public void setCardSum(double cardSum) {
        this.cardSum.set(cardSum);
    }

    public double getTotalSum() {
        return totalSum.get();
    }

    public DoubleProperty totalSumProperty() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum.set(totalSum);
    }
}
