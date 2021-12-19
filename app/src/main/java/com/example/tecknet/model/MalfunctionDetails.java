package com.example.tecknet.model;

public class MalfunctionDetails implements MalfunctionDetailsInt {
    //    static long malfunction_counter=0;
    String mal_id;
    /**
     * TODO: add product_id and connect between inventory to malfunctions
     * text_mal_details is only temporary
     */
    String explanation;
    String institution;
    boolean is_open;
    String product_id;
    long tech;
//    String mal_creator;

    public MalfunctionDetails() {
    }

    public MalfunctionDetails(String product, String institution, String explain/*, String mal_creator*/) {
//        this.mal_id = ++malfunction_counter;
        this.institution = institution;
        this.explanation = explain;
      //  this.mal_creator = mal_creator;
        this.is_open = true;
        this.product_id = product;
        this.tech = -1;
    }

    public String getMal_id() {
        return mal_id;
    }

    public void setMal_id(String mal_id) {
        this.mal_id = mal_id;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public boolean isIs_open() {
        return is_open;
    }

    public void setIs_open(boolean is_open) {
        this.is_open = is_open;
    }

    public String getProduct_id() {
        return product_id;
    }
//
//    public String getMal_creator() {
//        return mal_creator;
//    }
//
//    public void setMal_creator(String mal_creator) {
//        this.mal_creator = mal_creator;
//    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public long getTech() {
        return tech;
    }

    public void setTech(long tech) {
        this.tech = tech;
    }
}
