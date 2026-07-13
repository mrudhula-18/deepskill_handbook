package com.dn5.week2.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order entity demonstrating Lombok-generated boilerplate
 * (getters, setters, equals/hashCode, toString, builder, constructors).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String customerName;
    private double amount;
    private String status;
}
