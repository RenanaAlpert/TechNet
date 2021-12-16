package com.example.tecknet.model;

import java.util.GregorianCalendar;

/**
 * Interface of the product details
 */
public interface ProductDetailsInt {

    public String getProduct_id();

    public void setProduct_id(String product_id);

    public String getDevice();

    public void setDevice(String device);

    public String getCompany();

    public void setCompany(String company);

    public String getType();

    public void setType(String type);

    public String getYear_of_production();

    public void setYear_of_production(String year_of_production);

    public String getDate_of_responsibility();

    public void setDate_of_responsibility(String date_of_responsibility);
}
