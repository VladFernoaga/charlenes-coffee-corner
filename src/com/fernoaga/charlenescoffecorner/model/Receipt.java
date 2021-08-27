package com.fernoaga.charlenescoffecorner.model;

public class Receipt {
    private final ReceiptEntry[] entries;
    private final double totalPrice;

    public Receipt(ReceiptEntry[] entries, double totalPrice) {
        this.entries = entries;
        this.totalPrice = totalPrice;
    }

    public ReceiptEntry[] getEntries() {
        return entries;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

