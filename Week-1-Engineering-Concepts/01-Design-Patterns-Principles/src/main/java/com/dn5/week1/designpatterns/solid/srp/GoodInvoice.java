package com.dn5.week1.designpatterns.solid.srp;

/**
 * FIX: This class has exactly one responsibility - holding invoice data and
 * calculating the total. Printing and saving are delegated to their own classes.
 */
public class GoodInvoice {

    private final String customerName;
    private final double unitPrice;
    private final int quantity;

    public GoodInvoice(String customerName, double unitPrice, int quantity) {
        this.customerName = customerName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double calculateTotal() {
        return unitPrice * quantity;
    }
}
