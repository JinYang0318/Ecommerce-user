package com.example.Ecommerce_product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(
        Integer productId,
        @NotBlank(message = "Product Name is required") String productName,
        @NotNull(message = "Product Price is required") BigDecimal productPrice,
        @NotNull(message = "Product Quantity is required") Integer productQuantity
) {
}