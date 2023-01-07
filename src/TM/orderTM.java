package TM;

import javafx.scene.control.Button;
import model.Order;

public class orderTM extends Order {
    private String OrderId;
    private String SupplierId;
    private String HospitalName;
    private String MedicineName;
    private String Qty;
    private String Date;
    private Button btn;

    public orderTM() {
    }

    public orderTM(String orderId, String supplierId, String hospitalName, String medicineName, String qty, String date, Button btn) {
        OrderId = orderId;
        SupplierId = supplierId;
        HospitalName = hospitalName;
        MedicineName = medicineName;
        Qty = qty;
        Date = date;
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "orderTM{" +
                "OrderId='" + OrderId + '\'' +
                ", SupplierId='" + SupplierId + '\'' +
                ", HospitalName='" + HospitalName + '\'' +
                ", MedicineName='" + MedicineName + '\'' +
                ", Qty='" + Qty + '\'' +
                ", Date='" + Date + '\'' +
                ", btn=" + btn +
                '}';
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
