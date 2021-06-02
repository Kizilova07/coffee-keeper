package com.diploma.service.controller;

import com.diploma.enums.PaymentMethod;
import com.diploma.persistence.dao.RemainsDao;
import com.diploma.persistence.dao.SaleDao;
import com.diploma.persistence.dao.SaleProductDao;
import com.diploma.persistence.entity.RemainsEntity;
import com.diploma.persistence.entity.SaleEntity;
import com.diploma.persistence.entity.SaleProductEntity;
import com.diploma.service.Toast;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class PayController {

    public Label user_name_label;
    public Label check_number_label;
    public TextField cash_field;
    public Button pin_1_btn;
    public Button pin_2_btn;
    public Button pin_3_btn;
    public Button pin_4_btn;
    public Button pin_5_btn;
    public Button pin_6_btn;
    public Button pin_7_btn;
    public Button pin_8_btn;
    public Button pin_9_btn;
    public Button pin_0_btn;
    public Button pin_dot_btn;
    public Button pin_back_btn;
    public TextField card_field;
    public Button pay_btn;
    public Label back_label_btn;
    public Label total_label;

    private final Resource saleScene;
    private SaleDao saleDao;
    private SaleProductDao saleProductDao;
    private RemainsDao remainsDao;
    private boolean isCashSelected = true;
    private final ApplicationContext applicationContext;

    public PayController(@Value("classpath:/scenes/sale_scene.fxml") Resource saleScene,
                         SaleDao saleDao, SaleProductDao saleProductDao,
                         RemainsDao remainsDao, ApplicationContext applicationContext) {
        this.saleScene = saleScene;
        this.saleDao = saleDao;
        this.saleProductDao = saleProductDao;
        this.remainsDao = remainsDao;
        this.applicationContext = applicationContext;
    }

    @FXML
    public void initialize() {
        this.pin_0_btn.setOnAction(pinBtnHandler);
        this.pin_1_btn.setOnAction(pinBtnHandler);
        this.pin_2_btn.setOnAction(pinBtnHandler);
        this.pin_3_btn.setOnAction(pinBtnHandler);
        this.pin_4_btn.setOnAction(pinBtnHandler);
        this.pin_5_btn.setOnAction(pinBtnHandler);
        this.pin_6_btn.setOnAction(pinBtnHandler);
        this.pin_7_btn.setOnAction(pinBtnHandler);
        this.pin_8_btn.setOnAction(pinBtnHandler);
        this.pin_9_btn.setOnAction(pinBtnHandler);
        this.pin_dot_btn.setOnAction(pinBtnHandler);
        this.pin_back_btn.setOnAction(actionEvent -> {
            if (isCashSelected)
                cash_field.setText("0.0");
            else
                card_field.setText("0.0");
        });
        total_label.setText(SaleController.getTotalSum() + " ₴");
        cash_field.setText("0.0");
        card_field.setText("0.0");
        cash_field.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("^\\d\\.$")) {
                cash_field.setText(t1.replaceAll("[^\\d\\.$]", ""));
            }
        }));
        card_field.textProperty().addListener(((observableValue, s, t1) -> {
            if (!t1.matches("^\\d\\.$")) {
                card_field.setText(t1.replaceAll("[^\\d\\.$]", ""));
            }
        }));
        check_number_label.setText("Чек №" + SaleController.getCurrentSale().getId());
        user_name_label.setText(LoginController.getLoggedUser().getName());
        pay_btn.setOnAction(actionEvent -> {
            SaleEntity sale = SaleController.getCurrentSale();
            double profit = 0.0;
            List<SaleProductEntity> allBySaleId = saleProductDao.findAllBySaleId(sale.getId());
            for (SaleProductEntity saleProductEntity : allBySaleId) {
                double sumBySaleProduct = saleProductEntity.getSaleRetailProductEntity()
                                                           .getCost() * saleProductEntity.getAmount();
                double rawSumBySaleProduct = saleProductEntity.getSaleRetailProductEntity()
                                                              .getRawCost() * saleProductEntity.getAmount();
                profit += (sumBySaleProduct - rawSumBySaleProduct);
            }
            double cash = 0.0;
            double card = 0.0;
            try{ cash = Double.parseDouble(cash_field.getText()); }catch (NumberFormatException ignored){ }
            try{ card = Double.parseDouble(card_field.getText()); }catch (NumberFormatException ignored){ }
            if (SaleController.getTotalSum() == cash) {
                sale.setPaymentMethod(PaymentMethod.CASH);
                sale.setIncome(SaleController.getTotalSum());
                sale.setProfit(profit);
                saleDao.save(sale);

                for (SaleProductEntity product : saleProductDao.findAllBySaleId(sale.getId())) {
                    RemainsEntity remainsEntity = remainsDao
                            .findOneByProduct(product.getSaleRetailProductEntity().getName());
                    if (remainsEntity != null) {
                        remainsEntity.setAmount(remainsEntity.getAmount() - product.getAmount());
                    } else {
                        remainsEntity = new RemainsEntity();
                        remainsEntity.setAmount(product.getAmount() * -1);
                        remainsEntity.setRetailProductEntity(product.getSaleRetailProductEntity());
                    }
                    remainsDao.save(remainsEntity);
                }
                moveBack();
            } else if (SaleController.getTotalSum() == card) {
                sale.setPaymentMethod(PaymentMethod.TERMINAL);
                sale.setIncome(SaleController.getTotalSum());
                sale.setProfit(profit);
                saleDao.save(sale);

                for (SaleProductEntity product : saleProductDao.findAllBySaleId(sale.getId())) {
                    RemainsEntity remainsEntity = remainsDao
                            .findOneByProduct(product.getSaleRetailProductEntity().getName());
                    if (remainsEntity != null) {
                        remainsEntity.setAmount(remainsEntity.getAmount() - product.getAmount());
                    } else {
                        remainsEntity = new RemainsEntity();
                        remainsEntity.setAmount(product.getAmount() * -1);
                        remainsEntity.setRetailProductEntity(product.getSaleRetailProductEntity());
                    }
                    remainsDao.save(remainsEntity);
                }
                moveBack();
            } else {
                String toastMsg = "Невірно введена оплата!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            }
        });
    }

    @FXML
    public void setCashSelected() {
        cash_field.clear();
        isCashSelected = true;
    }

    @FXML
    public void setCardSelected() {
        card_field.clear();
        isCashSelected = false;
    }

    @FXML
    public void moveBack() {
        try {
            Stage stage = (Stage) pay_btn.getScene().getWindow();
            URL url = saleScene.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final EventHandler<ActionEvent> pinBtnHandler = event -> {

        Button source = (Button) event.getSource();
        String text = source.getText();
        if (isCashSelected)
            cash_field.appendText(text);
        else
            card_field.appendText(text);
    };
}
