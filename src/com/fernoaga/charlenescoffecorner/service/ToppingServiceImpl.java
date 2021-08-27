package com.fernoaga.charlenescoffecorner.service;

import com.fernoaga.charlenescoffecorner.model.Product;
import com.fernoaga.charlenescoffecorner.model.Topping;

import java.util.*;

public class ToppingServiceImpl implements ToppingService {

    public static final ToppingServiceImpl INSTANCE = new ToppingServiceImpl();

    private ToppingServiceImpl() {
    }

    public List<Topping> getToppingForProduct(Optional<List<String>> requestedToppings, Product product) {
        var availableToppings = product.getAvailableToppings()
                .orElse(Collections.EMPTY_LIST);

        var requiredToppings = new ArrayList<Topping>();
        if (requestedToppings.isPresent()) {
            requestedToppings.get().forEach(toppingId ->
                    requiredToppings.add(this.findToppingById(availableToppings, toppingId))
            );
        }
        return requiredToppings;
    }

    private Topping findToppingById(List<Topping> toppings, String toppingId) {
        return toppings.stream().filter(t -> t.getId().equals(toppingId)).findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Topping " + toppingId + " not available "));
    }
}
