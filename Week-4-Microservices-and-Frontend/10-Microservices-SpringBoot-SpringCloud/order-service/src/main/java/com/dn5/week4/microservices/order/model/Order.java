package com.dn5.week4.microservices.order.model;

/**
 * An order, enriched with product details fetched (via Feign) from
 * product-service at the time the order was created.
 */
public class Order {

    private Long id;
    private Long productId;
    private int quantity;
    private String productName;
    private double unitPrice;
    private double totalPrice;

    public Order() {
    }

    public Order(Long id, Long productId, int quantity, String productName, double unitPrice) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
