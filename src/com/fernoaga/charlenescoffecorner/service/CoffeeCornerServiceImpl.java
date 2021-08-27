package com.fernoaga.charlenescoffecorner.service;

import com.fernoaga.charlenescoffecorner.model.*;
import com.fernoaga.charlenescoffecorner.repository.CoffeeShopProductRepository;

import java.util.*;
import java.util.stream.Collectors;

public class CoffeeCornerServiceImpl implements CoffeeCornerService {

    private final CoffeeShopProductRepository repository;

    public CoffeeCornerServiceImpl(CoffeeShopProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Receipt orderProducts(List<OrderRequest> orderedProducts, Optional<Coupon> coupon) {

        var processedOrders =
                applyDiscountRules(
                        orderedProducts.stream().map(this::processOrderRequest).collect(Collectors.toList()),
                        coupon);

        return toReceipt(processedOrders);
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

    private ProcessedOrder processOrderRequest(OrderRequest orderRequest) {
        var requestedProduct = this.repository.getById(orderRequest.getProductId()).orElseThrow();
        var requestedToppings = this.getToppingForProduct(orderRequest, requestedProduct);
        return new ProcessedOrder(requestedProduct, requestedToppings);
    }

    private List<Topping> getToppingForProduct(OrderRequest orderRequest, Product product) {
        var availableToppings = product.getAvailableToppings()
                .orElse(Collections.EMPTY_LIST);

        var requiredToppings = new ArrayList<Topping>();
        if (orderRequest.getToppings().isPresent()) {
            orderRequest.getToppings().get().forEach(toppingId ->
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

    private List<ProcessedOrder> applyDiscountRules(List<ProcessedOrder> orders, Optional<Coupon> coupon) {
        var discounted = applyExtrasFreeDiscountRule(orders);
        if (coupon.isPresent()) {
            var discounted2 = applyCouponDiscount(orders, coupon.get());
            return discounted2;
        }
        return discounted;
    }

    private List<ProcessedOrder> applyExtrasFreeDiscountRule(List<ProcessedOrder> orders) {
        if (!hasSnackAndBeverage(orders)) {
            return orders;
        }

        var processedOrders = new ArrayList<ProcessedOrder>();
        for (var order : orders) {
            if (this.isBeverageWithToppings(order)) {
                processedOrders.add(applyToppingDiscount(order));
            } else {
                processedOrders.add(order);
            }
        }
        return processedOrders;
    }

    private ProcessedOrder applyToppingDiscount(ProcessedOrder order) {
        var minPriceIndex = indexOfMinPrice(order.toppings);
        var newToppings = new ArrayList<Topping>();
        for (var i = 0; i < order.toppings.size(); i++) {
            var t = order.toppings.get(i);
            if (i == minPriceIndex) {
                newToppings.add(new Topping(t.getId(), t.getName(), 0.0));
            } else {
                newToppings.add(t);
            }
        }
        return new ProcessedOrder(order.product, newToppings);
    }

    private int indexOfMinPrice(List<Topping> toppings) {
        double minPrice = Double.MAX_VALUE;
        var minPriceIndex = -1;

        for (var i = 0; i < toppings.size(); i++) {
            var t = toppings.get(i);
            if (t.getPrice() < minPrice) {
                minPrice = t.getPrice();
                minPriceIndex = i;
            }
        }
        return minPriceIndex;
    }

    private boolean isBeverageWithToppings(ProcessedOrder order) {
        return order.product.getProductType().equals(ProductType.BEVERAGE) && !order.toppings.isEmpty();
    }

    private boolean hasSnackAndBeverage(List<ProcessedOrder> orders) {
        return orders.stream().filter(order -> order.product.getProductType().equals(ProductType.SNACKS) ||
                order.product.getProductType().equals(ProductType.BEVERAGE)).count() >= 2;
    }

    private List<ProcessedOrder> applyCouponDiscount(List<ProcessedOrder> orders, Coupon coupon) {
        if (coupon.getStampAmount() != 5) {
            return orders;
        }
        var processedOrders = new ArrayList<ProcessedOrder>();
        for (var order : orders) {
            if (order.product.getProductType().equals(ProductType.BEVERAGE)) {
                processedOrders.add(createFreeProduct(order));
            } else {
                processedOrders.add(order);
            }
        }
        return processedOrders;
    }

    private ProcessedOrder createFreeProduct(ProcessedOrder order) {
        return
                new ProcessedOrder(new Product(order.product.getId(), order.product.getName(),
                        0.0, order.product.getProductType(), order.product.getAvailableToppings()),
                        order.toppings.stream().map(t -> new Topping(t.getId(), t.getName(), 0.0)).collect(Collectors.toList())
                );
    }

    private class ProcessedOrder {
        final Product product;
        final List<Topping> toppings;

        public ProcessedOrder(Product product, List<Topping> toppings) {
            this.product = product;
            this.toppings = toppings;
        }
    }
}
