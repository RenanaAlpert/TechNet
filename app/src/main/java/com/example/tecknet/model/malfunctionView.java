package com.example.tecknet.model;

public class malfunctionView {

    private MalfunctionDetailsInt mal;
    private InstitutionDetailsInt ins;
    private ProductDetailsInt product;
    private UserInt maintenance;

    public malfunctionView(MalfunctionDetailsInt mal, ProductDetailsInt product, InstitutionDetailsInt ins, UserInt user) {
        this.mal = mal;
        this.product = product;
        this.ins = ins;
        this.maintenance = user;
    }

    public MalfunctionDetailsInt getMal() {
        return mal;
    }

    public void setMal(MalfunctionDetailsInt mal) {
        this.mal = mal;
    }

    public ProductDetailsInt getProduct() {
        return product;
    }

    public void setProduct(ProductDetailsInt product) {
        this.product = product;
    }

    public InstitutionDetailsInt getIns() {
        return ins;
    }

    public void setIns(InstitutionDetailsInt ins) {
        this.ins = ins;
    }

    public UserInt getUser() {
        return maintenance;
    }

    public void setUser(UserInt user) {
        this.maintenance = user;
    }
}
