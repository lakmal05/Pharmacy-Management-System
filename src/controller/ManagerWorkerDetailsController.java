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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Medicine;
import model.Worker;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManagerWorkerDetailsController implements Initializable {
    public AnchorPane ManagerWorkerDetailscontext;
    public TableView tblWorker;
    public TableColumn colCashierId;
    public TableColumn colCashierName;
    public TableColumn colAge;
    public TableColumn colGender;
    public TableColumn colAdress;
    public TableColumn colSalary;
    public JFXTextField txtCashierId;
    public JFXTextField txtSalary;
    public JFXTextField txtAdress;
    public JFXTextField txtGender;
    public JFXTextField txtCashierName;
    public JFXTextField txtAge;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnaddID;
    public javafx.scene.control.TextField lblSearchLabel;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        TimeNow();


        colCashierId.setCellValueFactory(new PropertyValueFactory<>("CashierId"));
        colCashierName.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));


        try {
            loadAllWorker();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


  /*  public void initialize() {
        TimeNow();


        colCashierId.setCellValueFactory(new PropertyValueFactory<>("CashierId"));
        colCashierName.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("Age"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));


        try {
            loadAllWorker();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }*/


    public void textFields_Key_Released(javafx.scene.input.KeyEvent keyEvent) {


        Object validate = validate();
        //boolean validation ok
        //txtFeiled false


        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validate();

            if (response instanceof TextField) {//me welawe thiyen instense ntext feald ekkda
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }

        }


    }


    public Object validate() {
        String typedCashierId = txtCashierId.getText();
        String typedCashierName = txtCashierId.getText();
        String typedage = txtAge.getText();
        String typedgender = txtGender.getText();
        String typedaddress = txtAdress.getText();
        String typedSalary = txtSalary.getText();


        btnaddID.setDisable(true);


        Pattern idPattern = Pattern.compile("^(C0)[0-9]{2,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{4,15}$");
        Pattern agePattern = Pattern.compile("^(0?[1-9]|[1-9][0-9]|[1][1-9][1-9]|200)$");
        Pattern genderPattern = Pattern.compile("^[A-z ]{3,10}$");
        Pattern addressPattern = Pattern.compile("^[A-z ]{4,20}$");
        Pattern salaryPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

       /* boolean matches=idPattern.matcher(typedCashierId).matches();
        boolean matches1=namePattern.matcher(typedCashierName).matches();
        boolean matches2=agePattern.matcher(typedage).matches();
        boolean matches3=addressPattern.matcher(typedaddress).matches();
        boolean matches4=genderPattern.matcher(typedgender).matches();
        boolean matches5=salaryPattern.matcher(typedSalary).matches();*/

        if (!idPattern.matcher(txtCashierId.getText()).matches()) {
            //if input is not matching
            addError(txtCashierId);
            btnaddID.setDisable(false);
            return txtCashierId;
        } else {
            //if input is mathcong
            removeError(txtCashierId);
            //    btnaddID.setDisable(true);
            if (!namePattern.matcher(txtCashierName.getText()).matches()) {
                addError(txtCashierName);
                return txtCashierName;
            } else {
                removeError(txtCashierName);
                if (!agePattern.matcher(txtAge.getText()).matches()) {

                    addError(txtAge);
                    return txtAge;
                } else {
                    removeError(txtAge);

                    if (!genderPattern.matcher(txtGender.getText()).matches()) {
                        addError(txtGender);
                        return txtGender;
                    } else {
                        removeError(txtGender);
                        if (!addressPattern.matcher(txtAdress.getText()).matches()) {
                            addError(txtAdress);
                            return txtAdress;
                        } else {
                            removeError(txtAdress);
                            if (!salaryPattern.matcher(txtSalary.getText()).matches()) {
                                addError(txtSalary);
                                return txtSalary;
                            } else {
                                removeError(txtSalary);
                            }
                        }
                    }

                }


            }
        }
        return true;
    }


    private void removeError(JFXTextField textField) {

        textField.setStyle("-fx-text-fill: green; -fx-control-inner-background:  #fff7d2  ; -fx-background-radius:20");

        btnaddID.setDisable(false);

    }

    private void addError(JFXTextField textField) {

        textField.setStyle("-fx-text-fill: red ; -fx-control-inner-background:  #fff7d2 ; -fx-background-radius:20");
    }


    private void loadAllWorker() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
        String sql = "SELECT * FROM cashierdetails";
        Statement statement = con.createStatement();
        statement.executeQuery(sql);
        ResultSet result = statement.executeQuery(sql);

        ObservableList<Worker> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(
                    new Worker(
                            result.getString("cashierId"),
                            result.getString("cashierName"),
                            result.getString("age"),
                            result.getString("gender"),
                            result.getString("address"),
                            result.getDouble("salary")


                    )


            );
        }
        tblWorker.setItems(obList);



     FilteredList<Worker> filteredData = new FilteredList<>(obList, b -> true);

        lblSearchLabel.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(Worker -> {


                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKayword = newValue.toLowerCase();
                if (Worker.getCashierId().toLowerCase().indexOf(searchKayword) > -1) {
                    return true;

                }else if(Worker.getAddress().toLowerCase().indexOf(searchKayword)>-1){
                    return true;
                }
                else return Worker.getCashierName().toLowerCase().indexOf(searchKayword) > -1;

            });
        });
        SortedList<Worker> shortedData = new SortedList<>(filteredData);
        shortedData.comparatorProperty().bind(tblWorker.comparatorProperty());
       tblWorker.setItems(shortedData);











    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) ManagerWorkerDetailscontext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));

    }


    public void btnbackOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerMainForm");
    }

    public void btnAddWorkerDetailsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Worker w = new Worker(
                txtCashierId.getText(), txtCashierName.getText(), txtAge.getText(), txtGender.getText(), txtAdress.getText(), Double.parseDouble(txtSalary.getText())
        );


        try {
            if (CrudUtill.execute("INSERT INTO  unionchemistspharmacy.cashierdetails   VALUES(?,?,?,?,?,?)", w.getCashierId(), w.getCashierName(), w.getAge(), w.getGender(), w.getAddress(), w.getSalary())) {
                //NOTIFACTIN

            } else {

                //not
            }


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }

         loadAllWorker();
        clearAllTextFields();
        clearSearchTextField();

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


    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        Image image = new Image("Delete.png");


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
            String sql = "DELETE FROM   unionchemistspharmacy.cashierdetails  WHERE cashierId ='" + txtCashierId.getText() + "'";

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
        loadAllWorker();
  clearAllTextFields();
    }


    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        ResultSet resultSet = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.cashierdetails WHERE  cashierId=?", lblSearchLabel.getText());
        ResultSet resultSet1 = CrudUtill.execute("SELECT * FROM unionchemistspharmacy.cashierdetails WHERE  cashierName=?", lblSearchLabel.getText());


        if (resultSet.next()) {
            txtCashierId.setText(resultSet.getString(1));
            txtCashierName.setText(resultSet.getString(2));
            txtAge.setText(resultSet.getString(3));
            txtGender.setText(resultSet.getString(4));
            txtAdress.setText(resultSet.getString(5));
            txtSalary.setText(resultSet.getString(6));
        } else {
            //Conformation
            Image image = new Image("saved.png");


            Notifications notBuilder = Notifications.create()
                    .title("Success").title(" Reqest is complate !!").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Clicked Oon Notification");
                        }
                    });
            notBuilder.darkStyle();
            notBuilder.show();


        }

        if (resultSet1.next()) {
            txtCashierId.setText(resultSet1.getString(1));
            txtCashierName.setText(resultSet1.getString(2));
            txtAge.setText(resultSet1.getString(3));
            txtGender.setText(resultSet1.getString(4));
            txtAdress.setText(resultSet1.getString(5));
            txtSalary.setText(resultSet1.getString(6));
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


        clearSearchTextField();
    }


    public void clearSearchTextField() {
        lblSearchLabel.clear();
    }

    public void clearAllTextFields() {
        txtCashierId.clear();
        txtCashierName.clear();
        txtAge.clear();
        txtGender.clear();
        txtAdress.clear();
        txtSalary.clear();
        txtCashierId.requestFocus();
    }

}

