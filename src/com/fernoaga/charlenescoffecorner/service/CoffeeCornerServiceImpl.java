package com.fernoaga.charlenescoffecorner.service;

import com.fernoaga.charlenescoffecorner.model.*;
import com.fernoaga.charlenescoffecorner.repository.CoffeeShopProductRepository;
import com.fernoaga.charlenescoffecorner.service.discount.BeverageAndSnackDiscount;
import com.fernoaga.charlenescoffecorner.service.discount.CouponDiscount;
import com.fernoaga.charlenescoffecorner.service.discount.DiscountStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private final CoffeeShopProductRepository repository;
    private final ToppingService toppingService = ToppingServiceImpl.INSTANCE;

    private final List<DiscountStrategy> discountStrategies = Arrays.asList(BeverageAndSnackDiscount.INSTANCE,
            CouponDiscount.INSTANCE);

    public CoffeeCornerServiceImpl(CoffeeShopProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Receipt orderProducts(List<OrderRequest> orderedProducts, Optional<Coupon> coupon) {
        var processedOrders = orderedProducts.stream().map(this::toOrderRequest).collect(Collectors.toList());
        for (var strategy : discountStrategies) {
            processedOrders = strategy.apply(processedOrders, coupon);
        }
        return toReceipt(processedOrders);
    }

    private ProcessedOrder toOrderRequest(OrderRequest orderRequest) {
        var requestedProduct = this.repository.getById(orderRequest.getProductId()).orElseThrow();
        var requestedToppings = toppingService.getToppingForProduct(orderRequest.getToppings(), requestedProduct);
        return new ProcessedOrder(requestedProduct, requestedToppings);
    }

    private Receipt toReceipt(List<ProcessedOrder> orderedProducts) {

        var receiptEntries = orderedProducts.stream()
                .map(order ->
                        new ReceiptEntry(order.product.getName(),
                                order.product.getPrice() + order.toppings.stream()
                                        .map(t -> t.getPrice())
                                        .reduce(0.0,
                                                Double::sum)
                        ))
                .collect(Collectors.toList());
        return new Receipt(receiptEntries.toArray(new ReceiptEntry[0]),
                receiptEntries.stream().map(r -> r.getPrice()).reduce(0.0,
                        Double::sum));
    }
}
