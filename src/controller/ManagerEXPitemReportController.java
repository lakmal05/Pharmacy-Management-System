package controller;

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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Medicine;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerEXPitemReportController {
    public AnchorPane ManagerEXPitemcontext;
    public TableView tblExpMedicine;
    public TableColumn colMedicineId;
    public TableColumn colMedicineName;
    public TableColumn colMedicineQty;
    public TableColumn colManufactureDate;
    public TableColumn colExpDate;
    public TableColumn colPrice;
    public Label lblTime;
    public Label lblDate;
    public TextField txtMedicineId;
    public TextField txtMedicineName;
    public TextField txtManufactureDate;
    public TextField txtPrice;
    public TextField txtQty;
    public TextField lblSearch;
    public TextField txtOrderId;


    public void initialize() {

        TimeNow();


        colMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        colMedicineQty.setCellValueFactory(new PropertyValueFactory<>("medicineQty"));
        colManufactureDate.setCellValueFactory(new PropertyValueFactory<>("manufactureDate"));
        colExpDate.setCellValueFactory(new PropertyValueFactory<>("ExpireDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        try {
            loadAllMedicine();
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
        tblExpMedicine.setItems(obList);
        /*-----------------------------------------------auto Search------------------------------------------*/
        FilteredList<Medicine> filteredData1 = new FilteredList<>(obList, b -> true);
        lblSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData1.setPredicate(Medicine -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                ///Search uong ID
                if (Medicine.getMedicineId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Medicine.getMedicineName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Medicine.getExpireDate().toLowerCase().indexOf(searchKeyword) > -1;


            });


        });
        //Filters data insert sorted list
        SortedList<Medicine> sortedData = new SortedList<>(filteredData1);
        sortedData.comparatorProperty().bind(tblExpMedicine.comparatorProperty());
        tblExpMedicine.setItems(sortedData);


    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ManagerEXPitemcontext.getScene().getWindow();
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


    void setPage(String location) throws IOException {
        URL resource = getClass().getResource("../fxmlFiles/" + location + ".fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(location);
        stage.show();
    }


    public void btnTakeArepotOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        //  setUi("ManagerReportOrdersController");


        setPage("ManagerReportOrders");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
            String sql = "DELETE FROM unionchemistspharmacy.medicine WHERE medicineId ='" + txtMedicineId.getText() + "'";

            Statement statement = con.createStatement();
            boolean isDeleted = statement.executeUpdate(sql) > 0;


            if (isDeleted) {


                Image image = new Image("Delete.png");


                Notifications notBuilder = Notifications.create()
                        .title("Warning").title(" Drug Has been Reported !!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked Oon Notification");
                            }
                        });
                notBuilder.darkStyle();

                notBuilder.show();


            } else {
                //Conformation
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        loadAllMedicine();

    }

    public void btnSearch(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {


        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
        String sql = "SELECT * FROM unionchemistspharmacy.medicine    WHERE medicineId ='" + lblSearch.getText() + "'";

        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (resultSet.next()) {
            txtMedicineId.setText(resultSet.getString(1));
            txtMedicineName.setText(resultSet.getString(2));
            txtQty.setText(resultSet.getString(3));
            txtManufactureDate.setText(resultSet.getString(4));
            txtPrice.setText(resultSet.getString(6));
        }


        ResultSet resultSet1 = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.medicine WHERE  expDate=?", lblSearch.getText());
        if (resultSet1.next()) {
            txtMedicineId.setText(resultSet1.getString(1));
            txtMedicineName.setText(resultSet1.getString(2));
            txtQty.setText(resultSet1.getString(3));
            txtManufactureDate.setText(resultSet1.getString(4));
            txtPrice.setText(String.valueOf(resultSet1.getDouble(6)));
        }


    }

    public void clearAllTextField() {
        txtMedicineId.clear();
        txtMedicineName.clear();
        txtQty.clear();
        txtManufactureDate.clear();
        txtPrice.clear();


    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearAllTextField();
    }
}
