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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManagerSupplierUpdationController implements Initializable {
    public AnchorPane ManagerSupplierUpdationcontext;
    public TableView tblSupplierUpdation;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAdress;
    public TableColumn colContact;
    public TableColumn colSupplyHospitalName;
    public JFXTextField txtSupplierId;

    public JFXTextField txtSupplierName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXTextField txtSupplyHospitalName;
    public Label lblTime;
    public Label lblDate;
    public TextField lblSearchLabel;
    public JFXButton btnAddId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TimeNow();

        colId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colSupplyHospitalName.setCellValueFactory(new PropertyValueFactory<>("HospitalName"));


        try {
            loadAllSupplier();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }


    public void textFields_Key_Released(javafx.scene.input.KeyEvent keyEvent) {
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


    public Object validate() {

        String typeSupplierId = txtSupplierId.getText();
        String typeSupplierName = txtSupplierName.getText();
        String typeaddress = txtAddress.getText();
        String typecontact = txtContact.getText();
        String typeSupplyHospitalName = txtSupplyHospitalName.getText();


        btnAddId.setDisable(true);

        Pattern idPattern = Pattern.compile("^(S0)[0-9]{2,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{4,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z ]{4,20}$");
        Pattern contactPattern = Pattern.compile("[0-9]{3,15}");
        Pattern supplyHospitalNamerPattern = Pattern.compile("^[A-z ]{4,15}$");


        if (!idPattern.matcher(txtSupplierId.getText()).matches()) {
            addError(txtSupplierId);
            btnAddId.setDisable(false);
            return txtSupplierId;
        } else {

            removeError(txtSupplierId);

            if (!namePattern.matcher(txtSupplierName.getText()).matches()) {
                addError(txtSupplierName);
                return txtSupplierName;
            } else {
                removeError(txtSupplierName);

                if (!addressPattern.matcher(txtAddress.getText()).matches()) {
                    addError(txtAddress);
                    return txtAddress;

                } else {
                    removeError(txtAddress);

                    if (!contactPattern.matcher(txtContact.getText()).matches()) {
                        addError(txtContact);
                        return txtContact;

                    } else {
                        removeError(txtContact);

                        if (!supplyHospitalNamerPattern.matcher(txtSupplyHospitalName.getText()).matches()) {
                            addError(txtSupplyHospitalName);
                            return txtSupplyHospitalName;
                        } else {
                            removeError(txtSupplyHospitalName);
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
        btnAddId.setDisable(false);
    }

    private void addError(JFXTextField textField) {

        textField.setStyle("-fx-text-fill: red ; -fx-control-inner-background:  #fff7d2 ; -fx-background-radius:20");
        textField.getParent().setStyle("-fx-border-color: red");
    }


    private void loadAllSupplier() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
        String sql = "SELECT * FROM SupplierData";
        Statement statement = con.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);

        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(
                    new Supplier(
                            result.getString("supplierId"),
                            result.getString("supplierName"),
                            result.getString("address"),
                            result.getString("contact"),
                            result.getString("HospitalName")

                    )


            );
        }
        tblSupplierUpdation.setItems(obList);


        FilteredList<Supplier> filteredData1 = new FilteredList<>(obList, b -> true);
        lblSearchLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData1.setPredicate(Medicine -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                ///Search uong ID
                if (Medicine.getSupplierId().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (Medicine.getSupplierName().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else return Medicine.getHospitalName().toLowerCase().indexOf(searchKeyword) > -1;


            });


        });
        //Filters data insert sorted list
        SortedList<Supplier> sortedData = new SortedList<>(filteredData1);
        sortedData.comparatorProperty().bind(tblSupplierUpdation.comparatorProperty());
        tblSupplierUpdation.setItems(sortedData);


    }


    public void btnBacktoCashierMainFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierMainForm");

    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ManagerSupplierUpdationcontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));

    }


    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerMainForm");
    }

    public void btnAddtoTableOnAction(ActionEvent actionEvent) {

        Supplier s = new Supplier(
                txtSupplierId.getText(), txtSupplierName.getText(), txtAddress.getText(), txtContact.getText(), txtSupplyHospitalName.getText()
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");

            String sql = "INSERT INTO  supplierdata   VALUES('" + s.getSupplierId() + "','" + s.getSupplierName() + "','" + s.getAddress() + "','" + s.getContact() + "','" + s.getHospitalName() + "')";

            Statement stm = con.createStatement();
            int effectedRowCount = stm.executeUpdate(sql);
            System.out.println(effectedRowCount);


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }
        try {
            loadAllSupplier();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        clearTextFields();

    }


    public void btnSupplierdeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Image image = new Image("saved.png");


        Notifications notBuilder = Notifications.create()
                .title("Warning").title("Supplier Has been Deleted !!!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Clicked Oon Notification");
                    }
                });
        notBuilder.darkStyle();

        notBuilder.show();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
            String sql = "DELETE FROM unionchemistspharmacy.supplierdata WHERE supplierId ='" + txtSupplierId.getText() + "'";

            Statement statement = con.createStatement();
            boolean isDeleted = statement.executeUpdate(sql) > 0;


            if (isDeleted) {
                //Conformation
            } else {
                //Conformation
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        clearTextFields();
        loadAllSupplier();


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

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
            String sql = "SELECT * FROM unionchemistspharmacy.supplierdata     WHERE supplierId ='" + lblSearchLabel.getText() + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            if (resultSet.next()) {
                txtSupplierId.setText(resultSet.getString(1));
                txtSupplierName.setText(resultSet.getString(2));
                txtAddress.setText(resultSet.getString(3));
                txtContact.setText(resultSet.getString(4));
                txtSupplyHospitalName.setText(resultSet.getString(5));
            } else {
                //Conformation
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
            e.printStackTrace();

        }
        loadAllSupplier();
        clearSearchTextField();
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Supplier s = new Supplier(
                txtSupplierId.getText(), txtSupplierName.getText(), txtAddress.getText(), txtContact.getText(), txtSupplyHospitalName.getText()
        );

        try {
            boolean isUpdated = CrudUtill.execute("UPDATE unionchemistspharmacy.supplierdata SET HospitalName=? , contact=? , address=? , supplierName=? WHERE supplierId=?", s.getHospitalName(), s.getContact(), s.getAddress(), s.getSupplierName(), s.getSupplierId());
            if (isUpdated) {
                Image image = new Image("saved.png");


                Notifications notBuilder = Notifications.create()
                        .title("Success").title(" Supplier Updating Is Successful !!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
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
        loadAllSupplier();

    }


    public void clearSearchTextField() {

        lblSearchLabel.clear();
    }


    public void clearTextFields() {
        txtSupplierId.clear();
        txtSupplierName.clear();
        txtAddress.clear();
        txtSupplyHospitalName.clear();
        txtContact.clear();

    }

}