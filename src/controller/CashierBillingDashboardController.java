package controller;

import TM.paymentTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CartTM;
import model.Medicine;
import model.Payment;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierBillingDashboardController implements Initializable {
    public JFXButton CashierBillingcontext;
    public Label lblTime;
    public Label lblDate;
    public TableView<Medicine> tblMedicines;
    public TableColumn colMedicineId;
    public TableColumn colMedicineName;
    public TableColumn colQty;
    public TableColumn colExpDate;
    public TableColumn colPrice;
    public TableColumn colManufactureDate;
    public TextField txtQty;
    public TextField txtMedicineId;
    public TextField txtMedicineName;
    public TextField txtPrice;
    public TextField txtDate;
    public TableView tblAddToCart;
    public TableColumn colCartBillId;
    public TableColumn colCartMedicineName;
    public TableColumn colCartQty;
    public TableColumn cplCartPrice;
    public TableColumn colCartDate;
    public TextField txtBillId;
    public TableColumn colCartCashierId;

    public TextField searchMedicineField;
    public TextField txtTotalAmount;
    public TableColumn colCartTotal;
    public Label lblCashierID;
    public JFXButton btnBeanArray;
    public Label lblTotal;
    public Label lblPrice;
    public JFXTextField txtunitPrice;
    public TableColumn colbtn;
    double total = 0;

    ObservableList<CartTM> tmList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        TimeNow();


        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("medicineQty"));
        colManufactureDate.setCellValueFactory(new PropertyValueFactory<>("manufactureDate"));
        colExpDate.setCellValueFactory(new PropertyValueFactory<>("ExpireDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));


        try {
            loadAllMedicine();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        colCartBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colCartCashierId.setCellValueFactory(new PropertyValueFactory<>("cashierId"));
        colCartMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colCartQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        cplCartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCartTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colCartDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colbtn.setCellValueFactory(new PropertyValueFactory<>("btn"));


        try {
            loadAllPayment();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadAllMedicine() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
        String sql = "SELECT * FROM Medicine";
        Statement statement = con.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);

        ObservableList<Medicine> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(
                    new Medicine(
                            result.getString("medicineId"),
                            result.getString("medicineName"),
                            result.getString("medicineQty"),
                            result.getString("manufactureDate"),
                            result.getString("expDate"),
                            result.getDouble("Price")
                    )


            );
        }
        tblMedicines.setItems(obList);


        FilteredList<Medicine> filteredData = new FilteredList<>(obList, b -> true);

        searchMedicineField.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(Medicine -> {


                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKayword = newValue.toLowerCase();
                if (Medicine.getMedicineId().toLowerCase().indexOf(searchKayword) > -1) {
                    return true;
                } else return Medicine.getMedicineName().toLowerCase().indexOf(searchKayword) > -1;

            });
        });
        SortedList<Medicine> shortedData = new SortedList<>(filteredData);
        shortedData.comparatorProperty().bind(tblMedicines.comparatorProperty());
        tblMedicines.setItems(shortedData);


    }


    public void btnBacktoCashierMainFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierMainForm");

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) CashierBillingcontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));
    }


    private void TimeNow() {

        Timeline dateTime = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("hh:mm:ss a");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            lblTime.setText(LocalDateTime.now().format(formatter1));
            lblDate.setText(LocalDateTime.now().format(formatter2));
        }), new KeyFrame(Duration.seconds(1)));
        dateTime.setCycleCount(Animation.INDEFINITE);
        dateTime.play();
    }


    public void btnAddTopaymentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        Payment p = new Payment(
                txtBillId.getText(), lblCashierID.getText(), txtMedicineName.getText(), txtQty.getText(), Double.parseDouble(txtunitPrice.getText()), Double.parseDouble(lblTotal.getText()), txtDate.getText()
        );

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");

            String sql = "INSERT INTO  unionchemistspharmacy.payment  VALUES('" + p.getBillId() + "','" + p.getCashierId() + "','" + p.getMedicineName() + "','" + p.getQty() + "','" + p.getPrice() + "','" + p.getTotalAmount() + "','" + p.getDate() + "')";

            Statement stm = con.createStatement();
            int effectedRowCount = stm.executeUpdate(sql);
            System.out.println(effectedRowCount);


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        try {
            loadAllPayment();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        clearAllTextField();


    }


    private void loadAllPayment() throws ClassNotFoundException, SQLException {
    /*    Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
        String sql = "SELECT * FROM payment";
        Statement statement = con.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);*/
        ResultSet result = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.payment");
        ObservableList<paymentTM> obList = FXCollections.observableArrayList();


        while (result.next()) {

            Button btn = new Button("Delete");
            paymentTM tm = new paymentTM(

                    result.getString("billId"),
                    result.getString("cashierId"),
                    result.getString("medicineName"),
                    result.getString("medicineQty"),
                    result.getDouble("price"),
                    result.getDouble("totalAmount"),
                    result.getString("date"),
                    btn


            );
            btn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.get().equals(ButtonType.YES)) {

                    paymentTM paymentTM = (TM.paymentTM) tblAddToCart.getSelectionModel().getSelectedItem();

                    try {
                        if (CrudUtill.execute("DELETE FROM unionchemistspharmacy.payment WHERE billId=?", paymentTM.getBillId())) {

                            Notifications notifications = Notifications.create().title("Delete Competed !").text("Order Deleted Sucsessful");
                            notifications.darkStyle();
                            notifications.show();


                        } else {


                        }
                    } catch (SQLException | ClassNotFoundException exception) {

                    }
                    obList.remove(paymentTM);


                }

            });

            obList.add(tm);
            tblAddToCart.setItems(obList);

        }


    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.medicine WHERE  medicineId=?", searchMedicineField.getText());
        if (result.next()) {
            txtMedicineName.setText(result.getString(2));
            txtunitPrice.setText(String.valueOf((result.getDouble(6))));

        }
        ResultSet resultSet = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.medicine WHERE  medicineName=?", searchMedicineField.getText());
        if (resultSet.next()) {
            txtMedicineName.setText(resultSet.getString(2));
            txtunitPrice.setText(String.valueOf((resultSet.getDouble(6))));
        }


    }


   /* public void reportWithParam(javafx.scene.input.MouseEvent mouseEvent) {


        String billId = txtBillId.getText();
        String cashierId = lblCashierID.getText();
        String medicineName = txtMedicineName.getText();
        String qty = txtQty.getText();
        double price = Double.parseDouble(lblPrice.getText());
        String date = txtDate.getText();
        double total = Double.parseDouble(txtTotalAmount.getText());


        HashMap paraMap = new HashMap();
        paraMap.put("Billid", billId);
        paraMap.put("CashierID", cashierId);
        paraMap.put("Medicinename", medicineName);
        paraMap.put("amount", price);
        paraMap.put("total", total);
        paraMap.put("Date", date);
        paraMap.put("Qty", qty);

        try {
            JasperReport compilerReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/Reports/Bill.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compilerReport, paraMap, new JREmptyDataSource(1));
            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        }
    }*/

    public void beanArrayEvent(MouseEvent mouseEvent) {


        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/Reports/NewBill.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanArrayDataSource(tblAddToCart.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }


    }

    public void setTotalOnAction(ActionEvent actionEvent) {
        total = Double.parseDouble(txtunitPrice.getText()) * Double.parseDouble(txtQty.getText());
        lblTotal.setText(String.valueOf(total));


    }


    public void clearAllTextField() {

        txtBillId.clear();
        txtMedicineName.clear();
        txtunitPrice.clear();
        txtQty.clear();
        txtDate.clear();
        searchMedicineField.clear();

    }


    public void btnCllearOnAction(ActionEvent actionEvent) {
        clearAllTextField();

    }
}