package com.fernoaga.charlenescoffecorner.service;


import com.fernoaga.charlenescoffecorner.model.Coupon;
import com.fernoaga.charlenescoffecorner.model.OrderRequest;
import com.fernoaga.charlenescoffecorner.model.Receipt;

import java.util.Optional;

public interface CoffeeCornerService {
    /**
     * Creates a receipt based on the orderedProducts,
     * the request may contain also a coupon that is used to apply discounts
     */
    Receipt orderProducts(OrderRequest[] orderedProducts, Optional<Coupon> coupon);
}
