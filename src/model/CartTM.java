package model;

public class CartTM {

private int Qty;
private Double Price;
private Double TotalAmount;

    @Override
    public String toString() {
        return "CartTM{" +
                "Qty=" + Qty +
                ", Price=" + Price +
                ", TotalAmount=" + TotalAmount +
                '}';
    }

    public CartTM() {
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }

    public CartTM(int qty, Double price, Double totalAmount) {
        Qty = qty;
        Price = price;
        TotalAmount = totalAmount;
    }
}
