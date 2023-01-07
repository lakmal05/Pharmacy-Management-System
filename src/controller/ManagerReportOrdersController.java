package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Order;
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.CrudUtill;

import java.sql.*;

public class ManagerReportOrdersController {
    public AnchorPane ReportAndOrderContext;
    public TextField txtOrderId;
    public TextField txtHospitalName;
    public TextField txtMedicineName;
    public TextField txtQty;
    public TextField txtSupplierId;
    public TextField txtDate;

    public void btnOkOnAction(ActionEvent actionEvent) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/unionchemistspharmacy", "root", "1234");
            String sql = "SELECT * FROM unionchemistspharmacy.supplierimportorderitemdetails    WHERE  supplierId='" + txtSupplierId.getText() + "'";

            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            if (resultSet.next()) {
                txtHospitalName.setText(resultSet.getString(3));
                txtMedicineName.setText(resultSet.getString(4));
                txtQty.setText(resultSet.getString(5));
                //   txtDate.setText(resultSet.getString(6));

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
    }

    public void btnSubmitOnAction(ActionEvent actionEvent) throws SQLException {

        Order o = new Order(
                txtOrderId.getText(), txtSupplierId.getText(),txtHospitalName.getText() ,txtMedicineName.getText() , txtQty.getText(), txtDate.getText()
        );


        try {
            if (CrudUtill.execute("INSERT INTO  unionchemistspharmacy.supplierimportorderitemdetails VALUES (?,?,?,?,?,?)", o.getOrderId(), o.getSupplierId(), o.getHospitalName(), o.getMedicineName(), o.getQty(), o.getDate())) {

                Image image = new Image("saved.png");


                Notifications notBuilder = Notifications.create()
                        .title("Sucsess").title("Order Has been Completed !").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
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
                        .title("Error").title("Something is Wrong Try Again !").graphic(new ImageView(image)).hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("Clicked Oon Notification");
                            }
                        });
                notBuilder.darkStyle();

                notBuilder.show();


            }


        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
        }


    }


}
