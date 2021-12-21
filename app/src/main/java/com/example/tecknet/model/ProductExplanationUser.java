package com.example.tecknet.model;

public class ProductExplanationUser{
    ProductDetailsInt prod;
    String malfunctionExplanation;
    UserInt user;
    public ProductExplanationUser()
    {}

    public ProductExplanationUser(ProductDetailsInt prod, String malfunctionExplanation, UserInt user) {
        this.prod = prod;
        this.malfunctionExplanation = malfunctionExplanation;
        this.user = user;
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
}
