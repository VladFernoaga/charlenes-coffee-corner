package com.fernoaga.charlenescoffecorner.service.discount;

import com.fernoaga.charlenescoffecorner.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CouponDiscount implements DiscountStrategy {
    public static final CouponDiscount INSTANCE = new CouponDiscount();

    private CouponDiscount() {
    }

    @Override
    public List<ProcessedOrder> apply(List<ProcessedOrder> orders, Optional<Coupon> coupon) {
        if (coupon.isEmpty()) {
            return orders;
        }
        if (coupon.get().getStampAmount() != 5) {
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
}
