package com.fernoaga.charlenescoffecorner.service;

import com.fernoaga.charlenescoffecorner.model.Coupon;
import com.fernoaga.charlenescoffecorner.model.OrderRequest;
import com.fernoaga.charlenescoffecorner.model.Receipt;
import com.fernoaga.charlenescoffecorner.repository.CoffeeShopProductRepository;

import java.util.Optional;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private final CoffeeShopProductRepository repository;

    public CoffeeCornerServiceImpl(CoffeeShopProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Receipt orderProducts(OrderRequest[] orderedProducts, Optional<Coupon> coupon) {
        return null;
    }
}
