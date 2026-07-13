package com.dn5.week1.designpatterns.solid.srp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoodInvoiceTest {

    @Test
    void calculatesTotalCorrectly() {
        GoodInvoice invoice = new GoodInvoice("Alice", 10.0, 3);
        assertEquals(30.0, invoice.calculateTotal(), 0.0001);
    }

    @Test
    void printerFormatsIndependentlyOfInvoice() {
        GoodInvoice invoice = new GoodInvoice("Alice", 10.0, 3);
        InvoicePrinter printer = new InvoicePrinter();
        String formatted = printer.format(invoice);
        assertTrue(formatted.contains("Alice"));
        assertTrue(formatted.contains("30.0"));
    }

    @Test
    void repositoryCanSaveAndListIndependentlyOfPrinter() {
        GoodInvoice invoice = new GoodInvoice("Bob", 5.0, 2);
        InvoiceRepository repository = new InvoiceRepository();

        boolean saved = repository.save(invoice);

        assertTrue(saved);
        List<String> all = repository.findAll();
        assertEquals(1, all.size());
        assertTrue(all.get(0).contains("Bob"));
    }
}
