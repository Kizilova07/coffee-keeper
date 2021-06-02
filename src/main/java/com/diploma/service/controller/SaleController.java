package com.diploma.service.controller;

import com.diploma.dto.CheckDto;
import com.diploma.enums.PaymentMethod;
import com.diploma.persistence.dao.RetailProductDao;
import com.diploma.persistence.dao.SaleDao;
import com.diploma.persistence.dao.SaleProductDao;
import com.diploma.persistence.entity.RetailProductEntity;
import com.diploma.persistence.entity.SaleEntity;
import com.diploma.persistence.entity.SaleProductEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class SaleController {

    public Label user_name_label;
    public Label check_number_label;
    public TableView<CheckDto> check_table;
    public TableColumn<CheckDto, String> product_name_column;
    public TableColumn<CheckDto, String> amount_column;
    public TableColumn<CheckDto, String> cost_column;
    public TableColumn<CheckDto, String> sum_column;
    public TableColumn<CheckDto, Void> delete_col;
    public Label total_cost_label;
    public Button pay_btn;
    public Button print_btn;
    public ListView<String> product_list;
    public TextField search_field;

    private Resource payScene;
    private Resource loginScene;
    private RetailProductDao productDao;
    private SaleDao saleDao;
    private SaleProductDao saleProductDao;
    private ObservableList<CheckDto> checkTableData = FXCollections.observableArrayList();
    private ObservableList<String> productEntities = FXCollections.observableArrayList();
    private final ApplicationContext applicationContext;
    private static SaleEntity currentSale;
    private static double totalSum;

    public SaleController(@Value("classpath:/scenes/pay_scene.fxml") Resource payScene,
                          @Value("classpath:/scenes/login_scene.fxml") Resource loginScene,
                          RetailProductDao productDao, SaleDao saleDao,
                          SaleProductDao saleProductDao,
                          ApplicationContext applicationContext) {
        this.payScene = payScene;
        this.loginScene = loginScene;
        this.productDao = productDao;
        this.saleDao = saleDao;
        this.saleProductDao = saleProductDao;
        this.applicationContext = applicationContext;
    }

    @FXML
    public void initialize() {
        checkTableData.clear();
        productEntities.clear();
        initSale();
        initFields();
    }

    private void initSale() {

            if (saleDao.findOneById(saleDao.findLastId()) == null || !saleDao.findOneById(saleDao.findLastId())
                                                                             .getPaymentMethod().name()
                                                                             .equals(PaymentMethod.OPEN.name())) {
                currentSale = new SaleEntity();
                long newId = 1L;
                if (saleDao.findLastId() != null)
                    newId += saleDao.findLastId();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                currentSale.setId(newId);
                currentSale.setDate(sdf.format(new Date()));
                currentSale.setPaymentMethod(PaymentMethod.OPEN);
                currentSale.setUserEntity(LoginController.getLoggedUser());
                currentSale.setIncome(0.0);
                currentSale.setProfit(0.0);
                saleDao.save(currentSale);
            } else
                currentSale = saleDao.findOneById(saleDao.findLastId());
    }

    private void initFields() {
        user_name_label.setText(LoginController.getLoggedUser().getName());
        check_number_label.setText("Чек №" + currentSale.getId());
        initTable();
        pay_btn.setOnAction(actionEvent -> {
            try {
                Stage stage = (Stage) pay_btn.getScene().getWindow();
                URL url = payScene.getURL();
                FXMLLoader fxmlLoader = new FXMLLoader(url);
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        print_btn.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showSaveDialog((Stage) print_btn.getScene().getWindow());

            if (file != null) {
                saveTextToFile(checkTableData, file);
            }
        });
        initProductList();
        search_field.textProperty().addListener(((observableValue, s, t1) -> {
            ObservableList<String> newProductEntities = FXCollections.observableArrayList();
            for (String productEntity : productEntities) {
                if (productEntity.contains(t1)) {
                    newProductEntities.add(productEntity);
                }
            }
            product_list.setItems(newProductEntities);
        }));

    }

    @FXML
    public void logout() {
        try {
            Stage stage = (Stage) pay_btn.getScene().getWindow();
            URL url = loginScene.getURL();
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

    @FXML
    public void handleMouseClick(MouseEvent event) {
        String[] split = product_list.getSelectionModel().getSelectedItem().split(" ");
        StringBuilder productName = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            productName.append(split[i]).append(" ");
        }
        productName.deleteCharAt(productName.length() - 1);
        RetailProductEntity product = productDao.findOneByName(productName.toString());
        SaleProductEntity saleProduct = saleProductDao.findOneByProduct(product.getName(), currentSale.getId());
        if (saleProduct != null) {
            saleProduct.setAmount(saleProduct.getAmount() + 1);
        } else {
            saleProduct = new SaleProductEntity();
            saleProduct.setSaleEntity(currentSale);
            saleProduct.setSaleRetailProductEntity(product);
            saleProduct.setAmount(1.0);
        }
        saleProductDao.save(saleProduct);
        checkTableData.clear();
        initTable();
        product_list.getSelectionModel().clearSelection();
    }

    private void initProductList() {
        List<RetailProductEntity> all = productDao.findAll();
        for (RetailProductEntity productEntity : all) {
            StringBuilder s = new StringBuilder(
                    productEntity.getName() + " \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
            int i = productEntity.getName().length() / 4;
            for (int i1 = 0; i1 < i; i1++) {
                s.deleteCharAt(s.length() - 1);
            }
            s.append(productEntity.getCost()).append("₴");
            productEntities.add(s.toString());
        }
        product_list.setItems(productEntities);
    }

    private void saveTextToFile(ObservableList<CheckDto> content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println("Назва\t\t\t\tКіль-ть\tЦіна\tВзагалом");
            for (CheckDto checkDto : content) {
                String data = checkDto.getProductName() + "\t\t\t\t" + checkDto.getAmount() + "\t" + checkDto
                        .getCost() + "\t" + checkDto.getSum();
                writer.println(data);
            }
            writer.println("Взагалом: " + total_cost_label.getText());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initTable() {
        fillCheckDataList(currentSale.getId());
        product_name_column.setCellValueFactory(new PropertyValueFactory<CheckDto, String>("productName"));
        amount_column.setCellValueFactory(new PropertyValueFactory<CheckDto, String>("amount"));
        cost_column.setCellValueFactory(new PropertyValueFactory<CheckDto, String>("cost"));
        sum_column.setCellValueFactory(new PropertyValueFactory<CheckDto, String>("sum"));
        delete_col.setCellFactory(col -> new TableCell<CheckDto, Void>() {
            private final Button button;

            {
                button = new Button("-");
                button.setOnAction(evt -> {
                    int index = getTableRow().getIndex();
                    CheckDto selected = check_table.getItems().get(index);
                    String[] split = selected.getAmount().split(" шт.");
                    if (Double.parseDouble(split[0]) > 1) {

                        saleProductDao
                                .update(saleProductDao.findOneByProduct(selected.getProductName(), currentSale.getId())
                                                      .getId(),
                                        Double.parseDouble(split[0]) - 1);
                    } else
                        saleProductDao.deleteOneByProductId(productDao.findOneByName(selected.getProductName()).getId(),
                                                            currentSale.getId());
                    checkTableData.clear();
                    initTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });
        double sum = 0.0;
        for (CheckDto checkTableDatum : checkTableData) {
            sum += Double.parseDouble(checkTableDatum.getSum());
        }
        total_cost_label.setText(Double.toString(sum) + " ₴");
        totalSum = sum;
    }

    private void fillCheckDataList(Long saleId) {
        int id = 1;
        for (SaleProductEntity saleProductEntity : saleProductDao.findAllBySaleId(saleId)) {
            Button button = new Button("-");
            checkTableData.add(new CheckDto(saleProductEntity.getSaleRetailProductEntity().getName(),
                                            saleProductEntity.getAmount().toString()+" шт.",
                                            saleProductEntity.getSaleRetailProductEntity().getCost().toString(),
                                            String.valueOf(saleProductEntity.getAmount() * saleProductEntity
                                                    .getSaleRetailProductEntity().getCost()), button));
            id++;
        }
        check_table.setItems(checkTableData);
    }

    public static SaleEntity getCurrentSale() {
        return currentSale;
    }

    public static double getTotalSum() {
        return totalSum;
    }
}
