package model;

public class Payment {

    String billId;
    String  cashierId;
    String medicineName;
    String Qty;
    Double price;
     Double totalAmount;
    String date;

    public Payment() {
    }

    public Payment(String billId, String cashierId, String medicineName, String qty, Double price, Double totalAmount, String date) {
        this.billId = billId;
        this.cashierId = cashierId;
        this.medicineName = medicineName;
         this. Qty = qty;
        this.price = price;
        this.totalAmount = totalAmount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "billId='" + billId + '\'' +
                ", cashierId='" + cashierId + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", Qty='" + Qty + '\'' +
                ", price='" + price + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
