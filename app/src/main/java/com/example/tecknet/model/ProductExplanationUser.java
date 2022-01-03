package com.example.tecknet.model;

public class ProductExplanationUser {

    private ProductDetailsInt prod;
    private UserInt user;
    private MalfunctionDetailsInt mal;



    /**
     * default constructor
     */
    public ProductExplanationUser() {
    }

    /**
     * parameter constructor
     *
     * @param prod
     * @param user
     * @param mal
     */
    public ProductExplanationUser(ProductDetailsInt prod, UserInt user, MalfunctionDetailsInt mal) {
        this.prod = prod;
        this.user = user;
        this.mal = mal;
    }

    public ProductDetailsInt getProd() {
        return prod;
    }

    public void setProd(ProductDetailsInt prod) {
        this.prod = prod;
    }

    public UserInt getUser() {
        return user;
    }

    public void setUser(UserInt user) {
        this.user = user;
    }

    public MalfunctionDetailsInt getMal() {
        return mal;
    }

    public void setMal(MalfunctionDetailsInt mal) {
        this.mal = mal;
    }
}
