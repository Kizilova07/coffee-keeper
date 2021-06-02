package com.diploma.service.controller;

import com.diploma.dto.*;
import com.diploma.enums.PaymentMethod;
import com.diploma.persistence.entity.*;
import com.diploma.persistence.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class AdminController {
    public TabPane main_tab_pane;
    public Tab admin_menu_main_tab;
    public Tab admin_warehouse_main_tab;
    public Tab admin_statistics_main_tab;

    public TabPane menu_tab_pane;
    public Tab raw_tab;
    public TableView<ProductDto> raw_table;
    public TableColumn<ProductDto, Integer> raw_id_column;
    public TableColumn<ProductDto, String> raw_product_column;
    public TableColumn<ProductDto, String> raw_supplier_column;
    public TableColumn<ProductDto, String> raw_cost_column;
    public TableColumn<ProductDto, String> raw_raw_cost_column;
    public TableColumn<ProductDto, Void> raw_detail_column;
    public Label raw_amount;
    public Button raw_add_btn;

    public TabPane warehouse_tab_pane;
    public Tab remains_tab;
    public TableView<RemainDto> leaves_table;
    public TableColumn<RemainDto, Integer> leaves_name_column;
    public TableColumn<RemainDto, String> leaves_product_column;
    public TableColumn<RemainDto, String> leaves_amount_column;
    public Label leaves_current_date_label;

    public Tab supplies_tab;
    public DatePicker supplies_end_date_picker;
    public DatePicker supplies_start_date_picker;
    public Label supplies_amount;
    public TableView<SupplyDto> supplies_table;
    public TableColumn<SupplyDto, String> supplies_date_column;
    public TableColumn<SupplyDto, String> supplies_suppliers_column;
    public TableColumn<SupplyDto, String> supplies_product_column;
    public TableColumn<SupplyDto, String> supplies_comment_column;
    public TableColumn<SupplyDto, String> supplies_sum_column;
    public TableColumn<SupplyDto, Void> supplies_details_column;
    public Button supplies_add_btn;

    public Tab suppliers_tab;
    public ScrollPane suppliers_scroll;
    public TableView<SupplierDto> suppliers_table;
    public TableColumn<SupplierDto, Integer> suppliers_numb_column;
    public TableColumn<SupplierDto, String> suppliers_name_column;
    public TableColumn<SupplierDto, String> suppliers_address_column;
    public TableColumn<SupplierDto, String> suppliers_phone_column;
    public TableColumn<SupplierDto, Void> suppliers_details_column;
    public Button suppliers_add_btn;
    public Label suppliers_amount;

    public Tab calcel_tab;
    public DatePicker cancel_end_date_picker;
    public DatePicker cancel_start_date_picker;
    public Label cancel_amount;
    public TableView<CancelDto> cancel_table;
    public TableColumn<CancelDto, String> cancel_date_column;
    public TableColumn<CancelDto, String> cancel_product_column;
    public TableColumn<CancelDto, String> cancel_sum_column;
    public TableColumn<CancelDto, String> cancel_user_column;
    public TableColumn<CancelDto, String> cancel_description_column;
    public TableColumn<CancelDto, Void> cancel_change_column;
    public Button cancel_add_btn;

    public TabPane statistics_tab_pane;
    public Tab staff_tab;
    public DatePicker staff_end_date_picker;
    public DatePicker staff_start_date_picker;
    public Label staff_current_date_label;
    public Label staff_income;
    public Label staff_check_amount;
    public TableView<StaffDto> staff_table;
    public TableColumn<StaffDto, String> staff_name_column;
    public TableColumn<StaffDto, String> staff_income_column;
    public TableColumn<StaffDto, String> staff_profit_column;
    public TableColumn<StaffDto, String> staff_check_amount_column;
    public TableColumn<StaffDto, String> staff_average_check_column;

    public Tab sales_tab;
    public DatePicker sales_end_date_picker;
    public DatePicker sales_start_date_picker;
    public Label sales_current_date_label;
    public Label sales_today_income;
    public Label sales_today_profit;
    public LineChart<String, Double> sales_income_chart;
    public CategoryAxis income_date_axis;
    public NumberAxis income_amount_axis;
    public BarChart<String, Double> sales_indome_by_day_chart;
    public CategoryAxis income_by_day_days_axis;
    public NumberAxis income_by_day_amount_axis;

    public Tab payments_tab;
    public DatePicker payments_end_date_picker;
    public DatePicker payments_start_date_picker;
    public Label payments_current_date_label;
    public Label payments_income_cash;
    public Label payments_income_card;
    public TableView<PaymentDto> payments_table;
    public TableColumn<PaymentDto, String> payment_date_column;
    public TableColumn<PaymentDto, String> payment_amount_column;
    public TableColumn<PaymentDto, String> payment_cash_column;
    public TableColumn<PaymentDto, String> payment_card_column;
    public TableColumn<PaymentDto, String> payment_total_column;
    public Label logout_label_btn;

    public Tab expences_tab;
    public DatePicker expence_end_date_picker;
    public DatePicker expence_start_date_picker;
    public Label expence_current_date_label;
    public Label sales_today_expence;
    public LineChart<String, Double> sales_expence_chart;
    public CategoryAxis expence_date_axis;
    public NumberAxis expence_amount_axis;
    public BarChart<String, Double> sales_expence_by_day_chart;
    public CategoryAxis expence_by_day_days_axis;
    public NumberAxis expence_by_day_amount_axis;


    private final ApplicationContext applicationContext;

    private SupplierDao supplierDao;
    private SupplyDao supplyDao;
    private RemainsDao remainsDao;
    private CancellationDao cancellationDao;
    private RetailProductDao rawProductDao;
    private Resource supplierDialog;
    private Resource suppliesDialog;
    private Resource rawProductDialog;
    private Resource cancelDialog;
    private SaleDao saleDao;
    private Resource loginScene;
    private ObservableList<SupplierDto> supplierTableData = FXCollections.observableArrayList();
    private ObservableList<SupplyDto> suppliesTableData = FXCollections.observableArrayList();
    private ObservableList<ProductDto> rawProductTableData = FXCollections.observableArrayList();
    private ObservableList<RemainDto> remainsTableData = FXCollections.observableArrayList();
    private ObservableList<CancelDto> cancelTableData = FXCollections.observableArrayList();
    private ObservableList<PaymentDto> paymentsTableData = FXCollections.observableArrayList();
    private ObservableList<StaffDto> staffTableData = FXCollections.observableArrayList();
    private List<SaleEntity> currentSales = new ArrayList<>();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat df = new DecimalFormat("###.##");
    private static final Calendar currentDate = new GregorianCalendar();

    public AdminController(ApplicationContext applicationContext, SupplierDao supplierDao,
                           SupplyDao supplyDao, RemainsDao remainsDao,
                           CancellationDao cancellationDao, RetailProductDao rawProductDao,
                           SaleDao saleDao,
                           @Value("classpath:/scenes/supplier_dialog.fxml") Resource supplierDialog,
                           @Value("classpath:/scenes/cancel_dialog.fxml") Resource cancelDialog,
                           @Value("classpath:/scenes/supply_dialog.fxml") Resource suppliesDialog,
                           @Value("classpath:/scenes/raw_product_dialog.fxml") Resource rawProductDialog,
                           @Value("classpath:/scenes/login_scene.fxml") Resource loginScene) {
        this.applicationContext = applicationContext;
        this.supplierDao = supplierDao;
        this.supplyDao = supplyDao;
        this.remainsDao = remainsDao;
        this.cancelDialog = cancelDialog;
        this.cancellationDao = cancellationDao;
        this.rawProductDao = rawProductDao;
        this.supplierDialog = supplierDialog;
        this.suppliesDialog = suppliesDialog;
        this.rawProductDialog = rawProductDialog;
        this.saleDao = saleDao;
        this.loginScene = loginScene;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);
    }

    @FXML
    public void initialize() {
        cleanAll();
        currentSales = saleDao.findAllByDate(formatter.format(new Date()));
        initSuppliers();
        initRawProducts();
        initSupplies();
        initRemains();
        initCancel();
        initPayments();
        initStaff();
        initIncome();
        initExpense();
    }

    private void initExpense() {
        sales_expence_by_day_chart.getData().clear();
        sales_expence_chart.getData().clear();
        if (expence_start_date_picker.getValue() == null && expence_end_date_picker.getValue() == null) {
            fillExpenseByDayBarChart();
            fillExpenseLineChart();
        } else if (expence_start_date_picker.getValue() != null && expence_end_date_picker.getValue() != null) {
            fillExpenseByDayBarChart(expence_start_date_picker.getValue().toString(),
                                     expence_end_date_picker.getValue().toString());
            fillExpenseLineChart(expence_start_date_picker.getValue().toString(),
                                 expence_end_date_picker.getValue().toString());
        } else if (expence_start_date_picker.getValue() == null && expence_end_date_picker.getValue() != null) {
            fillExpenseByDayBarChart("1970-01-01", expence_end_date_picker.getValue().toString());
            fillExpenseLineChart("1970-01-01", expence_end_date_picker.getValue().toString());
        } else {
            fillExpenseByDayBarChart(expence_start_date_picker.getValue().toString(),
                                     formatter.format(new Date()));
            fillExpenseLineChart(expence_start_date_picker.getValue().toString(),
                                 formatter.format(new Date()));
        }
        expence_start_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            initExpense();
        }));
        expence_end_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            initExpense();
        }));
        expence_current_date_label.setText(
                currentDate.get(Calendar.DAY_OF_MONTH) + " " + currentDate.getDisplayName(Calendar.MONTH,
                                                                                          Calendar.LONG,
                                                                                          new Locale("uk")));
        sales_today_expence.setText(getCurrentExpense() + " ₴");
    }

    private void fillExpenseByDayBarChart(String startDate, String endDate) {

        expence_by_day_amount_axis.setLabel("Витрати");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_expence_by_day_chart.setLegendVisible(false);
        List<SupplyEntity> allSupplies = supplyDao.findAllInPeriod(startDate, endDate);
        List<String> days = new ArrayList<>();
        days.add("понеділок");
        days.add("вівторок");
        days.add("середа");
        days.add("четвер");
        days.add("пʼятниця");
        days.add("субота");
        days.add("неділя");
        try {
            Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("Europe/Moscow"));
            for (String date : days) {
                double expense = 0.0;
                for (SupplyEntity supply : allSupplies) {
                    Date parse = formatter.parse(supply.getDate());
                    calendar.setTime(parse);
                    String ru = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("uk"));
                    if (date.equals(ru)) {
                        expense += (supply.getAmount() * supply.getRetailProductEntity().getRawCost());
                    }
                }
                series.getData().add(new XYChart.Data<>(date, expense));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sales_expence_by_day_chart.getData().add(series);
    }

    private void fillExpenseLineChart(String startDate, String endDate) {

        expence_amount_axis.setLabel("Витрати");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_expence_chart.setLegendVisible(false);
        List<SupplyEntity> allSupplies = supplyDao.findAllInPeriod(startDate, endDate);
        Set<String> dates = new TreeSet<>();
        for (SupplyEntity supplyEntity : allSupplies) {
            dates.add(supplyEntity.getDate());
        }
        for (String date : dates) {
            double expense = 0.0;
            for (SupplyEntity supply : allSupplies) {
                if (date.equals(supply.getDate())) {
                    expense += (supply.getAmount() * supply.getRetailProductEntity().getRawCost());
                }
            }
            series.getData().add(new XYChart.Data<>(date, expense));
        }
        sales_expence_chart.getData().add(series);
    }

    private void fillExpenseByDayBarChart() {
        expence_by_day_amount_axis.setLabel("Витрати");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_expence_by_day_chart.setLegendVisible(false);
        List<SupplyEntity> allSupplies = supplyDao.findAll();
        List<String> days = new ArrayList<>();
        days.add("понеділок");
        days.add("вівторок");
        days.add("середа");
        days.add("четвер");
        days.add("пʼятниця");
        days.add("субота");
        days.add("неділя");
        try {
            Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("Europe/Moscow"));
            for (String date : days) {
                double expense = 0.0;
                for (SupplyEntity supply : allSupplies) {
                    Date parse = formatter.parse(supply.getDate());
                    calendar.setTime(parse);
                    String ru = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("uk"));
                    if (date.equals(ru)) {
                        expense += (supply.getAmount() * supply.getRetailProductEntity().getRawCost());
                    }
                }
                series.getData().add(new XYChart.Data<>(date, expense));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sales_expence_by_day_chart.getData().add(series);
    }

    private void fillExpenseLineChart() {
        expence_amount_axis.setLabel("Витрати");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_expence_chart.setLegendVisible(false);
        List<SupplyEntity> allSupplies = supplyDao.findAll();
        Set<String> dates = new TreeSet<>();
        for (SupplyEntity supplyEntity : allSupplies) {
            dates.add(supplyEntity.getDate());
        }
        for (String date : dates) {
            double expense = 0.0;
            for (SupplyEntity supply : allSupplies) {
                if (date.equals(supply.getDate())) {
                    expense += (supply.getAmount() * supply.getRetailProductEntity().getRawCost());
                }
            }
            series.getData().add(new XYChart.Data<>(date, expense));
        }
        sales_expence_chart.getData().add(series);
    }

    private void initIncome() {
        if (sales_start_date_picker.getValue() == null && sales_end_date_picker.getValue() == null) {
            fillIncomeByDayBarChart();
            fillIncomeLineChart();
        } else if (sales_start_date_picker.getValue() != null && sales_end_date_picker.getValue() != null) {
            fillIncomeByDayBarChart(sales_start_date_picker.getValue().toString(),
                                    sales_end_date_picker.getValue().toString());
            fillIncomeLineChart(sales_start_date_picker.getValue().toString(),
                                sales_end_date_picker.getValue().toString());
        } else if (sales_start_date_picker.getValue() == null && sales_end_date_picker.getValue() != null) {
            fillIncomeByDayBarChart("1970-01-01", sales_end_date_picker.getValue().toString());
            fillIncomeLineChart("1970-01-01", sales_end_date_picker.getValue().toString());
        } else {
            fillIncomeByDayBarChart(sales_start_date_picker.getValue().toString(),
                                    formatter.format(new Date()));
            fillIncomeLineChart(sales_start_date_picker.getValue().toString(),
                                formatter.format(new Date()));
        }
        sales_start_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            initIncome();
        }));
        sales_end_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            initIncome();
        }));
        sales_current_date_label.setText(
                currentDate.get(Calendar.DAY_OF_MONTH) + " " + currentDate.getDisplayName(Calendar.MONTH,
                                                                                          Calendar.LONG,
                                                                                          new Locale("uk")));
        sales_today_income.setText(getCurrentIncome() + " ₴");
        sales_today_profit.setText(getCurrentProfit() + " ₴");
    }

    private void fillIncomeByDayBarChart(String startDate, String endDate) {
        sales_indome_by_day_chart.getData().clear();
        income_by_day_amount_axis.setLabel("Дохід");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_indome_by_day_chart.setLegendVisible(false);
        List<SaleEntity> allSales = saleDao.findAllInPeriod(startDate, endDate);
        List<String> days = new ArrayList<>();
        days.add("понеділок");
        days.add("вівторок");
        days.add("середа");
        days.add("четвер");
        days.add("пʼятниця");
        days.add("субота");
        days.add("неділя");
        try {
            Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("Europe/Moscow"));
            for (String date : days) {
                double income = 0.0;
                for (SaleEntity sale : allSales) {
                    Date parse = formatter.parse(sale.getDate());
                    calendar.setTime(parse);
                    String ru = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("uk"));
                    if (date.equals(ru)) {
                        income += sale.getIncome();
                    }
                }
                series.getData().add(new XYChart.Data<>(date, income));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sales_indome_by_day_chart.getData().add(series);
    }

    private void fillIncomeLineChart(String startDate, String endDate) {
        sales_income_chart.getData().clear();
        income_amount_axis.setLabel("Дохід");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_income_chart.setLegendVisible(false);
        List<SaleEntity> allSales = saleDao.findAllInPeriod(startDate, endDate);
        Set<String> dates = new TreeSet<>();
        for (SaleEntity saleEntity : allSales) {
            dates.add(saleEntity.getDate());
        }
        for (String date : dates) {
            double income = 0.0;
            for (SaleEntity sale : allSales) {
                if (date.equals(sale.getDate())) {
                    income += sale.getIncome();
                }
            }
            series.getData().add(new XYChart.Data<>(date, income));
        }
        sales_income_chart.getData().add(series);
    }

    private void fillIncomeByDayBarChart() {
        income_by_day_amount_axis.setLabel("Дохід");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_indome_by_day_chart.setLegendVisible(false);
        List<SaleEntity> allSales = saleDao.findAll();
        List<String> days = new ArrayList<>();
        days.add("понеділок");
        days.add("вівторок");
        days.add("середа");
        days.add("четвер");
        days.add("пʼятниця");
        days.add("субота");
        days.add("неділя");
        try {
            Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("Europe/Moscow"));
            for (String date : days) {
                double income = 0.0;
                for (SaleEntity sale : allSales) {
                    Date parse = formatter.parse(sale.getDate());
                    calendar.setTime(parse);
                    String ru = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("uk"));
                    if (date.equals(ru)) {
                        income += sale.getIncome();
                    }
                }
                series.getData().add(new XYChart.Data<>(date, income));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sales_indome_by_day_chart.getData().add(series);
    }

    private void fillIncomeLineChart() {
        income_amount_axis.setLabel("Дохід");
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        sales_income_chart.setLegendVisible(false);
        List<SaleEntity> allSales = saleDao.findAll();
        Set<String> dates = new TreeSet<>();
        for (SaleEntity saleEntity : allSales) {
            dates.add(saleEntity.getDate());
        }
        for (String date : dates) {
            double income = 0.0;
            for (SaleEntity sale : allSales) {
                if (date.equals(sale.getDate())) {
                    income += sale.getIncome();
                }
            }
            series.getData().add(new XYChart.Data<>(date, income));
        }
        sales_income_chart.getData().add(series);
    }

    private void initStaff() {
        if (staff_start_date_picker.getValue() == null && staff_end_date_picker.getValue() == null)
            fillStaffDataList();
        else if (staff_start_date_picker.getValue() != null && staff_end_date_picker.getValue() != null)
            fillStaffDataList(staff_start_date_picker.getValue().toString(),
                              staff_end_date_picker.getValue().toString());
        else if (staff_start_date_picker.getValue() == null && staff_end_date_picker.getValue() != null)
            fillStaffDataList("1970-01-01", staff_end_date_picker.getValue().toString());
        else
            fillStaffDataList(staff_start_date_picker.getValue().toString(),
                              formatter.format(new Date()));
        staff_start_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            staffTableData.clear();
            initStaff();
        }));
        staff_end_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            staffTableData.clear();
            initStaff();
        }));
        staff_name_column.setCellValueFactory(new PropertyValueFactory<StaffDto, String>("user"));
        staff_income_column.setCellValueFactory(new PropertyValueFactory<StaffDto, String>("income"));
        staff_profit_column.setCellValueFactory(new PropertyValueFactory<StaffDto, String>("profit"));
        staff_check_amount_column.setCellValueFactory(new PropertyValueFactory<StaffDto, String>("amount"));
        staff_average_check_column.setCellValueFactory(new PropertyValueFactory<StaffDto, String>("averageSale"));
        staff_current_date_label.setText(
                currentDate.get(Calendar.DAY_OF_MONTH) + " " + currentDate.getDisplayName(Calendar.MONTH,
                                                                                          Calendar.LONG,
                                                                                          new Locale("uk")));
        staff_income.setText(getCurrentIncome() + " ₴");
        staff_check_amount.setText(String.valueOf(getCurrentSalesAmount()) + " шт.");
    }

    private void fillStaffDataList(String startDate, String endDate) {
        List<SaleEntity> allSales = saleDao.findAllInPeriod(startDate, endDate);
        Set<String> users = new TreeSet<>();
        for (SaleEntity saleEntity : allSales) {
            users.add(saleEntity.getUserEntity().getName());
        }
        for (String user : users) {
            StaffDto staffDto = new StaffDto(user);
            int amount = 0;
            double income = 0.0;
            double profit = 0.0;
            for (SaleEntity sale : allSales) {
                if (staffDto.getUser().equals(sale.getUserEntity().getName())) {
                    amount++;
                    income += sale.getIncome();
                    profit += sale.getProfit();
                    staffDto.setAmount(amount + " шт.");
                    staffDto.setIncome(income + "₴");
                    staffDto.setProfit(profit + "₴");
                }
            }
            staffDto.setAverageSale(df.format(income / amount) + "₴");
            staffTableData.add(staffDto);
        }
        staff_table.setItems(staffTableData);
    }

    private void fillStaffDataList() {
        List<SaleEntity> allSales = saleDao.findAll();
        Set<String> users = new TreeSet<>();
        for (SaleEntity saleEntity : allSales) {
            users.add(saleEntity.getUserEntity().getName());
        }
        for (String user : users) {
            StaffDto staffDto = new StaffDto(user);
            int amount = 0;
            double income = 0.0;
            double profit = 0.0;
            for (SaleEntity sale : allSales) {
                if (staffDto.getUser().equals(sale.getUserEntity().getName())) {
                    amount++;
                    income += sale.getIncome();
                    profit += sale.getProfit();
                    staffDto.setAmount(amount + " шт.");
                    staffDto.setIncome(income + "₴");
                    staffDto.setProfit(profit + "₴");
                }
            }
            staffDto.setAverageSale(df.format(income / amount) + "₴");
            staffTableData.add(staffDto);
        }
        staff_table.setItems(staffTableData);
    }

    private void initPayments() {
        if (payments_start_date_picker.getValue() == null && payments_end_date_picker.getValue() == null)
            fillPaymentsDataList();
        else if (payments_start_date_picker.getValue() != null && payments_end_date_picker.getValue() != null)
            fillPaymentsDataList(payments_start_date_picker.getValue().toString(),
                                 payments_end_date_picker.getValue().toString());
        else if (payments_start_date_picker.getValue() == null && payments_end_date_picker.getValue() != null)
            fillPaymentsDataList("1970-01-01", payments_end_date_picker.getValue().toString());
        else
            fillPaymentsDataList(payments_start_date_picker.getValue().toString(),
                                 formatter.format(new Date()));
        payments_start_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            paymentsTableData.clear();
            initPayments();
        }));
        payments_end_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            paymentsTableData.clear();
            initPayments();
        }));
        payment_date_column.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("date"));
        payment_amount_column.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("amount"));
        payment_cash_column.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("cashSum"));
        payment_card_column.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("cardSum"));
        payment_total_column.setCellValueFactory(new PropertyValueFactory<PaymentDto, String>("totalSum"));
        payments_current_date_label.setText(
                currentDate.get(Calendar.DAY_OF_MONTH) + " " + currentDate.getDisplayName(Calendar.MONTH,
                                                                                          Calendar.LONG,
                                                                                          new Locale("uk")));
        payments_income_cash.setText(getCurrentCash() + " ₴");
        payments_income_card.setText(getCurrentCard() + " ₴");
    }

    private void fillPaymentsDataList(String startDate, String endDate) {
        List<SaleEntity> allSales = saleDao.findAllInPeriod(startDate, endDate);
        Set<String> dates = new TreeSet<>();
        for (SaleEntity saleEntity : allSales) {
            dates.add(saleEntity.getDate());
        }
        for (String date : dates) {
            PaymentDto paymentDto = new PaymentDto(date);
            int amount = 0;
            double cashSum = 0.0;
            double cardSum = 0.0;
            for (SaleEntity sale : allSales) {
                if (paymentDto.getDate().equals(sale.getDate())) {
                    amount++;
                    paymentDto.setAmount(amount + " шт.");
                    if (sale.getPaymentMethod().name().equals(PaymentMethod.CASH.name())) {
                        cashSum += sale.getIncome();
                        paymentDto.setCashSum(cashSum + "₴");
                    } else if (sale.getPaymentMethod().name().equals(PaymentMethod.TERMINAL.name())) {
                        cardSum += sale.getIncome();
                        paymentDto.setCardSum(cardSum + "₴");
                    }
                }
            }
            paymentDto.setTotalSum((cashSum + cardSum) + "₴");
            paymentsTableData.add(paymentDto);
        }
        payments_table.setItems(paymentsTableData);
    }

    private void fillPaymentsDataList() {
        List<SaleEntity> allSales = saleDao.findAll();
        Set<String> dates = new TreeSet<>();
        for (SaleEntity saleEntity : allSales) {
            dates.add(saleEntity.getDate());
        }
        for (String date : dates) {
            PaymentDto paymentDto = new PaymentDto(date);
            int amount = 0;
            double cashSum = 0.0;
            double cardSum = 0.0;
            for (SaleEntity sale : allSales) {
                if (paymentDto.getDate().equals(sale.getDate())) {
                    amount++;
                    paymentDto.setAmount(amount + " шт.");
                    if (sale.getPaymentMethod().name().equals(PaymentMethod.CASH.name())) {
                        cashSum += sale.getIncome();
                        paymentDto.setCashSum(cashSum + "₴");
                    } else if (sale.getPaymentMethod().name().equals(PaymentMethod.TERMINAL.name())) {
                        cardSum += sale.getIncome();
                        paymentDto.setCardSum(cardSum + "₴");
                    }
                }
            }
            paymentDto.setTotalSum((cashSum + cardSum) + "₴");
            paymentsTableData.add(paymentDto);
        }
        payments_table.setItems(paymentsTableData);
    }

    private void initCancel() {
        if (cancel_start_date_picker.getValue() == null && cancel_end_date_picker.getValue() == null)
            fillCancelDataList();
        else if (cancel_start_date_picker.getValue() != null && cancel_end_date_picker.getValue() != null)
            fillCancelDataList(cancel_start_date_picker.getValue().toString(),
                               cancel_end_date_picker.getValue().toString());
        else if (cancel_start_date_picker.getValue() == null && cancel_end_date_picker.getValue() != null)
            fillCancelDataList("1970-01-01", cancel_end_date_picker.getValue().toString());
        else
            fillCancelDataList(cancel_start_date_picker.getValue().toString(),
                               formatter.format(new Date()));
        cancel_start_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            cancelTableData.clear();
            initCancel();
        }));
        cancel_end_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            cancelTableData.clear();
            initCancel();
        }));
        cancel_date_column.setCellValueFactory(new PropertyValueFactory<CancelDto, String>("date"));
        cancel_product_column.setCellValueFactory(new PropertyValueFactory<CancelDto, String>("productName"));
        cancel_sum_column.setCellValueFactory(new PropertyValueFactory<CancelDto, String>("amount"));
        cancel_user_column.setCellValueFactory(new PropertyValueFactory<CancelDto, String>("user"));
        cancel_description_column.setCellValueFactory(new PropertyValueFactory<CancelDto, String>("comment"));
        cancel_change_column.setCellFactory(col -> new TableCell<CancelDto, Void>() {

            private final Button button;

            {
                button = new Button("Ред.");
                button.setOnAction(evt -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(cancelDialog.getURL());
                        fxmlLoader.setControllerFactory(applicationContext::getBean);
                        Parent parent = fxmlLoader.load();
                        int index = getTableRow().getIndex();
                        CancelDto selected = cancel_table.getItems().get(index);
                        String[] split = selected.getAmount().split(" шт.");
                        CancellationEntity cancellationEntity = cancellationDao.findOne(selected.getDate(),
                                                                                        selected.getComment(),
                                                                                        Double.parseDouble(split[0]),
                                                                                        selected.getUser(),
                                                                                        selected.getProductName());

                        Scene scene = new Scene(parent, 300, 200);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        CancelDialogController controller = fxmlLoader.getController();
                        controller.initUpdate(cancellationEntity, LoginController.getLoggedUser().getId());
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        initialize();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });
        cancel_add_btn.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(cancelDialog.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent, 300, 200);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                CancelDialogController controller = fxmlLoader.getController();
                controller.initCreate(LoginController.getLoggedUser().getId());
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                initialize();
            }
        });
        cancel_amount.setText(String.valueOf(cancelTableData.size()) + " шт.");
    }

    private void fillCancelDataList(String startDate, String endDate) {
        int id = 1;
        for (CancellationEntity cancellationEntity : cancellationDao.findAllInPeriod(startDate, endDate)) {
            Button button = new Button("Ред.");
            cancelTableData.add(new CancelDto(cancellationEntity.getDate(),
                                              cancellationEntity.getRetailProductEntity().getName(),
                                              String.valueOf(cancellationEntity.getAmount()) + " шт.",
                                              cancellationEntity.getUserEntity().getName(),
                                              cancellationEntity.getComment(),
                                              button));

            id++;
        }
        cancel_table.setItems(cancelTableData);
    }

    private void fillCancelDataList() {
        int id = 1;
        for (CancellationEntity cancellationEntity : cancellationDao.findAll()) {
            Button button = new Button("Ред.");
            cancelTableData.add(new CancelDto(cancellationEntity.getDate(),
                                              cancellationEntity.getRetailProductEntity().getName(),
                                              String.valueOf(cancellationEntity.getAmount()) + " шт.",
                                              cancellationEntity.getUserEntity().getName(),
                                              cancellationEntity.getComment(),
                                              button));

            id++;
        }
        cancel_table.setItems(cancelTableData);
    }

    private void initRemains() {
        fillRemainsDataList();
        leaves_name_column.setCellValueFactory(new PropertyValueFactory<RemainDto, Integer>("id"));
        leaves_product_column.setCellValueFactory(new PropertyValueFactory<RemainDto, String>("productName"));
        leaves_amount_column.setCellValueFactory(new PropertyValueFactory<RemainDto, String>("amount"));
        leaves_current_date_label.setText(
                currentDate.get(Calendar.DAY_OF_MONTH) + " " + currentDate.getDisplayName(Calendar.MONTH,
                                                                                          Calendar.LONG,
                                                                                          new Locale("uk")));
    }

    private void fillRemainsDataList() {
        int id = 1;
        for (RemainsEntity remainsEntity : remainsDao.findAll()) {
            remainsTableData.add(new RemainDto(id, remainsEntity.getRetailProductEntity().getName(),
                                               remainsEntity.getAmount().toString() + " шт."));
            id++;
        }
        leaves_table.setItems(remainsTableData);
    }

    private void initSupplies() {
        if (supplies_start_date_picker.getValue() == null && supplies_end_date_picker.getValue() == null)
            fillSuppliesDataList();
        else if (supplies_start_date_picker.getValue() != null && supplies_end_date_picker.getValue() != null)
            fillSuppliesDataList(supplies_start_date_picker.getValue().toString(),
                                 supplies_end_date_picker.getValue().toString());
        else if (supplies_start_date_picker.getValue() == null && supplies_end_date_picker.getValue() != null)
            fillSuppliesDataList("1970-01-01", supplies_end_date_picker.getValue().toString());
        else
            fillSuppliesDataList(supplies_start_date_picker.getValue().toString(),
                                 formatter.format(new Date()));
        supplies_start_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            suppliesTableData.clear();
            initSupplies();
        }));
        supplies_end_date_picker.valueProperty().addListener(((observableValue, localDate, t1) -> {
            suppliesTableData.clear();
            initSupplies();
        }));
        supplies_date_column.setCellValueFactory(new PropertyValueFactory<SupplyDto, String>("date"));
        supplies_suppliers_column.setCellValueFactory(new PropertyValueFactory<SupplyDto, String>("supplierName"));
        supplies_product_column.setCellValueFactory(new PropertyValueFactory<SupplyDto, String>("productName"));
        supplies_comment_column.setCellValueFactory(new PropertyValueFactory<SupplyDto, String>("comment"));
        supplies_sum_column.setCellValueFactory(new PropertyValueFactory<SupplyDto, String>("cost"));
        supplies_details_column.setCellFactory(col -> new TableCell<SupplyDto, Void>() {

            private final Button button;

            {
                button = new Button("Ред.");
                button.setOnAction(evt -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(suppliesDialog.getURL());
                        fxmlLoader.setControllerFactory(applicationContext::getBean);
                        Parent parent = fxmlLoader.load();
                        int index = getTableRow().getIndex();
                        SupplyDto selected = supplies_table.getItems().get(index);
                        String[] split = selected.getCost().split("₴");
                        SupplyEntity supplyEntity = supplyDao.findOne(selected.getDate(),
                                                                      selected.getComment(),
                                                                      Double.parseDouble(split[0]),
                                                                      selected.getProductName());

                        Scene scene = new Scene(parent, 300, 200);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        SupplyDialogController controller = fxmlLoader.getController();
                        controller.initUpdate(supplyEntity);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        initialize();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });
        supplies_add_btn.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(suppliesDialog.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent, 300, 200);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                SupplyDialogController controller = fxmlLoader.getController();
                controller.initCreate();
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                initialize();
            }
        });
        supplies_amount.setText(String.valueOf(suppliesTableData.size()) + " шт.");
    }

    private void fillSuppliesDataList(String startDate, String endDate) {
        int id = 1;
        for (SupplyEntity supplyEntity : supplyDao.findAllInPeriod(startDate, endDate)) {
            Button button = new Button("Ред.");
            suppliesTableData.add(new SupplyDto(supplyEntity.getDate(),
                                                supplyEntity.getRetailProductEntity().getRetailSupplier().getName(),
                                                supplyEntity.getRetailProductEntity().getName(),
                                                supplyEntity.getDescription(),
                                                supplyEntity.getCost().toString() + "₴",
                                                button));

            id++;
        }
        supplies_table.setItems(suppliesTableData);
    }

    private void fillSuppliesDataList() {
        int id = 1;
        for (SupplyEntity supplyEntity : supplyDao.findAll()) {
            Button button = new Button("Ред.");
            suppliesTableData.add(new SupplyDto(supplyEntity.getDate(),
                                                supplyEntity.getRetailProductEntity().getRetailSupplier().getName(),
                                                supplyEntity.getRetailProductEntity().getName(),
                                                supplyEntity.getDescription(),
                                                supplyEntity.getCost().toString() + "₴",
                                                button));

            id++;
        }
        supplies_table.setItems(suppliesTableData);
    }

    private void initRawProducts() {
        fillRawProductsDataList();
        raw_id_column.setCellValueFactory(new PropertyValueFactory<ProductDto, Integer>("id"));
        raw_product_column.setCellValueFactory(new PropertyValueFactory<ProductDto, String>("productName"));
        raw_supplier_column.setCellValueFactory(new PropertyValueFactory<ProductDto, String>("supplierName"));
        raw_cost_column.setCellValueFactory(new PropertyValueFactory<ProductDto, String>("cost"));
        raw_raw_cost_column.setCellValueFactory(new PropertyValueFactory<ProductDto, String>("rawCost"));
        raw_detail_column.setCellFactory(col -> new TableCell<ProductDto, Void>() {

            private final Button button;

            {
                button = new Button("Ред.");
                button.setOnAction(evt -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(rawProductDialog.getURL());
                        fxmlLoader.setControllerFactory(applicationContext::getBean);
                        Parent parent = fxmlLoader.load();
                        int index = getTableRow().getIndex();
                        ProductDto selected = raw_table.getItems().get(index);
                        RetailProductEntity rawProductEntity = rawProductDao.findOneByName(selected.getProductName());

                        Scene scene = new Scene(parent, 300, 200);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        RawProductDialogController controller = fxmlLoader.<RawProductDialogController>getController();
                        controller.initUpdate(rawProductEntity);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        initialize();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });
        raw_add_btn.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(rawProductDialog.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent, 300, 200);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                RawProductDialogController controller = fxmlLoader.<RawProductDialogController>getController();
                controller.initCreate();
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                initialize();
            }
        });
        raw_amount.setText(String.valueOf(rawProductTableData.size()) + " шт.");
    }

    private void fillRawProductsDataList() {
        int id = 1;
        for (RetailProductEntity rawProductEntity : rawProductDao.findAll()) {
            Button button = new Button("Ред.");
            rawProductTableData.add(new ProductDto(id, rawProductEntity.getName(),
                                                   rawProductEntity.getRetailSupplier().getName(),
                                                   rawProductEntity.getCost().toString() + "₴",
                                                   rawProductEntity.getRawCost().toString() + "₴",
                                                   rawProductEntity.getDescription(),
                                                   button));

            id++;
        }
        raw_table.setItems(rawProductTableData);
    }

    private void initSuppliers() {
        fillSuppliersDataList();
        suppliers_numb_column.setCellValueFactory(new PropertyValueFactory<SupplierDto, Integer>("id"));
        suppliers_name_column.setCellValueFactory(new PropertyValueFactory<SupplierDto, String>("name"));
        suppliers_address_column.setCellValueFactory(new PropertyValueFactory<SupplierDto, String>("address"));
        suppliers_phone_column.setCellValueFactory(new PropertyValueFactory<SupplierDto, String>("phone"));
        suppliers_details_column.setCellFactory(col -> new TableCell<SupplierDto, Void>() {

            private final Button button;

            {
                button = new Button("Ред.");
                button.setOnAction(evt -> {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(supplierDialog.getURL());
                        fxmlLoader.setControllerFactory(applicationContext::getBean);
                        Parent parent = fxmlLoader.load();
                        int index = getTableRow().getIndex();
                        SupplierDto selected = suppliers_table.getItems().get(index);
                        SupplierEntity supplierEntity = supplierDao.findOneByName(selected.getName());

                        Scene scene = new Scene(parent, 300, 200);
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setScene(scene);
                        SupplierDialogController controller = fxmlLoader.<SupplierDialogController>getController();
                        controller.initUpdate(supplierEntity);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        initialize();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        });
        suppliers_add_btn.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(supplierDialog.getURL());
                fxmlLoader.setControllerFactory(applicationContext::getBean);
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent, 300, 200);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                SupplierDialogController controller = fxmlLoader.<SupplierDialogController>getController();
                controller.initCreate();
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                initialize();
            }
        });
        suppliers_amount.setText(String.valueOf(supplierTableData.size()) + " шт.");
    }

    private void fillSuppliersDataList() {
        int id = 1;
        for (SupplierEntity supplierEntity : supplierDao.findAll()) {
            Button button = new Button("Ред.");
            supplierTableData.add(new SupplierDto(id, supplierEntity.getName(), supplierEntity.getAddress(),
                                                  supplierEntity.getPhone(), button));

            id++;
        }
        suppliers_table.setItems(supplierTableData);
    }

    private double getCurrentCash() {
        double res = 0.0;
        for (SaleEntity currentSale : currentSales) {
            if (currentSale.getPaymentMethod().name().equals(PaymentMethod.CASH.name()))
                res += currentSale.getIncome();
        }
        return res;
    }

    private double getCurrentCard() {
        double res = 0.0;
        for (SaleEntity currentSale : currentSales) {
            if (currentSale.getPaymentMethod().name().equals(PaymentMethod.TERMINAL.name()))
                res += currentSale.getIncome();
        }
        return res;
    }

    private double getCurrentIncome() {
        double res = 0.0;
        for (SaleEntity currentSale : currentSales) {
            res += currentSale.getIncome();
        }
        return res;
    }

    private double getCurrentProfit() {
        double res = 0.0;
        for (SaleEntity currentSale : currentSales) {
            res += currentSale.getProfit();
        }
        return res;
    }

    private double getCurrentExpense() {
        double res = 0.0;
        for (SupplyEntity supplyEntity : supplyDao.findAllByDate(formatter.format(new Date()))) {
            res += supplyEntity.getAmount() * supplyEntity.getRetailProductEntity().getRawCost();
        }
        return res;
    }

    private double getCurrentSalesAmount() {
        return currentSales.size();
    }

    private void cleanAll() {
        supplierTableData.clear();
        suppliesTableData.clear();
        rawProductTableData.clear();
        remainsTableData.clear();
        cancelTableData.clear();
        paymentsTableData.clear();
        staffTableData.clear();
    }

    @FXML
    public void logout() {
        try {
            Stage stage = (Stage) logout_label_btn.getScene().getWindow();
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


}
