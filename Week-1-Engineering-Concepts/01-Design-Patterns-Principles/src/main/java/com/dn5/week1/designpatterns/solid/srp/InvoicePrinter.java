package com.dn5.week1.designpatterns.solid.srp;

/**
 * FIX: Sole responsibility is presenting an invoice.
 */
public class InvoicePrinter {

    public String format(GoodInvoice invoice) {
        return "Invoice for " + invoice.getCustomerName() + ": total = " + invoice.calculateTotal();
    }

    public void printInvoice(GoodInvoice invoice) {
        System.out.println(format(invoice));
    }
}
