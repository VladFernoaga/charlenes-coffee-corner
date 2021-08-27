package com.fernoaga.charlenescoffecorner.service.discount;

import com.fernoaga.charlenescoffecorner.model.Coupon;
import com.fernoaga.charlenescoffecorner.model.ProcessedOrder;
import com.fernoaga.charlenescoffecorner.model.ProductType;
import com.fernoaga.charlenescoffecorner.model.Topping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BeverageAndSnackDiscount implements DiscountStrategy {
    public static final BeverageAndSnackDiscount INSTANCE = new BeverageAndSnackDiscount();

    private BeverageAndSnackDiscount() {
    }

    @Override
    public List<ProcessedOrder> apply(List<ProcessedOrder> orders, Optional<Coupon> coupon) {
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
}
