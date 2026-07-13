package com.dn5.week3.jpa;

import java.math.BigDecimal;

/**
 * Interface-based projection exposing only a subset of {@link Product}
 * columns. Spring Data generates a proxy implementation at runtime that
 * reads only the projected columns from the database.
 */
public interface ProductNameOnly {

    String getName();

    BigDecimal getPrice();
}
