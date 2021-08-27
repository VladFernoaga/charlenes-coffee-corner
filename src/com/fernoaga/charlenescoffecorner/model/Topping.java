package com.fernoaga.charlenescoffecorner.model;

public class Topping {

    private final String id;
    private final String name;
    private final double price;

    public Topping(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
