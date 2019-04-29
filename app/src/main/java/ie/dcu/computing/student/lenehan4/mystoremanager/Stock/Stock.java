package ie.dcu.computing.student.lenehan4.mystoremanager.Stock;

import android.content.Intent;

public class Stock {
    private String UPC, Brand;
    private String Quantity;
    private String Price;
    private String company;


    public Stock() {
    }

    public Stock(String UPC, String brand, String quantity, String price,String company) {
        this.UPC = UPC;
        Brand = brand;
        Quantity = quantity;
        Price = price;
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }



}
