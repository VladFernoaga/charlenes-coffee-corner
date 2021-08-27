package com.fernoaga.charlenescoffecorner.service;

import com.fernoaga.charlenescoffecorner.model.Coupon;
import com.fernoaga.charlenescoffecorner.model.OrderRequest;
import com.fernoaga.charlenescoffecorner.model.Receipt;
import com.fernoaga.charlenescoffecorner.repository.CoffeeShopProductRepository;
import com.fernoaga.charlenescoffecorner.repository.mock.CoffeeShopProductRepositoryMockImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class CoffeeCornerServiceTest {

    private CoffeeShopProductRepository repository = new CoffeeShopProductRepositoryMockImpl();
    private CoffeeCornerService coffeeCornerService = new CoffeeCornerServiceImpl(repository);


    @Test
    public void order_NoTopping_Success() {
        OrderRequest[] request = {new OrderRequest(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL, Optional.empty()),
                new OrderRequest(CoffeeShopProductRepositoryMockImpl.ORANGE_JUICE, Optional.empty())
        };
        Receipt result = coffeeCornerService.orderProducts(request, Optional.empty());

        Assertions.assertEquals(6.45, result.getTotalPrice());
        Assertions.assertEquals(2, result.getEntries().length);
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL).orElseThrow().getName(), result.getEntries()[0].getProductName());
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.ORANGE_JUICE).orElseThrow().getName(), result.getEntries()[1].getProductName());
    }

    @Test
    public void order_WithTwoTopping_Success() {
        OrderRequest[] request = {new OrderRequest(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL,
                Optional.of(new String[]{CoffeeShopProductRepositoryMockImpl.TOPPING_SPECIAL_ROAST_COFFEE,
                        CoffeeShopProductRepositoryMockImpl.TOPPING_FOAMED_MILK}))
        };
        Receipt result = coffeeCornerService.orderProducts(request, Optional.empty());

        Assertions.assertEquals(3.9, result.getTotalPrice());
        Assertions.assertEquals(1, result.getEntries().length);
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL).orElseThrow().getName(), result.getEntries()[0].getProductName());
    }

    @Test
    public void order_WithTwoToppingAndSnack_OnTopicForFree() {
        OrderRequest[] request = {new OrderRequest(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL,
                Optional.of(new String[]{CoffeeShopProductRepositoryMockImpl.TOPPING_SPECIAL_ROAST_COFFEE,
                        CoffeeShopProductRepositoryMockImpl.TOPPING_FOAMED_MILK})),
                new OrderRequest(CoffeeShopProductRepositoryMockImpl.BACON_ROLL, Optional.empty())
        };
        Receipt result = coffeeCornerService.orderProducts(request, Optional.empty());

        // the cheapest  Topping os free
        Assertions.assertEquals(2.5 + 0.9 + 4.5, result.getTotalPrice());
        Assertions.assertEquals(2, result.getEntries().length);
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL).orElseThrow().getName(), result.getEntries()[0].getProductName());
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.BACON_ROLL).orElseThrow().getName(), result.getEntries()[0].getProductName());
    }

    @Test
    public void order_WithValidCoupon_CoffeeIsFree() {
        OrderRequest[] request = {new OrderRequest(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL,
                Optional.empty()),
                new OrderRequest(CoffeeShopProductRepositoryMockImpl.BACON_ROLL, Optional.empty())
        };
        Receipt result = coffeeCornerService.orderProducts(request, Optional.of(new Coupon(5)));

        // the cheapest  Topping os free
        Assertions.assertEquals(4.5, result.getTotalPrice());
        Assertions.assertEquals(2, result.getEntries().length);
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL).orElseThrow().getName(), result.getEntries()[0].getProductName());
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.BACON_ROLL).orElseThrow().getName(), result.getEntries()[0].getProductName());
    }

    @Test
    public void order_WithInValidCoupon_CoffeeNotFree() {
        OrderRequest[] request = {new OrderRequest(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL,
                Optional.empty()),
                new OrderRequest(CoffeeShopProductRepositoryMockImpl.BACON_ROLL, Optional.empty())
        };
        Receipt result = coffeeCornerService.orderProducts(request, Optional.of(new Coupon(3)));

        // the cheapest  Topping os free
        Assertions.assertEquals(2.5 + 4.5, result.getTotalPrice());
        Assertions.assertEquals(2, result.getEntries().length);
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL).orElseThrow().getName(), result.getEntries()[0].getProductName());
        Assertions.assertEquals(repository.getById(CoffeeShopProductRepositoryMockImpl.BACON_ROLL).orElseThrow().getName(), result.getEntries()[0].getProductName());
    }

    @Test
    public void order_InvalidProductId_NotSuchProductException() {
        OrderRequest[] request = {new OrderRequest("custom-coffee",
                Optional.empty())
        };
        Assertions.assertThrows(RuntimeException.class, () -> coffeeCornerService.orderProducts(request,
                Optional.empty()));
    }
}

