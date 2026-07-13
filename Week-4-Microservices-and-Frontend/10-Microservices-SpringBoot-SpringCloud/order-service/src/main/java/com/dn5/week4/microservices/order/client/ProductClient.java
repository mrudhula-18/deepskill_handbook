package com.dn5.week4.microservices.order.client;

import com.dn5.week4.microservices.order.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Declarative HTTP client for product-service. The "name" is resolved
 * through Eureka by Spring Cloud LoadBalancer -- no hardcoded host/port.
 */
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDto getProduct(@PathVariable("id") Long id);
}
