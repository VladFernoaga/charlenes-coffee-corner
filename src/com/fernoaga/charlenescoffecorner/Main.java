package com.fernoaga.charlenescoffecorner;

import com.fernoaga.charlenescoffecorner.model.OrderRequest;
import com.fernoaga.charlenescoffecorner.model.Receipt;
import com.fernoaga.charlenescoffecorner.repository.mock.CoffeeShopProductRepositoryMockImpl;
import com.fernoaga.charlenescoffecorner.service.CoffeeCornerService;
import com.fernoaga.charlenescoffecorner.service.CoffeeCornerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        CoffeeCornerService coffeeCornerService =
                new CoffeeCornerServiceImpl(new CoffeeShopProductRepositoryMockImpl());


        List<OrderRequest> request = Arrays.asList(new OrderRequest(CoffeeShopProductRepositoryMockImpl.COFFEE_SMALL,
                        Optional.of((Arrays.asList(CoffeeShopProductRepositoryMockImpl.TOPPING_SPECIAL_ROAST_COFFEE,
                                CoffeeShopProductRepositoryMockImpl.TOPPING_FOAMED_MILK)))),
                new OrderRequest(CoffeeShopProductRepositoryMockImpl.BACON_ROLL, Optional.empty())
        );
        Receipt receipt = coffeeCornerService.orderProducts(request, Optional.empty());
        printReceipt(receipt);
    }

    private static void printReceipt(Receipt receipt) {

        for (var r : receipt.getEntries()) {
            System.out.println(r.getProductName() + "       " + r.getPrice());
        }
        System.out.println("\n");
        System.out.println("Total price:       " + receipt.getTotalPrice());
    }
}
