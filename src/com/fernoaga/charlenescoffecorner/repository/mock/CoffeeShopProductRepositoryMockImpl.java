package com.fernoaga.charlenescoffecorner.repository.mock;

import com.fernoaga.charlenescoffecorner.model.Product;
import com.fernoaga.charlenescoffecorner.model.ProductType;
import com.fernoaga.charlenescoffecorner.model.Topping;
import com.fernoaga.charlenescoffecorner.repository.CoffeeShopProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CoffeeShopProductRepositoryMockImpl implements CoffeeShopProductRepository {


    public static final String TOPPING_EXTRA_MILK = "extra-milk";
    public static final String TOPPING_FOAMED_MILK = "foamed-milk";
    public static final String TOPPING_SPECIAL_ROAST_COFFEE = "special-roast-coffee";

    public static final String COFFEE_SMALL = "coffee-small";
    public static final String COFFEE_MEDIUM = "coffee-medium";
    public static final String COFFEE_BIG = "coffee-big";
    public static final String ORANGE_JUICE = "orange-juice";
    public static final String BACON_ROLL = "bacon-roll";

    private static final List<Topping> coffeeToppings = Arrays.asList(
            new Topping(TOPPING_EXTRA_MILK, "Extra Milk", 0.3),
            new Topping(TOPPING_FOAMED_MILK, "Foamed Milk", 0.5),
            new Topping(TOPPING_SPECIAL_ROAST_COFFEE, "Special Roast Coffee", 0.9)
    );

    private static final List<Product> products = Arrays.asList(
            new Product(COFFEE_SMALL, "Coffee Small", 2.5, ProductType.BEVERAGE, Optional.of(coffeeToppings)),
            new Product(COFFEE_MEDIUM, "Coffee Medium", 3, ProductType.BEVERAGE, Optional.of(coffeeToppings)),
            new Product(COFFEE_BIG, "Coffee Big", 3.5, ProductType.BEVERAGE, Optional.of(coffeeToppings)),
            new Product(ORANGE_JUICE, "Orange Juice", 3.95, ProductType.BEVERAGE, Optional.empty()),
            new Product(BACON_ROLL, "Bacon Roll", 4.5, ProductType.SNACKS, Optional.empty())
    );

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public Optional<Product> getById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

}
