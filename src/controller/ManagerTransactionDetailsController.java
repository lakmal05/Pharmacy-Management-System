package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Order;
import model.Payment;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerTransactionDetailsController {
    public AnchorPane ManagerTransactionDetailscontext;
    public Label lblTime;
    public Label lblDate;
    public TableColumn colBillId;
    public TableColumn colCashierId;
    public TableColumn colMedicineName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colAmount;
    public TableColumn colDate;
    public TableView tblPayment;


    public void initialize() {
        TimeNow();


        colBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colCashierId.setCellValueFactory(new PropertyValueFactory<>("CashierId"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));


        try {
            loadAllPayment();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }





    private void loadAllPayment() throws ClassNotFoundException, SQLException {
        Class .forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy","root","1234");
        String sql="SELECT * FROM payment";
        Statement statement=con.createStatement();
        statement.executeQuery(sql);
        ResultSet result=statement.executeQuery(sql);

        ObservableList<Payment> obList= FXCollections.observableArrayList();

        while (result.next()){

            obList.add(
                    new Payment(
                            result.getString("billId"),
                            result.getString("cashierId"),
                            result.getString("MedicineName"),
                            result.getString("medicineQty"),
                            result.getDouble("price"),
                            result.getDouble("totalAmount"),
                            result.getString("date")

                    )


            );
        }
        tblPayment.setItems(obList);

    }





    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ManagerTransactionDetailscontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));

    }

    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerMainForm");


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


}
