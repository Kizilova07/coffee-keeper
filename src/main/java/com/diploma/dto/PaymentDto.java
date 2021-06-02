package com.diploma.dto;

import javafx.beans.property.*;

public class PaymentDto {

    private StringProperty date;
    private StringProperty amount;
    private StringProperty cashSum;
    private StringProperty cardSum;
    private StringProperty totalSum;

    public PaymentDto(String date, String amount, String cashSum,
                      String cardSum, String totalSum) {
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(amount);
        this.cashSum = new SimpleStringProperty(cashSum);
        this.cardSum = new SimpleStringProperty(cardSum);
        this.totalSum = new SimpleStringProperty(totalSum);
    }

    public PaymentDto(String date){
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(String.valueOf(0));
        this.cashSum = new SimpleStringProperty(String.valueOf(0));
        this.cardSum = new SimpleStringProperty(String.valueOf(0));
        this.totalSum = new SimpleStringProperty(String.valueOf(0));
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

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public String getCashSum() {
        return cashSum.get();
    }

    public StringProperty cashSumProperty() {
        return cashSum;
    }

    public void setCashSum(String cashSum) {
        this.cashSum.set(cashSum);
    }

    public String getCardSum() {
        return cardSum.get();
    }

    public StringProperty cardSumProperty() {
        return cardSum;
    }

    public void setCardSum(String cardSum) {
        this.cardSum.set(cardSum);
    }

    public String getTotalSum() {
        return totalSum.get();
    }

    public StringProperty totalSumProperty() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum.set(totalSum);
    }
}
