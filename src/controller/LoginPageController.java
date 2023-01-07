package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginPageController implements Initializable {
    public JFXButton loginBtncontext;
    public JFXPasswordField pwdPassword;
    public JFXTextField txtUserName;
    public AnchorPane LoginPageContext;
    public Label lblTime;
    public Label lblDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TimeNow();

    }

    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        String tempUserName = txtUserName.getText();
        String tempPassword = pwdPassword.getText();

        if (tempUserName.equals("Admin") && tempPassword.equals("1234")) {

          /*  //    LoginPageContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../fxmlFiles/ManagerProgressPage.fxml"));
            LoginPageContext.getChildren().add(parent);*/
            setUi("ManagerProgressPage");

         /*   URL resource = getClass().getResource("../fxmlFiles/ManagerProgressPage.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Loading Manager Page");
            stage.show();*/


            //---------------------The Push Notification-----------///////


            Image image = new Image("saved.png");
            Notifications notBuilder = Notifications.create()
                    .title("Success")
                    .title("Login Success !!!.Admin Welcome ")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Clicked Oon Notification");
                        }
                    });
            notBuilder.darkStyle();
            notBuilder.show();
            //-----------------------------------Over--------------------------------------//


        }/* else {
            Image image = new Image("Delete.png");
            Notifications notBuilder = Notifications.create()
                    .title("Error1")
                    .title("Something Went Wrong..Try Again !! ").graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Clicked On Notification");
                        }
                    });
            notBuilder.darkStyle();
            notBuilder.show();


        }*/ else if (tempUserName.equals("Cashier") && tempPassword.equals("1234")) {
       /*    LoginPageContext.getChildren().clear();
          Parent parent1 = FXMLLoader.load(getClass().getResource("../fxmlFiles/CashierProgressPage.fxml"));
            LoginPageContext.getChildren().add(parent1);*/

            setUi("CashierProgressPage");
     /*       URL resource = getClass().getResource("../fxmlFiles/CashierProgressPage.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Loading Cashier Page");
            stage.show();*/



            /*------------------------------------------------*////////
            Image image = new Image("saved.png");
            Notifications notBuilder = Notifications.create()
                    .title("Success")
                    .title("Login Success !!!.Cashier Welcome ").graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Clicked On Notification");
                        }
                    });
            notBuilder.darkStyle();
            notBuilder.show();
        } else {

            Image image = new Image("Delete.png");
            Notifications notBuilder = Notifications.create()
                    .title("Error")
                    .title("Something Went Wrong..Try Again !! ").graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Clicked On Notification");
                        }
                    });
            notBuilder.darkStyle();
            notBuilder.show();


        }


    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) LoginPageContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/" + location + ".fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Loading");
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


    public void textFields_Key_Released(KeyEvent keyEvent) {



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

    private Object validate() {
        String typeUserName = txtUserName.getText();
        String typePassword = pwdPassword.getText();


        Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
        Pattern passwordPattern = Pattern.compile("[0-9]{3,15}");


        if (!namePattern.matcher(txtUserName.getText()).matches()) {
            addError(txtUserName);
            return txtUserName;
        } else {
            removeError(txtUserName);
            if (!passwordPattern.matcher(pwdPassword.getText()).matches()) {
                addError1(pwdPassword);
                return pwdPassword;
            } else {
                removeError1(pwdPassword);
            }
        }

        return true;

    }

    private void removeError(JFXTextField textField) {
        textField.getParent().setStyle("-fx-border-color: green");
    }

    private void removeError1(JFXPasswordField passwordField) {
        passwordField.getParent().setStyle("-fx-border-color: green");
    }


    private void addError(JFXTextField textField) {

        textField.getParent().setStyle("-fx-border-color: red");

    }

    private void addError1(JFXPasswordField passwordField) {

        passwordField.getParent().setStyle("-fx-border-color: red");

    }


}
