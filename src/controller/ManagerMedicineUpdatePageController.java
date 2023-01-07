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
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerMedicineUpdatePageController {
    public AnchorPane ManagerMedicineUpdatecontext;
    public TextField txtMedicineId;
    public TextField txtManufactureDate;
    public TextField txtQty;
    public TextField txtMedicineName;
    public TextField txtExpireDate;
    public TextField txtPrice;
    public TableColumn MedicineIDTableCoulmn;
    public TableColumn MedicineNameTableCoulmn;
    public TableColumn MedicineQtyTableCoulmn;
    public TableColumn ManufactureDateTableCoulmn;
    public TableColumn ExpireDateTableCoulmn;
    public TableColumn PriceTableCoulmn;

    public TextField searchMedicinetxtField;

    public TableView MedicineTableView;
    public Label lblTime;
    public Label lblDate;


    public void initialize() {
        TimeNow();

        MedicineIDTableCoulmn.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        MedicineNameTableCoulmn.setCellValueFactory(new PropertyValueFactory<>("medicineName"));
        MedicineQtyTableCoulmn.setCellValueFactory(new PropertyValueFactory<>("medicineQty"));
        ManufactureDateTableCoulmn.setCellValueFactory(new PropertyValueFactory<>("manufactureDate"));
        ExpireDateTableCoulmn.setCellValueFactory(new PropertyValueFactory<>("ExpireDate"));
        PriceTableCoulmn.setCellValueFactory(new PropertyValueFactory<>("Price"));

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
        MedicineTableView.setItems(obList);

        FilteredList<Medicine> filteredData = new FilteredList<>(obList, b -> true);
        searchMedicinetxtField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Medicine -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                ///Search uong ID
                if (Medicine.getMedicineId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Medicine.getMedicineName().toLowerCase().indexOf(searchKeyword) > -1;

            });


        });
        //Filters data insert sorted list
        SortedList<Medicine> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(MedicineTableView.comparatorProperty());
        MedicineTableView.setItems(sortedData);


    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ManagerMedicineUpdatecontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));

    }


    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerMainForm");

    }


    public void btnAddToTableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        Image image = new Image("saved.png");


        Notifications notBuilder = Notifications.create()
                .title("Success").title(" Medicine Updating Successful !!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Clicked Oon Notification");
                    }
                });
        notBuilder.darkStyle();
        notBuilder.show();


        Medicine m = new Medicine(
                txtMedicineId.getText(), txtMedicineName.getText(), txtQty.getText(), txtManufactureDate.getText(), txtExpireDate.getText(), Double.parseDouble(txtPrice.getText())
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");

            String sql = "INSERT INTO medicine VALUES('" + m.getMedicineId() + "','" + m.getMedicineName() + "','" + m.getMedicineQty() + "','" + m.getManufactureDate() + "','" + m.getExpireDate() + "','" + m.getPrice() + "')";

            Statement stm = con.createStatement();
            int effectedRowCount = stm.executeUpdate(sql);
            System.out.println(effectedRowCount);


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

        loadAllMedicine();
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


    public void btnClearOnAction(ActionEvent actionEvent) {
        clearTextFields();
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Medicine m = new Medicine(
                txtMedicineId.getText(), txtMedicineName.getText(), txtQty.getText(), txtManufactureDate.getText(), txtExpireDate.getText(),Double.parseDouble(txtPrice.getText())
        );

        try {
            boolean isUpdated = CrudUtill.execute("UPDATE unionchemistspharmacy.medicine SET   medicineName=? , medicineQty=? , manufactureDate=? ,expDate=? WHERE medicineId=?", m.getMedicineName(), m.getMedicineQty(), m.getManufactureDate(), m.getExpireDate(), m.getPrice());
            if (isUpdated) {
                Image image = new Image("saved.png");


                Notifications notBuilder = Notifications.create()
                        .title("Success").title(" Medicine Updating Is Successful !!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked Oon Notification");
                            }
                        });
                notBuilder.darkStyle();
                notBuilder.show();


            } else {

                Image image = new Image("Delete.png");


                Notifications notBuilder = Notifications.create()
                        .title("Success").title(" Something Went Wrong! Try Again !!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked Oon Notification");
                            }
                        });
                notBuilder.darkStyle();
                notBuilder.show();


            }


        } catch (SQLException | ClassNotFoundException e) {

        }
        loadAllMedicine();




    }


    public void clearTextFields() {
        txtMedicineId.clear();
        txtMedicineName.clear();
        txtQty.clear();
        txtManufactureDate.clear();
        txtExpireDate.clear();
        txtPrice.clear();
        searchMedicinetxtField.clear();

    }


    public void btnSearchOnActon(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.medicine WHERE  medicineId=?", searchMedicinetxtField.getText());
        if (result.next()) {
            txtMedicineId.setText(result.getString(1));
            txtMedicineName.setText(result.getString(2));
            txtQty.setText(result.getString(3));
            txtManufactureDate.setText(result.getString(4));
            txtExpireDate.setText(result.getString(5));
            txtPrice.setText(String.valueOf(result.getDouble(6)));

        }
        ResultSet resultSet = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.medicine WHERE  medicineName=?", searchMedicinetxtField.getText());
        if (resultSet.next()) {
            txtMedicineId.setText(resultSet.getString(1));
            txtMedicineName.setText(resultSet.getString(2));
            txtQty.setText(resultSet.getString(3));
            txtManufactureDate.setText(resultSet.getString(4));
            txtExpireDate.setText(resultSet.getString(5));
            txtPrice.setText(String.valueOf(resultSet.getDouble(6)));

        }




    }
}
