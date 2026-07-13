package com.dn5.week1.designpatterns.creational.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder: {@link Pizza} instances are immutable and are only assembled via
 * the fluent, nested {@link Builder}.
 */
public final class Pizza {

    private final String size;
    private final List<String> toppings;
    private final boolean cheese;

    private Pizza(Builder builder) {
        this.size = builder.size;
        this.toppings = Collections.unmodifiableList(new ArrayList<>(builder.toppings));
        this.cheese = builder.cheese;
    }

    public String getSize() {
        return size;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public boolean hasCheese() {
        return cheese;
    }

    @Override
    public String toString() {
        return "Pizza{size=" + size + ", toppings=" + toppings + ", cheese=" + cheese + "}";
    }

    public static class Builder {
        private String size = "medium";
        private final List<String> toppings = new ArrayList<>();
        private boolean cheese = false;

        public Builder size(String size) {
            this.size = size;
            return this;
        }

        public Builder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        public Builder cheese(boolean cheese) {
            this.cheese = cheese;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }
}
