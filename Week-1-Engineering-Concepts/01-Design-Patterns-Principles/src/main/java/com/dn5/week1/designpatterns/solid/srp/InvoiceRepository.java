package com.dn5.week1.designpatterns.solid.srp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FIX: Sole responsibility is persistence. Real file I/O is simulated with an
 * in-memory list so this class is easily testable without touching disk.
 */
public class InvoiceRepository {

    private final List<String> savedInvoices = new ArrayList<>();

    public boolean save(GoodInvoice invoice) {
        String record = invoice.getCustomerName() + ":" + invoice.calculateTotal();
        savedInvoices.add(record);
        System.out.println("(simulated) Saved invoice record -> " + record);
        return true;
    }

    public List<String> findAll() {
        return Collections.unmodifiableList(savedInvoices);
    }
}
