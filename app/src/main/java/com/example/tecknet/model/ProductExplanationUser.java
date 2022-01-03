package com.example.tecknet.model;

public class ProductExplanationUser {

    private ProductDetailsInt prod;
    private String malfunctionExplanation;
    private UserInt user;
    private String malId;

    /**
     * default constructor
     */
    public ProductExplanationUser() {
    }

    /**
     * parameter constructor
     *
     * @param prod
     * @param malfunctionExplanation
     * @param user
     * @param malId
     */
    public ProductExplanationUser(ProductDetailsInt prod, String malfunctionExplanation, UserInt user, String malId) {
        this.prod = prod;
        this.malfunctionExplanation = malfunctionExplanation;
        this.user = user;
        this.malId = malId;
    }

    public ProductDetailsInt getProd() {
        return prod;
    }

    public void setProd(ProductDetailsInt prod) {
        this.prod = prod;
    }

    public String getMalfunctionExplanation() {
        return malfunctionExplanation;
    }

    public void setMalfunctionExplanation(String malfunctionExplanation) {
        this.malfunctionExplanation = malfunctionExplanation;
    }

    public UserInt getUser() {
        return user;
    }

    public void setUser(UserInt user) {
        this.user = user;
    }

    public String getMalId() {
        return malId;
    }

    public void setMalId(String malId) {
        this.malId = malId;
    }
}
