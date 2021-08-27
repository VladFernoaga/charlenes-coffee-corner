package com.fernoaga.charlenescoffecorner.model;

public class ReceiptEntry {
    private final String productName;
    private final double price;

    public ReceiptEntry(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }
}
