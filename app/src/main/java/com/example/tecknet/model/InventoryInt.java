package com.example.tecknet.model;

import com.example.tecknet.model.ProductDetailsInt;

/**
 * Interface of inventory management
 */
public interface InventoryInt {

    /**
     * The method remove the given product from the database.
     * If it succeed the method return the id of the product. if it didn't succeed the method return -1.
     * @param id_product - the id of the product to remove.
     * @return the id of the product and -1 if it didn't removed
     */
    public int remove_product(int id_product);

    /**
     * The method add the given product to the database.
     * If it succeed the method return the id of the product. if it didn't succeed the method return -1.
     * @param id_product - the id of the product to add.
     * @return the id of the product and -1 if it didn't added
     */
    public int add_product(int id_product);

    /**
     * The method update the given product in the database.
     * If it succeed the method return the id of the product. if it didn't succeed the method return -1.
     * @param id_product - the id of the product to update.
     * @return the id of the product and -1 if it didn't update
     */
    public int update_product(int id_product);

    /**
     * @param id_product - the product to get
     * @return the product details
     */
    public ProductDetailsInt get_product(int id_product);
}
