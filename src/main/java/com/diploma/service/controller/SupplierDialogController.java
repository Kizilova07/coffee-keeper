package com.diploma.service.controller;

import com.diploma.persistence.dao.SupplierDao;
import com.diploma.persistence.entity.SupplierEntity;
import com.diploma.service.Toast;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SupplierDialogController {

    public TextField edit_supplier_name;
    public TextField edit_supplier_address;
    public TextField edit_supplier_phone;
    public Button edit_supplier_save_btn;
    public Text label;

    private final SupplierDao supplierDao;

    public SupplierDialogController(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @FXML
    public void initialize() {

    }

    public void initUpdate(SupplierEntity supplierEntity) {
        edit_supplier_name.setText(supplierEntity.getName());
        edit_supplier_address.setText(supplierEntity.getAddress());
        edit_supplier_phone.setText(supplierEntity.getPhone());

        edit_supplier_save_btn.setOnAction(saveEvent -> {

            if (isFilled()) {
                supplierDao.update(supplierEntity.getId(),
                                   edit_supplier_name.getText(),
                                   edit_supplier_address.getText(),
                                   edit_supplier_phone.getText());
                closeDialogStage(saveEvent);
            } else {
                String toastMsg = "Форма заполнена не корректно!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            }
        });
    }

    public void initCreate() {
        label.setText("Новый поставщик");


        edit_supplier_save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                SupplierEntity supplierEntity = new SupplierEntity();
                supplierEntity.setName(edit_supplier_name.getText());
                supplierEntity.setAddress(edit_supplier_address.getText());
                supplierEntity.setPhone(edit_supplier_phone.getText());
                supplierDao.save(supplierEntity);
                closeDialogStage(saveEvent);
            } else {
                String toastMsg = "Форма заполнена не корректно!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) saveEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            }
        });
    }

    private boolean isFilled() {
        if (!edit_supplier_name.getText().isEmpty()) {
            if (!edit_supplier_address.getText().isEmpty()) {
                if (!edit_supplier_phone.getText().isEmpty()) {
                    Pattern pattern = Pattern.compile("^\\+380\\d\\d\\d\\d\\d\\d\\d\\d\\d$", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(edit_supplier_phone.getText());
                    if (matcher.matches())
                        return true;
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
