package com.example.tecknet.model;

import java.util.GregorianCalendar;

/**
 * Interface of the product details
 */
public interface ProductDetailsInt {

    /**
     * @return the id of the product
     */
    public int get_product_id();

    /**
     * @return the name of the product
     */
    public String get_product_name();

    /**
     * @return the model of the product
     */
    public String get_type();

    /**
     * @return the product's year of manufacture
     */
    public int get_year_of_product();

    /**
     * @return when bought the product
     */
    public GregorianCalendar get_date_of_purchase();

    /**
     * @return the Warranty Expiration Date
     */
    public GregorianCalendar get_date_of_responsibility();

    /**
     * @param date - the date of the responsibility
     */
    public void set_date_of_responsibility(GregorianCalendar date);

    /**
     * @return if the product work or not
     */
    public boolean is_functioning();

    /**
     * @param f - the functioning of the product
     */
    public void is_functioning(boolean f);
}
