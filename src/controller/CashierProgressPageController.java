package controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class CashierProgressPageController implements Initializable {

      @FXML
    public ProgressBar CashierProgressbar;
    @FXML
    public JFXButton ProgressBtnContext;
    public Label MyLable;
    public AnchorPane ProgressContext;
    BigDecimal progress=new BigDecimal(String.format("%.2f",0.0)) ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CashierProgressbar.setStyle("-fx-alignment:#a4b0be;");

    }

   private void setUi(String location) throws IOException {

        Stage stage =(Stage)ProgressContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/"+location+".fxml"))));
         stage.centerOnScreen();
         stage.setTitle("Cashier Main Form");



    }




    public void btnOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierMainForm");

       if (progress.doubleValue()<1) {

           progress = new BigDecimal(String.format("%.2f",progress.doubleValue()+0.5));
           System.out.println(progress.doubleValue());
           CashierProgressbar.setProgress(progress.doubleValue());
           MyLable.setText(Double.toString((int) Math.round(progress .doubleValue()* 100)) + "%  Completed");

       }





    }










}
