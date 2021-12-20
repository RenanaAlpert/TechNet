package com.example.tecknet.model;

public class malfunctionView {

    private MalfunctionDetailsInt mal;
    InstitutionDetailsInt ins;
    private ProductDetailsInt product;

    public malfunctionView(MalfunctionDetailsInt mal, ProductDetailsInt product, InstitutionDetailsInt ins) {
        this.mal = mal;
        this.product = product;
        this.ins = ins;
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

}
