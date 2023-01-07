package controller;

import TM.orderTM;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Order;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderingItemReportController  implements Initializable{
    public AnchorPane OrderingItemReportcontext;
    public TableView<Order> tblOrderItemReport;
    public TableColumn colOrderId;
    public TableColumn colSupplierId;
    public TableColumn colHospitalName;
    public TableColumn colMedicineName;
    public TableColumn colQty;
    public TableColumn colDate;
    public Label lblTime;
    public Label lblDate;
    public TableColumn colAction;
    public TextField txtSearchOrders;
    private Button btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TimeNow();

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colHospitalName.setCellValueFactory(new PropertyValueFactory<>("HospitalName"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("MedicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));



        try {
            loadAllOrder();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

    }



 /*   public void initialize(){
        TimeNow();


        colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colHospitalName.setCellValueFactory(new PropertyValueFactory<>("HospitalName"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("MedicineName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));



        try {
            loadAllOrder();
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }*/

    private void loadAllOrder() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.supplierimportorderitemdetails");


        ObservableList<Order> obList= FXCollections.observableArrayList();

        while (result.next()){

            Button btn =new Button("STOP ORDER");
            orderTM tm =new orderTM(
                    result.getString("orderId"),
                    result.getString("supplierId"),
                    result.getString("HospitalName"),
                    result.getString("MedicineName"),
                    result.getString("MedicineQty"),
                    result.getString("orderdDate"),
                    btn
            );

            btn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.get().equals(ButtonType.YES)) {


                      Order orderTM = tblOrderItemReport.getSelectionModel().getSelectedItem();


                    try {
                       if (CrudUtill.execute("DELETE FROM unionchemistspharmacy.supplierimportorderitemdetails WHERE orderId=?", orderTM.getOrderId())) {

                           Image image = new Image("Delete.png");


                           Notifications notBuilder = Notifications.create()
                                   .title("Success").title("Your Order Has been Stopped!!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                                       @Override
                                       public void handle(ActionEvent event) {
                                           System.out.println("Clicked Oon Notification");
                                       }
                                   });
                           notBuilder.darkStyle();

                           notBuilder.show();






                        } else {

                            Notifications notifications = Notifications.create().title("Delete Uncompleted !").text("Try Again").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                            notifications.darkStyle();
                            notifications.showError();

                        }

                   } catch (SQLException | ClassNotFoundException exception) {


                    }

                    obList.remove(orderTM);








                }
            });






            obList.add(tm);
            tblOrderItemReport.setItems(obList);

        }

        FilteredList<Order> filteredData = new FilteredList<>(obList, b -> true);
      txtSearchOrders.textProperty().addListener((observable, oldValue, newValue) -> {

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
        sortedData.comparatorProperty().bind(tblOrderItemReport.comparatorProperty());
        tblOrderItemReport.setItems(sortedData);

    }











    private void setUi(String location) throws IOException {
        Stage stage =(Stage)OrderingItemReportcontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/"+location+".fxml"))));

    }


    public void btnbackOnAvtion(ActionEvent actionEvent) throws IOException {
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
