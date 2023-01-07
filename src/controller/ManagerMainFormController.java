package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerMainFormController {
    public JFXButton backtoLoginPageContext;
    public AnchorPane ManagerDashboardcontext;
    public Label lblTime;
    public Label lblDate;


    public void initialize(){
        TimeNow();
    }



    public void setUi(String location) throws IOException {
       Stage stage =(Stage)ManagerDashboardcontext.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/"+location+".fxml"))));
       stage.centerOnScreen();
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



        public void backOnAction(ActionEvent actionEvent) throws IOException {
    setUi("LoginPage");
    }


    public void btnMedicineUpdationOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerMedicineUpdatePage");
    }

    public void btnSupplaerUpdationOnAction(ActionEvent actionEvent) throws IOException {
     setUi("ManagerSupplierUpdation");

    }

    public void btnOrderingItemReportOnAction(ActionEvent actionEvent) throws IOException {
    setUi("OrderingItemReport");
    }

    public void btnEXPItemOnAction(ActionEvent actionEvent) throws IOException {
    setUi("ManagerEXPitemReport");
    }

    public void btnTransactionReportOnAction(ActionEvent actionEvent) throws IOException {
   setUi("ManagerTransactionDetails");
    }

    public void btnWorkerDetailsOnAction(ActionEvent actionEvent) throws IOException {
    setUi("ManagerWorkerDetails");
    }


    public void btnImportOrdersOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ManagerImportOrders");
    }
}
