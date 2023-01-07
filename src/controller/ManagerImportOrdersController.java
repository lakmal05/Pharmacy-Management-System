package controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Order;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class ManagerImportOrdersController implements Initializable {

    public AnchorPane ImportOrderscontext;
    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colSupplierId;
    public TableColumn colHospitalName;
    public TableColumn colMedicineName;
    public TableColumn colQty;
    public TableColumn colDate;

    public Label lblTime;
    public Label lblDate;
    public JFXTextField txtDate;
    public JFXTextField txtQty;
    public JFXTextField txtMedicineName;
    public JFXTextField txtHospitalName;
    public JFXTextField txtSupplierId;
    public JFXTextField txtOrderId;
    public JFXTextField lblSearchOrder;
    public JFXButton btnAddOrderId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TimeNow();


        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colHospitalName.setCellValueFactory(new PropertyValueFactory<>("HospitalName"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("MedicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));


        try {
            loadAllOrder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


  /*  public void initialize() {

        TimeNow();


        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colHospitalName.setCellValueFactory(new PropertyValueFactory<>("HospitalName"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("MedicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));


        try {
            loadAllOrder();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }*/

    private void loadAllOrder() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
        String sql = "SELECT * FROM supplierimportorderitemdetails";
        Statement statement = con.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);

        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(
                    new Order(
                            result.getString("orderId"),
                            result.getString("supplierId"),
                            result.getString("HospitalName"),
                            result.getString("MedicineName"),
                            result.getString("MedicineQty"),
                            result.getString("orderdDate")

                    )


            );
        }
        tblOrder.setItems(obList);


        FilteredList<Order> filteredData = new FilteredList<>(obList, b -> true);
        lblSearchOrder.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(Order -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (Order.getOrderId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Order.getSupplierId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Order.getMedicineName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Order.getHospitalName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Order.getDate().toLowerCase().indexOf(searchKeyword) > -1;


            });

        });

        SortedList<Order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblOrder.comparatorProperty());
        tblOrder.setItems(sortedData);

    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ImportOrderscontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));

    }


    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerMainForm");
    }


    public void btnPlaceAOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Image image = new Image("saved.png");


        Notifications notBuilder = Notifications.create()
                .title("Success").title("Your Order Has been Completed !!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Clicked Oon Notification");
                    }
                });
        notBuilder.darkStyle();

        notBuilder.show();


        Order o = new Order(
                txtOrderId.getText(), txtSupplierId.getText(), txtHospitalName.getText(), txtMedicineName.getText(), txtQty.getText(), txtDate.getText()
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");

            String sql = "INSERT INTO supplierimportorderitemdetails VALUES('" + o.getOrderId() + "','" + o.getSupplierId() + "','" + o.getHospitalName() + "','" + o.getMedicineName() + "','" + o.getQty() + "','" + o.getDate() + "')";

            Statement stm = con.createStatement();
            int effectedRowCount = stm.executeUpdate(sql);
            System.out.println(effectedRowCount);


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        loadAllOrder();
        clearAllTextFields();
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


    public void textFields_Key_Release(KeyEvent keyEvent) {
        Object validate = validate();

        //boolean validation ok
        //txtFeiled false


        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validate();

            if (response instanceof java.awt.TextField) {//me welawe thiyen instense ntext feald ekkda
                java.awt.TextField textField = (java.awt.TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }

        }


    }

    private Object validate() {

        String typeOrder = txtOrderId.getText();
        String typeSupplierId = txtSupplierId.getText();
        String typeHospitalName = txtHospitalName.getText();
        String typeMedicineName = txtMedicineName.getText();
        String typeQTY = txtQty.getText();
        String typeOrderdDate = txtDate.getText();

        btnAddOrderId.setDisable(true);

        Pattern OrderIdPattern = Pattern.compile("^(O0)[0-9]{2,6}$");
        Pattern SupplierIdPattern = Pattern.compile("^(S0)[0-9]{2,6}$");
        Pattern HospitalNamePattern = Pattern.compile("^[A-z ]{4,20}$");
        Pattern MedicineNamePattern = Pattern.compile("^[A-z ]{4,15}$");
        Pattern QTYPattern = Pattern.compile("[0-9]{1,15}");
        Pattern OrderdDatePattern = Pattern.compile("[0-9]{3,15}");


        if (!OrderIdPattern.matcher(txtOrderId.getText()).matches()) {
            addError(txtOrderId);
            btnAddOrderId.setDisable(false);
            return txtOrderId;
        } else {
            removeError(txtOrderId);

            if (!SupplierIdPattern.matcher(txtSupplierId.getText()).matches()) {
                addError(txtSupplierId);
                return txtSupplierId;
            } else {
                removeError(txtSupplierId);

                if (!HospitalNamePattern.matcher(txtHospitalName.getText()).matches()) {
                    addError(txtHospitalName);
                    return txtHospitalName;
                } else {
                    removeError(txtHospitalName);

                    if (!MedicineNamePattern.matcher(txtMedicineName.getText()).matches()) {
                        addError(txtMedicineName);
                        return txtMedicineName;
                    } else {
                        removeError(txtMedicineName);

                        if (!QTYPattern.matcher(txtQty.getText()).matches()) {
                            addError(txtQty);
                            return txtQty;
                        } else {

                            removeError(txtQty);

                            if (!OrderdDatePattern.matcher(txtDate.getText()).matches()) {
                                addError(txtDate);
                                return txtDate;
                            } else {
                                removeError(txtDate);
                            }
                        }
                    }
                }

            }
        }


        return true;

    }

    public void removeError(JFXTextField textField) {
        textField.setStyle("-fx-text-fill: green; -fx-control-inner-background:  #fff7d2  ; -fx-background-radius:20");
        textField.getParent().setStyle("-fx-border-color: green");
        btnAddOrderId.setDisable(false);
    }

    private void addError(JFXTextField textField) {

        textField.setStyle("-fx-text-fill: red ; -fx-control-inner-background:  #fff7d2 ; -fx-background-radius:20");
        textField.getParent().setStyle("-fx-border-color: red");


    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        clearAllTextFields();

    }


    public void clearAllTextFields() {
        txtOrderId.clear();
        txtSupplierId.clear();
        txtMedicineName.clear();
        txtDate.clear();
        txtHospitalName.clear();
        txtQty.clear();
        lblSearchOrder.clear();
    }


}