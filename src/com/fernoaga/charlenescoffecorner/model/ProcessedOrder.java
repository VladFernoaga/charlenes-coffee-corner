package com.fernoaga.charlenescoffecorner.model;

import java.util.List;

public class ProcessedOrder {
    public final Product product;
    public final List<Topping> toppings;

    public ProcessedOrder(Product product, List<Topping> toppings) {
        this.product = product;
        this.toppings = toppings;
    }
}
