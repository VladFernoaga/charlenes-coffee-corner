package com.fernoaga.charlenescoffecorner.repository;

import com.fernoaga.charlenescoffecorner.model.Product;

import java.util.List;
import java.util.Optional;

public interface CoffeeShopProductRepository {

    List<Product> getAll();

    Optional<Product> getById(String id);
}
