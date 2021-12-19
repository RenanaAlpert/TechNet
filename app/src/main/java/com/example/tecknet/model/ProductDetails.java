package com.example.tecknet.model;

public class ProductDetails implements ProductDetailsInt{

    String product_id;
    String device;
    String company;
    String type;
    String year_of_production;
    String date_of_responsibility;


    public ProductDetails(){}

    public ProductDetails(String device, String company, String type, String year_of_production, String date_of_responsibility){
        this.product_id = "";
        this.device = device;
        this.company = company;
        this.type = type;
        this.year_of_production = year_of_production;
        this.date_of_responsibility = date_of_responsibility;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear_of_production() {
        return year_of_production;
    }

    public void setYear_of_production(String year_of_production) {
        this.year_of_production = year_of_production;
    }

    public String getDate_of_responsibility() {
        return date_of_responsibility;
    }

    public void setDate_of_responsibility(String date_of_responsibility) {
        this.date_of_responsibility = date_of_responsibility;
    }
    @Override
    public String toString(){

        return " סוג: " +type+", חברה : "+company+", דגם : "+ device+" . ";
    }
}
