package model;

public class Supplier {

    String SupplierId;
    String SupplierName;
    String Address;
    String Contact;
    String HospitalName;






    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String address, String contact, String hospitalName) {
        SupplierId = supplierId;
        SupplierName = supplierName;
        Address = address;
        Contact = contact;
        HospitalName = hospitalName;
    }










    @Override
    public String toString() {
        return "Supplier{" +
                "SupplierId='" + SupplierId + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", Address='" + Address + '\'' +
                ", Contact='" + Contact + '\'' +
                ", HospitalName='" + HospitalName + '\'' +
                '}';
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }


}
