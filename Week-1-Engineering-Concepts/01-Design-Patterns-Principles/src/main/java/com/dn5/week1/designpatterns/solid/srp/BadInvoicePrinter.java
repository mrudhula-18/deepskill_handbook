package com.dn5.week1.designpatterns.solid.srp;

/**
 * ANTIPATTERN: Violates the Single Responsibility Principle.
 * This single class is responsible for THREE unrelated things:
 * 1. Business calculation (calculateTotal)
 * 2. Presentation (printInvoice)
 * 3. Persistence (saveToFile)
 * <p>
 * Any change to how we print, how we save, or how we calculate forces this
 * one class to change - multiple reasons to change means multiple responsibilities.
 */
public class BadInvoicePrinter {

    private final String customerName;
    private final double unitPrice;
    private final int quantity;

    public BadInvoicePrinter(String customerName, double unitPrice, int quantity) {
        this.customerName = customerName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /** Responsibility #1: business calculation. */
    public double calculateTotal() {
        return unitPrice * quantity;
    }

    /** Responsibility #2: presentation/formatting. */
    public void printInvoice() {
        System.out.println("Invoice for " + customerName + ": total = " + calculateTotal());
    }

    /** Responsibility #3: persistence. */
    public boolean saveToFile() {
        System.out.println("(simulated) Saving invoice for " + customerName + " to disk...");
        return true;
    }
}
