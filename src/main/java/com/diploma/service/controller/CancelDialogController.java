package com.diploma.service.controller;

import com.diploma.persistence.dao.CancellationDao;
import com.diploma.persistence.dao.RemainsDao;
import com.diploma.persistence.dao.RetailProductDao;
import com.diploma.persistence.dao.UserDao;
import com.diploma.persistence.entity.CancellationEntity;
import com.diploma.persistence.entity.RemainsEntity;
import com.diploma.persistence.entity.RetailProductEntity;
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
public class CancelDialogController {

    public Text label;
    public ChoiceBox<String> product;
    public DatePicker date;
    public TextField amount;
    public TextField comment;
    public Button save_btn;

    private CancellationDao cancellationDao;
    private RemainsDao remainsDao;
    private RetailProductDao retailProductDao;
    private UserDao userDao;

    public CancelDialogController(CancellationDao cancellationDao, RemainsDao remainsDao,
                                  RetailProductDao retailProductDao, UserDao userDao) {
        this.cancellationDao = cancellationDao;
        this.remainsDao = remainsDao;
        this.retailProductDao = retailProductDao;
        this.userDao = userDao;
    }

    @FXML
    public void initialize() {

    }

    public void initUpdate(CancellationEntity cancellationEntity, Long currentUser) {
        ObservableList<String> productList = FXCollections.observableArrayList();
        Double oldAmount;
        for (RetailProductEntity productEntity : retailProductDao.findAll()) {
            productList.add(productEntity.getName());
        }
        product.setItems(productList);
        product.setValue(cancellationEntity.getRetailProductEntity().getName());
        date.setValue(LOCAL_DATE(cancellationEntity.getDate()));
        amount.setText(cancellationEntity.getAmount().toString());
        oldAmount = Double.parseDouble(amount.getText());
        comment.setText(cancellationEntity.getComment());

        save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                cancellationDao.update(cancellationEntity.getId(),
                                 date.getValue().toString(),
                                 comment.getText(),
                                 Double.parseDouble(amount.getText()),
                                 currentUser,
                                 retailProductDao.findOneByName(
                                         product.getSelectionModel().getSelectedItem()).getId());

                RemainsEntity remainsEntity = remainsDao
                        .findOneByProduct(product.getSelectionModel().getSelectedItem());

                remainsEntity.setAmount(remainsEntity.getAmount() + oldAmount - Double.parseDouble(amount.getText()));
                remainsDao.update(remainsEntity.getId(), remainsEntity.getAmount(),
                                  remainsEntity.getRetailProductEntity().getId());

                closeDialogStage(saveEvent);
            } else {
                String toastMsg = "Заповніть усі поля!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            }
        });
    }

    public void initCreate(Long userId) {
        ObservableList<String> productList = FXCollections.observableArrayList();

        for (RetailProductEntity productEntity : retailProductDao.findAll()) {
            productList.add(productEntity.getName());
        }
        product.setItems(productList);
        label.setText("Нове зписання");

        save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                CancellationEntity cancellationEntity = new CancellationEntity();
                cancellationEntity.setDate(date.getValue().toString());
                cancellationEntity.setRetailProductEntity(
                        retailProductDao.findOneByName(product.getSelectionModel().getSelectedItem()));
                cancellationEntity.setAmount(Double.parseDouble(amount.getText()));
                cancellationEntity.setComment(comment.getText());
                cancellationEntity.setUserEntity(userDao.findOneById(userId));
                cancellationDao.save(cancellationEntity);

                RemainsEntity remainsEntity = remainsDao
                        .findOneByProduct(product.getSelectionModel().getSelectedItem());
                if (remainsEntity != null) {
                    remainsEntity.setAmount(remainsEntity.getAmount() - Double.parseDouble(amount.getText()));
                } else {
                    remainsEntity = new RemainsEntity();
                    remainsEntity.setAmount(Double.parseDouble(amount.getText()) * -1);
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

    private static LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    private boolean isFilled() {
        if (date.getValue() != null) {
            if (!product.getSelectionModel().isEmpty()) {
                if (!amount.getText().isEmpty()) {
                    if (!comment.getText().isEmpty()) {
                        try {
                            Double.parseDouble(amount.getText());
                            return true;
                        } catch (NumberFormatException e) {
                            return false;
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
