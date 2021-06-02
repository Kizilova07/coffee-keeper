package com.diploma.service.controller;

import com.diploma.persistence.dao.RemainsDao;
import com.diploma.persistence.dao.RetailProductDao;
import com.diploma.persistence.dao.SupplyDao;
import com.diploma.persistence.entity.RemainsEntity;
import com.diploma.persistence.entity.RetailProductEntity;
import com.diploma.persistence.entity.SupplyEntity;
import com.diploma.service.Toast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SupplyDialogController {
    public Text label;
    public ChoiceBox<String> product;
    public TextField amount;
    public TextField cost;
    public TextField comment;
    public Button save_btn;
    public DatePicker date;

    private SupplyDao supplyDao;
    private RemainsDao remainsDao;
    private RetailProductDao retailProductDao;

    public SupplyDialogController(SupplyDao supplyDao, RemainsDao remainsDao,
                                  RetailProductDao retailProductDao) {
        this.supplyDao = supplyDao;
        this.remainsDao = remainsDao;
        this.retailProductDao = retailProductDao;
    }

    @FXML
    public void initialize() {

    }

    public void initUpdate(SupplyEntity supplyEntity) {
        ObservableList<String> productList = FXCollections.observableArrayList();
        Double oldAmount;
        for (RetailProductEntity productEntity : retailProductDao.findAll()) {
            productList.add(productEntity.getName());
        }
        product.setItems(productList);
        product.setValue(supplyEntity.getRetailProductEntity().getName());
        date.setValue(LOCAL_DATE(supplyEntity.getDate()));
        amount.setText(supplyEntity.getAmount().toString());
        oldAmount = Double.parseDouble(amount.getText());
        cost.setText(supplyEntity.getCost().toString());
        comment.setText(supplyEntity.getDescription());
        amount.textProperty().addListener(((observableValue, s, t1) -> {
            if (!product.getSelectionModel().isEmpty()) {
                RetailProductEntity oneByName = retailProductDao
                        .findOneByName(product.getSelectionModel().getSelectedItem());
                cost.setText(String.valueOf(Double.parseDouble(t1) * oneByName.getRawCost()));
            }
        }));
        save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                supplyDao.update(supplyEntity.getId(),
                                 comment.getText(),
                                 Double.parseDouble(amount.getText()),
                                 Double.parseDouble(cost.getText()),
                                 date.getValue().toString(),
                                 retailProductDao.findOneByName(
                                         product.getSelectionModel().getSelectedItem()).getId());

                RemainsEntity remainsEntity = remainsDao
                        .findOneByProduct(product.getSelectionModel().getSelectedItem());

                remainsEntity.setAmount(remainsEntity.getAmount() - oldAmount + Double.parseDouble(amount.getText()));
                remainsDao.update(remainsEntity.getId(), remainsEntity.getAmount(),
                                  remainsEntity.getRetailProductEntity().getId());
                closeDialogStage(saveEvent);
            } else {
                String toastMsg = "Форма заповнена не корректно!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            }
        });
    }

    public void initCreate() {
        ObservableList<String> productList = FXCollections.observableArrayList();

        for (RetailProductEntity productEntity : retailProductDao.findAll()) {
            productList.add(productEntity.getName());
        }
        product.setItems(productList);
        label.setText("Нова закупка");
        amount.textProperty().addListener(((observableValue, s, t1) -> {
            if (!product.getSelectionModel().isEmpty()) {
                RetailProductEntity oneByName = retailProductDao
                        .findOneByName(product.getSelectionModel().getSelectedItem());
                cost.setText(String.valueOf(Double.parseDouble(t1) * oneByName.getRawCost()));
            }
        }));
        save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                SupplyEntity supplyEntity = new SupplyEntity();
                supplyEntity.setDate(date.getValue().toString());
                supplyEntity.setRetailProductEntity(
                        retailProductDao.findOneByName(product.getSelectionModel().getSelectedItem()));
                supplyEntity.setCost(Double.parseDouble(cost.getText()));
                supplyEntity.setAmount(Double.parseDouble(amount.getText()));
                supplyEntity.setDescription(comment.getText());
                supplyDao.save(supplyEntity);

                RemainsEntity remainsEntity = remainsDao
                        .findOneByProduct(product.getSelectionModel().getSelectedItem());
                if (remainsEntity != null) {
                    remainsEntity.setAmount(remainsEntity.getAmount() + Double.parseDouble(amount.getText()));
                } else {
                    remainsEntity = new RemainsEntity();
                    remainsEntity.setAmount(Double.parseDouble(amount.getText()));
                    remainsEntity.setRetailProductEntity(
                            retailProductDao.findOneByName(product.getSelectionModel().getSelectedItem()));
                }
                remainsDao.save(remainsEntity);
                closeDialogStage(saveEvent);
            } else {
                String toastMsg = "Форма заповнена не корректно!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            }
        });
    }

    public static LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    private boolean isFilled() {
        if (date.getValue() != null) {
            if (!product.getSelectionModel().isEmpty()) {
                if (!amount.getText().isEmpty()) {
                    if (!cost.getText().isEmpty()) {
                        if (!comment.getText().isEmpty()) {
                            try {
                                Double.parseDouble(amount.getText());
                                Double.parseDouble(cost.getText());
                                return true;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void closeDialogStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }
}
