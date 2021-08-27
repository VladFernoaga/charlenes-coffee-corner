package com.fernoaga.charlenescoffecorner.model;

import java.util.List;
import java.util.Optional;

public class OrderRequest {
    private final String productId;
    private final Optional<List<String>> toppings;

    public OrderRequest(String productId, Optional<List<String>> toppings) {
        this.productId = productId;
        this.toppings = toppings;
    }

    public String getProductId() {
        return productId;
    }

    public Optional<List<String>> getToppings() {
        return toppings;
    }
}

