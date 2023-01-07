package model;

import com.sun.xml.internal.fastinfoset.util.StringArray;

public class Worker {

    String CashierId;
    String CashierName;
    String Age;
    String Gender;
    String Address;
    Double Salary;

    public Worker() {
    }

    public Worker(String cashierId, String cashierName, String age, String gender, String address, Double salary) {
        CashierId = cashierId;
        CashierName = cashierName;
        Age = age;
        Gender = gender;
        Address = address;
        Salary = salary;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "CashierId='" + CashierId + '\'' +
                ", CashierName='" + CashierName + '\'' +
                ", Age='" + Age + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Address='" + Address + '\'' +
                ", Salary=" + Salary +
                '}';
    }

    public String getCashierId() {
        return CashierId;
    }

    public void setCashierId(String cashierId) {
        CashierId = cashierId;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String cashierName) {
        CashierName = cashierName;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }
}
