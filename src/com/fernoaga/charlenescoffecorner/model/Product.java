package com.fernoaga.charlenescoffecorner.model;

import java.util.List;
import java.util.Optional;

public class Product {

    private final String id;
    private final String name;
    private final double price;
    private final ProductType productType;
    private final Optional<List<Topping>> availableToppings;


    public Product(String id, String name, double price, ProductType productType, Optional<List<Topping>> availableToppings) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.availableToppings = availableToppings;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Optional<List<Topping>> getAvailableToppings() {
        return availableToppings;
    }
}
