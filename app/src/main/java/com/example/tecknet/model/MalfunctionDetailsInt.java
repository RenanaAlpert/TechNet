package com.example.tecknet.model;

/**
 * Interface of the malfunction details
 */
public interface MalfunctionDetailsInt {

    public long getMal_id();

    public void setMal_id(long mal_id);

    public String getExplanation();

    public void setExplanation(String explanation);

    public String getInstitution();

    public void setInstitution(String institution);

    public boolean isIs_open();

    public void setIs_open(boolean is_open);

    public long getProduct_id();

    public void setProduct_id(long product_id);

    public long getTech();

    public void setTech(long tech);

    public String getMal_creator();

    public void setMal_creator(String mal_creator);
}
