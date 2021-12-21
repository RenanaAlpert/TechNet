package com.example.tecknet.model;

public class ProductDetails implements ProductDetailsInt ,Comparable {

    private String product_id ,device , company ,type ,year_of_production, date_of_responsibility;


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

        String productStr = "";
        if(type!=null && !type.isEmpty()){
            productStr = type +"\n";
        }
        if(device!=null && !device.isEmpty()){
            productStr += " דגם : "+device;
        }
        if (company!=null && !company.isEmpty()){
            productStr += ", חברה : "+ company;
        }
        if (year_of_production!=null && !year_of_production.isEmpty()){
            productStr += ", שנת יצור : " + year_of_production;
        }
        if (date_of_responsibility!=null && !date_of_responsibility.equals("DD/MM/YYYY") && !date_of_responsibility.equals("")){
            productStr += ", תאריך אחריות : "+date_of_responsibility;
        }
        return productStr;
    }


//Comparable
    @Override
    public int compareTo(Object other) {
        String thisProd = toString();
        String otherProd = ((ProductDetails) other).toString();
        return thisProd.compareTo(otherProd);
    }
}
