package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import model.Payment;
import model.Supplier;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CashierTransactionDetailsController {
    public AnchorPane CashierTransactioncontext;
    public TableView tblCashierTransaction;
    public TableColumn colBillId;
    public TableColumn colMedicineName;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colTotalAmount;
    public TableColumn colDate;
    public Label lblTime;
    public Label lblDate;
    public TableColumn colCashierId;
    public JFXTextField lblSearch;


    public void initialize(){
        TimeNow();




        colBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
        colCashierId.setCellValueFactory(new PropertyValueFactory<>("CashierId"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
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
      tblCashierTransaction.setItems(obList);

        FilteredList<Payment> filteredData1 = new FilteredList<>(obList, b -> true);
       lblSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData1.setPredicate(Payment -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                ///Search uong ID
                if (Payment.getBillId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Payment.getDate().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Payment.getMedicineName().toLowerCase().indexOf(searchKeyword) > -1;


            });


        });
        //Filters data insert sorted list
        SortedList<Payment> sortedData = new SortedList<>(filteredData1);
        sortedData.comparatorProperty().bind(tblCashierTransaction.comparatorProperty());
        tblCashierTransaction.setItems(sortedData);













    }
















    private void setUi(String location) throws IOException {
        Stage stage =(Stage)CashierTransactioncontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/"+location+".fxml"))));

    }






    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierMainForm");
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
