package model;

public class Order {

    String OrderId;
    String SupplierId;
    String HospitalName;
    String MedicineName;
    String Qty;
    String Date;

    public Order(String text, String text1, String text2, String text3, String text4) {
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderId='" + OrderId + '\'' +
                ", SupplierId='" + SupplierId + '\'' +
                ", HospitalName='" + HospitalName + '\'' +
                ", MedicineName='" + MedicineName + '\'' +
                ", Qty='" + Qty + '\'' +
                ", Date='" + Date + '\'' +
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

    public Order() {
    }

    public Order(String orderId, String supplierId, String hospitalName, String medicineName, String qty, String date) {
        OrderId = orderId;
        SupplierId = supplierId;
        HospitalName = hospitalName;
        MedicineName = medicineName;
        Qty = qty;
        Date = date;
    }
}
