package com.fernoaga.charlenescoffecorner.service.discount;

import com.fernoaga.charlenescoffecorner.model.Coupon;
import com.fernoaga.charlenescoffecorner.model.ProcessedOrder;

import java.util.List;
import java.util.Optional;

public interface DiscountStrategy {

    List<ProcessedOrder> apply(List<ProcessedOrder> orders, Optional<Coupon> coupon);
}
