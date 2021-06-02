package com.diploma.service.controller;

import com.diploma.persistence.dao.RetailProductDao;
import com.diploma.persistence.dao.SupplierDao;
import com.diploma.persistence.entity.RetailProductEntity;
import com.diploma.persistence.entity.SupplierEntity;
import com.diploma.service.Toast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class RawProductDialogController {

    public Text label;
    public TextField edit_raw_product_name;
    public ChoiceBox<String> edit_raw_supplier_name;
    public TextField edit_raw_cost;
    public TextField edit_raw_description;
    public Button edit_raw_save_btn;
    public TextField edit_prod_cost;

    private RetailProductDao rawProductDao;
    private SupplierDao supplierDao;

    public RawProductDialogController(RetailProductDao rawProductDao, SupplierDao supplierDao) {
        this.rawProductDao = rawProductDao;
        this.supplierDao = supplierDao;
    }

    @FXML
    public void initialize() {

    }

    public void initUpdate(RetailProductEntity rawProductEntity) {
        ObservableList<String> suppliersList = FXCollections.observableArrayList();

        edit_raw_product_name.setText(rawProductEntity.getName());
        for (SupplierEntity supplierEntity : supplierDao.findAll()) {
            suppliersList.add(supplierEntity.getName());
        }
        edit_raw_supplier_name.setItems(suppliersList);
        edit_raw_supplier_name.setValue(rawProductEntity.getRetailSupplier().getName());
        edit_raw_cost.setText(rawProductEntity.getRawCost().toString());
        edit_prod_cost.setText(rawProductEntity.getCost().toString());
        edit_raw_description.setText(rawProductEntity.getDescription());

        edit_raw_save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                rawProductDao.update(rawProductEntity.getId(),
                                     edit_raw_product_name.getText(),
                                     edit_raw_description.getText(),
                                     Double.parseDouble(edit_prod_cost.getText()),
                                     Double.parseDouble(edit_raw_cost.getText()),
                                     supplierDao.findOneByName(
                                             edit_raw_supplier_name.getSelectionModel().getSelectedItem())
                                                .getId());
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
        ObservableList<String> suppliersList = FXCollections.observableArrayList();

        for (SupplierEntity supplierEntity : supplierDao.findAll()) {
            suppliersList.add(supplierEntity.getName());
        }
        edit_raw_supplier_name.setItems(suppliersList);
        label.setText("Новий товар");

        edit_raw_save_btn.setOnAction(saveEvent -> {
            if (isFilled()) {
                RetailProductEntity rawProductEntity = new RetailProductEntity();
                rawProductEntity.setName(edit_raw_product_name.getText());
                rawProductEntity.setRetailSupplier(
                        supplierDao.findOneByName(edit_raw_supplier_name.getSelectionModel().getSelectedItem()));
                rawProductEntity.setCost(Double.parseDouble(edit_prod_cost.getText()));
                rawProductEntity.setRawCost(Double.parseDouble(edit_raw_cost.getText()));
                rawProductEntity.setDescription(edit_raw_description.getText());
                rawProductDao.save(rawProductEntity);
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

    private boolean isFilled() {
        if (!edit_raw_product_name.getText().isEmpty()) {
            if (!edit_raw_supplier_name.getSelectionModel().isEmpty()) {
                if (!edit_raw_description.getText().isEmpty()) {
                    if (!edit_raw_cost.getText().isEmpty()) {
                        if (!edit_prod_cost.getText().isEmpty()) {
                            try {
                                Double.parseDouble(edit_raw_cost.getText());
                                Double.parseDouble(edit_prod_cost.getText());
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
