package model;

public class Medicine {

    String MedicineId;
    String MedicineName;
    String MedicineQty;
    String ManufactureDate;
    String ExpireDate;
    Double Price;


    public Medicine() {
    }

    public Medicine(String medicineId, String medicineName, String medicineQty, String manufactureDate, String expireDate, Double price) {
        MedicineId = medicineId;
        MedicineName = medicineName;
        MedicineQty = medicineQty;
        ManufactureDate = manufactureDate;
        ExpireDate = expireDate;
        Price = price;
    }


    public String getMedicineId() {
        return MedicineId;
    }

    public void setMedicineId(String medicineId) {
        MedicineId = medicineId;
    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        MedicineName = medicineName;
    }

    public String getMedicineQty() {
        return MedicineQty;
    }

    public void setMedicineQty(String medicineQty) {
        MedicineQty = medicineQty;
    }

    public String getManufactureDate() {
        return ManufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        ManufactureDate = manufactureDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }


    @Override
    public String toString() {
        return "Medicine{" +
                "MedicineId='" + MedicineId + '\'' +
                ", MedicineName='" + MedicineName + '\'' +
                ", MedicineQty='" + MedicineQty + '\'' +
                ", ManufactureDate='" + ManufactureDate + '\'' +
                ", ExpireDate='" + ExpireDate + '\'' +
                ", Price=" + Price +
                '}';
    }


}
