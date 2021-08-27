package com.fernoaga.charlenescoffecorner.model;

import java.util.Optional;

public class OrderRequest {
    private final String productId;
    private final Optional<String[]> toppings;

    public OrderRequest(String productId, Optional<String[]> toppings) {
        this.productId = productId;
        this.toppings = toppings;
    }

    public String getProductId() {
        return productId;
    }

    public Optional<String[]> getToppings() {
        return toppings;
    }
}

