package com.fernoaga.charlenescoffecorner.service;

import com.fernoaga.charlenescoffecorner.model.Product;
import com.fernoaga.charlenescoffecorner.model.Topping;

import java.util.List;
import java.util.Optional;

public interface ToppingService {

    List<Topping> getToppingForProduct(Optional<List<String>> requestedToppings, Product product);
}
